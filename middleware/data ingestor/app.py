from flask import Flask,Response,request,jsonify
import json
import data_parser


app = Flask(__name__)

di_run = data_parser.DataIngestor()

di_run.start()

@app.route('/get_loc', methods=['POST'])
def get_location():
	loc_url = request.form['date']
	data = di_run.get_stationlist(root_prefix = loc_url, type=3)
	data = di_run.parse_json(data)
	return jsonify(data)





if __name__ == '__main__':
    app.run(debug=True)