echo 'Running the downloaded container'
docker run -d -p 8080:8080 njetty/registry
sleep 60
