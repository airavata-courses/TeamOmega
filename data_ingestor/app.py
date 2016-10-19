from flask import Flask,Response,request,jsonify
import json
import data_parser
import time
import msg_q as q



app = Flask(__name__)

di_run = data_parser.DataIngestor()

di_run.start()

print "start"

@app.route('/get_loc', methods=['POST'])
def get_location():
	loc_url = request.json['date']
	room = request.json['room']

	#print("Printing Lock URLl",loc_url)
	data = di_run.get_stationlist(root_prefix = loc_url, type=3)
	data = di_run.parse_json(data);

	#print(jsonify(data));
	data["msg"]="Station codes from dataingestor"
	# r = requests.post("http://localhost:3000/home/service-response", data = {
	# 	"room" : room,
	# 	"data" : data
	# 	});

	# print("status_code: "+ r.status_code);
	return jsonify(data)

@app.route('/get_url', methods=['POST'])
def get_timeurl():
	loc_url = request.json['date']
	timest = int(request.json['timest'])
	room = request.json['room']

	q.process_data((loc_url,timest, room, time.time()))

	return jsonify({"msg" : "Data added to the queue.."})




@app.route('/')
def hello_world():
	return "<h1> Hello, Data Ingestor is running</h1>"



# Tell RQ what Redis connection to use





if __name__ == '__main__':
    app.run()