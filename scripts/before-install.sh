#!/bin/bash

echo 'Killing any container of the old Docker image'
if [[ $(sudo docker ps -a -q --filter ancestor=njetty/data_ingestor --format="{{.ID}}") ]]; then
	docker rm -f $(docker ps -a -q --filter ancestor=njetty/data_ingestor --format="{{.ID}}")
fi


echo 'Removing the previous image'
sudo docker rmi $(sudo docker images | grep "^<none>" | awk '{print $3}')

if  ["$?" -ne 0]; then echo "No previous images found"
fi

echo 'Removing the previous images with exit status'
if  [ "$(sudo docker ps -a | grep Exit  )" != "" ]; then
        sudo docker ps -a | grep Exit | cut -d ' ' -f 1 | xargs sudo docker rm
fi

echo 'Checking consul status and installing if required'
if [[ "$(docker ps -q --filter ancestor=gliderlabs/consul-server)" == "" ]]; then
        
        export ATLAS_TOKEN=HRGXTfhWgXPSbg.atlasv1.waWUCvjiX9IDvhGAc9xOvl8BUkgtgV7ia1nX0OzFCEbfyAKGwAmPVlhLykeuqa7OAF8

        alias wanip='dig +short myip.opendns.com @resolver1.opendns.com'
        echo $wanip
         sudo docker run -d -p 8300:8300 -p 8301:8301/tcp -p 8301:8301/udp -p 8312:8302/tcp -p 8302:8302/udp -p 8400:8400 -p 8500:8500 -p 53:53/udp -p 8600:8600  --name=consul -e SERVICE_IGNORE=true -h $(hostname) gliderlabs/consul-server -node=$(hostname)  -atlas=vkalvaku/dem -atlas-join -atlas-token=$ATLAS_TOKEN -advertise $(wanip)
    
    if [[ "$(docker ps -a -q --filter ancestor=gliderlabs/registrator)" != "" ]]; then
        docker rm $(docker stop $(docker ps -a -q --filter ancestor=gliderlabs/registrator --format="{{.ID}}"))
    fi

    if [[ "$(docker ps -a -q --filter ancestor=gonkulatorlabs/rabbitmq)" != "" ]]; then
        docker rm $(docker stop $(docker ps -a -q --filter ancestor=gonkulatorlabs/rabbitmq --format="{{.ID}}"))
    fi
fi

echo 'Checking registrator status and installing if required'

if [[ "$(docker ps -q --filter ancestor=gliderlabs/registrator)" == "" ]]; then
        
     sudo docker run -d --name=registrator --net=host --volume=/var/run/docker.sock:/tmp/docker.sock gliderlabs/registrator:latest consul://localhost:8500
fi

echo 'Checking rabbitmq status and installing if required'

if [[ "$(docker ps -q --filter ancestor=gonkulatorlabs/rabbitmq)" == "" ]]; then
     sleep 50       
     sudo docker run -d --net=host --name rabbitmq -e SERVICE_IGNORE=true -e AUTOCLUSTER_TYPE=consul -e AUTOCLUSTER_LOG_LEVEL=debug -e CONSUL_HOST=localhost gonkulatorlabs/rabbitmq
fi