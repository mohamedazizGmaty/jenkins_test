pipeline {
    agent any

    // Déclencheur : exécution à chaque push sur le dépôt Git
    triggers {
        // Si vous utilisez un webhook GitHub/GitLab/Bitbucket, c’est le plus courant :
        githubPush()
        // ou pour GitLab :
        // gitlabPush()
    }

    stages {
        stage('Checkout') {
            steps {
                // Récupération du code source depuis le repo configuré dans le job
                checkout scm
            }
        }

        stage('Clean Target') {
            steps {
                // Suppression du dossier target s'il existe
                sh 'rm -rf target'
            }
        }

        stage('Build') {
            steps {
                // Compilation du projet (exemple Maven)
                sh 'mvn compile'
            }
        }

        stage('Package') {
            steps {
                // Création du livrable (ex : jar/war)
                sh 'mvn package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                // Build the Docker image using the Dockerfile in the root directory
                sh 'docker build -t student-management:latest .'
            }
        }

        stage('Start DB') {
            steps {
                sh 'docker compose -f docker-compose.test.yml up -d'
            }
        }
        
        stage('Wait for DB') {
            steps {
                script {
                    // Wait for PostgreSQL to be ready using healthcheck
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
