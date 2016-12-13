var express = require('express');
var router = express.Router();
var path = require('path');
var fetch = require('node-fetch');
var db = require("./dboperations");
var formidable = require('formidable');
var util = require('util');
var fs = require('fs');


var ensureLoggedIn = require('connect-ensure-login').ensureLoggedIn();


var amqp = require('amqplib/callback_api');


var http = require('http');

var io;		//this variable to get the socket object from the module.exports at bottom of this code..

/* Initial index page for weather report. */
router.get('/', renderIndexPage);

/* submit date and get response from the server.. */
router.post('/submit', submitDate);

router.route('/proc1/:room/:req_no').get(emitstatus);

router.post('/final', processGif);

router.route('/Gif_Files/:fname').get(renderImages);

var dataingestor_ch;

var getIP = function(callback){

	http.get({'host': 'api.ipify.org', 'port': 80, 'path': '/'}, function(resp) {
		console.log("coming here....");
	  return resp.on('data', function(ip) {
	    console.log("My public IP address is: " + ip);
	    callback(ip.toString());
	  });
	});

}

var new_ip;
var emit_q;

getIP(function(ip_add){
	new_ip = ip_add;

	console.log("inside callback....", ip_add);



	amqp.connect('amqp://'+ip_add, function(err, conn) {


	 conn.createChannel(function(err, ch) {
	    var q = 'dataIngestor';

	    ch.assertQueue(q, {durable: true});

	    dataingestor_ch = ch;

	});
	conn.createChannel(function(err, ch) {
    var ex = 'status';

    ch.assertExchange(ex, 'fanout', {durable: false});
		emit_q = ch;
  });


	  conn.createChannel(function(err, ch) {


			ch.assertExchange('status', 'fanout', {durable: false});

			ch.assertQueue('', {exclusive: true}, function(err, q) {

				console.log(" [*] Waiting for messages in %s. To exit press CTRL+C", q.queue);
				ch.bindQueue(q.queue, 'status', '');
			  ch.consume(q.queue, function(msg) {
						msg = JSON.parse(msg.content)
						console.log(" [x] Received: ", msg.msg);
						var curr_room = msg.room;
						var status_msg = msg.msg;


						io.to(curr_room).emit('message', status_msg);
						io.to(curr_room).emit('status',1);

					}, {noAck: true});
  		});
	  });


	  conn.createChannel(function(err, ch) {
	    var q = 'response';
	    //ch.assertQueue(q, {durable: false});
	  	ch.assertQueue(q, {durable: true});
	  	console.log(" [*] Waiting for messages in %s. To exit press CTRL+C", q);
		ch.consume(q, function(msg) {
			msg = JSON.parse(msg.content)
			// console.log(" [x] Received: ", msg);
			var curr_room = msg.room;
			var status_msg = msg.msg;

			var type = msg.type;

			io.to(curr_room).emit('message', status_msg);
			io.to(curr_room).emit('status',1);


			if(type == 1){

				delete msg.msg;
				delete msg.room;
				delete msg.type;

				io.to(curr_room).emit('locations',JSON.stringify(msg));

			}
			else if(type == 2){

				var data = JSON.stringify(msg);

				sendToRabbit(data, "stormDetection");
			}
			else if(type == 3){

				var data = JSON.stringify(msg);

				sendToRabbit(data, "stormClustering");
			}
			else if(type == 4){
				io.to(curr_room).emit('icon',msg["icon"]);

			}

		}, {noAck: true});


	  });
	});




});








function sendToRabbit(data, q){


		    // Note: on Node 6 Buffer.from(msg) should be used
		dataingestor_ch.sendToQueue(q, new Buffer(data), {persistent:true});
		console.log(" [x] Sent data");


	}



/* Initial index page for weather report. */
function renderIndexPage(req, res, next) {

	console.log("coming here",req.session.isAuthenticated);
	if(req.session.isAuthenticated){
		var index_page = path.join(__dirname, '../src/www/index.html');

		res.sendFile(index_page);

	}
	else{
		res.redirect('/');
	}

}

/* submit date and get response from the server.. */
function submitDate(req, res, next) {


	console.log("here-------------------------------------------------------", new_ip);

	var curr_room = req.body.room;


	io.to(curr_room).emit("Request sent to Data Ingestor");
	io.to(curr_room).emit('status',0);


	var data = JSON.stringify({room:curr_room, date: req.body.date ,timest:req.body.timest,
								type : req.body.type, req_no : req.body.req_no})
	console.log(data,"---------------------------------------------");
	sendToRabbit(data, "dataIngestor");

}
function emitstatus(req, res){
	// console.log(req.params.room);
	console.log("ForecastTrigger completed process 1/2 of request # "+req.params.req_no);
	// var data = JSON.stringify({msg:"ForecastTrigger completed process 1/2 of request # "+req.params.req_no,
	//  status:1, room:req.params.room })
	 io.to(req.params.room).emit('message', "ForecastTrigger completed processing 1/2 of request # "+req.params.req_no);
	 io.to(req.params.room).emit('status',1);
	//  emit_q.publish('status', '', new Buffer(data));
	 	res.send("Success");
}



function renderImages(req, res){
	console.log(req.params.fname);
	iname = path.join(__dirname, '../Gif_Files/') + req.params.fname;
	res.sendFile(iname);
}

function processGif(req, res, next){

	console.log("In processGif");
	console.log(req.form)
	var room,req_no;
	var filepath = [];
	var form = new formidable.IncomingForm();
    form.parse(req, function(err, fields, files) {
			req_no = fields.req_no;
			room = fields.room;
      console.log(req_no,room);
    });

form.on('fileBegin', function(name, file) {

		file.path = path.join(__dirname, '../Gif_Files/') + file.name;
		console.log(file.path.split("Gif_Files/"))
		filepath.push(file.path);
});
form.on('end', function(fields, files) {
	var fnames = [];
	for (var i = 0; i < filepath.length; i++) {
			var tmp_fname = filepath[i];
			var splitf = tmp_fname.split("Gif_Files/");
			new_f = splitf[0]+"Gif_Files/"+req_no+splitf[1];
			fnames.push(req_no+splitf[1]);
			fs.rename(tmp_fname,new_f);

	}
	var data = JSON.stringify({msg:"Forecast Trigger completed processing 2/2 of request # "+req_no, status:1, room:room });
	emit_q.publish('status', '', new Buffer(data));
	io.to(room).emit('image',fnames);

});
		res.send("Success");
}






//accepting the io object
module.exports = function(io1){
	io = io1;
	io.sockets.on('connection', function(socket){
	  console.log('a user connected in export module');

	  socket.on('room', function(room) {
        socket.join(room);
        io.to(room).emit('room',room);
    	});

	});

	return router;
};
