-- 商品テーブル作成
DROP TABLE IF EXISTS products;

CREATE TABLE products(
    product_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '商品ID',
    product_name VARCHAR(100) COMMENT '商品名',
    price INT COMMENT '価格',
    stocks INT COMMENT '在庫数',
    orders INT COMMENT '注文数',
    comment VARCHAR(200) COMMENT 'コメント',
    genre_id INT COMMENT 'ジャンルID',
    version INT COMMENT 'バージョン',
    created_at DATETIME COMMENT '登録日時',
    updated_at DATETIME COMMENT '更新日時'
) COMMENT '商品テーブル';

-- ジャンルテーブル作成
DROP TABLE IF EXISTS genres;

CREATE TABLE genres(
    genre_id INT NOT NULL PRIMARY KEY COMMENT 'ジャンルID',
    genre_name VARCHAR(100) COMMENT 'ジャンル名'
) COMMENT 'ジャンルマスタ';

-- ユーザーマスタテーブル作成
DROP TABLE IF EXISTS users;

CREATE TABLE users(
    user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ユーザID',
    username VARCHAR(20) COMMENT 'ユーザ名',
    password VARCHAR(100) COMMENT 'パスワード',
    role VARCHAR(100) COMMENT '権限',
    created_at DATETIME COMMENT '登録日時',
    updated_at DATETIME COMMENT '更新日時'
) COMMENT 'ユーザマスタ';