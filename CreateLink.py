import werkzeug.exceptions as ex
from flask import Flask, jsonify, abort

app = Flask(__name__)

##############################################
##############      TO DO    #################
##############################################
# REST API                  : DONE
# GET METHOD                : DONE
# Custom Error Message      : DONE
# Implement URL_FOR
# Add authentication
# Validations for the input
##############################################


@app.route('/getlink/<input_string>')
def create_link(input_string):
    if not input_string or len(input_string) < 18:
        abort(405)
    station_id = input_string[:4]
    month = input_string[4:6]
    day = input_string[6:8]
    year = input_string[8:12]
    time = input_string[12:18]
    return jsonify({'link': 'https://noaa-nexrad-level2.s3.amazonaws.com/' +
                            year + '/' + month + '/' + day + '/' +
                            station_id + '/' + station_id + year + month +
                            day + '_' + time + '_V06.gz'})

@app.route('/')
def main():
    return 'A REST-API for creating a link for Nexrad level 2 scan files on AWS'


@app.errorhandler(405)
def invalid(e):
    return jsonify({'error': 'Something went wrong with the given input parameters, link cannot be created'})


if __name__ == '__main__':
    app.run(debug=True)
