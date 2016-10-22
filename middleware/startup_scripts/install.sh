#!/bin/bash

if [ ! -d "$/home/ec2-user/data" ]; then
	echo "Creating data dir"
	mkdir /home/ec2-user/data
fi
cd /home/ec2-user/TeamOmega/middleware


echo "Installing weather prediction service..."


echo "installing npm dependencies"

npm install
sudo node bin/www > /home/ec2-user/stdout.txt 2> /home/ec2-user/stderr.txt &
# sudo pm2 kill
# echo "starting application using pm2"
# sudo pm2 start bin/www --watch -p 3000

if [ "$?" -ne 0 ]; then
	echo "Already running"
fi