import pika
import json

connection = pika.BlockingConnection(pika.ConnectionParameters(
        host='localhost'))

sendChannel = connection.channel()
receiveChannel = connection.channel()

receiveChannel.queue_declare(queue='status', durable=True)

sendChannel.queue_declare(queue='dataIngestor', durable=True)


data = {'date': '2016/09/20/', 
		'timest': '2016-10-21T03:13:38.994Z', 
		'room': 'songires@gmail.com-1db419e2741b55ce',
		'req_no' : 5678
}

message = json.dumps(data)
sendChannel.basic_publish(exchange='',
                      routing_key='dataIngestor',
                      body=message)
print(" [x] Sent message")


print(' [*] Waiting for messages. To exit press CTRL+C')

def callback(ch, method, properties, body):
	body = json.loads(body)
	print(body)


receiveChannel.basic_qos(prefetch_count=1)
receiveChannel.basic_consume(callback,
                      queue='status')

receiveChannel.start_consuming()

connection.close()