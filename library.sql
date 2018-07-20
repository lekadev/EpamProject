-- -------------------------------------------------------------------------
-- Creating schema----------------------------------------------------------
-- -------------------------------------------------------------------------
CREATE SCHEMA `library` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;


-- -------------------------------------------------------------------------
-- Creating table of users--------------------------------------------------
-- -------------------------------------------------------------------------
CREATE  TABLE `library`.`user` (
  `id_user` INT NOT NULL AUTO_INCREMENT ,
  `email` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(100) NOT NULL ,
  `role` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id_user`) )
DEFAULT CHARACTER SET = ascii;


-- -------------------------------------------------------------------------
-- Creating table of librarians---------------------------------------------
-- -------------------------------------------------------------------------
CREATE  TABLE `library`.`librarian` (
  `id_librarian` INT NOT NULL AUTO_INCREMENT ,
  `name_first` VARCHAR(45) NOT NULL ,
  `name_last` VARCHAR(45) NOT NULL ,
  `number_phone` VARCHAR(45) NOT NULL ,
  `id_user` INT NOT NULL ,
  PRIMARY KEY (`id_librarian`) );


-- -------------------------------------------------------------------------
-- Adding foreign key to link librarians to users---------------------------
-- -------------------------------------------------------------------------
ALTER TABLE `library`.`librarian`
  ADD CONSTRAINT `fk_librarian_user`
  FOREIGN KEY (`id_user` )
  REFERENCES `library`.`user` (`id_user` )
  ON DELETE CASCADE
  ON UPDATE CASCADE
, ADD INDEX `fk_librarian_user_idx` (`id_user` ASC) ;


-- -------------------------------------------------------------------------
-- Creating table of readers------------------------------------------------
-- -------------------------------------------------------------------------
CREATE  TABLE `library`.`reader` (
  `id_reader` INT NOT NULL AUTO_INCREMENT ,
  `name_first` VARCHAR(45) NOT NULL ,
  `name_last` VARCHAR(45) NOT NULL ,
  `date_registered` DATE NOT NULL ,
  `id_user` INT NOT NULL ,
  PRIMARY KEY (`id_reader`) );

-- -------------------------------------------------------------------------
-- Adding foreign key to link readers to users------------------------------
-- -------------------------------------------------------------------------
ALTER TABLE `library`.`reader`
  ADD CONSTRAINT `fk_reader_user`
  FOREIGN KEY (`id_user` )
  REFERENCES `library`.`user` (`id_user` )
  ON DELETE CASCADE
  ON UPDATE CASCADE
, ADD INDEX `fk_reader_user_idx` (`id_user` ASC) ;


-- -------------------------------------------------------------------------
-- Creating table of locales------------------------------------------------
-- -------------------------------------------------------------------------
CREATE  TABLE `library`.`locale` (
  `id_locale` INT NOT NULL AUTO_INCREMENT ,
  `name_locale` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id_locale`) );


-- -------------------------------------------------------------------------
-- Creating table of labels-------------------------------------------------
-- -------------------------------------------------------------------------
CREATE  TABLE `library`.`label` (
  `id_label` INT NOT NULL ,
  `label` VARCHAR(45) NOT NULL ,
  `text` VARCHAR(250) NOT NULL ,
  `id_locale` INT NOT NULL ,
  PRIMARY KEY (`id_label`, `id_locale`) );


-- -------------------------------------------------------------------------
-- Adding foreign key to link labels to locales-----------------------------
-- -------------------------------------------------------------------------

ALTER TABLE `library`.`label`
  ADD CONSTRAINT `fk_lable_locale`
  FOREIGN KEY (`id_locale` )
  REFERENCES `library`.`locale` (`id_locale` )
  ON DELETE CASCADE
  ON UPDATE CASCADE
, ADD INDEX `fk_lable_locale_idx` (`id_locale` ASC) ;


