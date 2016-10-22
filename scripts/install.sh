echo 'Running the downloaded container'
docker run -d -p 4000:4000 njetty/data_ingestor
sleep 60
