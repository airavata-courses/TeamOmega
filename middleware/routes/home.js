var express = require('express');
var passport = require('passport');
var router = express.Router();
const path = require('path');

var fetch = require('node-fetch');


var ensureLoggedIn = require('connect-ensure-login').ensureLoggedIn();

var fs = require('fs');

var url = require('url');

var http = require('http');

/* GET home page. */
router.get('/', function(req, res, next) {
  console.log(path.join(__dirname, '../src/www/index.html'));
  res.sendFile(path.join(__dirname, '../src/www/index.html'));

});

router.post('/submit', function(req, response, next) {

	fetch('http://localhost:5000/get_loc',{method: "POST",  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
   body: JSON.stringify({date: req.body.date})

 })
    .then(function(res) {
        return res.text();

    }).then(function(body) {
    	console.log("Received response from data ingestor");
    	console.log(body);
    	response.send(body);
        
    });

	
});

router.post('/submit_loc', function(req, response, next) {

	fetch('http://localhost:5000/get_url',{method: "POST",  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
   body: JSON.stringify({loc:req.body.loc , timest:req.body.timest})

 })
    .then(function(res) {
        return res.text();

    }).then(function(body) {

    	
    	console.log(body);

    	//call another function which will retrieve the file
    	response.send(body);
        
    });
});

module.exports = router;
