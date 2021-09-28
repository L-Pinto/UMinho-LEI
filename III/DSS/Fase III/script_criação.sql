-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Armazem
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Armazem
-- -----------------------------------------------------
-- DROP SCHEMA `armazem`;
CREATE SCHEMA IF NOT EXISTS `armazem` DEFAULT CHARACTER SET utf8 ;
USE `armazem` ;

-- -----------------------------------------------------
-- Table `Armazem`.`Utilizador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `armazem`.`Utilizador` (
  `codUtilizador` INT NOT NULL,
  `palavraPasse` VARCHAR(45) NOT NULL,
  `sessaoIniciada` TINYINT NOT NULL,
  PRIMARY KEY (`codUtilizador`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Armazem`.`Robot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `armazem`.`Robot` (
  `codRobot` VARCHAR(45) NOT NULL,
  `ocupado` TINYINT NOT NULL,
  `codQRPalete` VARCHAR(45) NULL,
  `percurso` VARCHAR(45) NULL,
  PRIMARY KEY (`codRobot`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Armazem`.`QRCode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `armazem`.`QRCode` (
  `codQRCode` VARCHAR(45) NOT NULL,
  `perecivel` TINYINT NOT NULL,
  `descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`codQRCode`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Armazem`.`Localizacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `armazem`.`Localizacao` (
  `prateleira` INT NOT NULL,
  `zona` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`prateleira`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Armazem`.`Palete`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `armazem`.`Palete` (
  `codPalete` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  `codQRCode` VARCHAR(45) NOT NULL,
  `localizacao` INT NOT NULL,
  PRIMARY KEY (`codPalete`),
  INDEX `fk_Palete_QRCode_idx` (`codQRCode` ASC) VISIBLE,
  INDEX `fk_Palete_Localizacao1_idx` (`localizacao` ASC) VISIBLE,
  CONSTRAINT `fk_Palete_QRCode`
    FOREIGN KEY (`codQRCode`)
    REFERENCES `Armazem`.`QRCode` (`codQRCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Palete_Localizacao1`
    FOREIGN KEY (`localizacao`)
    REFERENCES `armazem`.`Localizacao` (`prateleira`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Armazem`.`Corredor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `armazem`.`Corredor` (
  `codCorredor` INT NOT NULL,
  `zona` VARCHAR(45) NOT NULL,
  `ocupacao` INT NOT NULL,
  `capacidadeTotal` INT NOT NULL,
  PRIMARY KEY (`codCorredor`))
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

