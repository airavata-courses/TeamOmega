import time
import requests
import storm_cluster
import random
from threading import Thread


class jobThread(object):
	"""docstring for jobThread"""
	def __init__(self, sleep=10):
		super(jobThread, self).__init__()
		self.sleep = sleep
		self.sd_run = storm_cluster.StormCluster()
		# self.sd_run.start()
		self.count = 0


	def process_data(self,req_data):
		if self.count < 5:
			t = Thread(target=self.worker, args=(req_data,))
			self.count += 1
			t.start()
			return 1
		return 0
		

	def worker(self,req):
		print("Worker running for the task")


		wait_time = time.time()-req[-1]
		
		if wait_time<self.sleep:
			time.sleep(self.sleep-wait_time)
		
		
		kml,icon = self.sd_run.cluster(req[0])
		
		data1 = {
		"room" : req[1],
		"kml" : str(kml),
		"type" : 4,
		"msg": "Storm clustering processing complete..",
		"icon": icon,
		"hostIp": req[2]
		}
		try:
			r = requests.post("http://"+str(req[2])+":3000/home/service-response", data = data1)
			print r.status_code,"exit thread"		
		except:
			print "Server not reachable.."

		self.count -= 1
		return 1


