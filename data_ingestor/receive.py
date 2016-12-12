import pika
import time
import json
import msg_q
from json import load
from urllib2 import urlopen

FINAL_URL = load(urlopen('http://api.ipify.org/?format=json'))['ip']

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
	statusMessage = {
		"room": body["room"],
        "status" : "0",
		"msg" : "DataIngestor is processing the request number {}".format(body["req_no"])
	}

	message = json.dumps(statusMessage)
	statusChannel.basic_publish(exchange='status',
	                      routing_key='',
	                      body=message)
	print(" [x] Sent Status message")

	if int(body['type'])== 0:
		loc_url = body['date']
		mq.get_loc(body["room"],loc_url, ch, method.delivery_tag)
	else:
		loc_url = body['date']
		timest = int(body['timest'])
		room = body['room']
		req_no = body['req_no']
		mq.worker((loc_url,timest,req_no, room, time.time(), ch, method.delivery_tag))

	statusMessage = {
		"room": body["room"],
        "status" : "1",
		"msg" : "DataIngestor has completed processing the request number {}".format(body["req_no"])
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
