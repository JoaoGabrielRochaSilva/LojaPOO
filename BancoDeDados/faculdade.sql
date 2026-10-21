-- MySQL dump 10.13  Distrib 8.0.46, for Linux (x86_64)
--
-- Host: localhost    Database: faculdade
-- ------------------------------------------------------
-- Server version	8.0.46-0ubuntu0.24.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `carrinho`
--

DROP TABLE IF EXISTS `carrinho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrinho` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_usuario` bigint NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'Aberto',
  `data_criacao` date NOT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `carrinho_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrinho`
--

LOCK TABLES `carrinho` WRITE;
/*!40000 ALTER TABLE `carrinho` DISABLE KEYS */;
INSERT INTO `carrinho` VALUES (1,5,'Fechado','2026-06-28','2026-06-28'),(2,5,'Fechado','2026-06-29','2026-06-29'),(3,5,'Expirado','2026-07-09','2026-07-10'),(4,5,'Fechado','2026-06-30','2026-06-30'),(5,5,'Fechado','2026-06-30','2026-06-30');
/*!40000 ALTER TABLE `carrinho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cupom`
--

DROP TABLE IF EXISTS `cupom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cupom` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` int NOT NULL,
  `tipo_desconto` varchar(20) NOT NULL,
  `valor_desconto` double NOT NULL,
  `valor_minimo` double NOT NULL,
  `data_validade` date NOT NULL,
  `ativo` tinyint(1) NOT NULL DEFAULT '1',
  `data_criacao` date NOT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cupom`
--

LOCK TABLES `cupom` WRITE;
/*!40000 ALTER TABLE `cupom` DISABLE KEYS */;
INSERT INTO `cupom` VALUES (1,10,'Percentual',20,20,'2027-06-01',1,'2026-06-28','2026-06-28'),(2,11,'Percentual',25,30,'2027-08-30',1,'2026-06-28','2026-06-28'),(3,12,'Fixo',10,50,'2027-01-10',1,'2026-06-28','2026-06-28'),(4,32,'Percentual',50,100,'2026-07-25',1,'2026-07-09','2026-07-09');
/*!40000 ALTER TABLE `cupom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrega`
--

DROP TABLE IF EXISTS `entrega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrega` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_pedido` bigint NOT NULL,
  `codigo_rastreio` int DEFAULT NULL,
  `transportadora` varchar(100) DEFAULT NULL,
  `status` varchar(30) NOT NULL,
  `data_envio` date DEFAULT NULL,
  `data_entrega` date DEFAULT NULL,
  `data_criacao` date NOT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codigo_rastreio` (`codigo_rastreio`),
  KEY `id_pedido` (`id_pedido`),
  CONSTRAINT `entrega_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrega`
--

LOCK TABLES `entrega` WRITE;
/*!40000 ALTER TABLE `entrega` DISABLE KEYS */;
INSERT INTO `entrega` VALUES (1,2,100,'Uberaba Transporte','Entregue','2026-07-01','2026-07-03','2026-06-30','2026-07-03'),(3,5,101,'Uberaba Transporte','Entregue','2026-07-02','2026-07-04','2026-07-01','2026-07-04'),(4,6,102,'Uberaba Transporte','Entregue','2026-07-07','2026-07-09','2026-07-06','2026-07-09'),(5,8,103,'Uberaba Transporte','Cancelada','2026-07-02',NULL,'2026-07-01','2026-07-02');
/*!40000 ALTER TABLE `entrega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itens_carrinho`
--

DROP TABLE IF EXISTS `itens_carrinho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itens_carrinho` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_carrinho` bigint NOT NULL,
  `id_produto` bigint NOT NULL,
  `quantidade` int NOT NULL,
  `preco_unitario` double NOT NULL,
  `data_criacao` date NOT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_carrinho` (`id_carrinho`),
  KEY `id_produto` (`id_produto`),
  CONSTRAINT `itens_carrinho_ibfk_1` FOREIGN KEY (`id_carrinho`) REFERENCES `carrinho` (`id`),
  CONSTRAINT `itens_carrinho_ibfk_2` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itens_carrinho`
--

