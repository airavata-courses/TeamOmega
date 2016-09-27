#!/bin/bash

echo 'check if npm is installed'

echo 'installing node..'
sudo curl --silent --location https://rpm.nodesource.com/setup_4.x | sudo bash -
sudo yum -y install nodejs


echo 'check if node is installed'
node -v
if [ "$?" -ne 0 ]; then
	echo 'installing node..'
	sudo yum install npm -y --enablerepo=epel
	sudo npm install npm@latest -y -g
fi

# sudo curl --silent --location https://rpm.nodesource.com/setup_4.x | bash -
# sudo yum -y install nodejs
# sudo npm cache clean -f
# sudo npm install -g n
# sudo n 4.4.2
# sudo ln -sf /usr/local/n/versions/node/4.4.2/bin/node /usr/bin/node

# pm2 update
# if [ "$?" -ne 0 ]; then
# 	echo "Installing pm2 application server for nodejs"
# 	sudo npm install pm2@latest -g
# 	sudo su -c "env PATH=$PATH:/usr/local/bin pm2 startup amazon -u ec2-user --hp /home/ec2-user"
# fi
# echo "starting pm2 server"

