# room-service
To build docker image, do

   ###docker build --tag room-service .
   
To run the images, do :
###docker run -p room-service 
docker run -p 8080:8080 room-service --network=host

