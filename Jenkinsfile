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
                sh 'kubectl cluster-info'
            }
        }
        
        stage('Build and Push') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker_hub_creds') {
                        // Build with explicit path to Dockerfile
                        docker.build("${DOCKER_IMAGE}:${BUILD_TAG}", "-f Dockerfile .")
                        docker.image("${DOCKER_IMAGE}:${BUILD_TAG}").push()
                        docker.image("${DOCKER_IMAGE}:${BUILD_TAG}").push('latest')
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
            sh 'rm -f ~/.kube/config || echo "Cleanup skipped"'  # Non-blocking cleanup
        }
        failure {
            echo "DEBUG: Printing Kubernetes resources..."
            sh 'kubectl get pods --all-namespaces'
            sh 'kubectl describe pods -l app=springboot-app || true'
        }
    }
}
