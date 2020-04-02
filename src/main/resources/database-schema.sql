# noinspection SqlNoDataSourceInspectionForFile

CREATE DATABASE IF NOT EXISTS `todolist`;
USE `todolist`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `tasklist`;
DROP TABLE IF EXISTS `subtask`;
DROP TABLE IF EXISTS `task`;
DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `roles`;

CREATE TABLE `tasklist`
(
    `id`             int NOT NULL AUTO_INCREMENT,
    `task_list_name` varchar(45) DEFAULT NULL,
    `user_name`      varchar(45) DEFAULT NULL,
    `created_at`     datetime    DEFAULT CURRENT_TIMESTAMP,
    `modified_at`    datetime    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `task`
(
    `id`               int NOT NULL AUTO_INCREMENT,
    `task_list_id`     int         DEFAULT NULL,
    `task_name`        varchar(45) DEFAULT NULL,
    `user_name`        varchar(45) DEFAULT NULL,
    `task_description` varchar(45) DEFAULT NULL,
    `task_status`      varchar(45) DEFAULT NULL,
    `created_at`       datetime    DEFAULT CURRENT_TIMESTAMP,
    `modified_at`      datetime    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `FK_DETAIL_idx` (`task_list_id`),
    CONSTRAINT `FK_DETAIL` FOREIGN KEY (`task_list_id`) REFERENCES `tasklist` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = latin1;

CREATE TABLE `subtask`
(
    `id`                  int NOT NULL AUTO_INCREMENT,
    `task_id`             int         DEFAULT NULL,
    `subtask_name`        varchar(45) DEFAULT NULL,
    `user_name`           varchar(45) DEFAULT NULL,
    `subtask_description` varchar(45) DEFAULT NULL,
    `subtask_status`      varchar(45) DEFAULT NULL,
    `created_at`          timestamp   DEFAULT CURRENT_TIMESTAMP,
    `modified_at`         timestamp   DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `FK_SUBTASK_DETAIL_idx` (`task_id`),
    CONSTRAINT `FK_SUBTASK_DETAIL` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = latin1;

CREATE TABLE `roles`
(
    `id`   int NOT NULL AUTO_INCREMENT,
    `name` ENUM ('ROLE_USER','ROLE_MODERATOR', 'ROLE_ADMIN'),
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = latin1;

CREATE TABLE `users`
(
    `id`        int          NOT NULL AUTO_INCREMENT,
    `email`     varchar(45)  NOT NULL,
    `username`  varchar(45)  NOT NULL,
    `password`  varchar(120) NOT NULL,
    `firstname` varchar(50),
    `lastname`  varchar(50),
    PRIMARY KEY (`id`),
    CONSTRAINT UNIQUE_USERS UNIQUE (`username`, `password`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = latin1;

CREATE TABLE `user_roles`
(
    `user_id` INTEGER NOT NULL,
    `role_id` INTEGER NOT NULL,
    FOREIGN KEY (`role_id`) REFERENCES roles (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES users (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = latin1;

INSERT INTO `roles`(`name`)
VALUES ('ROLE_USER');
INSERT INTO `roles`(`name`)
VALUES ('ROLE_MODERATOR');
INSERT INTO `roles`(`name`)
VALUES ('ROLE_ADMIN');
