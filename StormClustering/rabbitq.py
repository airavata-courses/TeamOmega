import time
import requests
import storm_cluster
import random
import pika
import json
from threading import Thread
from json import load

from urllib2 import urlopen

FINAL_URL = load(urlopen('http://api.ipify.org/?format=json'))['ip']

FINAL_URL = "52.15.165.201"

class jobThread(object):
	"""docstring for jobThread"""
	def __init__(self, connection, sleep=10):
		super(jobThread, self).__init__()
		self.sleep = sleep
		self.sd_run = storm_cluster.StormCluster()
		# self.sd_run.start()
		self.count = 0
		self.connection = connection
		self.send_channel = connection.channel()
		self.send_channel.queue_declare(queue='response', durable=True)


	def process_data(self,req_data):
		if self.count < 5:
			t = Thread(target=self.worker, args=(req_data,))
			self.count += 1
			t.start()
			return 1
		return 0
		

	def worker(self,req):
		print("Worker running for the task")
		kml,room,req_no,msg_time,ch,delivery_tag = req

		wait_time = time.time()-msg_time
		
		if wait_time<self.sleep:
			self.connection.sleep(self.sleep-wait_time)
		
		
		kml,icon = self.sd_run.cluster(kml)
		
		data = {
		"room" : room,
		"kml" : str(kml),
		"type" : 4,
		"msg": "Storm clustering processing complete..",
		"icon": icon,
		"req_no":req_no
		}
		self.send_channel.basic_publish(exchange='', routing_key='response', body=json.dumps(data), properties=pika.BasicProperties(delivery_mode = 2))
		ch.basic_ack(delivery_tag=delivery_tag)
		print "Sent response"

		return 1

if __name__ == '__main__':
	
	connection = pika.BlockingConnection(pika.ConnectionParameters(host=FINAL_URL))

	jb = jobThread(connection)

	receiveChannel = connection.channel()

	statusChannel = connection.channel()


	receiveChannel.queue_declare(queue='stormClustering', durable=True)

	statusChannel.queue_declare(queue='status', durable=True)


	print(' [*] Waiting for messages. To exit press CTRL+C')

	def callback(ch, method, properties, body):
		body = json.loads(body)
		kml = body['kml']
		room = body['room']
		req_no = body['req_no']

		

		#sending status message....

		statusMessage = {
			"room": body["room"],
			"msg" : "StormCluster is processing the request number {}".format(body["req_no"])
		}

		message = json.dumps(statusMessage)
		statusChannel.basic_publish(exchange='',
		                      routing_key='status',
		                      body=message)
		print(" [x] Sent Status message")

		jb.worker((kml,room, req_no, time.time(), ch, method.delivery_tag))

		print(" [x] Done", ch, method.delivery_tag)



	receiveChannel.basic_qos(prefetch_count=1)
	receiveChannel.basic_consume(callback,queue='stormClustering')

	receiveChannel.start_consuming()


	connection.close()
