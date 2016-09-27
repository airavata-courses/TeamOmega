#!/bin/bash

cd /home/ec2-user/TeamOmega/middleware

echo "Installing weather prediction service..."


echo "installing npm dependencies"

npm install

echo "starting application using pm2"
pm2 kill
pm2 start bin/www --watch -f
pm2 start /home/ec2-user/TeamOmega/data_ingestor/wsgi.py --interpreter /home/ec2-user/TeamOmega/data_ingestor/venv/bin/python --watch -f
pm2 save