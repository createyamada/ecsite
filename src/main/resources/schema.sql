-- 商品テーブル作成
DROP TABLE IF EXISTS products;

CREATE TABLE products(
    product_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '商品ID',
    product_name VARCHAR(100) COMMENT '商品名',
    price BIGINT COMMENT '価格',
    stocks BIGINT COMMENT '在庫数',
    orders BIGINT COMMENT '注文数',
    comment VARCHAR(200) COMMENT 'コメント',
    genre_id INT COMMENT 'ジャンルID',
    version INT COMMENT 'バージョン',
    created_at DATETIME COMMENT '登録日時',
    updated_at DATETIME COMMENT '更新日時'
) COMMENT '商品テーブル';

-- ジャンルテーブル作成
DROP TABLE IF EXISTS genres;

CREATE TABLE genres(
    genre_id BIGINT NOT NULL PRIMARY KEY COMMENT 'ジャンルID',
    genre_name VARCHAR(100) COMMENT 'ジャンル名'
) COMMENT 'ジャンルマスタテーブル';

-- ユーザーマスタテーブル作成
SET
    FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS users;

SET
    FOREIGN_KEY_CHECKS = 1;

CREATE TABLE users(
    user_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ユーザID',
    username VARCHAR(100) COMMENT 'ユーザ名',
    password VARCHAR(200) COMMENT 'パスワード',
    role VARCHAR(10) NOT NULL COMMENT '権限',
    first_name VARCHAR(100) NOT NULL COMMENT '姓',
    last_name VARCHAR(100) NOT NULL COMMENT '名',
    zip VARCHAR(8) NOT NULL COMMENT '郵便番号',
    telno VARCHAR(11) NOT NULL COMMENT '電話番号',
    prefectures VARCHAR(10) NOT NULL COMMENT '都道府県',
    city VARCHAR(100) NOT NULL COMMENT '市町村',
    address VARCHAR(100) NOT NULL COMMENT '住所',
    enabled BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'アカウント設定完了フラグ',
    created_at DATETIME COMMENT '登録日時',
    updated_at DATETIME COMMENT '更新日時'
) COMMENT 'ユーザマスタテーブル';

-- 認証トークンテーブル作成
DROP TABLE IF EXISTS verification_tokens;

CREATE TABLE verification_tokens(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    expiration_time DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
) COMMENT '認証トークンテーブル';