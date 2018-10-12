node {
   def mvnHome = tool 'M3'
   
   stage('Loading Source Code') { 
      git 'https://github.com/majrul/myrepo.git'
   }
   
   dir('spring-boot-trial') {
       stage('Building the Application') {
          if (isUnix()) {
             sh "'${mvnHome}/bin/mvn' clean package install"
          } else {
             bat(/"${mvnHome}\bin\mvn" clean package install/)
          }
       }
       stage('Archive Results') {
          junit '**/target/surefire-reports/TEST-*.xml'
          archiveArtifacts artifacts: 'target/*.jar'
       }
   }
}
