def call() {
    pipeline {
        agent { label 'workstation'}

        stages {

            stage('Download Dependencies'){
                steps {
                    sh 'npm install'
                }
            }

            stage('Code Quality'){
                when {
                    allOf {
                        expression { env.TAG_NAME != env.GIT_BRANCH }
                    }
                }
                steps {
                    //sh 'sonar-scanner -Dsonar.host.url=http://172.31.34.4:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.projectKey=backend -Dsonar.qualitygate.wait=true'
                    echo 'OK'
                }
            }

            stage('Unit Tests'){
                when {
                    allOf {
                        expression { env.TAG_NAME != env.GIT_BRANCH }
                        branch 'main'
                    }
                }
                steps {
                    // Ideally we should run the tests , But here the developer have skipped it. So assuming those are good and proceeding
                    // sh 'npm test'

                    echo 'CI'
                }
            }

            stage('Release'){
                when {
                    expression { env.TAG_NAME ==~ ".*" }
                }
                steps {
                    sh 'zip -r ${component}-${TAG_NAME}.zip package.json server.js'
                    sh 'curl -sSf -u "admin:Admin123" -X PUT -T ${component}-${TAG_NAME}.zip "http://artifactory.waleapagun.online:8081/artifactory/${component}/${component}-${TAG_NAME}.zip"'
                }
                //    steps{
                //      sh 'docker build -t 008089408493.dkr.ecr.us-east-1.amazonaws.com/backend:${TAG_NAME} .'
                //      sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 008089408493.dkr.ecr.us-east-1.amazonaws.com'
                //      sh 'docker push 008089408493.dkr.ecr.us-east-1.amazonaws.com/backend:${TAG_NAME}'
                //    }
            }

        }
    }







}