import time
import requests
import storm_detector
import pika
import json

from threading import Thread


FINAL_URL = '52.43.210.8'

class jobThread(object):
	"""docstring for jobThread"""
	def __init__(self, connection,sleep=10):
		self.sleep = sleep
		self.sd_run = storm_detector.StormDetection()
		self.sd_run.start()
		self.count = 0
		self.connection = connection

	def process_data(self,req_data):
		if self.count < 5:
			t = Thread(target=self.worker, args=(req_data,))
			self.count += 1
			t.start()
			return 1
		return 0
		

	def worker(self,req):
		print("Worker running for the task")
		final_url,room,req_no,msg_time,ch,delivery_tag = req

		wait_time = time.time()-msg_time
		if wait_time<self.sleep:
			time.sleep(self.sleep-wait_time)
		print "thread woke up"
		kml = self.sd_run.detection(final_url)
		data = {
		"room" : room,
		"kml" : str(kml),
		"type" : 3,
		"msg": "Storm Detection processing complete..",
		"req_no" : req_no
		}
		try:
			channel = self.connection.channel()
			channel.queue_declare(queue='response', durable=True)
			channel.basic_publish(exchange='', routing_key='response', body=json.dumps(data), properties=pika.BasicProperties(delivery_mode = 2))
			channel.close()
			ch.basic_ack(delivery_tag=delivery_tag) 	
		except Exception, e:
			print "server not reachable", e
		self.count -= 1
		return 1


if __name__ == '__main__':



	connection = pika.BlockingConnection(pika.ConnectionParameters(
        host=FINAL_URL))


	jb = jobThread(connection)

	
	receiveChannel = connection.channel()

	statusChannel = connection.channel()


	receiveChannel.queue_declare(queue='stormDetection', durable=True)

	statusChannel.queue_declare(queue='status', durable=True)


	print(' [*] Waiting for messages. To exit press CTRL+C')

	def callback(ch, method, properties, body):
		body = json.loads(body)

		final_url = body['final_url']
		room = body['room']
		req_no = body['req_no']


		print("received message")

		jb.process_data((final_url,room, req_no, time.time(), ch, method.delivery_tag))

		print(" [x] Done", ch, method.delivery_tag)

		#sending status message....

		statusMessage = {
			"room": body["room"],
			"msg" : "Storm Detection is processing the request number {}".format(body["req_no"])
		}

		message = json.dumps(statusMessage)
		statusChannel.basic_publish(exchange='',
		                      routing_key='status',
		                      body=message)
		print(" [x] Sent message")



	receiveChannel.basic_qos(prefetch_count=1)
	receiveChannel.basic_consume(callback,
	                      queue='stormDetection')

	receiveChannel.start_consuming()


	connection.close()