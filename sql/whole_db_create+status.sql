
CREATE SCHEMA IF NOT EXISTS `cargo_servlet_db` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema new_schema1
-- -----------------------------------------------------
USE `cargo_servlet_db` ;

-- -----------------------------------------------------
-- Table `cargo_servlet_db`.`tariffs`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `cargo_servlet_db`.`tariffs` ;

CREATE TABLE IF NOT EXISTS `cargo_servlet_db`.`tariffs`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `price`
    INT
    UNSIGNED
    NOT
    NULL,
    `address`
    VARCHAR
(
    255
) NOT NULL,
    `size` VARCHAR
(
    255
) NOT NULL,
    `weight` VARCHAR
(
    255
) NOT NULL,
    `delivery_term_days` INT UNSIGNED NOT NULL,
    PRIMARY KEY
(
    `id`
))
    ENGINE = InnoDB;

insert into tariffs (id, price, address, size, weight, delivery_term_days)
values (1, 500, 'SPAIN', 'SMALL', 'LIGHT', 4),
       (2, 600, 'SPAIN', 'SMALL', 'MEDIUM', 4),
       (3, 680, 'SPAIN', 'SMALL', 'HEAVY', 4),
       (4, 550, 'SPAIN', 'MIDDLE', 'LIGHT', 4),
       (5, 650, 'SPAIN', 'MIDDLE', 'MEDIUM', 4),
       (6, 650, 'SPAIN', 'MIDDLE', 'HEAVY', 4),
       (7, 700, 'SPAIN', 'BIG', 'LIGHT', 4),
       (8, 800, 'SPAIN', 'BIG', 'MEDIUM', 4),
       (9, 1500, 'SPAIN', 'BIG', 'HEAVY', 4),
       (10, 500, 'GERMANY', 'SMALL', 'LIGHT', 3),
       (11, 600, 'GERMANY', 'SMALL', 'MEDIUM', 3),
       (12, 680, 'GERMANY', 'SMALL', 'HEAVY', 3),
       (13, 550, 'GERMANY', 'MIDDLE', 'LIGHT', 3),
       (14, 650, 'GERMANY', 'MIDDLE', 'MEDIUM', 3),
       (15, 650, 'GERMANY', 'MIDDLE', 'HEAVY', 3),
       (16, 700, 'GERMANY', 'BIG', 'LIGHT', 3),
       (17, 800, 'GERMANY', 'BIG', 'MEDIUM', 3),
       (18, 1500, 'GERMANY', 'BIG', 'HEAVY', 3),
       (19, 500, 'NORWAY', 'SMALL', 'LIGHT', 5),
       (20, 600, 'NORWAY', 'SMALL', 'MEDIUM', 5),
       (21, 680, 'NORWAY', 'SMALL', 'HEAVY', 5),
       (22, 550, 'NORWAY', 'MIDDLE', 'LIGHT', 5),
       (23, 650, 'NORWAY', 'MIDDLE', 'MEDIUM', 5),
       (24, 650, 'NORWAY', 'MIDDLE', 'HEAVY', 5),
       (25, 700, 'NORWAY', 'BIG', 'LIGHT', 5),
       (26, 800, 'NORWAY', 'BIG', 'MEDIUM', 5),
       (27, 1500, 'NORWAY', 'BIG', 'HEAVY', 5),
       (28, 500, 'PORTUGAL', 'SMALL', 'LIGHT', 4),
       (29, 600, 'PORTUGAL', 'SMALL', 'MEDIUM', 4),
       (30, 680, 'PORTUGAL', 'SMALL', 'HEAVY', 4),
       (31, 550, 'PORTUGAL', 'MIDDLE', 'LIGHT', 4),
       (32, 650, 'PORTUGAL', 'MIDDLE', 'MEDIUM', 4),
       (33, 650, 'PORTUGAL', 'MIDDLE', 'HEAVY', 4),
       (34, 700, 'PORTUGAL', 'BIG', 'LIGHT', 4),
       (35, 800, 'PORTUGAL', 'BIG', 'MEDIUM', 4),
       (36, 1500, 'PORTUGAL', 'BIG', 'HEAVY', 4),
       (37, 500, 'MOROCCO', 'SMALL', 'LIGHT', 7),
       (38, 600, 'MOROCCO', 'SMALL', 'MEDIUM', 7),
       (39, 680, 'MOROCCO', 'SMALL', 'HEAVY', 7),
       (40, 550, 'MOROCCO', 'MIDDLE', 'LIGHT', 7),
       (41, 650, 'MOROCCO', 'MIDDLE', 'MEDIUM', 7),
       (42, 650, 'MOROCCO', 'MIDDLE', 'HEAVY', 7),
       (43, 700, 'MOROCCO', 'BIG', 'LIGHT', 7),
       (44, 800, 'MOROCCO', 'BIG', 'MEDIUM', 7),
       (45, 1500, 'MOROCCO', 'BIG', 'HEAVY', 7);

-- -----------------------------------------------------
-- Table `cargo_servlet_db`.`roles`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `cargo_servlet_db`.`roles`;

CREATE TABLE IF NOT EXISTS `cargo_servlet_db`.`roles`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    45
) NOT NULL,
    PRIMARY KEY
(
    `id`
),
    UNIQUE INDEX `name_UNIQUE`
(
    `name` ASC
))
    ENGINE = InnoDB;

insert into `roles` (id, name)
values (1, 'USER'),
       (2, 'ADMIN');

-- -----------------------------------------------------
-- Table `cargo_servlet_db`.`users`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `cargo_servlet_db`.`users`;

CREATE TABLE IF NOT EXISTS `cargo_servlet_db`.`users`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `username`
    VARCHAR
(
    45
) NOT NULL,
    `email` VARCHAR
(
    45
) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Users_role_idx` (`role_id` ASC),
  CONSTRAINT `fk_Users_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `cargo_servlet_db`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cargo_servlet_db`.`users` create user with role 'ADMIN'
-- -----------------------------------------------------

insert into `users` (id, username, email, password, role_id) values
(1, 'admin', 'admin@google.com', '21232f297a57a5a743894a0e4a801fc3', 2);

-- -----------------------------------------------------
-- Table `cargo_servlet_db`.`status`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `cargo_servlet_db`.`status` ;

CREATE TABLE IF NOT EXISTS `cargo_servlet_db`.`status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;




insert into status (id, name) values
(1, 'NEW'),
(2, 'WAITING_FOR_PAYMENT'),
(3, 'PAID'),
(4, 'REJECTED');

-- -----------------------------------------------------
-- Table `cargo_servlet_db`.`transportations`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `cargo_servlet_db`.`transportations` ;

CREATE TABLE IF NOT EXISTS `cargo_servlet_db`.`transportations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `comment` VARCHAR(45) NULL,
  `user_id` INT NOT NULL,
  `tariff_id` INT NOT NULL,
  `created_at` DATE NOT NULL,
  `delivery_at` DATE NOT NULL,
  `status_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_transportations_tariffs1_idx` (`tariff_id` ASC),
  INDEX `fk_transportations_Users1_idx` (`user_id` ASC),
  INDEX `fk_transportations_status1_idx` (`status_id` ASC),
  CONSTRAINT `fk_transportations_tariffs1`
    FOREIGN KEY (`tariff_id`)
    REFERENCES `cargo_servlet_db`.`tariffs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transportations_Users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `cargo_servlet_db`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transportations_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `cargo_servlet_db`.`status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


