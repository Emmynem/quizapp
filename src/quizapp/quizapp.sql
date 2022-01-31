-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 31, 2022 at 01:01 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quizapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `firstName` varchar(150) NOT NULL,
  `lastName` varchar(150) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password` char(30) NOT NULL,
  `added_date` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `firstName`, `lastName`, `email`, `password`, `added_date`) VALUES
(1, 'Gideon', 'Davidson', 'gida@gmail.com', 'david', '2020/01/20 16:17'),
(2, 'Gold', 'Hawkins', 'goldhawk@yahoo.com', 'hawk', '2020/01/18 18:20'),
(3, 'Labrinth', 'Justin', 'labrat@gmail.com', 'labrat', '2020/01/16 11:04');

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `question` text NOT NULL,
  `option1` text NOT NULL,
  `option2` text NOT NULL,
  `option3` text NOT NULL,
  `option4` text NOT NULL,
  `answer` varchar(100) NOT NULL,
  `added_date` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`id`, `admin_id`, `question`, `option1`, `option2`, `option3`, `option4`, `answer`, `added_date`) VALUES
(1, 2, 'Which song did Alan Walker feature Marshmello that made a hit in 2019 ?', 'Routine', 'Alone', 'Ignite', 'Lonely', 'Option 2', '2020/01/16 05:20'),
(3, 1, 'Which song was featured in PUBG\'s (Player\'s Unknown BattleGround) season 5 gameplay?', 'On my way - Alan Walker', 'Risky - Davido', 'Live Fast - Alan Walker', 'Ebube - Frank Edwards', 'Option 1', '2020/01/16 04:20'),
(4, 1, 'In what year did Nigeria gain it\'s independence ?', '1890', '1990', '2016', '1960', 'Option 4', '2020/01/16 05:23'),
(6, 2, 'Who is the current president of the United States of America (USA) ?', 'President Barack Obama', 'President Malcom X', 'Oba Omega', 'President Donald Trump', 'Option 4', '2020/01/16 05:34'),
(7, 3, 'Which artist did Eminem feature in the song \"Beautiful Pain\" ?', 'Skylar Grey', 'Curtis (50 Cent) Jackson', 'Sia', 'Kendrick Lamar', 'Option 3', '2020/01/16 11:06');

-- --------------------------------------------------------

--
-- Table structure for table `results`
--

CREATE TABLE `results` (
  `id` int(11) NOT NULL,
  `user_email` varchar(150) NOT NULL,
  `percentage` varchar(10) NOT NULL,
  `added_date` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `results`
--

INSERT INTO `results` (`id`, `user_email`, `percentage`, `added_date`) VALUES
(1, 'katyperry@gmail.com', '60%', '2020/01/16 21:51'),
(2, 'katyperry@gmail.com', '60%', '2020/01/17 03:59'),
(3, 'johnnyboy@gmail.com', '80%', '2020/01/17 04:09'),
(4, 'johnnyboy@gmail.com', '100%', '2020/01/17 05:28'),
(5, 'johnnyboy@gmail.com', '20%', '2020/01/17 05:28'),
(6, 'leoben@yahoo.com', '0%', '2020/01/17 05:39'),
(7, 'katyperry@gmail.com', '40%', '2020/01/19 17:12'),
(8, 'katyperry@gmail.com', '80%', '2020/01/20 16:14'),
(9, 'leoben@yahoo.com', '100%', '2020/01/24 22:49'),
(10, 'katyperry@gmail.com', '60%', '2020/02/13 15:28');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `firstName` varchar(150) NOT NULL,
  `lastName` varchar(150) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password` char(30) NOT NULL,
  `added_date` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `firstName`, `lastName`, `email`, `password`, `added_date`) VALUES
(1, 'Katy', 'Perry', 'katyperry@gmail.com', 'katy', '2020/02/13 15:27'),
(2, 'John', 'Benson', 'johnnyboy@gmail.com', 'bully', '2020/01/15 04:51'),
(3, 'Bernard', 'Leonardo', 'leoben@yahoo.com', 'leo', '2020/01/24 22:49');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `results`
--
ALTER TABLE `results`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `results`
--
ALTER TABLE `results`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