-- -------------------------------------------------------------------------
-- Creating table of books--------------------------------------------------
-- -------------------------------------------------------------------------
CREATE  TABLE `library`.`book` (
  `id_book` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(250) NOT NULL ,
  `publisher` VARCHAR(45) NOT NULL ,
  `number_copies` INT NOT NULL ,
  PRIMARY KEY (`id_book`) );


-- -------------------------------------------------------------------------
-- Creating table of authors------------------------------------------------
-- -------------------------------------------------------------------------
CREATE  TABLE `library`.`author` (
  `id_author` INT NOT NULL AUTO_INCREMENT,
  `name_first` VARCHAR(45) NOT NULL ,
  `name_last` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id_author`) );


-- -------------------------------------------------------------------------
-- Creating table to link books to authors----------------------------------
-- -------------------------------------------------------------------------
CREATE  TABLE `library`.`book2author` (
  `id_book` INT NOT NULL ,
  `id_author` INT NOT NULL ,
  PRIMARY KEY (`id_book`, `id_author`) );


-- -------------------------------------------------------------------------
-- Adding foreign keys to link books to auhtors---------------------------
-- -------------------------------------------------------------------------
ALTER TABLE `library`.`book2author`
  ADD CONSTRAINT `fk_book`
  FOREIGN KEY (`id_book` )
  REFERENCES `library`.`book` (`id_book` )
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_author`
  FOREIGN KEY (`id_author` )
  REFERENCES `library`.`author` (`id_author` )
  ON DELETE CASCADE
  ON UPDATE CASCADE
, ADD INDEX `fk_book_idx` (`id_book` ASC)
, ADD INDEX `fk_author_idx` (`id_author` ASC) ;


-- -------------------------------------------------------------------------
-- Creating table of orders-------------------------------------------------
-- -------------------------------------------------------------------------
CREATE  TABLE `library`.`order` (
  `id_order` INT NOT NULL AUTO_INCREMENT ,
  `id_book` INT NOT NULL ,
  `id_user` INT NOT NULL ,
  `status` VARCHAR(45) NOT NULL ,
  `date` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`id_order`) );


