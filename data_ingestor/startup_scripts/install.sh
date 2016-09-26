cd '~/data_ingestor'

if [ ! -d "$venv"]; then
	echo "initializing virtualenv"
	virtualenv venv
fi

source venv/bin/activate

echo "installing the requirements from requirements.txt.."
pip install -r requirements.txt

echo "starting data_ingestor on pm2 server.."
pm2 start ~/data_ingestor/wsgi.py

