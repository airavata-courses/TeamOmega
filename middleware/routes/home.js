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

/* this submits the final location */
router.post('/submit_loc', submitLOC);

//socket.emit('comments',"Your socket message from server..from outside the io.on connection");

//console.log(io);

/* Initial index page for weather report. */
function renderIndexPage(req, res, next) {

	//var session = req.session;

	//req.session.last = "abcde";
	//req.session.save();
	//console.log(session);
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
	

	console.log("after submiting date..........................");
	var email = req.cookies.email;
	console.log("Cookies------------------->",email);
	
  		

	//io.emit('comments',"abcde");

	var curr_room = req.body.room;
	io.to(curr_room).emit("Request sent to Data Ingestor");
	io.to(curr_room).emit('status',0);
	/*io1.on('connection', function(socket){
	  console.log('a user connected');
	  socket.emit('comments',"Message IO1");
	});
*/

	fetch('http://52.43.210.8:4000/get_loc',{method: "POST",  headers: {
	'Accept': 'application/json',
	'Content-Type': 'application/json'
	},
		body: JSON.stringify({room:curr_room, date: req.body.date, sessionID: req.sessionID, requestID: req.id})

	})
	.then(function(res) {
	
		return res.text();

	}).then(function(body) {
		console.log("Received response from data ingestor");
		io.to(curr_room).emit('message',"Received response from Data Ingestor....");
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




/* submit Loacation response from the server.. */	
function submitLOC(req, res, next) {
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

	var cookies = req.cookies.email;
	console.log(cookies);
	//console.log(session)

	fetch('http://52.43.210.8:4000/get_url',{method: "POST",  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
	  },
	   body: JSON.stringify({room:curr_room ,loc:req.body.loc , timest:req.body.timest, sessionID: req.sessionID, requestID: req.id})

	 })
	    .then(function(res) {
	        return res.text();

	    }).then(function(body) {
	    	console.log("Received url from data ingestor");
	    	res.send(body);
	      /*sendFinalUrl(body,req.sessionID, function(object){

	        //console.log("response from storm detector"+kml);

	        //sendFinalUrl(kml);
	        console.log(object);
	        response.send(JSON.stringify({forecast: object}));
	      });*/

        
    });

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
