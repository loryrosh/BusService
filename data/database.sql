/**
 * @author http://twin-persona.org
 *
 * SQL-file for the "busservice" database.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

--
-- Database name: `busservice`
--
-- --------------------------------------------------------
USE busservice;


--
-- Table structure `passengers`
--

CREATE TABLE IF NOT EXISTS `passengers`
(
  `id` int AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `surname` varchar(255) COLLATE utf8_bin NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE ( `name`, `surname` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- --------------------------------------------------------

--
-- Table structure `buses`
--

CREATE TABLE IF NOT EXISTS `buses`
(
  `id` int AUTO_INCREMENT,
  `number` int NOT NULL,
  `seats_num` int NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE (`number`),
  UNIQUE ( `number`, `seats_num` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- --------------------------------------------------------
--
-- Table structure `stations`
--

CREATE TABLE IF NOT EXISTS `stations`
(
  `id` int AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_bin NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE ( `title` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- --------------------------------------------------------

--
-- Table structure `timetables`
--

CREATE TABLE IF NOT EXISTS `timetables`
(
  `id` int AUTO_INCREMENT,
  `time` TIME NOT NULL,
  `bus_id` int NOT NULL COMMENT 'id in buses',
  `station_id` int NOT NULL COMMENT 'id in stations',

  PRIMARY KEY (`id`),
  FOREIGN KEY( `bus_id` ) REFERENCES buses( `id` ),
  FOREIGN KEY( `station_id` ) REFERENCES stations( `id` ),
  UNIQUE ( `bus_id`, `station_id`, `time` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- --------------------------------------------------------

--
-- Table structure `tickets`
--

CREATE TABLE IF NOT EXISTS `tickets`
(
  `id` int AUTO_INCREMENT,
  `bus_id` int NOT NULL COMMENT 'id in buses',
  `passenger_id` int NOT NULL COMMENT 'id in passengers',

  PRIMARY KEY (`id`),
  FOREIGN KEY( `bus_id` ) REFERENCES buses( `id` ),
  FOREIGN KEY( `passenger_id` ) REFERENCES passengers( `id` ),
  UNIQUE ( `bus_id`, `passenger_id` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



INSERT INTO `buses` VALUES (1,12,123),(2,15,12),(3,20,10);
INSERT INTO `stations` VALUES (1,'Station 1'),(2,'Station 2'),(3,'Station 3');
INSERT INTO `timetables` VALUES (1,'10:20:00',1,1),(2,'10:40:00',1,2),(3,'15:30:00',2,3),(4,'16:00:00',2,2),(5,'10:30:00',3,2),(6,'10:50:00',3,3);
INSERT INTO `passengers` VALUES (1,'Ivan','Petrov'),(2,'Maria','Ivanova'),(3,'Jack','Smith');