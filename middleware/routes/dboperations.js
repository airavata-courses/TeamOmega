var express = require('express');
var passport = require('passport');
const path = require('path');

var fetch = require('node-fetch');


var ensureLoggedIn = require('connect-ensure-login').ensureLoggedIn();



//Below code details how to send and get the data to mongo 

/*
router.get('/insert', function(req, res, next) {
    
    var json = {
      username: "suraj",
      timestamp: "123",
      description: "Normal insert"
    }

    insertDB(json, function(body){
      console.log(body);
    });

  
});


router.get('/', function(req, res, next) {
    
    getDB(function(body){
      console.log(body);
    });

});


*/

exports.insertDB = function(data, callback){

  fetch('http://localhost:4567/add-log',{method: "POST",  headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
  
    body: JSON.stringify(data)

  }).then(function(res) {
    
    return res.text();

  }).then(function(body) {
    
    console.log("Received response from Registery API");
    //response.send(body);
     callback(body); 

  });

}

exports.getDB = function(callback){

  fetch('http://localhost:4567/').then(function(res) {
        return res.json();
    }).then(function(json) {
        callback(json);
    });

}


