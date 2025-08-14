# UserService

Если необходимо производить сборку в репозитории gitHub, то перед первой выкладкой файла "gradlew" необходимо сделать этот файл выполняемым для работы на linux.
<br/>
Для этого необходимо выполнить команду 
```
git update-index --chmod=+x gradlew
git commit -m "Make gradlew executable"`
```
Либо попробовать переписать скрипт как показывают на сайте где есть решения этой проблемы на [stackoverflow](https://stackoverflow.com/questions/58282791/why-when-i-use-github-actions-ci-for-a-gradle-project-i-face-gradlew-permiss).