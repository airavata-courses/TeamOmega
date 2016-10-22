echo 'Creating network..'

docker network create my-fancy-network

echo 'Running the downloaded container '

sudo docker run -d -p 3000:3000 sagarkrkv/middleware --net=my-fancy-network --net-alias=middleware