-- ----------------------------
-- Table structure for t_action
-- ----------------------------
DROP TABLE IF EXISTS `t_action`;
CREATE TABLE `t_action` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `appName` varchar(500) DEFAULT NULL,
  `objectName` varchar(500) NOT NULL DEFAULT '',
  `objectId` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `actor` varchar(500) NOT NULL,
  `action` varchar(500) NOT NULL DEFAULT '',
  `actionName` varchar(500) NOT NULL DEFAULT '',
  `extraWords` varchar(5000) DEFAULT NULL,
  `comment` mediumtext,
  `actionTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for t_action_item
-- ----------------------------
DROP TABLE IF EXISTS `t_action_item`;
CREATE TABLE `t_action_item` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `actionId` mediumint(8) unsigned NOT NULL,
  `attributeType` varchar(500) NOT NULL DEFAULT '',
  `attribute` varchar(500) NOT NULL DEFAULT '',
  `attributeName` varchar(500) NOT NULL DEFAULT '',
  `oldValue` mediumtext,
  `newValue` mediumtext,
  `diffValue` mediumtext,
  PRIMARY KEY (`id`)
);