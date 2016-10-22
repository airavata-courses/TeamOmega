from flask import Flask,Response,request,jsonify
import json
import time
import msg_q



app = Flask(__name__)

                       

mq = msg_q.jobThread()




@app.route('/get_kml', methods=['POST'])
def get_timeurl():
	final_url = request.json['final_url']
	room = request.json['room']
<<<<<<< HEAD
	mq.process_data((final_url, room, time.time()))
=======
	ipaddr = request.json['hostIp']

	mq.process_data((final_url, room,ipaddr ,time.time()))
>>>>>>> 803d4080751264ba663564edde4c924a496f32e7
	return jsonify({"msg" : "Data added to the queue in Storm Detector"})






@app.route('/')
def hello_world():
	return "<h1> Hello, Storm Detector is running</h1>"



# Tell RQ what Redis connection to use





if __name__ == '__main__':
    app.run()