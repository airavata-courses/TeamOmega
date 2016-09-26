
echo 'check if npm is installed'
npm -v
echo 'installing node..'
# sudo curl -L https://www.npmjs.com/install.sh | sh
sudo yum install npm -y --enablerepo=epel
sudo npm install npm@latest -y -g


# echo 'check if node is installed'
# node -v
echo 'installing node..'
# sudo curl --silent --location https://rpm.nodesource.com/setup_4.x | bash -
# sudo yum -y install nodejs
sudo npm cache clean -f
sudo npm install -g n
sudo n 4.4.2
sudo ln -sf /usr/local/n/versions/node/4.4.2/bin/node /usr/bin/node

echo "Installing pm2 application server for nodejs"
npm install pm2@latest -g ; pm2 update

echo "starting pm2 server"
sudo su -c "env PATH=$PATH:/usr/local/bin pm2 startup amazon-u ec2-user --hp /home/ec2-user"