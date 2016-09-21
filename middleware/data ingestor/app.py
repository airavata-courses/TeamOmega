from flask import Flask,Response,request,jsonify
import json
import data_parser


app = Flask(__name__)

di_run = data_parser.DataIngestor()

di_run.start()

print "start"

@app.route('/get_loc', methods=['POST'])
def get_location():
	#print("Printting json--->",request.json)
	loc_url = request.json['date']
	#print("Printing Lock URLl",loc_url)
	data = di_run.get_stationlist(root_prefix = loc_url, type=3)
	data = di_run.parse_json(data)
	return jsonify(data)

@app.route('/get_url', methods=['POST'])
def get_timeurl():
	print "get time url"
	loc_url = request.json['loc']
	timest = int(request.json['timest'])
	new_url = di_run.timeparse(loc_url,timest=timest)
	final_url = { "final_url" : str(new_url)}
	return jsonify(final_url)





if __name__ == '__main__':
    app.run(debug=True)