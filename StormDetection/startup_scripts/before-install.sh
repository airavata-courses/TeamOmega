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
docker rm $(docker stop $(docker ps -a -q --filter ancestor=sagarkrkv/stormdetection --format="{{.ID}}"))

if  [ "$?" -ne 0 ]; then
	echo ""
fi

echo 'Pulling a new image from docker'
docker pull sagarkrkv/stormdetection


echo 'Removing the previous image'
sudo docker rmi $(sudo docker images | grep "^<none>" | awk '{print $3}')
if  [ "$?" -ne 0 ]; then
	echo ""
fi

echo 'Removing the previous images with exit status'
if  [ "$(sudo docker ps -a | grep Exit  )" != "" ]; then
	sudo docker ps -a | grep Exit | cut -d ' ' -f 1 | xargs sudo docker rm
fi

echo 'Checking consul status and installing if required'
if [[ "$(docker ps -q --filter ancestor=gliderlabs/consul-server)" == "" ]]; then
        
        export ATLAS_TOKEN=HRGXTfhWgXPSbg.atlasv1.waWUCvjiX9IDvhGAc9xOvl8BUkgtgV7ia1nX0OzFCEbfyAKGwAmPVlhLykeuqa7OAF8

		alias wanip='dig +short myip.opendns.com @resolver1.opendns.com'

		 sudo docker run -d -p 8300:8300 -p 8301:8301/tcp -p 8301:8301/udp -p 8312:8302/tcp -p 8302:8302/udp -p 8400:8400 -p 8500:8500 -p 53:53/udp -p 8600:8600  --name=consul -e SERVICE_IGNORE=true -h $(hostname) gliderlabs/consul-server -node=$(hostname)  -atlas=vkalvaku/dem -atlas-join -atlas-token=$ATLAS_TOKEN -advertise $(wanip)
	if [[ "$(docker ps -a -q --filter ancestor=gliderlabs/registrator)" != "" ]]; then
		docker rm $(docker stop $(docker ps -a -q --filter ancestor=gliderlabs/registrator --format="{{.ID}}"))
	fi
fi

echo 'Checking registrator status and installing if required'

if [[ "$(docker ps -q --filter ancestor=gliderlabs/registrator)" == "" ]]; then
        
     sudo docker run -d --name=registrator --net=host --volume=/var/run/docker.sock:/tmp/docker.sock gliderlabs/registrator:latest consul://localhost:8500
fi

# python /data_ingestor/startup_scripts/check_gunicorn.py
# if [[ $? = 0 ]]; then
#     echo "gunicorn is already installed"
# else
#     echo "installing gunicorn"
#     pip install gunicorn
# fi


# python /data_ingestor/startup_scripts/check_virtualenv.py
# if [[ $? = 0 ]]; then
#     echo "virtualenv is already installed"
# else
#     echo "installing virtualenv"
# fi




