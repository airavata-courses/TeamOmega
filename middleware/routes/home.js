var express = require('express');
var passport = require('passport');
var router = express.Router();
const path = require('path');

var fetch = require('node-fetch');


var ensureLoggedIn = require('connect-ensure-login').ensureLoggedIn();

var fs = require('fs');

var url = require('url');

var http = require('http');

var db = require('./dboperations.js')

//Initial Page
/* GET home page. */
router.get('/', function(req, res, next) {
  console.log(path.join(__dirname, '../src/www/index.html'));
  res.sendFile(path.join(__dirname, '../src/www/index.html'));

});



//After Selecting Date
router.post('/submit', function(req, response, next) {
	
  var insert_data = {
      "username" : req.sessionID,
      "timestamp" : req.sessionID,
      "description" : req.sessionID
  }

  db.insertDB(insert_data, function(res){
      console.log("response received..."+res)
  });

	fetch('http://localhost:4000/get_loc',{method: "POST",  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
   body: JSON.stringify({date: req.body.date})

 })
    .then(function(res) {
        return res.text();

    }).then(function(body) {
    	console.log("Received response from data ingestor");
    	response.send(body);
        
    });



});


//After Selecting submit
router.post('/submit_loc', function(req, response, next) {
	

  var insert_data = {
      "username" : req.sessionID,
      "timestamp" : req.sessionID,
      "description" : req.sessionID
  }

  db.insertDB(insert_data, function(res){
      console.log(res)
  });


	fetch('http://localhost:4000/get_url',{method: "POST",  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
   body: JSON.stringify({loc:req.body.loc , timest:req.body.timest})

 })
    .then(function(res) {
        return res.text();

    }).then(function(body) {
    	console.log("Received url from data ingestor");
    	console.log(body);

      sendFinalUrl(body, function(kml){

        console.log("response from storm detector"+kml);

      });

    	console.log(typeof(body))
    	response.send(body);
        
    });



});



//function for getting file from storm detector
var sendFinalUrl = function(req, callback){  

    var insert_data = {
        "username" : req.sessionID,
        "timestamp" : req.sessionID,
        "description" : req.sessionID
    }

    db.insertDB(insert_data, function(res){
        console.log(res)
    });


    fetch('<<<<<<<<<<<put url here>>>>>>>>>>>>',{method: "POST",  headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
     body: JSON.stringify(req)

   })
      .then(function(res) {
          return res.text();

      }).then(function(body) {
        console.log("Received url from storm detector");
        console.log(body);

        callback(body);

        console.log(typeof(body))
        response.send(body);
          
      });


}


module.exports = router;
