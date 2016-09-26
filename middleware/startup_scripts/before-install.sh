echo 'check if node is installed'
node -v
if [ "$?" -ne 0 ]; then
	echo 'installing node..'
		sudo yum install nodejs npm --enablerepo=epl
fi
echo 'check if npm is installed'
npm -v
if [ "$?" -ne 0 ]; then
	echo 'installing node..'
		sudo yum install npm
fi


echo "Installing pm2 application server for nodejs"
sudo npm install -g pm2

echo "starting pm2 server"
sudo su -c "env PATH=$PATH:/usr/local/bin pm2 startup amazon"