pipeline {
    agent any

    environment {
        ANSIBLE_PLAYBOOK = "ansible_project/playbook.yml"  // Путь до playbook
        INVENTORY_FILE = "ansible_project/hosts"          // Путь до inventory-файла
    }

    stages {
        stage('Clone Repositories') {
            steps {
                script {
                    echo "Этап 1: Клонирование репозиториев"
                }
                sh """
                    ansible-playbook ${ANSIBLE_PLAYBOOK} -i ${INVENTORY_FILE} --tags clone_repository
                """
            }
        }

        stage('Setup Docker') {
            steps {
                script {
                    echo "Этап 2: Настройка Docker и Docker Compose"
                }
                sh """
                    ansible-playbook ${ANSIBLE_PLAYBOOK} -i ${INVENTORY_FILE} --tags docker_setup
                """
            }
        }

        stage('Start Services') {
            steps {
                script {
                    echo "\n" +
                            "Этап 3: Запуск Docker Compose"
                }
                sh """
                    ansible-playbook ${ANSIBLE_PLAYBOOK} -i ${INVENTORY_FILE} --tags start_services
                """
            }
        }
    }

    post {
        always {
            echo "Pipeline выполнен"
        }
        cleanup {
            script {
                echo "Остановка сервисов"
                sh """
                    ansible-playbook ${ANSIBLE_PLAYBOOK} -i ${INVENTORY_FILE} --tags stop_services
                """
            }
        }
    }
}

