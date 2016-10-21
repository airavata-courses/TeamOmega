var chai = require('chai');
var chaiHttp = require('chai-http');
var path = require("path");
var server = require('../bin/www');
var should = chai.should();
var expect = chai.expect;
chai.use(chaiHttp);

describe('Testing if server started...', function() {
	it('Should render the index page on /home', function(done) {	

		//setTimeout(done, 15000);


		this.timeout(50000);
		chai.request(server)
			.get('/home/')
			.end(function(err, res){
			  res.should.have.status(200);
			  done();
		});
	});
});


describe('Testing functions in home.js...', function() {
		
	

	it('Should submit date and time on /home/submit', function(done) {

		//setTimeout(done, 15000);
		this.timeout(15000);
    	//sending date		
		chai.request(server)
			.post('/home/submit')
			.send({room:"songires@gmail.com-123345", date: "2000/01/21/",type:0,timest: new Date().getTime()})
			.end(function(err, res){
				res.should.have.status(200);
		});

		//sending locatiion	
		chai.request(server)
			.post('/home/submit')
			.send({room:"songires@gmail.com-123345", date: '2016/09/06/KABR/',type:1,timest: new Date().getTime()})
			.end(function(err, res){
				res.should.have.status(200);
			done();
		});
	});


	it('Should should post service responses on /home/service-response', function(done) {
		this.timeout(15000);
		data1 = {
		"room" : "songires-12334545645",
		"kml" : "FINAL URL STRING>>>>",
		"type" : 2,
		"msg": "Data Ingestor processing complete.."
		}
    	//sending date
    	
    	testServiceProcess(data1);
		

		data1 = {
		"room" : "songires-12334545645",
		"kml" : "KML STRING>>>>",
		"type" : 3,
		"msg": "Storm Detection processing complete...."
		}
    	//sending date		
		testServiceProcess(data1);

		data1 = {
		"type" : 4
		}
    	//sending date		
		testServiceProcess(data1);
		done();
	});
});


var testServiceProcess = function(data1){
	chai.request(server)
		.post('/home/service-response')
		.send(data1)
		.end(function(res){
			expect(res.status).to.not.equal(404);
		});
}