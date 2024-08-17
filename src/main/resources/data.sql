-- ジャンルデータ作成
INSERT INTO
    genres(genre_name)
VALUES
    ('ネックレス');

INSERT INTO
    genres(genre_name)
VALUES
    ('リング');

INSERT INTO
    genres(genre_name)
VALUES
    ('ピアス');

INSERT INTO
    genres(genre_name)
VALUES
    ('イヤリング');

INSERT INTO
    genres(genre_name)
VALUES
    ('ブレスレット');

INSERT INTO
    genres(genre_name)
VALUES
    ('その他小物');

-- 商品データ追加
INSERT INTO
    products(
        product_name,
        price,
        comment,
        genre_id,
        version,
        created_at,
        updated_at
    )
VALUES
    (
        '永久のネックレス',
        1200,
        '永遠を誓うことのできるネックレスです',
        1,
        1,
        DATE_SUB(now(), INTERVAL 10 DAY),
        null
    );

INSERT INTO
    products(
        product_name,
        price,
        comment,
        genre_id,
        version,
        created_at,
        updated_at
    )
VALUES
    (
        '破滅のリング',
        500,
        '相手を破滅させることのできるリングです',
        2,
        1,
        DATE_SUB(now(), INTERVAL 9 DAY),
        null
    );

INSERT INTO
    products(
        product_name,
        price,
        comment,
        genre_id,
        version,
        created_at,
        updated_at
    )
VALUES
    (
        '不滅のピアス',
        10000,
        '不滅になるピアスです',
        3,
        1,
        DATE_SUB(now(), INTERVAL 8 DAY),
        null
    );

INSERT INTO
    products(
        product_name,
        price,
        comment,
        genre_id,
        version,
        created_at,
        updated_at
    )
VALUES
    (
        '勝利のイヤリング',
        500000,
        '勝利を勝ち取ることのできるイヤリングです',
        4,
        1,
        DATE_SUB(now(), INTERVAL 6 DAY),
        null
    );

INSERT INTO
    products(
        product_name,
        price,
        comment,
        genre_id,
        version,
        created_at,
        updated_at
    )
VALUES
    (
        '火事場のブレスレット',
        10000,
        '火事場を力を引き出すことのできるブレスレットです',
        5,
        1,
        DATE_SUB(now(), INTERVAL 5 DAY),
        null
    );

INSERT INTO
    products(
        product_name,
        price,
        comment,
        genre_id,
        version,
        created_at,
        updated_at
    )
VALUES
    (
        '絶壁のガントレット',
        600,
        '絶壁のような防御力を誇るガントレットです',
        6,
        1,
        now(),
        null
    );

-- 在庫データ作成
INSERT INTO
    stocks(
        product_id,
        stock_cnt,
        created_at
    )
VALUES
    (1, 10, now());

INSERT INTO
    stocks(
        product_id,
        stock_cnt,
        created_at
    )
VALUES
    (1, 20, now());

INSERT INTO
    stocks(
        product_id,
        stock_cnt,
        created_at
    )
VALUES
    (2, 20, now());

INSERT INTO
    stocks(
        product_id,
        stock_cnt,
        created_at
    )
VALUES
    (2, 40, now());

INSERT INTO
    stocks(
        product_id,
        stock_cnt,
        created_at
    )
VALUES
    (3, 30, now());

INSERT INTO
    stocks(
        product_id,
        stock_cnt,
        created_at
    )
VALUES
    (3, 60, now());

INSERT INTO
    stocks(
        product_id,
        stock_cnt,
        created_at
    )
VALUES
    (4, 40, now());

INSERT INTO
    stocks(
        product_id,
        stock_cnt,
        created_at
    )
VALUES
    (4, 80, now());

INSERT INTO
    stocks(
        product_id,
        stock_cnt,
        created_at
    )
VALUES
    (5, 50, now());

INSERT INTO
    stocks(
        product_id,
        stock_cnt,
        created_at
    )
VALUES
    (5, 100, now());

INSERT INTO
    stocks(
        product_id,
        stock_cnt,
        created_at
    )
VALUES
    (6, 60, now());

INSERT INTO
    stocks(
        product_id,
        stock_cnt,
        created_at
    )
VALUES
    (6, 120, now());

-- ユーザデータ作成
INSERT INTO
    users(
        username,
        password,
        role,
        first_name,
        last_name,
        zip,
        telno,
        prefectures,
        city,
        address,
        enabled,
        created_at,
        updated_at
    )
VALUES
    (
        'test',
        '$2a$10$2N3VfsGYspPWmrW0VW9TvOuL5/r.Q80yUUeZSd.AFD8fphXbuQta6',
        'USER',
        'テスト',
        'ユーザ',
        '1234567',
        '00000000000',
        '神奈川県',
        '中郡二宮町',
        '山西175-9第一サンハイツ101',
        TRUE,
        now(),
        null
    );

INSERT INTO
    users(
        username,
        password,
        role,
        first_name,
        last_name,
        zip,
        telno,
        prefectures,
        city,
        address,
        enabled,
        created_at,
        updated_at
    )
VALUES
    (
        'admin',
        '$2a$10$2N3VfsGYspPWmrW0VW9TvOuL5/r.Q80yUUeZSd.AFD8fphXbuQta6',
        'ADMIN',
        '管理者',
        'ユーザ',
        '1234567',
        '00000000000',
        '東京都',
        '千代田区',
        '1-1',
        TRUE,
        now(),
        null
    );

-- 受注データ作成
INSERT INTO
    orders(
        product_id,
        user_id,
        order_cnt,
        created_at
    )
VALUES
    (1, 1, 11, now());

INSERT INTO
    orders(
        product_id,
        user_id,
        order_cnt,
        created_at
    )
VALUES
    (1, 2, 22, now());

INSERT INTO
    orders(
        product_id,
        user_id,
        order_cnt,
        created_at
    )
VALUES
    (2, 1, 33, now());

INSERT INTO
    orders(
        product_id,
        user_id,
        order_cnt,
        created_at
    )
VALUES
    (2, 2, 44, now());

INSERT INTO
    orders(
        product_id,
        user_id,
        order_cnt,
        created_at
    )
VALUES
    (3, 1, 55, now());

INSERT INTO
    orders(
        product_id,
        user_id,
        order_cnt,
        created_at
    )
VALUES
    (3, 2, 66, now());

INSERT INTO
    orders(
        product_id,
        user_id,
        order_cnt,
        created_at
    )
VALUES
    (4, 1, 77, now());

INSERT INTO
    orders(
        product_id,
        user_id,
        order_cnt,
        created_at
    )
VALUES
    (4, 2, 88, now());

INSERT INTO
    orders(
        product_id,
        user_id,
        order_cnt,
        created_at
    )
VALUES
    (5, 1, 99, now());

INSERT INTO
    orders(
        product_id,
        user_id,
        order_cnt,
        created_at
    )
VALUES
    (5, 2, 111, now());

INSERT INTO
    orders(
        product_id,
        user_id,
        order_cnt,
        created_at
    )
VALUES
    (6, 1, 222, now());

INSERT INTO
    orders(
        product_id,
        user_id,
        order_cnt,
        created_at
    )
VALUES
    (6, 2, 333, now());