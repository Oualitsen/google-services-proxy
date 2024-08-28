
pipeline {
    agent any
    tools {
        jdk 'amzn-21'
    }
    parameters {
        gitParameter branchFilter: 'origin/(.*)', defaultValue: 'main', name: 'BRANCH', type: 'PT_BRANCH'
    }
    environment {
        PATH = "$PATH:/opt/maven/bin"
    }
    stages {
        stage("Clone project") {
            steps {
                echo "Cloning project ..."
                git branch: "${params.BRANCH}", url: 'git@github.com:Oualitsen/google-services-proxy.git'
            }
        }

        stage("Build JAR file") {
            steps {
                echo "Building JAR file ..."
                sh "make jar"
            }
        }

        stage("Build Docker Image") {
            steps {
                echo "Building Docker Image ..."
                sh "make build_docker_image"
            }
        }

        stage("Stop current version") {
            steps {
                script {
                try {
                    echo "Stoping current version ..."
                    sh "make compose_dev_down"
                }catch(e) {
                    echo "could not stop current version"
                }
                }
            }
        }

        stage('Start new version') {
            steps {
                script {
                        echo 'Updating compose file ...'
                        def gitCommit = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                        def composeFile = 'docker-compose-dev.yaml'
                        // Read docker-compose.yml
                        def dockerComposeContent = readFile(composeFile)

                        // Replace $(GIT_COMMIT) with the actual commit hash
                        def updatedDockerComposeContent = dockerComposeContent.replaceAll('\\$\\(GIT_COMMIT\\)', gitCommit)

                        // Write back the updated docker-compose.yml
                        writeFile(file: composeFile, text: updatedDockerComposeContent)

                        sh "cat ${composeFile}"

                        echo 'Starting new version ...'
                        sh 'make compose_dev'
                }
            }
        }
    }
}
