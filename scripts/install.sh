echo 'Running the downloaded container'
docker run --restart unless-stopped -d -p 4000:4000 njetty/data_ingestor
