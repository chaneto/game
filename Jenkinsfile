node {
   stage('Get source code') {
      echo 'getting source code'
      git url: 'https://github.com/hivp/hellofrom.git', branch: 'kafka'
   }
   stage('Build with Gradle') {
      if(isUnix()) {
        sh './gradlew clean build'
      } else {
        bat 'gradlew.bat clean build'
      }
   }
   stage('Generate docker image in local registry') {
      if(isUnix()) {
        sh 'docker build -t hellofrom:1.0 .'
      } else {
        bat 'docker build -t hellofrom:1.0 .'
      }
   }
   /*
   stage('Upload to Docker Hub'){
     if(isUnix()) {
       sh 'docker tag hellofrom:1.0 hugovarela/hellofrom:1.0'
       sh 'docker push hugovarela/hellofrom:1.0'
     } else {
       bat 'docker tag hellofrom:1.0 hugovarela/hellofrom:1.0'
       bat 'docker push hugovarela/hellofrom:1.0'
     }
   }
*/
}