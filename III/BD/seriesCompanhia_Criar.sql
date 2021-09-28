-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema seriescompanhia
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema seriescompanhia
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `seriescompanhia`;
CREATE SCHEMA IF NOT EXISTS `seriescompanhia` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `seriescompanhia` ;

-- -----------------------------------------------------
-- Table `seriescompanhia`.`cartao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`cartao` (
  `IDCartao` INT NOT NULL,
  `cartao` INT NOT NULL,
  PRIMARY KEY (`IDCartao`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`utilizador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`utilizador` (
  `IDUtilizador` INT NOT NULL,
  `nome` VARCHAR(65) NOT NULL,
  `localidade` VARCHAR(60) NOT NULL,
  `rua` VARCHAR(60) NOT NULL,
  `codigoPostal` VARCHAR(65) NOT NULL,
  `pais` VARCHAR(65) NOT NULL,
  `email` VARCHAR(65) NOT NULL,
  `cartao` INT NOT NULL,
  PRIMARY KEY (`IDUtilizador`),
  INDEX `FK_Utilizador_2` (`cartao` ASC) VISIBLE,
  CONSTRAINT `FK_Utilizador_2`
    FOREIGN KEY (`cartao`)
    REFERENCES `seriescompanhia`.`cartao` (`IDCartao`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`genero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`genero` (
  `IDGenero` INT NOT NULL,
  `genero` VARCHAR(65) NOT NULL,
  PRIMARY KEY (`IDGenero`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`servico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`servico` (
  `IDServico` INT NOT NULL,
  `preco` DOUBLE NOT NULL,
  PRIMARY KEY (`IDServico`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`companhia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`companhia` (
  `IDCompanhia` INT NOT NULL,
  `nome` VARCHAR(65) NOT NULL,
  `comissao` DOUBLE NOT NULL,
  `dataFundacao` DATE NOT NULL,
  PRIMARY KEY (`IDCompanhia`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`serie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`serie` (
  `IDSerie` INT NOT NULL,
  `nome` VARCHAR(65) NOT NULL,
  `avaliacao` DOUBLE NOT NULL,
  `classificacaoEtaria` INT NOT NULL,
  `premio` INT NOT NULL,
  `idioma` VARCHAR(65) NOT NULL,
  `genero` INT NOT NULL,
  `servico` INT NOT NULL,
  `companhia` INT NOT NULL,
  PRIMARY KEY (`IDSerie`),
  INDEX `FK_Serie_2` (`genero` ASC) VISIBLE,
  INDEX `FK_Serie_3` (`servico` ASC) VISIBLE,
  INDEX `FK_Serie_4` (`companhia` ASC) VISIBLE,
  CONSTRAINT `FK_Serie_2`
    FOREIGN KEY (`genero`)
    REFERENCES `seriescompanhia`.`genero` (`IDGenero`),
  CONSTRAINT `FK_Serie_3`
    FOREIGN KEY (`servico`)
    REFERENCES `seriescompanhia`.`servico` (`IDServico`)
    ON DELETE RESTRICT,
  CONSTRAINT `FK_Serie_4`
    FOREIGN KEY (`companhia`)
    REFERENCES `seriescompanhia`.`companhia` (`IDCompanhia`)
    ON DELETE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`assiste`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`assiste` (
  `favorita` TINYINT NOT NULL,
  `utilizador` INT NOT NULL,
  `serie` INT NOT NULL,
  INDEX `FK_assiste_1` (`utilizador` ASC) VISIBLE,
  INDEX `FK_assiste_2` (`serie` ASC) VISIBLE,
  CONSTRAINT `FK_assiste_1`
    FOREIGN KEY (`utilizador`)
    REFERENCES `seriescompanhia`.`utilizador` (`IDUtilizador`)
    ON DELETE RESTRICT,
  CONSTRAINT `FK_assiste_2`
    FOREIGN KEY (`serie`)
    REFERENCES `seriescompanhia`.`serie` (`IDSerie`)
    ON DELETE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`ator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`ator` (
  `IDAtor` INT NOT NULL,
  `nome` VARCHAR(65) NOT NULL,
  `dataNascimento` DATE NOT NULL,
  `nacionalidade` VARCHAR(65) NOT NULL,
  `genero` VARCHAR(65) NOT NULL,
  PRIMARY KEY (`IDAtor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`criador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`criador` (
  `IDCriador` INT NOT NULL,
  `nome` VARCHAR(65) NOT NULL,
  `dataNascimento` DATE NOT NULL,
  `genero` VARCHAR(35) NOT NULL,
  `nacionalidade` VARCHAR(65) NOT NULL,
  PRIMARY KEY (`IDCriador`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`cria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`cria` (
  `serie` INT NOT NULL,
  `criador` INT NOT NULL,
  INDEX `FK_cria_1` (`serie` ASC) VISIBLE,
  INDEX `FK_cria_2` (`criador` ASC) VISIBLE,
  CONSTRAINT `FK_cria_1`
    FOREIGN KEY (`serie`)
    REFERENCES `seriescompanhia`.`serie` (`IDSerie`)
    ON DELETE RESTRICT,
  CONSTRAINT `FK_cria_2`
    FOREIGN KEY (`criador`)
    REFERENCES `seriescompanhia`.`criador` (`IDCriador`)
    ON DELETE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`legendas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`legendas` (
  `IDLegenda` INT NOT NULL,
  `legendas` VARCHAR(65) NOT NULL,
  PRIMARY KEY (`IDLegenda`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`temporada`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`temporada` (
  `IDTemporada` INT NOT NULL,
  `numero` INT NOT NULL,
  `ano` INT NOT NULL,
  `serie` INT NOT NULL,
  PRIMARY KEY (`IDTemporada`),
  INDEX `FK_Temporada_2` (`serie` ASC) VISIBLE,
  CONSTRAINT `FK_Temporada_2`
    FOREIGN KEY (`serie`)
    REFERENCES `seriescompanhia`.`serie` (`IDSerie`)
    ON DELETE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`episodio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`episodio` (
  `IDEpisodio` INT NOT NULL,
  `nome` VARCHAR(65) NOT NULL,
  `avaliacao` DOUBLE NOT NULL,
  `numero` INT NOT NULL,
  `duracao` TIME NOT NULL,
  `legendas` INT NOT NULL,
  `temporada` INT NOT NULL,
  PRIMARY KEY (`IDEpisodio`),
  INDEX `FK_Episodio_2` (`legendas` ASC) VISIBLE,
  INDEX `FK_Episodio_3` (`temporada` ASC) VISIBLE,
  CONSTRAINT `FK_Episodio_2`
    FOREIGN KEY (`legendas`)
    REFERENCES `seriescompanhia`.`legendas` (`IDLegenda`),
  CONSTRAINT `FK_Episodio_3`
    FOREIGN KEY (`temporada`)
    REFERENCES `seriescompanhia`.`temporada` (`IDTemporada`)
    ON DELETE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`possui`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`possui` (
  `serie` INT NOT NULL,
  `ator` INT NOT NULL,
  INDEX `FK_possui_1` (`serie` ASC) VISIBLE,
  INDEX `FK_possui_2` (`ator` ASC) VISIBLE,
  CONSTRAINT `FK_possui_1`
    FOREIGN KEY (`serie`)
    REFERENCES `seriescompanhia`.`serie` (`IDSerie`)
    ON DELETE RESTRICT,
  CONSTRAINT `FK_possui_2`
    FOREIGN KEY (`ator`)
    REFERENCES `seriescompanhia`.`ator` (`IDAtor`)
    ON DELETE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `seriescompanhia`.`subscreve`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `seriescompanhia`.`subscreve` (
  `DataInicio` DATE NOT NULL,
  `DataFim` DATE NOT NULL,
  `preco` DOUBLE NOT NULL,
  `utilizador` INT NOT NULL,
  `servico` INT NOT NULL,
  INDEX `FK_subscreve_1` (`utilizador` ASC) VISIBLE,
  INDEX `FK_subscreve_2` (`servico` ASC) VISIBLE,
  CONSTRAINT `FK_subscreve_1`
    FOREIGN KEY (`utilizador`)
    REFERENCES `seriescompanhia`.`utilizador` (`IDUtilizador`)
    ON DELETE RESTRICT,
  CONSTRAINT `FK_subscreve_2`
    FOREIGN KEY (`servico`)
    REFERENCES `seriescompanhia`.`servico` (`IDServico`)
    ON DELETE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


