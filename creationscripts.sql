##Since the data is in relational form, it makes sense to use a RDBMS. I went with MySQL.

##CREATING DATABASE SCHEMA AND TABLES. Please run in order below

CREATE DATABASE `component_schema` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `component_schema`.`Workspace` (
  `WorkspaceId` int(11) NOT NULL AUTO_INCREMENT,
  `WorkspaceName` varchar(45) NOT NULL,
  `OwnerGroupId` varchar(45) NOT NULL,
  `InsertDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdateDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`WorkspaceId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;



CREATE TABLE `component_schema`.`Environment` (
  `EnvironmentId` int(11) NOT NULL AUTO_INCREMENT,
  `WorkspaceId` int(11) DEFAULT NULL,
  `EnvironmentName` varchar(45) NOT NULL,
  `InsertDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdateDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`EnvironmentId`),
  KEY `EnvWsIdFK_idx` (`WorkspaceId`),
  CONSTRAINT `EnvWsIdFK` FOREIGN KEY (`WorkspaceId`) REFERENCES `component_schema`.`Workspace` (`WorkspaceId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

CREATE TABLE `component_schema`.`SourceRepository` (
  `SourceRepositoryId` int(11) NOT NULL AUTO_INCREMENT,
  `WorkspaceId` int(11) DEFAULT NULL,
  `RepositoryName` varchar(45) NOT NULL,
  `InsertDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdateDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`SourceRepositoryId`),
  KEY `SRWsIdFK_idx` (`WorkspaceId`),
  CONSTRAINT `SRWsIdFK` FOREIGN KEY (`WorkspaceId`) REFERENCES `component_schema`.`Workspace` (`WorkspaceId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


CREATE TABLE `component_schema`.`Database` (
  `DatabaseId` int(11) NOT NULL AUTO_INCREMENT,
  `EnvironmentId` int(11) DEFAULT NULL,
  `DatabaseName` varchar(45) NOT NULL,
  `InsertDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdateDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`DatabaseId`),
  KEY `DBEnvIdFK_idx` (`EnvironmentId`),
  CONSTRAINT `DBEnvIdFK` FOREIGN KEY (`EnvironmentId`) REFERENCES `component_schema`.`Environment` (`EnvironmentId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;