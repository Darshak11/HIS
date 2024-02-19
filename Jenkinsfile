pipeline {
    environment {
        backend = 'backend-image' // Specify your backend Docker image name/tag
        frontend = 'frontend-image' // Specify your frontend Docker image name/tag
        mysqlImage = 'mysql:latest' // Specify the MySQL Docker image
        mysqlContainerName = 'mysql-container' // Specify the name for your MySQL container
        MYSQL_ROOT_PASSWORD = 'Kota@2020'
        MYSQL_PORT = '3306'
        docker_image = ''
        NETWORK = 'deployment_my-network'
        
    }
    
    agent any

    stages {
        
        stage('Stage 0: Pull MySQL Docker Image') {
            steps {
                echo 'Pulling MySQL Docker image from DockerHub'
                script {
                    docker.withRegistry('', 'DockerCred') {
                        docker.image("${mysqlImage}").pull()
                    }
                }
            }
        }
        stage('Stage 0.1: Run MySQL Container') {
            steps {
                script {
                    sh  'docker container stop mysqldb'
                    sh  'docker container rm mysqldb'
                    sh  'docker run --name mysqldb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} -d -v "/var/lib/mysql" --network=${NETWORK} mysql:latest'
                }
            }
        }
        
        stage('Stage 1: Git Clone') {
            steps {
                echo 'Cloning the Git repository'
                git branch: 'main', url: 'https://github.com/KaranjitSaha/Employee-Management-System.git'
            }
        }

        stage('Stage 2: Build Spring Boot backend') {
            steps {
                echo 'Building Spring Boot backend'
                sh 'mvn clean install'
            }
        }
    }
}