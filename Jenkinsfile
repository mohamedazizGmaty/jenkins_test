pipeline {
    agent any
    triggers {
        githubPush()
 
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Clean Target') {
            steps {
                sh 'rm -rf target'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t student-management:latest .'
            }
        }

      /*  stage('Start DB') {
            steps {
                sh 'docker compose -f docker-compose.test.yml up -d'
            }
        }
        
        stage('Wait for DB') {
            steps {
                script {
                    sh '''
                        echo "Waiting for PostgreSQL to be ready..."
                        for i in {1..30}; do
                            if docker compose -f docker-compose.test.yml ps --services --filter "health=healthy" | grep -q db; then
                                echo "PostgreSQL is ready!"
                                break
                            elif [ $i -eq 30 ]; then
                                echo "Timeout waiting for PostgreSQL"
                                docker compose -f docker-compose.test.yml logs db
                                exit 1
                            else
                                echo "Attempt $i/30: PostgreSQL not ready, waiting 2 seconds..."
                                sleep 2
                            fi
                        done
                    '''
                }
            }
        }

        stage('Test') {
        steps {
        sh 'mvn test -Dspring.profiles.active=test'
            }
        }

        stage('Stop DB') {
    steps {
        sh 'docker compose -f docker-compose.test.yml down'
    }
         }*/
           stage('Push Docker Image') {
    steps {
        script {
            docker.withRegistry('https://index.docker.io/v1/', 'docker-hub-credentials') {
                def app = docker.build("azizgmaty/student-management:latest")
                app.push()
                app.push("${env.BUILD_NUMBER}")
            }
        }
    }
}



    }

    post {
        success {
            echo 'Pipeline terminé avec succès ✅'
        }
        failure {
            echo 'Pipeline échoué ❌'
        }
    }
}
