# TestSoapService

Приложение сгенерировано с использованием JHipster 7.7.0, 
вы можете найти документацию на [https://www.jhipster.tech](https://www.jhipster.tech).

## Структура проекта

Node необходим для генерации и рекомендован для разработки. 
`package.json` файл содержит соответствующие node-скрипты.

- `/src/main/docker` - Docker конфигурация

## Разработка

Чтобы запустить приложение с dev-профилем нужно запустить:

```
./mvnw
```

## Prod-сборка

### Сборка jar

Чтобы собрать jar в prod-сборке необходимо запустить:

```
./mvnw -Pprod clean verify
```

Чтобы убедиться, что все действительно работает, запустите:

```
java -jar target/*.jar
```

Больше информации на [Using JHipster in production][] .

### Сборка war

Чтобы собрать war запустите:

```
./mvnw -Pprod,war clean verify
```

## Упрощенная сборка и запуск
```
./mvnw -Pprod clean verify

docker-compose -f src/main/docker/postgresql.yml up -d

java -jar target/*.jar

```

## Тестирование

Чтобы запустить тесты укажите:

```
./mvnw verify
```

Больше информации на [Running tests page][].

### Качество кода

Sonar использован в качестве анализатора кода. Вы можете запустить sonar-сервер
(доступный по адресу http://localhost:9001) :

```
docker-compose -f src/main/docker/sonar.yml up -d
```
Чтобы запустить sonar-анализ:

```
./mvnw -Pprod clean verify sonar:sonar
```

Больше информации на [Code quality page][].

## Использование docker для упрощения разработки и запуска компонентов (optional)


Чтобы запустить сервер postgresql в docker контейнере, укажите:

```
docker-compose -f src/main/docker/postgresql.yml up -d
```

Чтобы остановить и удалить контейнер сервера, укажите:

```
docker-compose -f src/main/docker/postgresql.yml down
```

Вы также можете полностью контейнеризировать ваше приложение.
Для начала соберите docker image вашего приложения, запустив:

```
./mvnw -Pprod verify jib:dockerBuild
```

Затем:

```
docker-compose -f src/main/docker/app.yml up -d
```

Или одной командой с использованием предусмотренных файлов-скриптов:
```
sh docker-build-deploy.sh
```

Больше информации на [Using Docker and Docker-Compose][]

Сервисы доступны по адресам
```
<EXTERNAL HOST>:8080/ws/v1/categoryservice?wsdl
<EXTERNAL HOST>:8080/ws/v1/modelservice?wsdl
<EXTERNAL HOST>:8080/ws/v1/categorymodelservice?wsdl
```

См. <EXTERNAL HOST> в логах при запуске приложения
## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 7.7.0 archive]: https://www.jhipster.tech
[doing microservices with jhipster]: https://www.jhipster.tech/microservices-architecture/
[using jhipster in development]: https://www.jhipster.tech/development/
[using docker and docker-compose]: https://www.jhipster.tech/docker-compose
[using jhipster in production]: https://www.jhipster.tech/production/
[running tests page]: https://www.jhipster.tech/running-tests/
[code quality page]: https://www.jhipster.tech/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/setting-up-ci/
[node.js]: https://nodejs.org/
[npm]: https://www.npmjs.com/
