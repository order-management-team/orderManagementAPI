pipeline {
    agent none

    environment {
        SONAR_SERVER = 'sonarqube-server'
        env.IMAGE_NAME = env.REPO_NAME.toLowerCase()
    }

    stages {
        stage('Get Source') {
            agent any
            steps {
                checkout scm

                script {
                    env.REPO_NAME = "${env.GIT_URL.split('/').last().split('\\.').first()}"
                    echo "Repositorio detectado: ${env.REPO_NAME}"
                }
            }
        }

        stage('Build y Análisis QA') {
            parallel {
                stage('Build') {
                    agent {
                        docker {
                            image 'maven:3.9.15-eclipse-temurin-21'
                            args '-e HOME=/tmp'
                        }
                    }
                    steps {
                        sh 'mvn clean package -DskipTests'

                        stash name: 'app-jar', includes: 'target/*.jar'
                    }
                }

                stage('Análisis SonarQube Cloud') {
                    agent {
                        docker {
                            image 'maven:3.9.15-eclipse-temurin-21'
                            args '-e HOME=/tmp'
                        }
                    }
                    steps {
                        withSonarQubeEnv("${env.SONAR_SERVER}") {
                            sh """
                                mvn clean verify sonar:sonar \
                                -DskipTests \
                                -Dsonar.organization=order-management-team \
                                -Dsonar.projectKey=${env.REPO_NAME} \
                                -Dsonar.projectName=${env.REPO_NAME} \
                                -Dsonar.sources=src/main/java \
                                -Dsonar.java.binaries=target/classes
                            """
                        }
                    }
                }
            }
        }

        stage('Docker Build') {
            agent any
            steps {
                checkout scm

                unstash 'app-jar'

                sh """
                    docker build -t ${env.IMAGE_NAME}:latest .
                """
            }
        }
    }
}