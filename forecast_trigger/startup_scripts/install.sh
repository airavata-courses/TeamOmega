#!/bin/bash
echo 'Running the downloaded container'

sudo docker run --restart unless-stopped -d -p 9000:9000 sagarkrkv/forecast_trigger
