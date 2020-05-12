pipeline {
    agent any

    tools {
        jdk 'open-jdk-13.0.2+8'
        }
    stages {
        stage('Build') {
            steps {
                sh 'echo "JAVA_HOME:  $JAVA_HOME"'
               sh 'mvn clean test-compile'
            }
        }
        stage('Testing') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true test'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
