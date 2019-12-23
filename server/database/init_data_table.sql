-- ----------------------------
-- Table structure for operation
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `appName` varchar(500) DEFAULT NULL,
  `objectName` varchar(500) NOT NULL DEFAULT '',
  `objectId` varchar(500) NOT NULL DEFAULT '',
  `operator` varchar(500) NOT NULL,
  `operationName` varchar(500) NOT NULL DEFAULT '',
  `operationAlias` varchar(500) NOT NULL DEFAULT '',
  `extraWords` varchar(5000) DEFAULT NULL,
  `comment` mediumtext,
  `operationTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `appName` (`appName`) USING HASH,
  KEY `objectName` (`objectName`) USING HASH,
  KEY `objectId` (`objectId`) USING BTREE
);

-- ----------------------------
-- Table structure for attribute
-- ----------------------------
DROP TABLE IF EXISTS `attribute`;
CREATE TABLE `attribute` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `operationId` bigint(20) unsigned NOT NULL,
  `attributeType` varchar(500) NOT NULL DEFAULT '',
  `attributeName` varchar(500) NOT NULL DEFAULT '',
  `attributeAlias` varchar(500) NOT NULL DEFAULT '',
  `oldValue` mediumtext,
  `newValue` mediumtext,
  `diffValue` mediumtext,
  PRIMARY KEY (`id`),
  KEY `operationId` (`operationId`) USING BTREE
);