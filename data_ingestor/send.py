import pika
import json

connection = pika.BlockingConnection(pika.ConnectionParameters(
        host='localhost'))



# #edit
#  logging.basicConfig(level=logging.INFO, format=LOG_FORMAT)
#     example = ExampleConsumer('amqp://guest:guest@localhost:5672/%2F')

# def connect():
#         """This method connects to RabbitMQ, returning the connection handle.
#         When the connection is established, the on_connection_open method
#         will be invoked by pika.

#         :rtype: pika.SelectConnection

#         """
#         LOGGER.info('Connecting to %s', rabbit_url)
#         return pika.SelectConnection(pika.URLParameters(rabbit_url),
#                                      on_connection_open,
#                                      stop_ioloop_on_close=False)

# def on_connection_open(self, unused_connection):
#     """This method is called by pika once the connection to RabbitMQ has
#     been established. It passes the handle to the connection object in
#     case we need it, but in this case, we'll just mark it unused.

#     :type unused_connection: pika.SelectConnection

#     """
#     LOGGER.info('Connection opened')
#     add_on_connection_close_callback()

# def add_on_connection_close_callback(self):
#     """This method adds an on close callback that will be invoked by pika
#     when RabbitMQ closes the connection to the publisher unexpectedly.

#     """
#     LOGGER.info('Adding connection close callback')
#     self._connection.add_on_close_callback(self.on_connection_closed)

# def on_connection_closed(self, connection, reply_code, reply_text):
#     """This method is invoked by pika when the connection to RabbitMQ is
#     closed unexpectedly. Since it is unexpected, we will reconnect to
#     RabbitMQ if it disconnects.

#     :param pika.connection.Connection connection: The closed connection obj
#     :param int reply_code: The server provided reply_code if given
#     :param str reply_text: The server provided reply_text if given

#     """
#     self._channel = None
#     if self._closing:
#         self._connection.ioloop.stop()
#     else:
#         LOGGER.warning('Connection closed, reopening in 5 seconds: (%s) %s',
#                        reply_code, reply_text)
#         self._connection.add_timeout(5, self.reconnect)

# def reconnect(self):
#     """Will be invoked by the IOLoop timer if the connection is
#     closed. See the on_connection_closed method.

#     """
#     # This is the old connection IOLoop instance, stop its ioloop
#     self._connection.ioloop.stop()

#     if not self._closing:

#         # Create a new connection
#         self._connection = self.connect()

#         # There is now a new connection, needs a new ioloop to run
#         self._connection.ioloop.start()



# #edit finished












sendChannel = connection.channel()
receiveChannel = connection.channel()

receiveChannel.queue_declare(queue='status', durable=True)

sendChannel.queue_declare(queue='response', durable=True)


data = {'date': '2016/09/20/', 
		'timest': '2016-10-21T03:13:38.994Z', 
		'room': 'songires@gmail.com-1db419e2741b55ce',
		'req_no' : 5978,
		'type' : 3
}

message = json.dumps(data)
sendChannel.basic_publish(exchange='',
                      routing_key='response',
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