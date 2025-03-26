pipeline {
    agent any
    
    environment {
        DOCKER_REGISTRY_CREDS = credentials('docker_hub_creds')
        KUBECONFIG = credentials('kubeconfig')
        DOCKER_IMAGE = 'laxmanjavaappdev/springboot-app'
        BUILD_TAG = "${env.BUILD_NUMBER}"
    }
    
    stages {
        stage('Initialize') {
            steps {
                sh '''
                    mkdir -p ~/.kube && \
                    cp "$KUBECONFIG" ~/.kube/config && \
                    chmod 600 ~/.kube/config
                '''
            }
        }
        
        stage('Build and Push') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker_hub_creds') {
                        // Explicitly use root user for Docker commands
                        sh 'sudo docker build -t ${DOCKER_IMAGE}:${BUILD_TAG} -f Dockerfile .'
                        sh 'sudo docker push ${DOCKER_IMAGE}:${BUILD_TAG}'
                        sh 'sudo docker push ${DOCKER_IMAGE}:latest'
                    }
                }
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    sh 'kubectl apply -f k8s/mysql-deployment.yaml'
                    sh 'kubectl wait --for=condition=ready pod -l app=mysql --timeout=300s'
                    sh """
                        sed -i 's|image: ${DOCKER_IMAGE}:latest|image: ${DOCKER_IMAGE}:${BUILD_TAG}|g' k8s/springboot-deployment.yaml
                    """
                    sh 'kubectl apply -f k8s/springboot-deployment.yaml'
                }
            }
        }
    }
    
    post {
        always {
            sh 'rm -f ~/.kube/config || echo "Cleanup skipped"'
        }
        failure {
            echo "DEBUG: Printing Kubernetes resources..."
            sh 'kubectl get pods --all-namespaces'
            sh 'kubectl describe pods -l app=springboot-app || true'
        }
    }
}
