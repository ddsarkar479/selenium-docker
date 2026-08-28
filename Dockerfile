FROM bellsoft/liberica-openjdk-alpine:17

# Creating workspace
WORKDIR /home/selenium-docker

# Add required files . Here it is under docker-resources
ADD target/docker-resources ./

# Run test
CMD ["java", "-Dselenium.grid.hubHost=hub", "-cp", "libs/*", "org.testng.TestNG", "test-suites/flight-reservation.xml"]