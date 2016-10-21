import time
import requests
import data_parser

from threading import Thread


class jobThread(object):
	"""docstring for jobThread"""
	def __init__(self, sleep=2):
		super(jobThread, self).__init__()
		self.sleep = sleep
		self.dt_run = data_parser.DataIngestor()
		self.dt_run.start()


	def process_data(self,req_data):
		print "here",req_data
		t = Thread(target=self.worker, args=(req_data,))
		t.start()

	def worker(self,req):
		print("Worker running for the task")


		wait_time = time.time()-req[-1]
		
		if wait_time<self.sleep:
			time.sleep(self.sleep-wait_time)

		new_url = self.dt_run.timeparse(req[0],timest=req[1])
		data1 = {
		"room" : req[2],
		"final_url" : str(new_url),
		"type" : "2",
		"msg": "Data Ingestor processing complete.."
		}
		r = requests.post("http://localhost:3000/home/service-response", data = data1)
		print r.status_code,"exit thread"		
		return 1

	def get_loc(self, loc_url):
		data = self.dt_run.get_stationlist(root_prefix = loc_url, type=3)
		data = self.dt_run.parse_json(data)
		return data

