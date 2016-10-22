command_exists() {
        command -v "$@" > /dev/null 2>&1
}
if command_exists docker; then
                version="$(docker -v | awk -F '[ ,]+' '{ print $3 }')"
                MAJOR_W=1
                MINOR_W=10
		echo 'Docker is already installed'

else
		echo 'Installing Docker'
                sudo yum update -y
                sudo yum install -y docker
                sudo service docker start
fi

echo 'creating network between docker..'
docker network create my-fancy-network

echo 'creatinga a link between middleware and data_ingestor..'
docker run -i -t --name sagarkrkv/middleware --link njetty/data_ingestor


echo 'Killing any container of the old Docker image'
docker rm $(docker stop $(docker ps -a -q --filter ancestor=sagarkrkv/middleware --format="{{.ID}}"))

echo 'Pulling a new image from docker'
docker pull sagarkrkv/middleware