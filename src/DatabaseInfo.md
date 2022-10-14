## DATABASE Information

### Settlements:
`kod` varchar(50) NOT NULL,
`ad` varchar(100) NOT NULL,
`tip` int NOT NULL,
`birim` varchar(10) NOT NULL,
`barkod` varchar(30) NOT NULL,
`kdv_tipi` double DEFAULT NULL,
`aciklama` text,
`olusturma_tarihi` date DEFAULT NULL,
PRIMARY KEY (`kod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
