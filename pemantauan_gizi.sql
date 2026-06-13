-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 13, 2026 at 02:51 PM
-- Server version: 8.0.30
-- PHP Version: 8.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pemantauan_gizi`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'admin', 'sukaDonat5');

-- --------------------------------------------------------

--
-- Table structure for table `ahli_gizi`
--

CREATE TABLE `ahli_gizi` (
  `id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `spesialisasi` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `ahli_gizi`
--

INSERT INTO `ahli_gizi` (`id`, `username`, `password`, `nama`, `spesialisasi`) VALUES
(1, 'dokter', 'giziUtama', 'Dr. Movril Jentan', 'Gizi Klinik');

-- --------------------------------------------------------

--
-- Table structure for table `log_harian`
--

CREATE TABLE `log_harian` (
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `makanan_id` int DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `berat_gram` float DEFAULT NULL,
  `catatan_gizi` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `log_harian`
--

INSERT INTO `log_harian` (`id`, `user_id`, `makanan_id`, `tanggal`, `berat_gram`, `catatan_gizi`) VALUES
(1, 1, 1, '2026-06-04', 300, NULL),
(2, 1, 3, '2026-06-08', 150, NULL),
(3, 1, 2, '2026-06-11', 300, NULL),
(4, 2, 3, '2026-06-13', 200, NULL),
(5, 2, 1, '2026-06-13', 400, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `makanan`
--

CREATE TABLE `makanan` (
  `id` int NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kalori` float DEFAULT NULL,
  `protein` float DEFAULT NULL,
  `lemak` float DEFAULT NULL,
  `karbohidrat` float DEFAULT NULL,
  `status` enum('pending','approved') DEFAULT 'approved',
  `ditambah_oleh` enum('admin','ahli_gizi','user') DEFAULT 'admin',
  `user_id` int DEFAULT NULL,
  `alasan_tolak` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `makanan`
--

INSERT INTO `makanan` (`id`, `nama`, `kalori`, `protein`, `lemak`, `karbohidrat`, `status`, `ditambah_oleh`, `user_id`, `alasan_tolak`) VALUES
(1, 'salad dada ayam', 2000, 150, 0, 0, 'approved', 'admin', NULL, NULL),
(2, 'CheseeCake', 320, 15, 20, 45, 'approved', 'admin', NULL, NULL),
(3, 'Mie Gacoan', 300, 50, 800, 50, 'approved', 'admin', NULL, NULL),
(4, 'Nasi Goreng', 80, 40, 75, 100, 'approved', 'admin', NULL, NULL),
(5, 'Jagung Bakar', 240, 60, 40.5, 100, 'approved', 'user', 2, NULL),
(7, 'Es teh', 20, 5, 15, 10, 'approved', 'ahli_gizi', 0, NULL),
(8, 'Terang bulan', 500, 350, 450, 123.1, 'approved', 'ahli_gizi', 0, NULL),
(9, 'Mochi', 20, 35, 12.5, 9.8, 'approved', 'admin', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `usia` int DEFAULT NULL,
  `berat_kg` float DEFAULT NULL,
  `tinggi_cm` float DEFAULT NULL,
  `gender` enum('Laki-laki','Perempuan') DEFAULT NULL,
  `aktivitas` enum('Sedentary','Ringan','Sedang','Aktif','Sangat Aktif') DEFAULT NULL,
  `tujuan` varchar(50) DEFAULT NULL,
  `target_kalori` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `nama`, `usia`, `berat_kg`, `tinggi_cm`, `gender`, `aktivitas`, `tujuan`, `target_kalori`) VALUES
(1, 'hadi', 'cintaAnime', 'Hadi purnomo', 29, 75, 175, 'Laki-laki', 'Sedang', 'Jaga Berat', 2740.91),
(2, 'putri', 'lima5', 'Putri Tanjung', 21, 50, 158, 'Perempuan', 'Ringan', 'Diet', 1549.18);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `ahli_gizi`
--
ALTER TABLE `ahli_gizi`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `log_harian`
--
ALTER TABLE `log_harian`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `makanan_id` (`makanan_id`);

--
-- Indexes for table `makanan`
--
ALTER TABLE `makanan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `ahli_gizi`
--
ALTER TABLE `ahli_gizi`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `log_harian`
--
ALTER TABLE `log_harian`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `makanan`
--
ALTER TABLE `makanan`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `log_harian`
--
ALTER TABLE `log_harian`
  ADD CONSTRAINT `log_harian_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `log_harian_ibfk_2` FOREIGN KEY (`makanan_id`) REFERENCES `makanan` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
