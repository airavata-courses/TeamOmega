#!/bin/bash

echo 'check if python is installed'
python --version
if [ "$?" -ne 0 ]; then
	echo 'installing python..'
		sudo yum install python27  
fi

echo 'check if pip is installed'
pip --help
if [ "$?" -ne 0 ]; then
	echo 'installing pip..'
		sudo curl -o /tmp/ez_setup.py  https://bootstrap.pypa.io/get-pip.py 
        sudo python /tmp/ez_setup.py
        sudo /usr/bin/easy_install-2.7 pip  
fi
sudo fuser -k 4000/tcp


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




