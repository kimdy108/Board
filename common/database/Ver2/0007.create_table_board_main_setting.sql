DROP TABLE IF EXISTS `board`.`board_main_setting`;
CREATE TABLE `board`.`board_main_setting` (
  `setting_key` varchar(50) NOT NULL,
  `setting_val` text DEFAULT NULL,
  PRIMARY KEY (`setting_key`),
  KEY `idx_setting_key` (`setting_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;