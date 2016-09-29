import sys
try:
	import gunicorn
	sys.exit(0)
except Exception, e:
	sys.exit(1)
 