-- create schema
CREATE SCHEMA IF NOT EXISTS `demo` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- -----------------------------------------------------
-- Table `SCHEDULE_JOB`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SCHEDULE_JOB`;
create table `SCHEDULE_JOB`
(
  `JOB_ID`          INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `JOB_NAME`        VARCHAR(255),
  `JOB_GROUP`       VARCHAR(255),
  `STATUS`          VARCHAR(1),
  `CRON_EXPRESSION` VARCHAR(255),
  `REMARK`          VARCHAR(500) NULL,
  `RUN_CLASS`       VARCHAR(255),
  `CREATE_TIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_TIME` TIMESTAMP NULL,
  PRIMARY KEY (`JOB_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `SCHEDULE_JOB_DATA`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SCHEDULE_JOB_DATA`;
create table `SCHEDULE_JOB_DATA`
(
  `JOB_DATA_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `JOB_ID`      INT not null,
  `DATA_NAME`   VARCHAR(100),
  `DATA_VALUE`  VARCHAR(500),
  `CREATE_TIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_TIME` TIMESTAMP NULL,
  PRIMARY KEY (`JOB_DATA_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
