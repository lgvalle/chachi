pipeline {
  agent any
  stages {
    stage('Verifications') {
      parallel {
        stage('Android Lint') {
          steps {
            script {
              aaa
            }
            
          }
        }
        stage('FindBugs') {
          steps {
            sh 'aaa'
          }
        }
        stage('Unit tests') {
          steps {
            sh 'aaaa'
          }
        }
      }
    }
    stage('Build') {
      steps {
        sh 'aaaa'
      }
    }
    stage('Integration Tests') {
      steps {
        sh 'aaa'
      }
    }
    stage('Archive') {
      parallel {
        stage('Save APK') {
          steps {
            sh 'aaa'
          }
        }
        stage('Distribute to Slack') {
          steps {
            sh 'aa'
          }
        }
      }
    }
  }
}