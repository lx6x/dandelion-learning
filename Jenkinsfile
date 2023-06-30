// 当前分支
def currentBranch = env.BRANCH_NAME
// 获取当前项目的完整作业名称
def jobName = env.JOB_NAME
// jenkins 构建版本
//def buildNumber = env.BUILD_NUMBER
def buildNumber = "1.0"
// 项目名称
def projectName = "maven-nexus-parent"
// 当前项目目录
def projectDir = env.WORKSPACE


pipeline {
    agent any

    stages {

        stage("获取 当前分支") {
            steps {
                echo "当前分支：${currentBranch}"
                echo "当前项目名称：${projectName}"
//                echo "构建版本：${buildNumber}"
                echo "当前项目目录：${projectDir}"
            }
        }

        /*stage("构建 target") {
            steps {
                echo "maven 打包构建"
                sh "mvn clean package -Dmaven.test.skip=true"
            }
        }

        stage("构建 docker 镜像") {
            steps {
                echo "根据 Dockerfile 构建对应镜像"
                sh "cd maven-nexus-boot/"
                sh "ls"
                script {
                    def dockerImageExists = sh script: "docker image inspect ${projectName}-${currentBranch}:${buildNumber} > /dev/null 2>&1", returnStatus: true
                    if (dockerImageExists == 0) {
                        echo "Docker 镜像 ${projectName}-${currentBranch}:${buildNumber} 存在"

                        sh "docker stop ${projectName}-${currentBranch}"
                        sh "docker rm ${projectName}-${currentBranch}"
                        sh "docker rmi ${projectName}-${currentBranch}:${buildNumber}"
                        sh "ls"
                        dir("maven-nexus-boot") {
                            sh 'ls'
                            sh "docker build . -t ${projectName}-${currentBranch}:${buildNumber}"
                        }

                    } else {
                        echo "Docker 镜像 ${projectName}-${currentBranch}:${buildNumber} 不存在"
                        sh "ls"
                        dir("maven-nexus-boot") {
                            sh 'ls'
                            sh "docker build . -t ${projectName}-${currentBranch}:${buildNumber}"

                        }
                    }
                }
            }
        }

        stage("运行") {
            steps {
                echo "运行镜像"
                sh "docker run -itd --name ${projectName}-${currentBranch} -p 10013:8080 ${projectName}-${currentBranch}:${buildNumber}"
            }
        }*/
    }
}