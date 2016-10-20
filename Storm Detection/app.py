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
	mq.process_data((loc_url, room, time.time()))
	return jsonify({"msg" : "Data added to the queue in Storm Detector"})






@app.route('/')
def hello_world():
	return "<h1> Hello, Storm Detector is running</h1>"



# Tell RQ what Redis connection to use





if __name__ == '__main__':
    app.run()