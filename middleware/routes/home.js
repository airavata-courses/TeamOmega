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

      sendFinalUrl(body,req.sessionID, function(object){

        //console.log("response from storm detector"+kml);

        //sendFinalUrl(kml);
        console.log(object);
        response.send(JSON.stringify({forecast: object}));
      });

        
    });



});



//function for getting file from storm detector
var sendFinalUrl = function(req,sess, callback){  

    console.log("In function send Final");
    var file_path = "/home/suraj/data/file"+String(sess)+".zip";    
    var file = fs.createWriteStream(file_path);
    var request = http.get('http://localhost:5678/kmlfile', function(response) {
      response.pipe(file);
      console.log("Calling function send zip forward");
      
      sendZipForward(file_path, function(item){
          console.log(item);
          callback(item);

      });

      
    });
/*
    fetch('http://52.43.210.8:5678/kmlfile',{method: "GET",  headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
     body: JSON.stringify(req)

   })
      .then(function(res) {
          console.log(res);
          return res.text();

      }).then(function(body) {
        console.log("Received url from storm detector");
        //console.log(body);

        callback(body);
          
      });

*/
}


//function for getting file from storm detector
var sendZipForward = function(file_path, callback){  

    var url = 'http://localhost:5789/kml?path=' +file_path;

    var items = ["cloudy","hail", "lightning",  "night","partlycloudy", "pouring", "rainy", "snowy", "sunny"]

    var item = items[Math.floor(Math.random()*items.length)]; 

    console.log("Inside function send zip forward");
    fetch(url,{method:"get"}).then(function(res) {
          callback(item);

          return res.text();

      })


}


module.exports = router;
