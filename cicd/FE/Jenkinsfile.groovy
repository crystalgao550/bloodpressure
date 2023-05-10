pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'npm ci'
      }
    }
    stage('Test') {
      steps {
        sh 'npm test'
      }
    }
    stage('Build Docker Image') {
      steps {
        sh 'docker build -t abhishekf5/node-service:${GIT_COMMIT} -f ProductServiceDockerfile .'
      }
    }
    stage('Push Docker Image') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'docker-registry-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
          sh 'docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD} abhishekf5'
          sh 'docker push abhishekf5/node-service:${GIT_COMMIT}'
        }
      }
    }
    stage('Deploy to Kubernetes') {
      steps {
        withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
          sh 'kubectl --kubeconfig ${KUBECONFIG} set image deployment/node-service node-service=abhishekf5/node-service:${GIT_COMMIT} --record'
        }
      }
    }
  }
}
