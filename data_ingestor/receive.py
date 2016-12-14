import pika
import time
import json
import msg_q
from json import load
from urllib2 import urlopen

FINAL_URL = load(urlopen('http://api.ipify.org/?format=json'))['ip']

with open('data.json') as f:
	station_data = json.load(f)

print FINAL_URL

# FINAL_URL = "52.15.165.201"
connection = pika.BlockingConnection(pika.ConnectionParameters(
        host=FINAL_URL))

mq = msg_q.jobThread(connection)

receiveChannel = connection.channel()

statusChannel = connection.channel()


receiveChannel.queue_declare(queue='dataIngestor', durable=True)

statusChannel.exchange_declare(exchange='status', type='fanout')


print ' [*] Waiting for messages. To exit press CTRL+C'

def callback(ch, method, properties, body):
	body = json.loads(body)
	if int(body['type'])==0:
		x="3"
	else:
		x="0"
	statusMessage = {
		"room": body["room"],
        "status" : x,
		"msg" : "DataIngestor is processing the request number {}".format(body["req_no"])
	}

	message = json.dumps(statusMessage)
	statusChannel.basic_publish(exchange='status',
	                      routing_key='',
	                      body=message)
	print(" [x] Sent Status message")

	location = ""


	if int(body['type'])== 0:
		loc_url = body['date']
		mq.get_loc(body["room"],loc_url, ch, method.delivery_tag)
	else:
		loc_url = body['date']
		timest = int(body['timest'])
		room = body['room']
		req_no = body['req_no']
		x="1"
		loc_code = list(loc_url.split("/"))[3]
		location = station_data.get(loc_code.upper(),[None,None,None,None])[3]
		if location:
			location = " ".join(location.split("_")).title()
		mq.worker((loc_url,timest,req_no, room, time.time(), ch, method.delivery_tag))

	statusMessage = {
		"room": body["room"],
        "status" : x,
		"msg" : "DataIngestor has completed processing the request number {}".format(body["req_no"]),
		"req_date" : loc_url,
		"req_time" : timest,
		"req_loc" : location
	}


	message = json.dumps(statusMessage)
	statusChannel.basic_publish(exchange='status',
	                      routing_key='',
	                      body=message)
	print "DataIngestor has completed processing the request number {}".format(body["req_no"])

	#sending status message....





receiveChannel.basic_qos(prefetch_count=1)
receiveChannel.basic_consume(callback,
                      queue='dataIngestor')

receiveChannel.start_consuming()


connection.close()
