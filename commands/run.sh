git pull
mvn clean install -Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts -Djavax.net.ssl.trustStorePassword=changeit
java -jar target/playground.jar server ./config/local.yml