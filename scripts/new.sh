#!/bin/bash -x
echo 'Killing any container of the old Docker image'
if [[ $(sudo docker ps -a -q --filter ancestor=njetty/data_ingestor --format="{{.ID}}") ]]; then
	docker rm $(docker stop $(docker ps -a -q --filter ancestor=njetty/data_ingestor --format="{{.ID}}"))
fi

fuser -k 4000/tcp

echo 'Pulling a new image from docker'
docker pull njetty/data_ingestor

echo 'Removing the previous image'
sudo docker rmi $(sudo docker images | grep "^<none>" | awk '{print $3}')
if  ["$?" -ne 0]; then
        echo "No previous images found"
fi