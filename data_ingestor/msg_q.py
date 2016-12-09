import time
import data_parser
import pika
import json

from threading import Thread


class jobThread(object):
	"""docstring for jobThread"""
	def __init__(self, connection, sleep=10):
		super(jobThread, self).__init__()
		self.sleep = sleep
		self.dt_run = data_parser.DataIngestor()
		self.dt_run.start()
		self.connection = connection
		self.send_channel = connection.channel() 
		self.send_channel.queue_declare(queue='response', durable=True)

	def process_data(self,req_data):
		print "here",req_data
		t = Thread(target=self.worker, args=(req_data,))
		t.start()

	def worker(self,req):
		print("Worker running for the task")
		loc_url,timest,req_no, room,msg_time,ch,delivery_tag = req

		wait_time = time.time()-msg_time
		
		if wait_time<self.sleep:
			time.sleep(self.sleep-wait_time)

		new_url = self.dt_run.timeparse(loc_url,timest=msg_time)
		data = {
		"room" : room,
		"final_url" : str(new_url),
		"type" : "2",
		"msg": "Data Ingestor processing complete..",
		"req_no" : req_no
		}
		self.send_channel.basic_publish(exchange='', routing_key='response', body=json.dumps(data), properties=pika.BasicProperties(delivery_mode = 2))
		ch.basic_ack(delivery_tag=delivery_tag)	
		print "Msg sent"
		return 1

	def get_loc(self, room, loc_url,ch,delivery_tag):
		data = self.dt_run.get_stationlist(root_prefix = loc_url, type=3)
		data = self.dt_run.parse_json(data)
		data["msg"] = "Station codes from Data Ingestor"
		data["room"] = room
		data["type"] = "1"
		self.send_channel.basic_publish(exchange='', 
								routing_key='response', 
								body=json.dumps(data),
								properties=pika.BasicProperties(delivery_mode = 2,)
							)
		ch.basic_ack(delivery_tag=delivery_tag)

