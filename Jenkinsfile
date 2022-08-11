//
// pipeline {
//    agent any
//
//    triggers {
//        pollSCM('*/5 * * * *')
//        //cron('* * * * *')
//        // Nightly @12am, for "snapshot", skip "release" night.
//        //cron('0 0 2-31/2 * *')
//        // First of the month @12am, for "release" (also "current").
//        //cron('0 0 1 * *')
//            }
//
//     stages {
//         stage ('Build') {
//          steps { echo 'Building..'
//            }
//         }
//         stage ('Test') {
//          steps { echo 'Tests..'
//             }
//         }
//         stage ('QA') {
//           steps { echo 'QA..'
//               }
//         }
//         stage ('Deploy') {
//           steps { echo 'Deploy..'
//               }
//         }
//         stage ('Monitor') {
//           steps { echo 'Monitor..'
//               }
//         }
//
//     }
//
//       post {
//             failure {
//                 mail to: 'chaneto_80@abv.bg', subject: 'Build failed', body: 'Please fix!'
//             }
//         }
//  }


pipeline {
    agent any

    triggers {
        pollSCM('*/5 * * * *')
    }

    stages {
        stage('Compile') {
            steps {
                gradlew('clean', 'classes')
            }
        }
        stage('Unit Tests') {
            steps {
                gradlew('test')
            }
            post {
                always {
                    junit '**/build/test-results/test/TEST-*.xml'
                }
            }
        }
        stage('Long-running Verification') {
            environment {
                SONAR_LOGIN = credentials('SONARCLOUD_TOKEN')
            }
            parallel {
                stage('Integration Tests') {
                    steps {
                        gradlew('integrationTest')
                    }
                    post {
                        always {
                            junit '**/build/test-results/integrationTest/TEST-*.xml'
                        }
                    }
                }
                stage('Code Analysis') {
                    steps {
                        gradlew('sonarqube')
                    }
                }
            }
        }
        stage('Assemble') {
            steps {
                gradlew('assemble')
                stash includes: '**/build/libs/*.war', name: 'app'
            }
        }
        stage('Promotion') {
            steps {
                timeout(time: 1, unit:'DAYS') {
                    input 'Deploy to Production?'
                }
            }
        }
        stage('Deploy to Production') {
            environment {
                HEROKU_API_KEY = credentials('HEROKU_API_KEY')
            }
            steps {
                unstash 'app'
                gradlew('deployHeroku')
            }
        }
    }
    post {
        failure {
            mail to: 'benjamin.muschko@gmail.com', subject: 'Build failed', body: 'Please fix!'
        }
    }
}


 def gradlew(String... args) {
     sh "./gradlew ${args.join(' ')} -s"
 }

