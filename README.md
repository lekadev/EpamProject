# EpamProject

Web-based library management system

The task was to build web system that supports the following functionality: 

- create entity-classes that are appropriate to the subject domain
- packaging and naming of classes, methods and variables should be made in accordance with Java Code Conventions
- persistent information of the subject domain should be stored in a database and 
accessed using plain JDBC with self-created Connection pool 
- the system should support Cyrillic and addition of a new language should be possible at any time
- the app architecture should be implemented in accordance with MVC pattern
- when implementing business logic, use of the GoF patterns (Factory, Builder, Strategy, Observer etc.) is highly recommended 
- use JSTL library jsp
- use filters, sessions and listeners
- use Log4j
- all input fileds should be checked on validity of typed information
- passwords should be hashed
- use of CSS libraries is highly recommended
- the implementation approach should be consistent with all the OOP principles
- use of external frameworks is prohibited

The following is highly recommended in designing the database: 

- recommended db types:MySQL, H2, PostgreSQL, Oracle
- use of views
- the tables structure should be brought to 3rd normal form
- all tables should be linked

Library system: readers should be able to search through the books catalogue and order books that are present in library 
in one or more copies. Librarians serve readers by giving them ordered books for long or short period of loan. 


Задание: построить веб-систему, поддерживающую заданную функциональность:
- на основе сущностей предметной области создать классы, их описывающие
- классы и методы должны иметь названия отражающие их функциональность, и быть грамотно структурированы по пакетам
- оформление кода должно соответствовать Java Code Convention
- информацию о предметной области хранить в БД, для доступа использовать JDBC с использованием самостоятельно реализованного 
пула соединений 
- обязательна поддержка кириллицы (система должна быть многоязычной), в том числе и при хранении информации в БД 
- в системе должна быть возможность в любой момент добавить еще один язык 
- архитектура приложения должна соответствовать шаблону MVC 
- при реализации бизнес логики использовать шаблоны GoF: Factory, Builder, Strategy, State, Observer etc.
- в страницах JSP применять библиотеку JSTL
- использовать сессии, фильтры и слушателей
- использовать Log4j
- экранировать поля для ввода пароля, проверятьа на корректность заполнения всех полей, хэшировать пароли
- соблюдать всех парадигмы ООП 
- рекомендовано использовать CSS библиотеки
- не использовать внешние  фрэймворки 

Рекомендации для построения базы данных: 

- допустимые БД : MySQL, H2, PostgreSQL, Oracle 
- должен быть реализован view
- БД должна ,snm доведена до 3 нормальной формы
- все таблицы должны быть связаны между собой 

Система Библиотека: Читатель имеет возможность осуществлять поиск и заказ Книг в Каталоге. Библиотекарь выдает 
Читателю Книгу на абонемент или в читальный зал. Книга может присутствовать в Библиотеке в одном или нескольких экземплярах.
