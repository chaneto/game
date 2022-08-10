pipeline {
  agent any
  stages {

    stage('ready application') {
      parallel {
        stage('compile') {
          steps {
            sh './gradlew classes'
          }
        }

        stage('test') {
          steps {
            sh './gradlew test'
          }
        }

      }
    }

    stage('run checks') {
      parallel {
        stage('checkstyle') {
          steps {
            sh './gradlew check'
          }
        }

        stage('security checks') {
          steps {
            sh './gradlew dependencyCheckAnalyze'
          }
        }

      }
    }

    stage('Staging') {
      steps {
        echo 'Build Docker image'
        sh './gradlew dockerBuildImage'
      }
    }

  }
  tools {
    gradle 'gradle6.5'
  }
}