#!/bin/bash

command_exists() {
        command -v "$@" > /dev/null 2>&1
}
if command_exists docker; then
                version="$(docker -v | awk -F '[ ,]+' '{ print $3 }')"
                MAJOR_W=1
                MINOR_W=10
		echo 'Docker is already installed'

else
		echo 'Installing Docker'
                sudo yum update -y
                sudo yum install -y docker
                sudo service docker start
fi

echo 'Killing any container of the old Docker image'

docker rm $(docker stop $(docker ps -a -q --filter ancestor=sagarkrkv/forecast_trigger --format="{{.ID}}"))
if  [ "$?" -ne 0 ]; then
	echo ""
fi

sudo fuser -k 9000/tcp
echo 'Pulling a new image from docker'
docker pull sagarkrkv/forecast_trigger

echo 'Removing the previous image'
sudo docker rmi $(sudo docker images | grep "^<none>" | awk '{print $3}')
if  [ "$?" -ne 0 ]; then
	echo ""
fi

echo 'Removing the previous images with exit status'
if  [ "$(sudo docker ps -a | grep Exit  )" != "" ]; then
	sudo docker ps -a | grep Exit | cut -d ' ' -f 1 | xargs sudo docker rm
fi
