pipeline {
  agent any
  environment {
    DOCKER_REGISTRY = 'localhost:5000'
    IMAGE_NAME = 'sample-node'
    IMAGE_TAG = "${env.BUILD_NUMBER}"
  }
  stages {
    stage('Checkout') {
      steps {
        git url: 'https://github.com/kofti-chris/sample-node.git', branch: 'master'
      }
    }
    stage('Build') {
      steps {
        sh 'npm install'
      }
    }
    stage('Test') {
      steps {
        sh 'echo "running tests" '
        // MUST IMPLEMENT TEST
      }
    }
    stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}")
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    dockerImage.push()
                    dockerImage.push('latest')
                }
            }
        }
    }
    post {
        always {
            sh 'docker rmi ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} || true'
            sh 'docker rmi ${DOCKER_REGISTRY}/${IMAGE_NAME}:latest || true'
        }
    }
  }
}
