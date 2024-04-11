-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 11, 2024 at 02:59 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `librarymanagementsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `ID` int(11) NOT NULL,
  `title` text NOT NULL,
  `author` text NOT NULL,
  `publicationYear` text NOT NULL,
  `ISBN` text NOT NULL,
  `borrowed` int(1) NOT NULL,
  `patron` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`ID`, `title`, `author`, `publicationYear`, `ISBN`, `borrowed`, `patron`) VALUES
(1000, 'Our World in Data', 'Max Roser and Hannah Ritchie', '2023', '978-3-16-148410', 1, 2002),
(1003, 'World', 'Max', '1978', '978-3-16-1484578', 0, NULL),
(1007, 'All Tan', 'Roser', '1978', '978-3161484894', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `borrowingrecord`
--

CREATE TABLE `borrowingrecord` (
  `ID` int(11) NOT NULL,
  `bookID` int(11) NOT NULL,
  `patronID` int(11) NOT NULL,
  `borrowingDate` text NOT NULL,
  `returnDate` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `borrowingrecord`
--

INSERT INTO `borrowingrecord` (`ID`, `bookID`, `patronID`, `borrowingDate`, `returnDate`) VALUES
(3500, 1000, 2002, '2024-04-08', '2024-04-15');

-- --------------------------------------------------------

--
-- Table structure for table `patron`
--

CREATE TABLE `patron` (
  `ID` int(11) NOT NULL,
  `name` text NOT NULL,
  `contactInformation` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `patron`
--

INSERT INTO `patron` (`ID`, `name`, `contactInformation`) VALUES
(2002, 'John Doe', '+01214748364 - john@gmail.com'),
(2003, 'John Doe', '+01214748364 - john@gmail.com'),
(2005, 'Smith Jane', '+01121474836 - smith@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `username`, `password`) VALUES
(1, 'admin', 'asdfghjklqwertyuio');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FKj5evbg7m7pjplmqpogkdpfhvr` (`patron`);

--
-- Indexes for table `borrowingrecord`
--
ALTER TABLE `borrowingrecord`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `patron`
--
ALTER TABLE `patron`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `FKj5evbg7m7pjplmqpogkdpfhvr` FOREIGN KEY (`patron`) REFERENCES `patron` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
