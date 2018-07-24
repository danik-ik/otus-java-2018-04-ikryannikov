# Домашнее задание №13

Собрать war для приложения из ДЗ-12.  Создавать кэш и DBService как Spring beans, передавать (inject) их в сервлеты.  Запустить веб приложение во внешнем веб сервере. 

### Комментарии

Используется зависимость от ДЗ 11. Соответственно, ДЗ 11 должно быть помещено в локальный Maven-репозиторий:

````cmd
cd ..\homework11
mvn clean source:jar install
cd ..\homework13
````

Под windows пожно запустить prepare.cmd в текущей папке

Для запуска необходимо подготовить War-файл:

````cmd
mvn clean package
````

Затем собранный файл (root.war) копируется из папки target в папку webapps сервера jetty

Запускается сервер (в папке с jetty):

````cmd
java -jar start.jar
````

Доступ: http://localhost:8080

В Idea Ultimate можно настроить запуск под jetty в конфигурациях запуска.

Кэш, оба DBService (исходный и декоратор с кэшем) и эмулятор нагрузки созбаются как Spring beans (см. src\main\resources\SpringBeans.xml)

Поскольку CacheEngineImpl является дженериком да ещё и принимает лямбду в параметрах конструктора, я немного конкретизировал его при помощи наследования:

````java
public class CacheEngineBean extends CacheEngineImpl<Long, UserDataSet> {
    public CacheEngineBean(int maxElements, long lifeTimeMs, 
                           long idleTimeMs, boolean isEternal) {
        super(maxElements, lifeTimeMs, idleTimeMs, 
                isEternal, CacheHelper.SoftEntryFactory());
    }
}
````


Для инжекции зависимости используются поля с аннотациями @Autowired в AdminServlet. Эмулятор нагрузки не используется в AdminServlet, он инжектируется для того, чтобы просто создался объект и автоматически при этом запустился в отдельном потоке (запуск производится в конструкторе).

На главной странице две ссылки: login и admin. Без авторизации переход по ссылке Admin приводит к перенаправлению на страницу авторизации. Для авторизации надо ввести любой логин, состоящий ровно из 18 букв и/или цифр

На странице /admin показывается, среди, прочего, статистика по использованию кэша: количество промахов и попаданий.


