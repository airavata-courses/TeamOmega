#!/bin/bash

cd /home/ec2-user/TeamOmega/data_ingestor
echo pwd
pip install guincorn
virtualenv venv
if [ "$?" -ne 0 ]; then
    pip install virtualenv
    virtualenv venv
fi


source venv/bin/activate
pip install --upgrade pip
echo "installing the requirements from requirements.txt.."
pip install -r requirements.txt
pip install gunicorn
echo "starting data_ingestor on guincorn.."
gunicorn -b 0.0.0.0:5678 wsgi --daemon&
if  [ "$?" -ne 0 ]; then
	echo ""
fi
