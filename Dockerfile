FROM openjdk:8u191-jre-alpine3.8

RUN apk add curl jq maven git


# Workspace
WORKDIR /usr/share/innroad

# ADD .jar under target from host into this image
ADD target/selenium-docker.jar 			selenium-docker.jar
ADD target/selenium-docker-tests.jar 	selenium-docker-tests.jar

# ADD dependency libraries into this image
ADD target/libs							libs

# ADD required project folders into this image
ADD config-files						config-files
ADD extent-reports						extent-reports
#ADD externalFiles						externalFiles
ADD Images								Images
#ADD log								log
ADD resources							resources
ADD screenshots							screenshots
COPY test-data							test-data
ADD schema-files						schema-files


# ADD batch files into this image
ADD CentralParkSanity-Failed.bat		CentralParkSanity-Failed.bat
ADD Testng.bat							Testng.bat

# ADD all other resource files into this image
ADD ConfirmationNumber.txt				ConfirmationNumber.txt
ADD extent-config.xml					extent-config.xml
ADD GiftCertificate.txt					GiftCertificate.txt
ADD log4j.properties					log4j.properties


# in case of any other dependency like .csv / .json / .xls
# please ADD that as well

# ADD suite files
ADD testng.xml						testng.xml
ADD sanitysuite.xml					sanitysuite.xml
ADD testng_gs.xml					testng_gs.xml
ADD testng.CP_Reservation.xml		testng.CP_Reservation.xml
ADD vrbo_test.xml					vrbo_test.xml

# ADD health check script
#ADD healthcheck.sh                      healthcheck.sh
RUN wget https://s3.amazonaws.com/selenium-docker/healthcheck/healthcheck.sh

# BROWSER
# HUB_HOST
# MODULE

# ENTRYPOINT java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* -DBROWSER=$BROWSER -DHUB_HOST=$HUB_HOST org.testng.TestNG $MODULE
ENTRYPOINT sh healthcheck.sh