-- -------------------------------------------------------------------------
-- Adding foreign keys to link orders to users and orders to books----------
-- -------------------------------------------------------------------------
ALTER TABLE `library`.`order`
  ADD CONSTRAINT `fk_order_user`
  FOREIGN KEY (`id_user` )
  REFERENCES `library`.`user` (`id_user` )
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_order_book`
  FOREIGN KEY (`id_book` )
  REFERENCES `library`.`book` (`id_book` )
  ON DELETE CASCADE
  ON UPDATE CASCADE
, ADD INDEX `fk_order_user_idx` (`id_user` ASC)
, ADD INDEX `fk_order_book_idx` (`id_book` ASC) ;


-- -------------------------------------------------------------------------
-- Creating a view of all users---------------------------------------------
-- -------------------------------------------------------------------------
USE `library`;
CREATE  OR REPLACE VIEW `library`.`user_view` AS
SELECT * FROM user JOIN
(SELECT name_first, name_last, number_phone, "" date_registered, id_user
FROM librarian UNION ALL
(SELECT name_first, name_last, "" number_phone, date_registered, id_user
FROM reader)) AS temp USING(id_user);


-- -------------------------------------------------------------------------
-- Inserting data ----------------------------------------------------------
-- -------------------------------------------------------------------------
INSERT INTO `library`.`user` (`email`, `password`, `role`)
VALUES ('kaliyeva.laura@gmail.com', '48690', 'LIBRARIAN'),
('a.kaliyeva@mail.ru', '48690', 'READER'),
('islam@mail.ru', '48690', 'READER');


INSERT INTO `library`.`librarian` (`name_first`, `name_last`, `number_phone`, `id_user`)
VALUES ('Laura', 'K', '87776635422', '1');

INSERT INTO `library`.`reader` (`name_first`, `name_last`, `date_registered`, `id_user`)
VALUES ('Aliya', 'Kaliyeva', '2018-06-17', '2'),
('Islam', 'Kaliyeva', '2018-07-12', '3');


INSERT INTO `library`.`locale` (`id_locale`, `name_locale`)
VALUES ('1', 'en'),
('2', 'ru');

INSERT INTO `library`.`label` (`id_label`, `label`, `text`, `id_locale`)
VALUES ('1', 'loginPage', 'Login page', '1'),
('1', 'loginPage', 'Страница входа', '2'),
('2', 'language', 'Language', '1'),
('2', 'language', 'Язык', '2'),
('3', 'email', 'Email', '1'),
('3', 'email', 'Почтовый адрес', '2'),
('4', 'password', 'Password', '1'),
('4', 'password', 'Пароль', '2'),
('5', 'signIn', 'Sign iIn', '1'),
('5', 'signIn', 'Войти', '2'),
('6', 'newAccount', 'Don\'t have account yet?', '1'),
('6', 'newAccount', 'Зарегистрироваться', '2'),
('7', 'quote', 'A room without books is like a body without a soul', '1'),
('7', 'quote', 'Дом без книг, что тело без души', '2'),
('8', 'quoteAuthor', 'Marcus Tullius Cicero', '1'),
('8', 'quoteAuthor', 'Цицерон', '2'),
('9', 'phone', 'Phone', '1'),
('9', 'phone', 'Номер телефона', '2'),
('10', 'catalogue', 'Catalogue', '1'),
('10', 'catalogue', 'Каталог', '2'),
('11', 'settings', 'Settings', '1'),
('11', 'settings', 'Настройки', '2'),
('12', 'logout', 'Log out', '1'),
('12', 'logout', 'Выход', '2'),
('13', 'allOrders', 'All orders', '1'),
('13', 'allOrders', 'Текущие заказы', '2'),
('14', 'user', 'User', '1'),
('14', 'user', 'Пользователь', '2'),
('15', 'bookTitle', 'Book title', '1'),
('15', 'bookTitle', 'Название книги', '2'),
('16', 'approve', 'Approve', '1'),
('16', 'approve', 'Подтвердить', '2'),
('17', 'date', 'Date', '1'),
('17', 'date', 'Дата', '2'),
('18', 'emailHolder', 'Enter your email', '1'),
('18', 'emailHolder', 'Введите адрес', '2'),
('19', 'passwordHolder', 'Enter you password', '1'),
('19', 'passwordHolder', 'Enter you password', '2'),
('20', 'status', 'Current status', '1'),
('20', 'status', 'Состояние', '2'),
('21', 'changeStatus', 'Change status', '1'),
('21', 'changeStatus', 'Поменять статус', '2'),
('22', 'addBook', 'Add book', '1'),
('22', 'addBook', 'Добавить новую книгу', '2'),
('23', 'title', 'Title', '1'),
('23', 'title', 'Название', '2'),
('24', 'publisher', 'Publisher', '1'),
('24', 'publisher', 'Издательство', '2'),
('25', 'quantity', 'Quantity', '1'),
('25', 'quantity', 'Количество копий', '2'),
('26', 'bookAuthors', 'Authors', '1'),
('26', 'bookAuthors', 'Авторы', '2'),
('27', 'ifNoAuthor', 'If the author is not in the list then', '1'),
('27', 'ifNoAuthor', 'Если нет нужного автора,', '2'),
('28', 'addNewAuthor', 'add new author', '1'),
('28', 'addNewAuthor', 'можно добавить нового', '2'),
('29', 'selectAuthors', 'Select authors', '1'),
('29', 'selectAuthors', 'Выбрать авторов', '2'),
('30', 'add', 'Add', '1'),
('30', 'add', 'Добавить', '2'),
('31', 'tableTitle', 'library.com Books Catalogue', '1'),
('31', 'tableTitle', 'Книжный каталог сайта library.com', '2'),
('32', 'profile', 'Profile', '1'),
('32', 'profile', 'Профиль', '2'),
('33', 'copies', 'Copies', '1'),
('33', 'copies', 'Копий', '2'), ('34', 'name', 'Name', '1'),
('34', 'name', 'Имя', '2'), ('35', 'surname', 'Surname', '1'),
('35', 'surname', 'Фамилия', '2'),
('36', 'addAuthor', 'Add new author', '1'),
('36', 'addAuthor', 'Добавить нового автора', '2'),
('37', 'repeatPassword', 'Repeat password', '1'),
('37', 'repeatPassword', 'Повторите пароль', '2'),
('38', 'confirm', 'Confirm', '1'),
('38', 'confirm', 'Подтвердить', '2'),
('39', 'changeLang', 'Change preferred language', '1'),
('39', 'changeLang', 'Изменить язык', '2'),
('40', 'changePass', 'Change password', '1'),
('40', 'changePass', 'Изменить пароль', '2'),
('41', 'availableCopies', 'Available copies', '1'),
('41', 'availableCopies', 'Доступно копий', '2'),
('42', 'orderBook', 'Order', '1'),
('42', 'orderBook', 'Заказать', '2'),
('43', 'reader', 'Reader', '1'),
('43', 'reader', 'Читатель', '2'),
('44', 'ordersHistory', 'Your orders history', '1'),
('44', 'ordersHistory', 'История ваших заказов', '2'),
('45', 'order', 'Order', '1'),
('45', 'order', 'Заказ', '2'),
('46', 'book', 'Book', '1'),
('46', 'book', 'Книга', '2'),
('47', 'cancel', 'Cancel', '1'),
('47', 'cancel', 'Отменить', '2'),
('48', 'register', 'Register', '1'),
('48', 'register', 'Зарегистрироваться', '2'),
('49', 'haveAccount', 'Already have an account?', '1'),
('49', 'haveAccount', 'Уже есть аккаунт?', '2'),
('50', 'pageNotFound', 'Unfortunately, the page you requested is not found ', '1'),
('50', 'pageNotFound', 'К сожалению, данная страница недоступна', '2'),
('51', 'homePage', 'Go to home page', '1'),
('51', 'homePage', 'Вернуться на главную страницу', '2'),
('52', 'noOrders', 'You do not have orders yet', '1'),
('52', 'noOrders', 'У вас пока нет заказов', '2'),
('53', 'statusPending', 'Pending', '1'),
('53', 'statusPending', 'Заказ обрабатывается', '2'),
('54', 'statusReturned', 'Returned', '1'),
('54', 'statusReturned', 'Книга возвращена', '2'),
('55', 'statusLongLoan', 'Loaned for 2 weeks', '1'),
('55', 'statusLongLoan', 'Выдана на 2 недели', '2'),
('56', 'statusShortLoan', 'Loaned for 2 hours', '1'),
('56', 'statusShortLoan', 'Выдана в читальный зал', '2'),
('57', 'statusDenied', 'Denied', '1'),
('57', 'statusDenied', 'Отказано', '2'),
('58', 'action', 'Action', '1'),
('58', 'action', 'Действие', '2'),
('59', 'edit', 'Edit', '1'),
('59', 'edit', 'Редактировать', '2'),
('60', 'editBook', 'Edit book', '1'),
('60', 'editBook', 'Редактировать информацию о книге', '2'),
('61', 'textInputError', 'Please, fill all required fields correctly', '1'),
('61', 'textInputError', 'Все поля обязательны для заполнения', '2'),
('62', 'emailInvalidError', 'Invalid email', '1'),
('62', 'emailInvalidError', 'Неверный почтовый адрес', '2'),
('63', 'emailExistError', 'An account with this email already exists', '1'),
('63', 'emailExistError', 'Аккаунт с таким почтовый адресом уже зарегистрирован', '2'),
('64', 'passwordPatternError', 'Please, in your password allow use of both numbers, lowercase and uppercase letters as well as special characters', '1'),
('64', 'passwordPatternError', 'Пожалуйста, для создания пароля используйте цифры, символы нижнего и верхнего регистра, а также специальные символы', '2'),
('65', 'passwordMatchError', 'Passwords do not match', '1'),
('65', 'passwordMatchError', 'Пароли не совпадают', '2'),
('66', 'passwordUpdateSuccess', 'Password was updated successfully. Please, sign in again', '1'),
('66', 'passwordUpdateSuccess', 'Пароль успешно обновлён. Пожалуйста, авторизуйтесь снова', '2'),
('67', 'loginError', 'Incorrect email or password', '1'),
('67', 'loginError', 'Неверный почтовый адрес или пароль', '2'),
('68', 'bookAddSuccess', 'The book was added successfully', '1'),
('68', 'bookAddSuccess', 'Книга успешно добавлена', '2'),
('69', 'noAuthorError', 'Please, select at least 1 author', '1'),
('69', 'noAuthorError', 'Необходимо выбрать хотя бы 1 автора', '2'),
('70', 'authorAddSuccess', 'Author was added successfully', '1'),
('70', 'authorAddSuccess', 'Автор успешно добавлен', '2'),
('71', 'updateSuccess', 'Information was updated successfully', '1'),
('71', 'updateSuccess', 'Информация успешно обновлена', '2'),
('72', 'registrationSuccess', 'Registration was successful! You can now proceed signing in', '1'),
('72', 'registrationSuccess', 'Регистрация прошла успешно! Пожалуйста, авторизуйтесь', '2');

INSERT INTO `library`.`book` (`title`, `publisher`, `number_copies`)
VALUES ('Head First Java', 'O’Reilly Media', '1'),
('Java 8. The complete reference', 'Oracle Press', '2'),
('Core Java Fundamentals vol.1', 'Prentica Hall', '1'),
('Core Java Fundamentals vol.2', 'Prentica Hall', '1'),
('Design Patterns: Elements of Reusable Object-Oriented Software', 'Addison-Wesley', '2'),
('Algorithms Unlocked', 'MIT Press', '3'),
('The Phoenix Project: A Novel About IT, DevOps, and Helping Your Business Win', 'IT Revolution Press', '1'),
('7 habits of highly effective people', 'Alpina Publisher', '2'),
('CONSCIOUSNESS. An Introduction', 'Oxford University Press', '1'),
('Moonwalking with Einstein', 'Chicago Press', '1'),
('War and Piece', 'Google Press', '1');


INSERT INTO `library`.`author` (`name_first`, `name_last`)
VALUES ('Kathy', 'Sierra'), ('Bert', 'Bates'), ('Herbert', 'Shildt'),
('Cay', 'Horstmann'), ('Gary', 'Cornell'), ('Erich', 'Gamma'),
('Richard', 'Helm'), ('Ralph', 'Johnson'), ('John', 'Vlissides'),
('Thomas', 'Cormen'), ('Gene', 'Kim'), ('Kevin', 'Behr'),
('George', 'Spafford'), ('Stephen', 'Covey'), ('Susan', 'Blackmore'),
('Joshua', 'Foer'), ('Leo', 'Tolstoy');


INSERT INTO `library`.`book2author` (`id_book`, `id_author`)
VALUES ('1', '1'), ('1', '2'), ('2', '3'), ('3', '4'),
('3', '5'), ('4', '4'), ('4', '5'), ('5', '6'),
('5', '7'), ('5', '8'), ('5', '9'), ('6', '10'),
('7', '11'), ('7', '12'), ('7', '13'), ('8', '14'),
('9', '15'), ('10', '16');


INSERT INTO `library`.`order` (`id_book`, `id_user`, `status`)
VALUES ('1', '2', 'LONG_LOAN'), ('6', '2', 'PENDING'),
('7', '3', 'SHORT_LOAN'), ('2', '3', 'DENIED');
