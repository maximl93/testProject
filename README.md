### Запуск проекта

- скачиваем репозиторий git clone https://github.com/maximl93/testProject.git
- в терминале, в корневой папке проекта выполнить команду по сборке ./gradlew clean build
- для запуска контейнера выполнить команду docker-compose up --build
- в браузере перейти по адресу http://localhost:8080/swagger-ui/index.html

### Доступные эндпоинты:

POST /users - создать пользователя
GET /users/{id} - получить информацию о пользователе
PUT /users/{id} - обновить пользователя
DELETE /users/{id} - удалить пользователя
POST /users/{id}/subscriptions - добавить подписку
GET /users/{id}/subscriptions - получить подписки пользователя
DELETE /users/{id}/subscriptions/{sub_id} - удалить подписку у пользователя

POST /subscriptions - создать подписку
GET /subscriptions/{id} - получить информацию о подписке
PUT /subscriptions/{id} - обновить подписку
DELETE /subscriptions/{id} - удалить подписку
GET /subscriptions/top - получить ТОП-3 популярных подписок
