pipeline {
  agent any
  stages {
    stage('Gradle Build') {
      steps {
        sh 'gradle clean build'
      }
    }
    stage('Veracode Pipeline Scan') {
      steps {
        sh 'curl -O https://downloads.veracode.com/securityscan/pipeline-scan-LATEST.zip'
        sh 'unzip pipeline-scan-LATEST.zip pipeline-scan.jar'
        sh 'java -jar pipeline-scan.jar \
          --veracode_api_id "${VERACODE_API_ID}" \
          --veracode_api_key "${VERACODE_API_SECRET}" \
          --file "build/libs/sample.jar" \
          --fail_on_severity="Very High, High" \
          --fail_on_cwe="80" \
          --baseline_file "${CI_BASELINE_PATH}" \
          --timeout "${CI_TIMEOUT}" \
          --project_name "${env.JOB_NAME}" \
          --project_url "${env.GIT_URL}" \
          --project_ref "${env.GIT_COMMIT}"'
      }
    }
  }
  post {
    always {
      archiveArtifacts artifacts: 'results.json', fingerprint: true
    }
  }
}

def gradlew(String... args) {
    sh "./gradlew ${args.join(' ')} -s"
}