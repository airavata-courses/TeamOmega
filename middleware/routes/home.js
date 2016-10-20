var express = require('express');
var router = express.Router();
var path = require('path');
var fetch = require('node-fetch');

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

	/*var insert_data = {
	  "username" : req.sessionID,
	  "timestamp" : req.sessionID,
	  "description" : req.sessionID
	}

	db.insertDB(insert_data, function(res){
	  console.log("response received..."+res)
	});*/
	

	var curr_room = req.body.room;
	io.to(curr_room).emit("Request sent to Data Ingestor");
	io.to(curr_room).emit('status',0);
	
	if (req.body.type == 0){
		var suff = "get_loc";
	}
	else{
		var suff = "get_url";
	}

	fetch('http://0.0.0.0:4000/'+suff,{method: "POST",  headers: {
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

//implement JWT methods here
	
	console.log("coming to process response..");

	var room = req.body.room;
	var username = room.split('-')[0];
	var sessionNumber = room.split('-')[1];

	console.log("room: ", room);
	console.log("username: ", username);
	console.log("sessionNumber: ", sessionNumber);
	console.log("data: ", typeof(req.body.final_url));

	io.to(room).emit("message", req.body.final_url);

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
