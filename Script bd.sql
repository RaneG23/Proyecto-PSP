CREATE SCHEMA `appgestalmacen` ;

CREATE TABLE `appgestalmacen`.`usuarios` (
  `USUARIO` INT NOT NULL DEFAULT 0,
  `NOMBRE` VARCHAR(30) NOT NULL,
  `CONTRASENYA` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`USUARIO`));
  
INSERT INTO `appgestalmacen`.`usuarios`(`USUARIO`,`NOMBRE`,`CONTRASENYA`)
VALUES(0,"admin","1234");



CREATE TABLE `appgestalmacen`.`articulos` (
  `articulo` VARCHAR(15) NOT NULL,
  `titulo` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`articulo`));

INSERT INTO `appgestalmacen`.`articulos`
(`articulo`,`titulo`)
VALUES
("00001","Artículo de prueba 1");

INSERT INTO `appgestalmacen`.`articulos`
(`articulo`,`titulo`)
VALUES
("00002","Artículo de prueba 2");

INSERT INTO `appgestalmacen`.`articulos`
(`articulo`,`titulo`)
VALUES
("00003","Artículo de prueba 3");

INSERT INTO `appgestalmacen`.`articulos`
(`articulo`,`titulo`)
VALUES
("00004","Artículo de prueba 4");

INSERT INTO `appgestalmacen`.`articulos`
(`articulo`,`titulo`)
VALUES
("00005","Artículo de prueba 5");



CREATE TABLE `appgestalmacen`.`almacenes` (
  `almacen` VARCHAR(3) NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`almacen`));
  
INSERT INTO `appgestalmacen`.`almacenes`
(`almacen`,`titulo`)
VALUES
("ALE","Almacén Elche");

INSERT INTO `appgestalmacen`.`almacenes`
(`almacen`,`titulo`)
VALUES
("ALM","Almacén Madrid");

INSERT INTO `appgestalmacen`.`almacenes`
(`almacen`,`titulo`)
VALUES
("ALV","Almacén Valencia");

INSERT INTO `appgestalmacen`.`almacenes`
(`almacen`,`titulo`)
VALUES
("ALB","Almacén Barcelona");



CREATE TABLE `appgestalmacen`.`stocks` (
  `almacen` VARCHAR(3) NOT NULL,
  `articulo` VARCHAR(15) NOT NULL,
  `stock` INT NOT NULL,
  PRIMARY KEY (`almacen`, `articulo`),
  INDEX `fk_stocks_art_idx` (`articulo` ASC) VISIBLE,
  CONSTRAINT `fk_stocks_alm`
    FOREIGN KEY (`almacen`)
    REFERENCES `appgestalmacen`.`almacenes` (`almacen`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stocks_art`
    FOREIGN KEY (`articulo`)
    REFERENCES `appgestalmacen`.`articulos` (`articulo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
INSERT INTO `appgestalmacen`.`stocks`
(`almacen`,`articulo`,`stock`)
VALUES
("ALE","00001",15);

INSERT INTO `appgestalmacen`.`stocks`
(`almacen`,`articulo`,`stock`)
VALUES
("ALE","00002",6);

INSERT INTO `appgestalmacen`.`stocks`
(`almacen`,`articulo`,`stock`)
VALUES
("ALE","00003",10);

INSERT INTO `appgestalmacen`.`stocks`
(`almacen`,`articulo`,`stock`)
VALUES
("ALM","00002",20);

INSERT INTO `appgestalmacen`.`stocks`
(`almacen`,`articulo`,`stock`)
VALUES
("ALB","00001",30);

INSERT INTO `appgestalmacen`.`stocks`
(`almacen`,`articulo`,`stock`)
VALUES
("ALV","00005",18);