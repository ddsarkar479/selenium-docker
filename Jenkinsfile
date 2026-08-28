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

                bat "docker build -t=debdeeptestautomation/selenium-framework ."

            }

        }

        stage('Push-Image'){
            steps{

                bat "docker push debdeeptestautomation/selenium-framework "

            }

        }
    }


}