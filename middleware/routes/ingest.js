var express = require('express');
var passport = require('passport');
var router = express.Router();
const path = require('path');

var ensureLoggedIn = require('connect-ensure-login').ensureLoggedIn();

var fs = require('fs');

var url = require('url');

var http = require('http');

/* GET home page. */

router.get('/', function(req, response, next) {

	//var url = "http://127.0.0.1:5000/getpath?"+req.body.date.toString()
	
	//console.log(url);
	
	var post_options = {
      host: 'localhost',
      port: '5000',
      path: '/get_loc',
      method: 'POST',
      headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          'Content-Length': Buffer.byteLength(post_data)
      }
  };

  // Set up the request
  var post_req = http.request(post_options, function(res) {
      res.setEncoding('utf8');
      res.on('data', function (chunk) {
          console.log('Response: ' + chunk);
      });
  });

});

module.exports = router;
