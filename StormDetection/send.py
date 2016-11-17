import pika
import json

connection = pika.BlockingConnection(pika.ConnectionParameters(
        host='localhost'))


sendChannel = connection.channel()
receiveChannel = connection.channel()

receiveChannel.queue_declare(queue='status', durable=True)

sendChannel.queue_declare(queue='stormDetection', durable=True)


data = {"final_url": '2016/09/12/KAKQ/KAKQ20160912_000336_V06',
		'room': 'songires@gmail.com-1db419e2741b55ce',
		'req_no' : 5978,
		'type' : 0
}

message = json.dumps(data)
sendChannel.basic_publish(exchange='',
                      routing_key='stormDetection',
                      body=message)
print(" [x] Sent message")


print(' [*] Waiting for messages. To exit press CTRL+C')

def callback(ch, method, properties, body):
	body = json.loads(body)
	print(body)


receiveChannel.basic_qos(prefetch_count=1)
receiveChannel.basic_consume(callback,
                      queue='response')

receiveChannel.start_consuming()

connection.close()