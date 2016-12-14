echo 'Running the downloaded container'
sudo docker run --restart unless-stopped -d -v /Gif_Files:/usr/src/app/Gif_Files -p 3000:3000 sagarkrkv/middleware
