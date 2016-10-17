var express = require('express');
var router = express.Router();
var path = require('path');
var fetch = require('node-fetch');
var server = require('../bin/www');

var ensureLoggedIn = require('connect-ensure-login').ensureLoggedIn();


var io = require('socket.io')(server);

/* Initial index page for weather report. */
router.get('/', renderIndexPage);

/* submit date and get response from the server.. */
router.post('/submit', submitDate);

/* this submits the final location */
router.post('/submit_loc', submitLOC);


io.on('connection', function(socket){
  console.log('a user connected');
  socket.emit('comments',"Your socket message from server..");
});


//socket.emit('comments',"Your socket message from server..from outside the io.on connection");





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
	var cookies = req.cookies;
	console.log("Cookies------------------->")
	console.log(cookies);
 	
	io.sockets.emit('comments',"abcde");



	fetch('http://52.43.210.8:4000/get_loc',{method: "POST",  headers: {
	'Accept': 'application/json',
	'Content-Type': 'application/json'
	},
		body: JSON.stringify({date: req.body.date, sessionID: req.sessionID, requestID: req.id})

	})
	.then(function(res) {
    	return res.text();

	}).then(function(body) {
		console.log("Received response from data ingestor");
		res.send(body);    
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

	var cookies = req.cookies.email;
	console.log(cookies);
	//console.log(session)

	fetch('http://52.43.210.8:4000/get_url',{method: "POST",  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
	  },
	   body: JSON.stringify({loc:req.body.loc , timest:req.body.timest, sessionID: req.sessionID, requestID: req.id})

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


module.exports = router;
