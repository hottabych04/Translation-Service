# Тестовое задание для отбора на курс Финтех от Т-Банк
## Условие задания

Вам необходимо разработать веб-приложение на языке Java/Kotlin для перевода набора слов на другой язык с использованием стороннего сервиса перевода (Яндекс, Google или др.).

Требования к программе:

Приложение должно принимать на вход строку, состоящую из набора слов, исходный язык и целевой язык в качестве параметров для перевода. В ответе программа должна вернуть переведенную строку.
Каждое слово должно быть переведено отдельно в нескольких потоках. При этом число одновременно работающих потоков не должно превышать 10.
Приложение должно сохранять в реляционную базу данных информацию о запросе: IP-адрес пользователя, входную строку для перевода и результат перевода. Структуру хранения нужно придумать самостоятельно.
Код программы должен быть выложен на github и содержать readme — инструкции по запуску приложения и как его использовать.
Дополнительные требования:

Можно использовать фреймворк Spring/SpringBoot
Для базы данных использовать только JDBC
Для вызова внешней системы использовать RestTemplate 

Пример:

Вход:

* en → ru

* Hello world, this is my first program

Выход:

* Пример 1: http 200 Привет мир, это является мой первый программа

* Пример 2: http 400 Не найден язык исходного сообщения

* Пример 3: http 400 Ошибка доступа к ресурсу перевода

## Автор решения

Бабиченко Илья Станиславович

## Иструкция по запуску приложения

**Для запуска сервиса необходим Docker и плагин docker-compose**

Инструкция:
1. Скачиваем zip-архив репозитория и распаковываем его в необходимое место. Или клонируем репозиторий
   (необходим установленный git в системе):
    ```
    git clone https://github.com/hottabych04/Translation-Service.git
    ```

2. Переходим в директорию архива:
    ```
    cd Translation-Service
    ```

3. С помощью любого удобного редактора откройте файл `.env`, находящийся в корне проекта
   и отредактируйте следующую переменную:
   ```
   ...
   YANDEX_CLOUD_API_KEY=<API-KEY>
   ...
   ```
   Вместо `<API-KEY>` необходимо вставить предоставленный API-ключ.


4. Запускаем инициализацию необходимых контейнеров в Docker:
    ```
    docker-compose up
    ```

**Готово!**

Теперь наш сервис доступен на порту 8080, куда можно отправлять http запросы.

## Примеры запросов

1. Для получения всех доступных языков для перевода выполните GET запрос по адресу
   http://localhost:8080/api/v1/translation/languages. Сервис вернет JSON следующего формата
   и статус код `200 OK`:

```json
{
  "languages": [
    {
      "code": "string",
      "name": "string"
    },
    {
      "code": "string",
      "name": "string"
    },
    {
      "code": "string",
      "name": "string"
    }
  ]
}
```

2. Для перевода текста необходимо отправить POST запрос по
   адресу http://localhost:8080/api/v1/translation/translate с телом запроса JSON следующего формата:
```json
{
  "sourceLanguageCode": "string",
  "targetLanguageCode": "string",
  "texts": "string"
}
```
после чего, в случае успеха, сервис вернет ответ JSON формата и статусом `200 OK`:

```json
{
  "text": "string"
}
```

3. Для получения перевода сохраненного в базе данных по `id` необходимо отправить GET запрос по адресу
   http://localhost:8080/api/v1/translation/get/{id}, где вместо `{id}` указать `id` перевода который вы желаете получить.
   В случае успеха, сервер вернет статус код `200 OK` и ответ JSON следующего формата:
```json
{
    "ip": "string",
    "sourceText": "string",
    "translateText": "string"
}
```

4. Для получения всех переводов из базы данных постранично, необходимо отправить GET запрос по адресу
   http://localhost:8080/api/v1/translation/get/page/{page}, где вместо `{page}` указать номер страницы которую вы желаете получить.
   В случае успеха, сервер вернет статус код `200 OK` и ответ JSON следующего формата:
```json
{
  "totalElements": 10,
  "totalPages": 1,
  "number": 0,
  "pageSize": 10,
  "numberOfElements": 10,
  "first": true,
  "last": false,
  "empty": false,
  "content": [
    {
      "id": 10,
      "ip": "string",
      "sourceText": "string",
      "translateText": "string"
    },
    {
      "id": 9,
      "ip": "string",
      "sourceText": "string",
      "translateText": "string"
    },
    {
      "id": 8,
      "ip": "string",
      "sourceText": "string",
      "translateText": "string"
    },
    {
      "id": 7,
      "ip": "string",
      "sourceText": "string",
      "translateText": "string"
    },
    {
      "id": 6,
      "ip": "string",
      "sourceText": "string",
      "translateText": "string"
    },
    {
      "id": 5,
      "ip": "string",
      "sourceText": "string",
      "translateText": "string"
    },
    {
      "id": 4,
      "ip": "string",
      "sourceText": "string",
      "translateText": "string"
    },
    {
      "id": 3,
      "ip": "string",
      "sourceText": "string",
      "translateText": "string"
    },
    {
      "id": 2,
      "ip": "string",
      "sourceText": "string",
      "translateText": "string"
    },
    {
      "id": 1,
      "ip": "string",
      "sourceText": "string",
      "translateText": "string"
    }
  ]
}
```
