from flask import Flask,request,jsonify
from boto import connect_s3

app = Flask(__name__)


@app.route('/')
def root_path():
    return 'REST API for getting the files from AWS Nexrad'


@app.route('/getpath')
def getpath():
    prefix = ''
    level = 'root'
    if 'year' in request.args:
        prefix+=request.args['year']+'/'
        level = 'year'
        if 'month' in request.args:
            prefix+=request.args['month']+'/'
            level = 'month'
            if 'day' in request.args:
                prefix+=request.args['day']+'/'
                level = 'day'
                if 'station' in request.args:
                    prefix+=request.args['station']+'/'
                    level = 'station'
                    if 'time' in request.args:
                        level = 'time'
                        prefix+= request.args['station'] \
                                 + request.args['year'] \
                                 + request.args['month'] \
                                 + request.args['day'] + '_' \
                                 + request.args['time']

    s3conn = connect_s3(anon=True)
    bucket = s3conn.get_bucket('noaa-nexrad-level2',validate=False)
    file_list=[]
    for key in bucket.list(prefix=prefix, delimiter='/'):
        fullname = key.name.encode('utf-8')
        if fullname != 'index.html' and len(fullname)<=42:

            if level == 'time':
                file_list.append("https://noaa-nexrad-level2.s3.amazonaws.com/"+fullname)
            elif level == 'station':
                file_list.append(fullname[29:35])
            elif level == 'day':
                file_list.append(fullname[11:15])
            elif level == 'month':
                file_list.append(fullname[8:10])
            elif level == 'year':
                file_list.append(fullname[5:7])
            else:
                file_list.append(fullname[0:4])

    if level == 'time':
        key = 'link'
    elif level == 'station':
        key = "time"
    elif level == 'day':
        key = "station"
    elif level == 'month':
        key = "day"
    elif level == 'year':
        key = "month"
    else:
        key = "year"

    #If nothing is returning, send a error.
    if not file_list:
        return jsonify({"error":"Nothing exists matching the given parameters"})
    return jsonify({key:file_list})

if __name__ == '__main__':
    app.run(debug=True)
