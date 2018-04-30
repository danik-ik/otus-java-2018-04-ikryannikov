# Домашнее задание №3

Задание на "поинтереснее": написать собственную реализацию очереди (Queue)

### Комментарии

Реализовано из задания: 

- addAll()

Не реализовано из задания (т.к. относится к List):

- copy

- sort

Не стал реализовывать следующие методы (выкидывают UnsupportedOperationException):

``` java
boolean contains(Object o)
boolean remove(Object o)
boolean containsAll(Collection<?> c)
boolean removeAll(Collection<?> c)
boolean retainAll(Collection<?> c)
```

также и итератор не поддерживает удаление. 

Прочие методы интерфейса Queue реализованы.



Все проверки по сути задания -- в тестах. Приложение уже так, баловство одно.

Сборка и запуск:
mvn clean package
java -jar target/homework03-1.0-SNAPSHOT.jar