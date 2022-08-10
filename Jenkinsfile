pipeline {
    agent any

    stages {
        stage ('Compile Stage') {

            steps {
		    sh "gradle build"
		   // bat "gradle build"
               }
        }

        stage ('Testing Stage') {

            steps {

                     sh "gradle test"
		    // bat "gradle test"


            }
        }


        stage ('Deployment Stage') {
            steps {

                   sh "gradle deploy"
		//bat "gradle deploy"
            }
        }
    }
}