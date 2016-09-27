#!/bin/bash

cd /home/ec2-user/TeamOmega/data_ingestor
echo pwd
pip install guincorn
virtualenv venv
if [ "$?" -ne 0 ]; then
    pip install virtualenv
    virtualenv venv
fi


source venv/bin/activate
pip install
fi
