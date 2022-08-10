
pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *')
        cron('@midnight')
    }

    stages {

        stage('Test') {
            steps {
               echo 'Test....'
               sh "./gradlew clean test"
            }
        }
        stage('Build Docker') {
            steps {
                echo 'Build Docker....'
                sh "./gradlew build"
            }
        }
    }

    post {
      // Always runs. And it runs before any of the other post conditions.
      always {
         archive "target/**/*"
         junit allowEmptyResults: true, keepLongStdio: true, testResults: 'build/test-results/test/*.xml'
         publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'build/reports/tests/test/', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])

         // Wipe the workspace so we are building completely clean
         //deleteDir();
      }

      success {
             echo 'Success....'
             archiveArtifacts 'build/libs/*.jar'
             echo 'Archive artifacts....'
             echo 'Clean Workspace....'
             cleanWs()
            //mail(from: "bob@example.com", to: "steve@example.com", subject: "That build passed.", body: "Nothing to see here")
      }

      failure {
              echo 'Failure....'
              echo 'Clean Workspace....'
              cleanWs()
            //mail(from: "bob@example.com", to: "steve@example.com", subject: "That build failed!", body: "Nothing to see here")
      }
    }

}
}