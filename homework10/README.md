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



# Домашнее задание №9

myORM

Создайте в базе таблицу с полями: 

```sql
id bigint(20) NOT NULL auto_increment 
name varchar(255)
age int(3)
```

Создайте абстрактный класс DataSet. Поместите long id в DataSet. 
Добавьте класс UserDataSet (с полями, которые соответствуют таблице) унаследуйте его от DataSet. 
Напишите Executor, который сохраняет наследников DataSet в базу и читает их из базы по id и классу. 

```java
<T extends DataSet> void save(T user){…}
<T extends DataSet> T load(long id, Class<T> clazz){…}
```
### Комментарии

Преобразование должно быть построено на механизме рефлексии. Запрос на сохранение должен строиться динамически. Задача-минимум -- сохранение примитывных типов

#### Ограничения

работаю только с геттерами/сеттерами, не с полями.

на аргументы геттера накладываются строгие ограничения.

Реализованы только четыре типа данных: long, float, String, LocalDate

