cd "~/TeamOmega/middleware"

echo "Installing weather prediction service..."


echo "installing npm dependencies"

npm install

echo "starting application using pm2"
pm2 start './bin/www' --watch