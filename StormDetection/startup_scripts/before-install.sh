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




