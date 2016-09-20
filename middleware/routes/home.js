var express = require('express');
var passport = require('passport');
var router = express.Router();
const path = require('path');

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

	var url = "http://127.0.0.1:5000/getpath?"+req.body.date.toString()
	
	console.log(url);
	
	http.get(url, function(res) {

	    console.log("statusCode: ", res.statusCode);

	    res.on('data', function(d) {

	    	//Reading station codes from files

	    	fs.readFile('data.json', function(err,data){
	    		var fileData = JSON.parse(data.toString())
				console.info('GET result:\n');
				//process.stdout.write(d);
				console.info('\n\nCall completed');
				var x = d.toString();
				var array = JSON.parse(x);
				array = array["station"]

				console.log(Object.keys(fileData).length)
				for(var index in fileData){
					if(array.indexOf(index)<0){
						delete fileData[index]
					}
					else{
						console.log(index);
					}
				}

				response.send(fileData);
				});



	    });
 
	});

});


var construct_url = function(body){

	var months = {
		"January" : "01",
		"February" : "02",
		"March" : "03",
		"April" : "04",
		"May" : "05",
		"June" : "06",
		"July" : "07",
		"August" : "08",
		"September" : "09",
		"October" : "10",
		"November" : "11",
		"December" : "12"
	}

	var base_url = "http://127.0.0.1:5000/getpath?";
	
	var year = body.year;
	var month = months[body.month];
	var day = body.day;

	var new_url = base_url+"year="+year+"&&"+"month="+month+"&&"+"day="+day;

	console.log(new_url);
	return (new_url);
}

module.exports = router;
