# Домашнее задание №1

Создать проект под управлением maven в Intellij IDEA.

Добавить зависимость на Google Guava/Apache Commons/библиотеку на ваш выбор.

Использовать библиотечные классы для обработки входных данных.

Задать имя проекта (project_name) в pom.xml

Собрать project_name.jar содержащий все зависимости.

Проверить, что приложение можно запустить из командной строки.

Выложить проект на github.

Создать ветку "obfuscation" изменить в ней pom.xml, так чтобы сборка содержала стадию обфускации байткода.

### Комментарии

Долго сомневался, не имелось ли в виду в задании сделать jar с зависимостями без суффикса (-jar-with-dependencies). Сначала так и сделал. Но потом всё же вернул суффикс обратно -- чтобы самому не путаться. По этой же причине обфусцированный файл называется иначе, чем исходный (по умолчанию названия совпадают).

Не сразу придумал, куда (а главное, зачем) ввернуть библиотеку. Ибо "сделай что-нибудь" — это для меня Очень Сложное Задание. Решил пощупать MultiMap, на примере коллекционирования слов по их длине.

Хотелось бы, конечно, попробовать загнать большой текст в ресурсы, или принимать его через стандатрный ввод, или из переданного в параметрах файла -- но уже вряд ли успею в рамках этого задания.