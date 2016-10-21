from flask import Flask,Response,request,jsonify
import json
import time
import msg_q



app = Flask(__name__)

# di_run = data_parser.DataIngestor()

# di_run.start()

mq = msg_q.jobThread()


@app.route('/get_loc', methods=['POST'])
def get_location():
	#print(request.data)
	loc_url = request.json['date']

	print("Printing Lock URLl",loc_url)
	data = mq.get_loc(loc_url)

	data["msg"]="Station codes from dataingestor"

	return jsonify(data)

@app.route('/get_url', methods=['POST'])
def get_timeurl():
	loc_url = request.json['date']
	timest = int(request.json['timest'])
	room = request.json['room']
	print "==========================="

	mq.process_data((loc_url,timest, room, time.time()))
	print "--------------------"
	return jsonify({"msg" : "Data added to the queue.."})




@app.route('/')
def hello_world():
	return "<h1> Hello, Data Ingestor is running</h1>"



# Tell RQ what Redis connection to use





if __name__ == '__main__':
    app.run()