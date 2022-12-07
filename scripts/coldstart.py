import os

sql="USE ernests_villa;"+\
    "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT,account VARCHAR(50),pwHashed VARCHAR(200),sessionIdHashed VARCHAR(200),sessionIdExpire DATETIME, UNIQUE (account(10)), INDEX (sessionIdHashed(45)));"+\
    "CREATE TABLE IF NOT EXISTS access_ids (id INT PRIMARY KEY AUTO_INCREMENT,account VARCHAR(50),accessIdHashed VARCHAR(200),accessIdExpire DATETIME, UNIQUE (accessIdHashed(45)));"+\
    "CREATE TABLE IF NOT EXISTS items (id INT PRIMARY KEY AUTO_INCREMENT,sellerAccount VARCHAR(50),name VARCHAR(50),description VARCHAR(1000),remaining INT, priceCents INT, INDEX (sellerAccount(10)), INDEX (name(20)));"+\
    "CREATE TABLE IF NOT EXISTS purchases (id INT PRIMARY KEY AUTO_INCREMENT,customerAccount VARCHAR(50),sellerAccount VARCHAR(50),itemId INT, paymentCents INT,purchaseTime DATETIME, INDEX (customerAccount(10)), INDEX (sellerAccount(10)));"

sql_file=open("./cold_start.sql",mode="w")
sql_file.write(sql)
sql_file.close()

os.system("mysql -u czj -p12345678 < cold_start.sql")