echo 'Running the downloaded container'
docker run -d -p 4000:4000 njetty/data_ingestor




sudo docker run -it -h node \
 -p 8500:8500 \
 -p 8600:53/udp \
 gliderlabs/consul-server \
 -bootstrap \
 -ui-dir /ui \
 -advertise 172.17.0.1 \
 -log-level debug

sudo docker run -it \
-e "SERVICE_NAME=data_ingestor" \
-p 4000:4000 njetty/data_ingestor