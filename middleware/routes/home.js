var express = require('express');
var router = express.Router();
var path = require('path');
var fetch = require('node-fetch');
var db = require("./dboperations");

var ensureLoggedIn = require('connect-ensure-login').ensureLoggedIn();


var io;		//this variable to get the socket object from the module.exports at bottom of this code..


/* Initial index page for weather report. */
router.get('/', renderIndexPage);

/* submit date and get response from the server.. */
router.post('/submit', submitDate);

//posting from a microservice

router.post('/service-response', process_response);

/* Initial index page for weather report. */
function renderIndexPage(req, res, next) {


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




	var curr_room = req.body.room;


	io.to(curr_room).emit("Request sent to Data Ingestor");
	io.to(curr_room).emit('status',0);
	
	if (req.body.type == 0){
		var suff = "get_loc";
		var d = "get location"
	}
	else{
		var suff = "get_url";
		var d = "get url"
	}


	//Inserting data to registry
	var desc = "Request sent to Data Ingestor to "+ d;  
	

	var insert_data = {
	  "username" : req.cookies.email,
	  "timestamp" : new Date().getTime(),
	  "description" : desc
	}

	
	//log the process
	db.insertDB(insert_data, function(res){
	  console.log("response received..."+res)
	});
	//registry part over


	fetch('http://localhost:4000/'+suff,{method: "POST",  headers: {
	'Accept': 'application/json',
	'Content-Type': 'application/json'
	},
		body: JSON.stringify({room:curr_room, date: req.body.date ,timest:req.body.timest})
	})
	.then(function(res) {
		return res.text();
	}).then(function(body) {

		var body1= JSON.parse(body);
		console.log("Received response from data ingestor", body1["msg"]);
		io.to(curr_room).emit('message',body1["msg"]);
		res.send(body);    
	}).catch(function(error) {
	  // Treat network errors without responses as 500s.
	  // const status = error.response ? error.response.status : 500
	  // if (status === 404) {
	  //   // Not found handler.
	  // } else {
	  //   // Other errors.
	  // }

	  	console.log("error response from data ingestor: ", res.status);
		io.to(curr_room).emit('message',"Error response from Data Ingestor....");
		io.to(curr_room).emit('status',-1);

	});
}


//new function to accept the response from the microservice

function process_response(req , res){



		//var body1= JSON.parse(req.body);
		var room = req.body.room;
		var type = req.body.type;
		// console.log(room);
		var msg = req.body["msg"];

		console.log(room,type,msg);
		io.to(room).emit('status',1);
		io.to(room).emit('message',msg);

		// console.log("BODY>>>>>>>>>>>....", req.body);

		// // console.log("BODY>TYPE type....", typeof(type),type);

		//if(type == 4){
		// 	console.log("Should not be here....", type);
		// 	// io.to(room).emit('icon',req.body["icon"]);
		// 	//next();
		
		// }

		// //implement JWT methods here
		if(type == 2){
			console.log("inside type 2");
			var suff = "5678/get_kml";
			var service = "storm detection";			//for strom detector
		}
		else {
		 	var suff = "5789/get_kml"
		 	var service = "storm clustering";			//for storm clustering
		 }

		
		// //passing first response to next request
	    fetch('http://localhost:'+suff,{method: "POST",  headers: {
		'Accept': 'application/json',
		'Content-Type': 'application/json'
		},
			body: JSON.stringify(req.body)
		})
		.then(function(res) {
		return res.text();
		})
		.then(function(body) {

			var body2 = JSON.parse(body);
			console.log("Received response...", body2["msg"]);
			io.to(room).emit('message',body2["msg"]);
			//res.send(body);


		}).catch(function(error) {
		  	
		  	console.log("error response from: ", error);
			io.to(room).emit('message',"Error response from ", service);
			io.to(room).emit('status',-1);

		});

		
		// //res.sendStatus(200);

		res.sendStatus(200);
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
