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
                sh 'mvn package'
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
                sh 'sleep 15'
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
