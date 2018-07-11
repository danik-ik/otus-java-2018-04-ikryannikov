# Домашнее задание №10

Hibernate ORM

На основе предыдущего ДЗ (myORM):

1. Оформить решение в виде DBService (interface DBService, class DBServiceImpl, UsersDAO, UsersDataSet, Executor)

2. Не меняя интерфейс DBSerivice сделать DBServiceHibernateImpl на Hibernate.
3. Добавить в UsersDataSet поля:

адресс (OneToOne) 
``` java
class AddressDataSet{
private String street;
}
```
и телефон* (OneToMany)
``` java
class PhoneDataSet{
private String number;
}
```
Добавить соответствущие датасеты и DAO. 
4.** Поддержать работу из пункта (3) в myORM

### Комментарии

Тестировал с MySql и с H2.

Возникали проблемы при сохранении даты без времени при настройке сервера и клиента на разные тайм-зоны (дата необратимо изменялась на сутки), безусловно работающего решения не нашёл.

Реализована работа через набор данных пользователя, получение адресов и телефонов в отрвые от пользователя не реализовано. 