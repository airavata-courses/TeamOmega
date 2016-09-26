echo 'check if python is installed'
python --version
if [ "$?" -ne 0 ]; then
	echo 'installing python..'
		sudo yum install python27  
fi

echo 'check if pip is installed'
pip --help
if [ "$?" -ne 0 ]; then
	echo 'installing pip..'
		curl -o https://bootstrap.pypa.io/get-pip.py
		sudo python27 get-pip.py  
fi


python check_gunicorn.py
if [[ $? = 0 ]]; then
    echo "gunicorn is already installed"
else
    echo "installing gunicorn"
    pip install gunicorn
fi


python check_virtualenv.py
if [[ $? = 0 ]]; then
    echo "virtualenv is already installed"
else
    echo "installing virtualenv"
    pip install virtualenv
fi

cd '~/TeamOmega/data_ingestor'

if [ ! -d "$venv"]; then
	echo "initializing virtualenv"
	virtualenv venv
fi

source venv/bin/activate



