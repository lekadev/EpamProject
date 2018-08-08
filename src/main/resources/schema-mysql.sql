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
  `name_first` VARCHAR(45) NOT NULL ,
  `name_last` VARCHAR(45) NOT NULL ,
  `date_registered` DATE NOT NULL ,
  PRIMARY KEY (`id_user`));


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
  ADD CONSTRAINT `fk_label_locale`
  FOREIGN KEY (`id_locale` )
  REFERENCES `library`.`locale` (`id_locale` )
  ON DELETE CASCADE
  ON UPDATE CASCADE
, ADD INDEX `fk_label_locale_idx` (`id_locale` ASC) ;


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
-- Adding foreign keys to link books to authors-----------------------------
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
-- Inserting data ----------------------------------------------------------
-- -------------------------------------------------------------------------
INSERT INTO `library`.`user` (`email`, `password`, `role`, `name_first`, `name_last`, `date_registered`)
VALUES ('kaliyeva.laura@gmail.com', '48690', 'LIBRARIAN', 'Laura', 'K', '2018-07-12'),
('a.kaliyeva@mail.ru', '48690', 'READER', 'Aliya', 'Kaliyeva', '2018-06-17'),
('islam@mail.ru', '48690', 'READER', 'Islam', 'K', '2018-07-12');

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
('5', 'signIn', 'Sign in', '1'),
('5', 'signIn', 'Войти', '2'),
('6', 'newAccount', 'Don\'t have account yet?', '1'),
('6', 'newAccount', 'Зарегистрироваться', '2'),
('7', 'quote', 'A room without books is like a body without a soul', '1'),
('7', 'quote', 'Дом без книг, что тело без души', '2'),
('8', 'quoteAuthor', 'Marcus Tullius Cicero', '1'),
('8', 'quoteAuthor', 'Цицерон', '2'),
('9', 'catalogue', 'Catalogue', '1'),
('9', 'catalogue', 'Каталог', '2'),
('10', 'settings', 'Settings', '1'),
('10', 'settings', 'Настройки', '2'),
('11', 'logout', 'Log out', '1'),
('11', 'logout', 'Выход', '2'),
('12', 'allOrders', 'All orders', '1'),
('12', 'allOrders', 'Текущие заказы', '2'),
('13', 'user', 'User', '1'),
('13', 'user', 'Пользователь', '2'),
('14', 'bookTitle', 'Book title', '1'),
('14', 'bookTitle', 'Название книги', '2'),
('15', 'approve', 'Approve', '1'),
('15', 'approve', 'Подтвердить', '2'),
('16', 'date', 'Date', '1'),
('16', 'date', 'Дата', '2'),
('17', 'emailHolder', 'Enter your email', '1'),
('17', 'emailHolder', 'Введите адрес', '2'),
('18', 'passwordHolder', 'Enter you password', '1'),
('18', 'passwordHolder', 'Введите пароль', '2'),
('19', 'status', 'Current status', '1'),
('19', 'status', 'Состояние', '2'),
('20', 'changeStatus', 'Change status', '1'),
('20', 'changeStatus', 'Поменять статус', '2'),
('21', 'addBook', 'Add book', '1'),
('21', 'addBook', 'Добавить новую книгу', '2'),
('22', 'title', 'Title', '1'),
('22', 'title', 'Название', '2'),
('23', 'publisher', 'Publisher', '1'),
('23', 'publisher', 'Издательство', '2'),
('24', 'quantity', 'Quantity', '1'),
('24', 'quantity', 'Количество копий', '2'),
('25', 'bookAuthors', 'Authors', '1'),
('25', 'bookAuthors', 'Авторы', '2'),
('26', 'ifNoAuthor', 'If the author is not in the list then', '1'),
('26', 'ifNoAuthor', 'Если нет нужного автора,', '2'),
('27', 'addNewAuthor', 'add new author', '1'),
('27', 'addNewAuthor', 'можно добавить нового', '2'),
('28', 'selectAuthors', 'Select authors', '1'),
('28', 'selectAuthors', 'Выбрать авторов', '2'),
('29', 'add', 'Add', '1'),
('29', 'add', 'Добавить', '2'),
('30', 'tableTitle', 'MyLibrary.com Books Catalogue', '1'),
('30', 'tableTitle', 'Книжный каталог сайта library.com', '2'),
('31', 'profile', 'Profile', '1'),
('31', 'profile', 'Профиль', '2'),
('32', 'copies', 'Copies', '1'),
('32', 'copies', 'Копий', '2'),
('33', 'name', 'Name', '1'),
('33', 'name', 'Имя', '2'),
('34', 'surname', 'Surname', '1'),
('34', 'surname', 'Фамилия', '2'),
('35', 'addAuthor', 'Add new author', '1'),
('35', 'addAuthor', 'Добавить нового автора', '2'),
('36', 'repeatPassword', 'Repeat password', '1'),
('36', 'repeatPassword', 'Повторите пароль', '2'),
('37', 'confirm', 'Confirm', '1'),
('37', 'confirm', 'Подтвердить', '2'),
('38', 'changeLang', 'Change preferred language', '1'),
('38', 'changeLang', 'Изменить язык', '2'),
('39', 'changePass', 'Change password', '1'),
('39', 'changePass', 'Изменить пароль', '2'),
('40', 'availableCopies', 'Available copies', '1'),
('40', 'availableCopies', 'Доступно копий', '2'),
('41', 'orderBook', 'Order', '1'),
('41', 'orderBook', 'Заказать', '2'),
('42', 'reader', 'Reader', '1'),
('42', 'reader', 'Читатель', '2'),
('43', 'ordersHistory', 'Your orders history', '1'),
('43', 'ordersHistory', 'История ваших заказов', '2'),
('44', 'order', 'Order', '1'),
('44', 'order', 'Заказ', '2'),
('45', 'book', 'Book', '1'),
('45', 'book', 'Книга', '2'),
('46', 'cancel', 'Cancel', '1'),
('46', 'cancel', 'Отменить', '2'),
('47', 'register', 'Register', '1'),
('47', 'register', 'Зарегистрироваться', '2'),
('48', 'haveAccount', 'Already have an account?', '1'),
('48', 'haveAccount', 'Уже есть аккаунт?', '2'),
('49', 'pageNotFound', 'Unfortunately, the page you requested is not found ', '1'),
('49', 'pageNotFound', 'К сожалению, данная страница недоступна', '2'),
('50', 'homePage', 'Go to home page', '1'),
('50', 'homePage', 'Вернуться на главную страницу', '2'),
('51', 'noOrders', 'You do not have orders yet', '1'),
('51', 'noOrders', 'У вас пока нет заказов', '2'),
('52', 'statusPending', 'Pending', '1'),
('52', 'statusPending', 'Заказ обрабатывается', '2'),
('53', 'statusReturned', 'Returned', '1'),
('53', 'statusReturned', 'Книга возвращена', '2'),
('54', 'statusLongLoan', 'Loaned for 2 weeks', '1'),
('54', 'statusLongLoan', 'Выдана на 2 недели', '2'),
('55', 'statusShortLoan', 'Loaned for 2 hours', '1'),
('55', 'statusShortLoan', 'Выдана в читальный зал', '2'),
('56', 'statusDenied', 'Denied', '1'),
('56', 'statusDenied', 'Отказано', '2'),
('57', 'edit', 'Edit', '1'),
('57', 'edit', 'Редактировать', '2'),
('58', 'editBook', 'Edit book', '1'),
('58', 'editBook', 'Редактировать информацию о книге', '2'),
('59', 'textInputError', 'Please, fill all required fields correctly', '1'),
('59', 'textInputError', 'Все поля обязательны для заполнения', '2'),
('60', 'emailExistError', 'An account with this email already exists', '1'),
('60', 'emailExistError', 'Аккаунт с таким почтовый адресом уже зарегистрирован', '2'),
('61', 'passwordPatternError', 'Please, in your password allow use of both numbers, lowercase and uppercase letters as well as special characters', '1'),
('61', 'passwordPatternError', 'Пожалуйста, для создания пароля используйте цифры, символы нижнего и верхнего регистра, а также специальные символы', '2'),
('62', 'passwordMatchError', 'Passwords do not match', '1'),
('62', 'passwordMatchError', 'Пароли не совпадают', '2'),
('63', 'passwordUpdateSuccess', 'Password was updated successfully. Please, sign in again', '1'),
('63', 'passwordUpdateSuccess', 'Пароль успешно обновлён. Пожалуйста, авторизуйтесь снова', '2'),
('64', 'loginError', 'Incorrect email or password', '1'),
('64', 'loginError', 'Неверный почтовый адрес или пароль', '2'),
('65', 'bookAddSuccess', 'The book was added successfully', '1'),
('65', 'bookAddSuccess', 'Книга успешно добавлена', '2'),
('66', 'noAuthorError', 'Please, select at least 1 author', '1'),
('66', 'noAuthorError', 'Необходимо выбрать хотя бы 1 автора', '2'),
('67', 'authorAddSuccess', 'Author was added successfully', '1'),
('67', 'authorAddSuccess', 'Автор успешно добавлен', '2'),
('68', 'updateSuccess', 'Information was updated successfully', '1'),
('68', 'updateSuccess', 'Информация успешно обновлена', '2'),
('69', 'registrationSuccess', 'Registration was successful! You can now proceed signing in', '1'),
('69', 'registrationSuccess', 'Регистрация прошла успешно! Пожалуйста, авторизуйтесь', '2'),
('70', 'editProfile', 'Edit profile', '1'),
('70', 'editProfile', 'Редактировать профиль', '2'),
('71', 'error', 'Error', '1'),
('71', 'error', 'Ошибка', '2');

INSERT INTO `library`.`book` (`title`, `publisher`, `number_copies`)
VALUES ('Head First Java', 'OReilly Media', '1'),
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
