-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema porali
-- -----------------------------------------------------

-- DROP SCHEMA `porali`;
CREATE SCHEMA IF NOT EXISTS `porali` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `porali` ;

-- -----------------------------------------------------
-- Table `porali`.`passageiro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `porali`.`passageiro` (
  `Email` VARCHAR(100) NOT NULL,
  `Password` VARCHAR(100) NOT NULL,
  `Avaliacao` INT NOT NULL,
  `Codigo` VARCHAR(100) NOT NULL,
  `Nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`Email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `porali`.`notificacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `porali`.`notificacao` (
  `IDNotificacao` INT NOT NULL,
  `Nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`IDNotificacao`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `porali`.`ativa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `porali`.`ativa` (
  `UserEmail` VARCHAR(100) NOT NULL,
  `NotificacaoID` INT NOT NULL,
  `ativada` TINYINT(1) NOT NULL,
  INDEX `FK_ativa_1` (`UserEmail` ASC) VISIBLE,
  INDEX `FK_ativa_2` (`NotificacaoID` ASC) VISIBLE,
  PRIMARY KEY (`NotificacaoID`, `UserEmail`),
  CONSTRAINT `FK_ativa_1`
    FOREIGN KEY (`UserEmail`)
    REFERENCES `porali`.`passageiro` (`Email`)
    ON DELETE RESTRICT,
  CONSTRAINT `FK_ativa_2`
    FOREIGN KEY (`NotificacaoID`)
    REFERENCES `porali`.`notificacao` (`IDNotificacao`)
    ON DELETE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `porali`.`viagem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `porali`.`viagem` (
  `IDViagem` INT NOT NULL,
  `Hora_inicio` DATETIME NOT NULL,
  `Hora_Fim` DATETIME NOT NULL,
  `Local_chegada` VARCHAR(100) NOT NULL,
  `Local_partida` VARCHAR(100) NOT NULL,
  `Preco` DOUBLE NOT NULL,
  PRIMARY KEY (`IDViagem`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `porali`.`efetua`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `porali`.`efetua` (
  `UserEmail` VARCHAR(100) NOT NULL,
  `ViagemID` INT NOT NULL,
  INDEX `FK_efetua_1` (`UserEmail` ASC) VISIBLE,
  INDEX `FK_efetua_2` (`ViagemID` ASC) VISIBLE,
  PRIMARY KEY (`ViagemID`, `UserEmail`),
  CONSTRAINT `FK_efetua_1`
    FOREIGN KEY (`UserEmail`)
    REFERENCES `porali`.`passageiro` (`Email`)
    ON DELETE RESTRICT,
  CONSTRAINT `FK_efetua_2`
    FOREIGN KEY (`ViagemID`)
    REFERENCES `porali`.`viagem` (`IDViagem`)
    ON DELETE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `porali`.`viagem_gratis`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `porali`.`viagem_gratis` (
  `Codigo` INT NOT NULL,
  `Redimida` TINYINT(1) NOT NULL,
  `Validade` DATE NOT NULL,
  `UserEmail` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`Codigo`),
  INDEX `FK_Viagem_Gratis_2` (`UserEmail` ASC) VISIBLE,
  CONSTRAINT `FK_Viagem_Gratis_2`
    FOREIGN KEY (`UserEmail`)
    REFERENCES `porali`.`passageiro` (`Email`)
    ON DELETE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
