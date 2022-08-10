
pipeline {
agent any
    stages {
        stage ('Build') {
         steps { echo 'Building..'
           }
        }
        stage ('Test') {
//          steps { echo 'Tests..'
//             }
             steps {
                           gradlew('test')
                       }
                       post {
                           always {
                               junit '**/build/test-results/test/TEST-*.xml'
                           }
                       }
        }
        stage ('QA') {
          steps { echo 'QA..'
              }
        }
        stage ('Deploy') {
          steps { echo 'Deploy..'
              }
        }
        stage ('Monitor') {
          steps { echo 'Monitor..'
              }
        }

    }
 }