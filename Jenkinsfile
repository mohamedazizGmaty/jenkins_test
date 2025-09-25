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
