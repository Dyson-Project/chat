pipeline {
    agent any
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew assemble'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh './gradlew docker'
            }
        }
        stage('Remove container'){
           steps{
               sh  './gradlew dockerStop'
               sh  './gradlew dockerRemoveContainer'
           }
        }
        stage('Run Docker Image') {
            steps {
                sh './gradlew dockerRun'
            }
        }
    }
}
