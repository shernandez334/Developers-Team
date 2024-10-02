CREATE TABLE `user_plays_rooms`(
    `user_play_rooms_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `room_id` INT NOT NULL,
    `solved` TINYINT NOT NULL
);
CREATE TABLE `subscription`(
    `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT
);
CREATE TABLE `User`(
    `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` ENUM('') NOT NULL
);
CREATE TABLE `clue`(
    `element_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `theme` BIGINT NOT NULL
);
CREATE TABLE `decor_item`(
    `element_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `theme` VARCHAR(255) NOT NULL
);
CREATE TABLE `ticket`(
    `ticket_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `date` DATETIME NOT NULL,
    `price` INT NOT NULL,
    `chashed` TINYINT NOT NULL
);
CREATE TABLE `room`(
    `room_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `element_id` INT NOT NULL,
    `difficult` ENUM('') NOT NULL
);
CREATE TABLE `reward`(
    `reward_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL
);
CREATE TABLE `stock_manager`(
    `element_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY
);
CREATE TABLE `user_has reward`(
    `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `reward_id` INT NOT NULL,
    `date` DATETIME NOT NULL
);
CREATE TABLE `notification`(
    `notification_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `message` VARCHAR(255) NOT NULL
);
CREATE TABLE `element`(
    `element_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` INT NOT NULL,
    `type` ENUM('') NOT NULL,
    `quantity` INT NOT NULL,
    `status` INT NOT NULL,
    `price` FLOAT NOT NULL
);
ALTER TABLE
    `ticket` ADD CONSTRAINT `ticket_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `User`(`user_id`);
ALTER TABLE
    `stock_manager` ADD CONSTRAINT `stock_manager_element_id_foreign` FOREIGN KEY(`element_id`) REFERENCES `element`(`element_id`);
ALTER TABLE
    `clue` ADD CONSTRAINT `clue_element_id_foreign` FOREIGN KEY(`element_id`) REFERENCES `element`(`element_id`);
ALTER TABLE
    `user_has reward` ADD CONSTRAINT `user_has reward_reward_id_foreign` FOREIGN KEY(`reward_id`) REFERENCES `reward`(`reward_id`);
ALTER TABLE
    `user_has reward` ADD CONSTRAINT `user_has reward_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `User`(`user_id`);
ALTER TABLE
    `room` ADD CONSTRAINT `room_element_id_foreign` FOREIGN KEY(`element_id`) REFERENCES `element`(`element_id`);
ALTER TABLE
    `user_plays_rooms` ADD CONSTRAINT `user_plays_rooms_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `User`(`user_id`);
ALTER TABLE
    `subscription` ADD CONSTRAINT `subscription_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `User`(`user_id`);
ALTER TABLE
    `notification` ADD CONSTRAINT `notification_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `User`(`user_id`);
ALTER TABLE
    `decor_item` ADD CONSTRAINT `decor_item_element_id_foreign` FOREIGN KEY(`element_id`) REFERENCES `element`(`element_id`);
ALTER TABLE
    `user_plays_rooms` ADD CONSTRAINT `user_plays_rooms_room_id_foreign` FOREIGN KEY(`room_id`) REFERENCES `room`(`room_id`);