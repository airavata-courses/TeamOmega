import pika
import time
import json
import msg_q




connection = pika.BlockingConnection(pika.ConnectionParameters(
        host='localhost'))

mq = msg_q.jobThread(connection)

receiveChannel = connection.channel()

statusChannel = connection.channel()


receiveChannel.queue_declare(queue='dataIngestor', durable=True)

statusChannel.queue_declare(queue='status', durable=True)


print(' [*] Waiting for messages. To exit press CTRL+C')

def callback(ch, method, properties, body):
	body = json.loads(body)
	print(body)
	body['type'] = 0
	if int(body['type'])== 0:
		loc_url = body['date']
		mq.get_loc(loc_url, ch, method.delivery_tag)
	else:
		loc_url = body['date']
		timest = int(body['timest'])
		room = body['room']
		mq.process_data((loc_url,timest, room, time.time(), ch, method.delivery_tag))

	print(" [x] Done", ch, method.delivery_tag)

	#sending status message....

	statusMessage = {
		"room": body["room"],
		"msg" : "DataIngestor is processing the request number {}".format(body["req_no"])
	}

	message = json.dumps(statusMessage)
	statusChannel.basic_publish(exchange='',
	                      routing_key='status',
	                      body=message)
	print(" [x] Sent message")



receiveChannel.basic_qos(prefetch_count=1)
receiveChannel.basic_consume(callback,
                      queue='dataIngestor')

receiveChannel.start_consuming()


connection.close()