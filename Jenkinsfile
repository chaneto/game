pipeline {

    agent any

    triggers {
        cron('H */8 * * *') //regular builds
        //cron('* * * * *') //regular builds
        pollSCM('* * * * *') //polling for changes, here once a minute
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
    post {
         failure {
                   mail to: 'chaneto_80@abv.bg', subject: 'Build failed', body: 'Please fix!'
               }
    }
}

def gradlew(String... args) {
    sh "./gradlew ${args.join(' ')} -s"
}