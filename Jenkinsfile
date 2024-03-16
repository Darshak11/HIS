pipeline {
    environment {
        backend = 'his_backend-image' // Specify your backend Docker image name/tag
        frontend = 'his_frontend-image' // Specify your frontend Docker image name/tag
        mysqlImage = 'mysql:latest' // Specify the MySQL Docker image
        mysqlContainerName = 'mysql-container' // Specify the name for your MySQL container
        MYSQL_ROOT_PASSWORD = '123456'
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
                    sh  'docker run --name mysqldb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} -d -v "/var/lib/mysql" mysql:latest'
                }
            }
        }
        
        stage('Stage 1: Git Clone') {
            steps {
                echo 'Cloning the Git repository'
                git branch: 'Karan', url: 'https://github.com/Darshak11/HIS.git', credentialsId: 'github-credentials'
            }
        }

        stage('Stage 2: Build Spring Boot backend') {
            steps {
                echo 'Building Spring Boot backend'
                sh 'mvn clean install'
            }
        }
        
        stage('Stage 3: Build backend Docker Image') {
            steps {
                echo 'Building backend Docker image'
                sh "docker build -t karanjit708/${backend} ."
            }
        }
        
        stage('Stage 4: Push backend Docker image to DockerHub') {
            steps {
                echo 'Pushing backend Docker image to DockerHub'
                script {
                    docker.withRegistry('', 'DockerCred') {
                        sh 'docker push karanjit708/${backend}'
                    }
                }
            }
        }
        
        stage('Stage 5: Clean docker images') {
            steps {
                script {
                    sh 'docker container prune -f'
                    sh 'docker image prune -f'
                }
            }
        }
    }
}