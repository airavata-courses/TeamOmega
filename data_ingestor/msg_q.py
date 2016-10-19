from Queue import Queue
import thread
import time
import requests
import data_parser

SLEEP = 10
q = Queue()
	
di_run = data_parser.DataIngestor()

di_run.start()


def process_data(req_data):
	thread.start_new_thread(worker,(req_data))


def worker(v1,v2,v3,v4):
	print("Worker running for the task")

	print v1,v2

	wait_time = time.time()-v4
	
	if wait_time<SLEEP:
		time.sleep(SLEEP-wait_time)

	new_url = di_run.timeparse(v1,timest=v2)
	data1 = {
	"room" : v3,
	"final_url" : str(new_url)
	}
	r = requests.post("http://localhost:3000/home/service-response", data = data1)
	print r.status_code
	return 1