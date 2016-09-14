var express = require('express');
var passport = require('passport');
var router = express.Router();
var url = require('url');

var http = require('http');



var optionsget = {
    host : 'localhost', // here only the domain name
    // (no http/https !)
    port : 5000,
    path : '/getpath', // the rest of the url with parameters if needed
    method : 'GET' // do GET
};


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('home');
});

router.get('/submit', function(req, res, next) {

	console.log("Inside submit")
	
	http.get("http://127.0.0.1:5000/getpath", function(res) {

	    console.log("statusCode: ", res.statusCode);
	    // uncomment it for header details
	//  console.log("headers: ", res.headers);
 		console.log(res);
 
	    /*res.on('data', function(d) {
	        console.info('GET result:\n');
	        process.stdout.write(d);
	        console.info('\n\nCall completed');
	    });*/
 
	});

	/*reqGet.end();
	reqGet.on('error', function(e) {
	    console.error(e);
	});*/
/*

	var queryObj = url.parse(req.url,true).query;
	var queryLength = Object.keys(queryObj).length;

	res.send(req.query);

*/

});

module.exports = router;
