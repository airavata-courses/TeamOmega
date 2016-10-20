import time
from threading import Thread

def sleeper(a):
	i=a[0]
	print "thread %d sleeps for 5 seconds" % i
	time.sleep(5)
	print "thread %d woke up" % i

i =(1,2,3,4)
t = Thread(target=sleeper, args=(i,))
t.start()