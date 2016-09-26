echo "installing the requirements from requirements.txt.."
pip install -r requirements.txt

echo "starting gunicorn server.."
gunicorn -w 2 -b 127.0.0.1:4000 wsgi

