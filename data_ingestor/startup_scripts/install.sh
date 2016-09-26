cd '/home/ec2-user/data_ingestor'

if [ ! -d "$venv"]; then
	echo "initializing virtualenv"
	virtualenv venv
fi

source venv/bin/activate
pip install --upgrade pip
echo "installing the requirements from requirements.txt.."
pip install -r requirements.txt

echo "starting data_ingestor on pm2 server.."
pm2 start wsgi.py >> /var/log/sga-omega-wsgi.log 2>&1 &
sleep 60