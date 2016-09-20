from flask import Flask,Response,request,jsonify
from boto import connect_s3
import json

app = Flask(__name__)


@app.route('/')
def root_path():
    return 'REST API for getting the files from AWS Nexrad'

@app.route('/<root_prefix>')
def getroot(root_prefix):
    if root_prefix== 'getroot':
        root_prefix= ''
    else:
        root_prefix+= '/'
    s3conn = connect_s3(anon=True)
    bucket = s3conn.get_bucket('noaa-nexrad-level2',validate=False)
    root_list=[]
    for key in bucket.list(prefix=root_prefix, delimiter='/'):
        if key.name.encode('utf-8') != 'index.html':
            if root_prefix=='/':
                root_list.append({'year':key.name.encode('utf-8')})
            else:
                root_list.append
    response= Response(json.dumps(root_list),  mimetype='application/json')
    print response
    return response

@app.route('/<year>/<month_prefix>')
def getdays(year,month_prefix):
    month_prefix = year+'/'+month_prefix+'/'
    print(month_prefix)
    s3conn = connect_s3(anon=True)
    bucket = s3conn.get_bucket('noaa-nexrad-level2',validate=False)
    file_list=[]
    for key in bucket.list(prefix=month_prefix, delimiter='/'):
        if key.name.encode('utf-8') != 'index.html':
            file_list.append(key.name.encode('utf-8'))
    response= '\n'.join(file for file in file_list)
    print response
    return response

@app.route('/getpath')
def getpath():
    prefix = ''
    if 'year' in request.args:
        prefix+=request.args['year']+'/'
    if 'month' in request.args:
        prefix+=request.args['month']+'/'
    if 'day' in request.args:
        prefix+=request.args['day']+'/'
    if 'station' in request.args:
        prefix+=request.args['station']+'/'
    s3conn = connect_s3(anon=True)
    bucket = s3conn.get_bucket('noaa-nexrad-level2',validate=False)
    file_list=[]
    for key in bucket.list(prefix=prefix, delimiter='/'):
        fullname = key.name.encode('utf-8')
        if fullname != 'index.html' and len(fullname)<=42:

            if 'station' in request.args:
                file_list.append(fullname[29:35])
            elif 'day' in request.args:
                file_list.append(fullname[11:15])
            elif 'month' in request.args:
                file_list.append(fullname[8:10])
            elif 'year' in request.args:
                file_list.append(fullname[5:7])
            else:
                file_list.append(fullname[0:4])

    if 'station' in request.args:
        key = "time"
    elif 'day' in request.args:
        key = "station"
    elif 'month' in request.args:
        key = "day"
    elif 'year' in request.args:
        key = "month"
    else:
        key = "year"
            # if 'station' in request.args:
            #     file_list.append({"time":fullname[29:35]})
            # elif 'day' in request.args:
            #     file_list.append({"station":fullname[12:16]})
            # elif 'month' in request.args:
            #     file_list.append({"day":fullname[9:11]})
            # elif 'year' in request.args:
            #     file_list.append({"month":fullname[6:9]})
            # else:
            #     file_list.append({"year":fullname[0:5]})
    # response= '\n'.join(file for file in file_list)
    #response= Response(json.dumps(file_list),  mimetype='application/json')
    # print response
    return jsonify({key:file_list})

if __name__ == '__main__':
    app.run(debug=True)
