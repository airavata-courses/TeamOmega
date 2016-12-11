echo 'Running the downloaded container'
sudo docker run --restart unless-stopped -d -p 3000:3000 sagarkrkv/middleware
