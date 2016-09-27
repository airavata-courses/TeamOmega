cd '/home/ec2-user/clustering'
mvn -e clean install
mvn exec:java >> /var/log/sga-omega-api-clustering.log 2>&1 &
sleep 60
