CREATE TABLE `member` (
  `UID` int(10) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(100) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `ROLE` varchar(100) DEFAULT 'USER',
  `CREATED_AT` datetime DEFAULT NULL,
  `UPDATED_AT` datetime DEFAULT NULL,
  PRIMARY KEY (`UID`),
  UNIQUE KEY `EMAIL` (`EMAIL`)
);

CREATE TABLE `mountain` (
  `UID` int(10) NOT NULL AUTO_INCREMENT,
  `MOUNTAIN_NAME` varchar(100) NOT NULL,
  `REGION_TYPE` varchar(2) NOT NULL,
  `REGION_NAME` varchar(100) NOT NULL,
  `ALTITUDE` float DEFAULT 0,
  `LOCATION` varchar(300) DEFAULT NULL,
  `POSITION_X` int(10) DEFAULT NULL,
  `POSITION_Y` int(10) DEFAULT NULL,
  PRIMARY KEY (`UID`)
);

CREATE TABLE `record` (
  `UID` int(10) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(100) NOT NULL,
  `YYMMDD` varchar(8) DEFAULT NULL,
  `MOUNTAIN` varchar(100) DEFAULT NULL,
  `START_DATETIME` datetime DEFAULT NULL,
  `END_DATETIME` datetime DEFAULT NULL,
  `DISTANCE` float DEFAULT 0,
  `MIN_ALTITUDE` float DEFAULT 0,
  `MAX_ALTITUDE` float DEFAULT 0,
  `IMG_PATH` varchar(100) DEFAULT NULL,
  `CREATED_AT` datetime DEFAULT NULL,
  `UPDATED_AT` datetime DEFAULT NULL,
  `HIKING_TIME` int(10) DEFAULT 0,
  `BREAK_TIME` int(10) DEFAULT 0,
  `AVG_SPEED` float DEFAULT 0,
  `TOTAL_TIME` int(10) DEFAULT 0,
  PRIMARY KEY (`UID`),
  KEY `EMAIL` (`EMAIL`),
  CONSTRAINT `record_ibfk_1` FOREIGN KEY (`EMAIL`) REFERENCES `member` (`EMAIL`)
);

CREATE TABLE `stamp` (
  `UID` int(10) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(100) NOT NULL,
  `MOUNTAIN_ID` int(10) NOT NULL,
  `FLAG` tinyint(1) DEFAULT 0,
  `CREATED_AT` datetime DEFAULT NULL,
  `UPDATED_AT` datetime DEFAULT NULL,
  PRIMARY KEY (`UID`,`EMAIL`,`MOUNTAIN_ID`),
  KEY `EMAIL` (`EMAIL`),
  KEY `MOUNTAIN_ID` (`MOUNTAIN_ID`),
  CONSTRAINT `stamp_ibfk_1` FOREIGN KEY (`EMAIL`) REFERENCES `member` (`EMAIL`),
  CONSTRAINT `stamp_ibfk_2` FOREIGN KEY (`MOUNTAIN_ID`) REFERENCES `mountain` (`UID`)
);