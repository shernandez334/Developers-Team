-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema escape_room
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema escape_room
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `escape_room` DEFAULT CHARACTER SET utf8mb4 ;
USE `escape_room` ;

-- -----------------------------------------------------
-- Table `escape_room`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`room` (
  `room_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `difficulty` ENUM('EASY', 'MEDIUM', 'HARD', 'EPIC') NOT NULL,
  `deleted` INT NOT NULL,
  PRIMARY KEY (`room_id`),
  UNIQUE INDEX `room_id_UNIQUE` (`room_id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `escape_room`.`element`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`element` (
  `element_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(10,2) UNSIGNED NULL,
  `type` ENUM('ROOM', 'CLUE', 'DECORATION') NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`element_id`),
  UNIQUE INDEX `element_id_UNIQUE` (`element_id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `escape_room`.`clue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`clue` (
  `element_id` INT NOT NULL,
  `theme` ENUM('SCI_FI', 'MEDIEVAL', 'SPACE') NOT NULL,
  PRIMARY KEY (`element_id`),
  UNIQUE INDEX `element_id_UNIQUE` (`element_id` ASC) VISIBLE,
  CONSTRAINT `fk_element1`
    FOREIGN KEY (`element_id`)
    REFERENCES `escape_room`.`element` (`element_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `escape_room`.`decor_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`decor_item` (
  `element_id` INT NOT NULL,
  `material` ENUM('PLASTIC', 'PAPER', 'STONE', 'GLASS', 'METAL') NOT NULL,
  PRIMARY KEY (`element_id`),
  INDEX `fk_element2_idx` (`element_id` ASC) VISIBLE,
  UNIQUE INDEX `element_id_UNIQUE` (`element_id` ASC) VISIBLE,
  CONSTRAINT `fk_element2`
    FOREIGN KEY (`element_id`)
    REFERENCES `escape_room`.`element` (`element_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `escape_room`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `role` ENUM('player', 'admin') NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `escape_room`.`reward`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`reward` (
  `reward_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  `banner` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`reward_id`),
  UNIQUE INDEX `reward_id_UNIQUE` (`reward_id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `escape_room`.`user_plays_room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`user_plays_room` (
  `user_plays_room_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `room_id` INT NOT NULL,
  `solved` TINYINT NOT NULL,
  `date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX `fk_user1_idx` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`user_plays_room_id`),
  UNIQUE INDEX `user_plays_room_id_UNIQUE` (`user_plays_room_id` ASC) VISIBLE,
  INDEX `fk_room1_idx` (`room_id` ASC) VISIBLE,
  CONSTRAINT `fk_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `escape_room`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_room1`
    FOREIGN KEY (`room_id`)
    REFERENCES `escape_room`.`room` (`room_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `escape_room`.`user_has_reward`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`user_has_reward` (
  `user_id` INT NOT NULL,
  `reward_id` INT NOT NULL,
  `date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`, `reward_id`),
  INDEX `fk_reward1_idx` (`reward_id` ASC) VISIBLE,
  INDEX `fk_user2_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user2`
    FOREIGN KEY (`user_id`)
    REFERENCES `escape_room`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reward1`
    FOREIGN KEY (`reward_id`)
    REFERENCES `escape_room`.`reward` (`reward_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `escape_room`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`ticket` (
  `ticket_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `price` DECIMAL(4,2) UNSIGNED NOT NULL,
  `cashed` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`ticket_id`),
  INDEX `fk_user3_idx` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `ticket_id_UNIQUE` (`ticket_id` ASC) VISIBLE,
  CONSTRAINT `fk_user3`
    FOREIGN KEY (`user_id`)
    REFERENCES `escape_room`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `escape_room`.`subscription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`subscription` (
  `user_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user4`
    FOREIGN KEY (`user_id`)
    REFERENCES `escape_room`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `escape_room`.`notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`notification` (
  `notification_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `message` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`notification_id`),
  UNIQUE INDEX `notification_id_UNIQUE` (`notification_id` ASC) VISIBLE,
  INDEX `fk_user5_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user5`
    FOREIGN KEY (`user_id`)
    REFERENCES `escape_room`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `escape_room`.`persistent_property`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`persistent_property` (
  `property_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `value` VARCHAR(45) NULL,
  PRIMARY KEY (`property_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `escape_room`.`room_has_element`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `escape_room`.`room_has_element` (
  `room_id` INT NOT NULL,
  `element_id` INT NOT NULL,
  `quantity` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`room_id`, `element_id`),
  INDEX `fk_element3_idx` (`element_id` ASC) VISIBLE,
  INDEX `fk_room2_idx` (`room_id` ASC) VISIBLE,
  CONSTRAINT `fk_room2`
    FOREIGN KEY (`room_id`)
    REFERENCES `escape_room`.`room` (`room_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_element3`
    FOREIGN KEY (`element_id`)
    REFERENCES `escape_room`.`element` (`element_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `escape_room`.`reward`
-- -----------------------------------------------------
START TRANSACTION;
USE `escape_room`;
INSERT INTO `escape_room`.`reward` (`reward_id`, `name`, `description`, `banner`) VALUES (1, 'Welcome Onboard!', 'You have played your first room', '🎉');
INSERT INTO `escape_room`.`reward` (`reward_id`, `name`, `description`, `banner`) VALUES (2, 'Tutti Frutti', 'One room of each difficulty played', '🍎🍉🍓🍌');
INSERT INTO `escape_room`.`reward` (`reward_id`, `name`, `description`, `banner`) VALUES (3, 'Loyal Member', 'You have played 3 rooms', '🙌');
INSERT INTO `escape_room`.`reward` (`reward_id`, `name`, `description`, `banner`) VALUES (4, 'Heavy Thinker', 'You have solved 2 epic rooms', '🔍');
INSERT INTO `escape_room`.`reward` (`reward_id`, `name`, `description`, `banner`) VALUES (5, 'Bureaucrat', 'You have created a certificate', '📃');
INSERT INTO `escape_room`.`reward` (`reward_id`, `name`, `description`, `banner`) VALUES (6, 'Messy', 'Have 5 messages in the inbox', '📫');
INSERT INTO `escape_room`.`reward` (`reward_id`, `name`, `description`, `banner`) VALUES (7, 'Stubborn', 'Complete a room after 2 failed attempts', '💪');
INSERT INTO `escape_room`.`reward` (`reward_id`, `name`, `description`, `banner`) VALUES (8, 'U\'re a Looser', 'Fail to complete a room 5 times in a row', '💩');
INSERT INTO `escape_room`.`reward` (`reward_id`, `name`, `description`, `banner`) VALUES (9, 'Investor', 'Buy a bunch of tickets', '💸💸');
INSERT INTO `escape_room`.`reward` (`reward_id`, `name`, `description`, `banner`) VALUES (10, 'Easy Peasy!', 'You have checked your rewards', '🍋');

COMMIT;


-- -----------------------------------------------------
-- Data for table `escape_room`.`persistent_property`
-- -----------------------------------------------------
START TRANSACTION;
USE `escape_room`;
INSERT INTO `escape_room`.`persistent_property` (`property_id`, `name`, `value`) VALUES (1, 'ticket_price', '9.99');

COMMIT;

