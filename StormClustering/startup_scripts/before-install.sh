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
docker rm $(docker stop $(docker ps -a -q --filter ancestor=sagarkrkv/stormclustering --format="{{.ID}}"))

if  [ "$?" -ne 0 ]; then
	echo ""
fi

echo 'Pulling a new image from docker'
docker pull sagarkrkv/stormclustering



# #!/bin/bash

# echo 'check if python is installed'
# python --version
# if [ "$?" -ne 0 ]; then
# 	echo 'installing python..'
# 		sudo yum install python27  
# fi

# echo 'check if pip is installed'
# pip --help
# if [ "$?" -ne 0 ]; then
# 	echo 'installing pip..'
# 		sudo curl -o /tmp/ez_setup.py  https://bootstrap.pypa.io/get-pip.py 
#         sudo python /tmp/ez_setup.py
#         sudo /usr/bin/easy_install-2.7 pip  
# fi
# sudo fuser -k 5678/tcp


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




