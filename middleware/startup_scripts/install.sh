cd '/home/ec2-user/TeamOmega/data_ingestor'

echo "Installing weather prediction service..."


echo "installing npm dependencies"

npm install

echo "starting application using pm2"
pm2 start './bin/www' --watch