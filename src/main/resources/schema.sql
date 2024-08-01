SET foreign_key_checks = 0;

-- >> Admin Domain
DROP TABLE IF EXISTS `admin_user`;
-- << Admin Domain

-- >> User Domain
DROP TABLE IF EXISTS `user_profile_tag`;
DROP TABLE IF EXISTS `user_nickname`;
DROP TABLE IF EXISTS `user_tag`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `user`;
-- << User Domain

-- >> Image Domain
DROP TABLE IF EXISTS `image`;
-- << Image Domain

-- >> Log Domain
DROP TABLE IF EXISTS `api_log`;
-- << Log Domain

SET foreign_key_checks = 1;

-- >> Log Domain
CREATE TABLE `api_log`
(
    `api_log_seq`          BIGINT UNSIGNED   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `duration_millisecond` INT UNSIGNED      NOT NULL,
    `status_code`          SMALLINT UNSIGNED NOT NULL,
    `http_method`          VARCHAR(10)       NOT NULL,
    `http_uri`             VARCHAR(200)      NOT NULL,
    `parameter`            VARCHAR(1000),
    `user_agent`           VARCHAR(400),
    `auth_token`           VARCHAR(400),
    `request_header`        VARCHAR(1000),
    `response_header`      VARCHAR(400),
    `request_body`          MEDIUMTEXT,
    `response_body`        MEDIUMTEXT,
    `created_at`           DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `creator`              VARCHAR(250)
);
CREATE INDEX `IDX-api_log(http_uri)` ON `api_log` (`http_uri`);
CREATE INDEX `IDX-api_log(creator)` ON `api_log` (`creator`);
-- << Log Domain

-- >> Image Domain
CREATE TABLE `image`
(
    `image`      VARCHAR(200) NOT NULL PRIMARY KEY,
    `deleted_at` DATETIME,
    `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `creator`    VARCHAR(250),
    `updater`    VARCHAR(250)
);
-- << Image Domain

-- >> User Domain
CREATE TABLE `user`
(
    `user_seq`                   INT UNSIGNED                                  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `user_id`                    VARCHAR(80)                                   NOT NULL DEFAULT (UUID()) UNIQUE,
    `user_type`                  ENUM ('EMAIL', 'GOOGLE', 'FACEBOOK', 'APPLE') NOT NULL,
    `sso_id`                     VARCHAR(100)                                  NOT NULL,
    `password`                   VARCHAR(100),
    `email`                      VARCHAR(100),
    `nickname`                   VARCHAR(200)                                  NOT NULL DEFAULT 'Anonymous',
    `image`                      VARCHAR(200)                                  NOT NULL DEFAULT 'profile_icon_1',
    `biography`                  VARCHAR(200),
    `morning_reminder_time`      TIME                                          NOT NULL DEFAULT '08:00',
    `evening_reminder_time`      TIME                                          NOT NULL DEFAULT '22:00',
    `morning_reminder_active_yn` CHAR(1)                                       NOT NULL DEFAULT 'Y',
    `evening_reminder_active_yn` CHAR(1)                                       NOT NULL DEFAULT 'Y',
    `private_account_yn`         CHAR(1)                                       NOT NULL DEFAULT 'N',
    `deleted_at`                 DATETIME,
    `offline_updated_at`         DATETIME,
    `last_activity_at`           DATETIME                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_at`                 DATETIME                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                 DATETIME                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `creator`                    VARCHAR(250),
    `updater`                    VARCHAR(250)
);
CREATE INDEX `IDX-user(sso_id,user_type)` ON `user` (`sso_id`, `user_type`);
CREATE INDEX `IDX-user(user_id)` ON `user` (`user_id`);

CREATE TABLE `user_role`
(
    `user_role`  VARCHAR(20)  NOT NULL,
    `user_seq`   INT UNSIGNED NOT NULL,
    `expired_at` DATETIME,
    `deleted_at` DATETIME,
    `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `creator`    VARCHAR(250),
    `updater`    VARCHAR(250),
    FOREIGN KEY (`user_seq`) REFERENCES `user` (`user_seq`),
    PRIMARY KEY (`user_seq`, `user_role`)
);
CREATE INDEX `IDX-user_role(user_seq)` ON `user_role` (`user_seq`);

CREATE TABLE `user_tag`
(
    `user_tag`   VARCHAR(20)  NOT NULL,
    `user_seq`   INT UNSIGNED NOT NULL,
    `deleted_at` DATETIME,
    `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `creator`    VARCHAR(250),
    `updater`    VARCHAR(250),
    FOREIGN KEY (`user_seq`) REFERENCES `user` (`user_seq`),
    PRIMARY KEY (`user_seq`, `user_tag`)
);
CREATE INDEX `IDX-user_tag(user_seq)` ON `user_tag` (`user_seq`);

CREATE TABLE `user_nickname`
(
    `nickname` VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE `user_profile_tag`
(
    `user_tag`                    VARCHAR(20)       NOT NULL PRIMARY KEY,
    `user_tag_category`           VARCHAR(20)       NOT NULL,
    `user_tag_category_asc_order` SMALLINT UNSIGNED NOT NULL DEFAULT 0,
    `user_tag_asc_order`          SMALLINT UNSIGNED NOT NULL DEFAULT 0,
    `deleted_at`                  DATETIME,
    `created_at`                  DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`                  DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `creator`                     VARCHAR(250),
    `updater`                     VARCHAR(250)
);
-- << User Domain

-- >> Admin Domain
CREATE TABLE `admin_user`
(
    `admin_user_seq`   INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `email`            VARCHAR(100) NOT NULL UNIQUE,
    `password`         VARCHAR(100) NOT NULL,
    `nickname`         VARCHAR(200) NOT NULL DEFAULT 'Anonymous',
    `admin_type`       VARCHAR(20)  NOT NULL DEFAULT 'ADMIN',
    `admin_permission` VARCHAR(2000),
    `otp_secret_key`   VARCHAR(200),
    `deleted_at`       DATETIME,
    `last_activity_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_at`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `creator`          VARCHAR(250),
    `updater`          VARCHAR(250)
);
CREATE INDEX `IDX-admin_user(email)` ON `admin_user` (`email`);
-- << Admin Domain
