pipeline{

    agent any

    stages{

        stage('Build-jar'){
            steps{
                bat "mvn clean package -DskipTests"
            }
        }

        stage('Build-image'){
            steps{
                bat "docker build -t debdeeptestautomation/selenium-framework:latest ."
            }
        }

        stage('Push-Image'){
            environment {
                DOCKER_HUB = credentials('docker-creds')
            }
            steps{
                bat "docker login -u %DOCKER_HUB_USR% -p %DOCKER_HUB_PSW%"
                bat "docker push debdeeptestautomation/selenium-framework:latest"
            }
        }
    }

    post {
        always {
            bat "docker logout"
        }
    }
}
