echo 'Installing Science_Gateway_Omega to Maven'
cd '/home/ec2-user/detection/detection'
mvn -e clean install
mvn exec:java >> /var/log/sga-omega-api-spark.log 2>&1 &
sleep 60
