from Queue import Queue
from threading import Thread
import time
import requests
import data_parser

q = Queue()
	

def process_data(req_data):
	q.put(req_data);
	return 1


class workerThread(req):

	new_url = di_run.timeparse(req[0],timest=req[1])
	data1 = {
	"room" : req[3],
	"final_url" : str(new_url)
	}
	r = requests.post("http://localhost:3000/home/service-response", data = jsonify(data1))

