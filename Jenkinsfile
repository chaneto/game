pipeline {
  agent any
  stages {
    stage('Build') {
      post {
        failure {
          script {
            message="failure"
          }

        }

        success {
          script {
            message="Success"
          }

        }

      }
      steps {
        //bat 'gradle build'
        //bat 'gradle javadoc'
        archiveArtifacts 'build/libs/*.jar'
        //archiveArtifacts 'build/docs/javadoc/*'
        //junit 'build/test-results/test/*.xml'
      }
    }

    stage('Mail Notification') {
      steps {
        mail(subject: 'Build Notification', body: "${message}", from: 'stoyan.stoyanov@brainstarstech.com', to: 'chaneto_80@abv.bg')
      }
    }

    stage('Code Analysis') {
      parallel {
        stage('Code Analysis') {
          steps {
            withSonarQubeEnv('sonar') {
              bat 'gradle sonarqube'
            }

            waitForQualityGate true
          }
        }

        stage('Test Reporting') {
          steps {
            jacoco(execPattern: 'build/jacoco/*.exec', exclusionPattern: '**/test/*.class')
          }
        }

      }
    }

    stage('Deployment') {
      when {
        branch '*'
      }
      steps {
        bat 'gradle publish'
      }
    }

    stage('slack ') {
      when {
        branch '*'
      }
      steps {
        slackSend(baseUrl: 'https://hooks.slack.com/services/', teamDomain: 'outils-workspace', token: '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', message: 'deployee', channel: 'general')
      }
    }

  }
}