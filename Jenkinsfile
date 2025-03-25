pipeline {
    agent any
    
    environment {
        DOCKERHUB_CREDENTIALS = credentials('docker-hub-credentials')
        DOCKER_IMAGE = 'laxmanjavaappdev/springboot-app'
        KUBECONFIG = credentials('kubeconfig')
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/kumar89lax/springboot-rest-api.git'
            }
        }
        
        stage('Build with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${env.BUILD_NUMBER}")
                }
            }
        }
        
        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                        docker.image("${DOCKER_IMAGE}:${env.BUILD_NUMBER}").push()
                        docker.image("${DOCKER_IMAGE}:${env.BUILD_NUMBER}").push('latest')
                    }
                }
            }
        }
        
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Apply MySQL deployment
                    sh 'kubectl apply -f k8s/mysql-deployment.yaml'
                    
                    // Wait for MySQL to be ready
                    sh 'kubectl wait --for=condition=ready pod -l app=mysql --timeout=300s'
                    
                    // Update the deployment file with the new image tag
                    sh "sed -i 's|image: ${DOCKER_IMAGE}:latest|image: ${DOCKER_IMAGE}:${env.BUILD_NUMBER}|g' k8s/springboot-deployment.yaml"
                    
                    // Apply Spring Boot deployment
                    sh 'kubectl apply -f k8s/springboot-deployment.yaml'
                    
                    // Wait for Spring Boot app to be ready
                    sh 'kubectl wait --for=condition=ready pod -l app=springboot-app --timeout=300s'
                }
            }
        }
    }
    
    post {
        always {
            // Clean up workspace
            cleanWs()
        }
        success {
            echo 'Deployment successful! Access the application at http://localhost:30007'
        }
        failure {
            echo 'Deployment failed. Check logs for details.'
        }
    }
}