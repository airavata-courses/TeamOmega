import pika
import json

connection = pika.BlockingConnection(pika.ConnectionParameters(
        host='localhost'))


sendChannel = connection.channel()
receiveChannel = connection.channel()

receiveChannel.queue_declare(queue='response', durable=True)

sendChannel.queue_declare(queue='stormClustering', durable=True)


data = {"msg": 'Data Ingestor processing complete..',
  "kml": '<?xml version="1.0" encoding="UTF-8"?>\n<kml xmlns="http://www.opengis.net/kml/2.2" xmlns:gx="http://www.google.com/kml/ext/2.2">\n    <Document id="feat_5">\n        <Placemark id="feat_6">\n            <name>Albuquerque</name>\n            <Point id="geom_2">\n                <coordinates>-106.8233303,35.14972038,0</coordinates>\n            </Point>\n        </Placemark>\n    </Document>\n</kml>\n',
  "type": '2',
  "room": 'songires@gmail.com-1db419e2741b55ce',
		'req_no' : 5978,
		'type' : 0
}

message = json.dumps(data)
sendChannel.basic_publish(exchange='',
                      routing_key='stormClustering',
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