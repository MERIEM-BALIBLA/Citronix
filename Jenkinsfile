pipeline{
    agent any
    stages {

        stage('Build and SonarQube Analysis') {
            steps {
                echo "Running Maven build and SonarQube analysis..."
                withSonarQubeEnv('MySonarQubeServer') {
                    sh """
                    mvn clean package sonar:sonar
                    """
                }
            }
        }
        stage('Quality Gate Check') {
            steps {
                script {
                    echo "Waiting for SonarQube Quality Gate..."
                    def qualityGate = waitForQualityGate()
                    if (qualityGate.status != 'OK') {
                        error "Quality Gate failed :x::x::x: Stopping the build."
                    }
                    echo "Quality Gate passed :heavy_check_mark::heavy_check_mark::heavy_check_mark: Proceeding..."
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
        stage('Pre Deploy') {
            steps {
                script {
                    echo "Deploying Docker container..."
                    sh """
                    docker-compose down || true
                    docker rmi citronix:latest || true
                    """
                }
            }
        }
        stage('Deploy'){
            steps{
                script {
                    echo "Deploying Docker container..."
                    sh """
                        docker-compose up -d
                    """
                }
            }
        }
    }

    post {
        success {
            mail to: 'merrybalibla@gmail.com',
                 subject: "Success: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                 body: "The job passed. Check it here: ${env.BUILD_URL}"
        }
        failure {
            mail to: '@gmail.com',
                 subject: "Failed: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                 body: "The job failed. Check it here: ${env.BUILD_URL}"
        }
    }
}