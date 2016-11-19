var express = require('express');
var router = express.Router();
var path = require('path');
var fetch = require('node-fetch');
var db = require("./dboperations");

var ensureLoggedIn = require('connect-ensure-login').ensureLoggedIn();


var amqp = require('amqplib/callback_api');


var http = require('http');

var io;		//this variable to get the socket object from the module.exports at bottom of this code..

/* Initial index page for weather report. */
router.get('/', renderIndexPage);

/* submit date and get response from the server.. */
router.post('/submit', submitDate);



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

getIP(function(ip_add){

	new_ip = ip_add;

	console.log("inside callback....", ip_add);


	amqp.connect('amqp://'+ip_add, function(err, conn) {

	  conn.createChannel(function(err, ch) {
	    var q = 'status';

	    ch.assertQueue(q, {durable: true});

	    console.log(" [*] Waiting for messages in %s. To exit press CTRL+C", q);
		ch.consume(q, function(msg) {
			msg = JSON.parse(msg.content)
			console.log(" [x] Received: ", msg.msg);
			var curr_room = msg.room;
			var status_msg = msg.msg;
			

			io.to(curr_room).emit('message', status_msg);
			io.to(curr_room).emit('status',1);

		}, {noAck: true});




	  });
	});




	amqp.connect('amqp://'+ip_add, function(err, conn) {

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

		amqp.connect('amqp://'+new_ip, function(err, conn) {
		  conn.createChannel(function(err, ch) {
		    ch.assertQueue(q, {durable: true});
		    // Note: on Node 6 Buffer.from(msg) should be used
		    ch.sendToQueue(q, new Buffer(data), {persistent:true});
		    console.log(" [x] Sent data");
		  });
		});

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
