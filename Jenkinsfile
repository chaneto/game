
pipeline {
   agent any

   triggers {
       pollSCM('*/5 * * * *')
       //cron('* * * * *')
       // Nightly @12am, for "snapshot", skip "release" night.
       //cron('0 0 2-31/2 * *')
       // First of the month @12am, for "release" (also "current").
       //cron('0 0 1 * *')
           }

    stages {
        stage ('Build') {
         steps {
           echo 'Building..'
           }
        }
        stage ('Test') {
         steps {
            //echo 'Tests..'
             // sh 'make check || true'
              junit '**/target/*.xml'
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

      post {
            failure {
                mail to: 'chanetol_80@abv.bg', subject: 'Build failed', body: 'Please fix!'
            }
        }
 }



 def gradlew(String... args) {
     sh "./gradlew ${args.join(' ')} -s"
 }