LOCK TABLES `itens_carrinho` WRITE;
/*!40000 ALTER TABLE `itens_carrinho` DISABLE KEYS */;
INSERT INTO `itens_carrinho` VALUES (1,1,1,5,19.99,'2026-06-28',NULL),(2,2,1,10,19.99,'2026-06-29','2026-06-29'),(3,3,3,5,17.75,'2026-07-09','2026-07-09'),(4,3,1,2,19.99,'2026-07-09','2026-07-09'),(5,4,2,3,12.99,'2026-06-30','2026-06-30'),(6,4,3,2,17.75,'2026-06-30','2026-06-30'),(7,5,2,3,12.99,'2026-06-30','2026-06-30'),(8,5,3,2,17.75,'2026-06-30','2026-06-30');
/*!40000 ALTER TABLE `itens_carrinho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itens_pedido`
--

DROP TABLE IF EXISTS `itens_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itens_pedido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_pedido` bigint NOT NULL,
  `id_produto` bigint NOT NULL,
  `quantidade` int NOT NULL,
  `preco_unitario` double NOT NULL,
  `sub_total` double NOT NULL,
  `data_criacao` date NOT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pedido` (`id_pedido`),
  KEY `id_produto` (`id_produto`),
  CONSTRAINT `itens_pedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id`),
  CONSTRAINT `itens_pedido_ibfk_2` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itens_pedido`
--

LOCK TABLES `itens_pedido` WRITE;
/*!40000 ALTER TABLE `itens_pedido` DISABLE KEYS */;
INSERT INTO `itens_pedido` VALUES (1,2,1,10,19.99,199.89999999999998,'2026-06-29','2026-06-29'),(2,4,2,3,12.99,38.97,'2026-06-30','2026-06-30'),(3,4,3,2,17.75,35.5,'2026-06-30','2026-06-30'),(4,5,2,3,12.99,38.97,'2026-06-30','2026-06-30'),(5,5,3,2,17.75,35.5,'2026-06-30','2026-06-30'),(6,6,1,2,19.99,39.98,'2026-07-05','2026-07-05'),(7,7,4,5,8.75,43.75,'2026-06-30','2026-06-30'),(8,8,4,5,8.75,43.75,'2026-06-30','2026-06-30');
/*!40000 ALTER TABLE `itens_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimentacao_estoque`
--

DROP TABLE IF EXISTS `movimentacao_estoque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimentacao_estoque` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_produto` bigint NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `quantidade` int NOT NULL,
  `valor_unitario` double NOT NULL,
  `data_criacao` date NOT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_produto` (`id_produto`),
  CONSTRAINT `movimentacao_estoque_ibfk_1` FOREIGN KEY (`id_produto`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimentacao_estoque`
--

LOCK TABLES `movimentacao_estoque` WRITE;
/*!40000 ALTER TABLE `movimentacao_estoque` DISABLE KEYS */;
INSERT INTO `movimentacao_estoque` VALUES (1,1,'Saida',10,19.99,'2026-06-29','2026-06-29'),(2,2,'Saida',3,12.99,'2026-06-30','2026-06-30'),(3,3,'Saida',2,17.75,'2026-06-30','2026-06-30'),(4,2,'Saida',3,12.99,'2026-06-30','2026-06-30'),(5,3,'Saida',2,17.75,'2026-06-30','2026-06-30'),(6,1,'Saida',2,19.99,'2026-07-05','2026-07-05'),(7,1,'Entrada',12,19.99,'2026-07-09','2026-07-09'),(8,2,'Entrada',4,12.99,'2026-07-09','2026-07-09'),(9,3,'Entrada',6,17.75,'2026-07-09','2026-07-09'),(10,2,'Ajuste',2,12.99,'2026-07-09','2026-07-09'),(11,3,'Ajuste',-2,17.75,'2026-07-09','2026-07-09'),(12,4,'Entrada',25,8.75,'2026-07-09','2026-07-09'),(13,4,'Saida',5,8.75,'2026-06-30','2026-06-30'),(14,4,'Entrada',5,8.75,'2026-06-30','2026-06-30'),(15,4,'Saida',5,8.75,'2026-06-30','2026-06-30'),(16,4,'Entrada',5,8.75,'2026-07-02','2026-07-02');
/*!40000 ALTER TABLE `movimentacao_estoque` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedidos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_usuario` bigint NOT NULL,
  `id_cupom` bigint DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'Criado',
  `forma_pagamento` varchar(30) DEFAULT NULL,
  `valor_total` double NOT NULL DEFAULT '0',
  `data_criacao` date NOT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_cupom` (`id_cupom`),
  CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `pedidos_ibfk_2` FOREIGN KEY (`id_cupom`) REFERENCES `cupom` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidos`
--

LOCK TABLES `pedidos` WRITE;
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
INSERT INTO `pedidos` VALUES (1,5,NULL,'Cancelado',NULL,0,'2026-06-28','2026-06-29'),(2,5,NULL,'Entregue',NULL,199.89999999999998,'2026-06-29','2026-07-03'),(3,5,NULL,'Cancelado',NULL,0,'2026-06-30','2026-06-30'),(4,5,1,'Entregue','Pix',59.576,'2026-06-30','2026-07-04'),(5,5,1,'Entregue','Debito',59.576,'2026-06-30','2026-07-04'),(6,5,NULL,'Entregue','Credito',39.98,'2026-07-05','2026-07-09'),(7,5,NULL,'Cancelado',NULL,43.75,'2026-06-30','2026-06-30'),(8,5,NULL,'Cancelado','Pix',43.75,'2026-06-30','2026-07-02');
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pessoa` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `documento` varchar(50) DEFAULT NULL,
  `nascimento` date DEFAULT NULL,
  `data_criacao` date NOT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` VALUES (1,'josephina','RG','2000-01-01','2026-06-28',NULL),(2,'jaspion','RG','2006-06-01','2026-06-28',NULL),(3,'jiraia','RG','1995-05-25','2026-06-28',NULL),(4,'João Gabriel','CPF','2007-03-31','2026-06-28','2026-06-29'),(5,'Gabriel','CPF','2007-03-31','2026-07-10','2026-07-10');
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `preco_venda` double NOT NULL,
  `qtd_estoque` int NOT NULL DEFAULT '0',
  `ativo` tinyint(1) NOT NULL DEFAULT '1',
  `data_criacao` date NOT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (1,'Arroz','Pacote de arroz 5Kg',19.99,50,1,'2026-06-28','2026-07-09'),(2,'Feijao','Pacote de feijao 2Kg',12.99,50,1,'2026-06-28','2026-07-09'),(3,'Carne moida','Bandeja com 1Kg',17.75,50,1,'2026-06-28','2026-07-09'),(4,'Tody','Achocolatado do balacobaco.',8.75,25,1,'2026-07-09','2026-07-02');
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_pessoa` bigint NOT NULL,
  `login` varchar(50) NOT NULL,
  `senha` varchar(50) NOT NULL,
  `data_criacao` date NOT NULL,
  `data_modificacao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  KEY `id_pessoa` (`id_pessoa`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`id_pessoa`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,'josephina','josephina','2026-06-28',NULL),(2,2,'jaspion','jaspion','2026-06-28',NULL),(4,3,'ADM','ADM','2026-06-28',NULL),(5,4,'jao','123','2026-06-28',NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-01 13:34:10
