#!/bin/bash
mkdir /home/ec2-user/data
cd /home/ec2-user/TeamOmega/middleware

deactivate
if [ "$?" -ne 0 ]; then
	echo "Already deactivated"

echo "Installing weather prediction service..."


echo "installing npm dependencies"

npm install
sudo node bin/www > stdout.txt 2> stderr.txt &
# sudo pm2 kill
# echo "starting application using pm2"
# sudo pm2 start bin/www --watch -p 3000

if [ "$?" -ne 0 ]; then
	echo "Already running"
