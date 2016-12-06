-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 06-Dez-2016 às 18:18
-- Versão do servidor: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `app_cirdan_server`
--
CREATE DATABASE IF NOT EXISTS `app_cirdan_server` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `app_cirdan_server`;

-- --------------------------------------------------------

--
-- Estrutura da tabela `rel_profile_profile`
--

CREATE TABLE `rel_profile_profile` (
  `id` bigint(20) NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `profile_sender` bigint(20) NOT NULL,
  `profile_reciever` bigint(20) NOT NULL,
  `accepted` tinyint(1) NOT NULL,
  `blocked` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_password`
--

CREATE TABLE `tbl_password` (
  `id` bigint(20) NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `password` varchar(64) NOT NULL,
  `salt` varchar(45) NOT NULL,
  `iterations` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_profile`
--

CREATE TABLE `tbl_profile` (
  `id` bigint(20) NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `pstatus` smallint(6) NOT NULL,
  `tstatus` text NOT NULL,
  `last_ipv4` varchar(45) NOT NULL,
  `last_online_time` timestamp NOT NULL,
  `activated` tinyint(1) NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `password_id` bigint(20) NOT NULL,
  `nickname` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `rel_profile_profile`
--
ALTER TABLE `rel_profile_profile`
  ADD PRIMARY KEY (`id`),
  ADD KEY `profile_sender` (`profile_sender`),
  ADD KEY `profile_reciever` (`profile_reciever`);

--
-- Indexes for table `tbl_password`
--
ALTER TABLE `tbl_password`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_profile`
--
ALTER TABLE `tbl_profile`
  ADD PRIMARY KEY (`id`),
  ADD KEY `password_id` (`password_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `rel_profile_profile`
--
ALTER TABLE `rel_profile_profile`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `tbl_password`
--
ALTER TABLE `tbl_password`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tbl_profile`
--
ALTER TABLE `tbl_profile`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `rel_profile_profile`
--
ALTER TABLE `rel_profile_profile`
  ADD CONSTRAINT `rel_profile_profile_ibfk_1` FOREIGN KEY (`profile_sender`) REFERENCES `tbl_profile` (`id`),
  ADD CONSTRAINT `rel_profile_profile_ibfk_2` FOREIGN KEY (`profile_reciever`) REFERENCES `tbl_profile` (`id`);

--
-- Limitadores para a tabela `tbl_profile`
--
ALTER TABLE `tbl_profile`
  ADD CONSTRAINT `tbl_profile_ibfk_1` FOREIGN KEY (`password_id`) REFERENCES `tbl_password` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
