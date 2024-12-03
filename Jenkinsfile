pipeline {
    agent any

    environment {
        ANSIBLE_PLAYBOOK = "ansible_project/playbook.yml"  // Путь до playbook
        INVENTORY_FILE = "ansible_project/hosts"          // Путь до inventory-файла

    }

    stages {
        stage('Подготовка окружения') {
            steps {
                script {
                    echo "Этап 1: Подготовка окружения (установка Docker, клонирование репозиториев)"
                }
                sh """
                    ansible-playbook ${ANSIBLE_PLAYBOOK} -i ${INVENTORY_FILE} --tags docker_setup,clone_repository
                """
            }
        }

        stage('Запуск приложений') {
            steps {
                script {
                    echo "Этап 2: Запуск приложений (Docker Compose)"
                }
                sh """
                    ansible-playbook ${ANSIBLE_PLAYBOOK} -i ${INVENTORY_FILE} --tags start_services
                """
            }
        }
    }
}
