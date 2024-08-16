-- 商品データ追加
INSERT INTO
    products(
        -- product_id,
        product_name,
        price,
        stocks,
        orders,
        comment,
        genre_id,
        version,
        created_at,
        updated_at
    )
VALUES
    (
        -- 1,
        '永久のネックレス',
        1200,
        10,
        20,
        '永遠を誓うことのできるネックレスです',
        1,
        1,
        DATE_SUB(now(), INTERVAL 10 DAY),
        null
    );

INSERT INTO
    products(
        -- product_id,
        product_name,
        price,
        stocks,
        orders,
        comment,
        genre_id,
        version,
        created_at,
        updated_at
    )
VALUES
    (
        -- 2,
        '破滅のリング',
        500,
        5,
        200,
        '相手を破滅させることのできるリングです',
        2,
        1,
        DATE_SUB(now(), INTERVAL 9 DAY),
        null
    );

INSERT INTO
    products(
        -- product_id,
        product_name,
        price,
        stocks,
        orders,
        comment,
        genre_id,
        version,
        created_at,
        updated_at
    )
VALUES
    (
        -- 3,
        '不滅のピアス',
        10000,
        1,
        5000,
        '不滅になるピアスです',
        3,
        1,
        DATE_SUB(now(), INTERVAL 8 DAY),
        null
    );

INSERT INTO
    products(
        -- product_id,
        product_name,
        price,
        stocks,
        orders,
        comment,
        genre_id,
        version,
        created_at,
        updated_at
    )
VALUES
    (
        -- 4,
        '勝利のイヤリング',
        500000,
        1,
        60000,
        '勝利を勝ち取ることのできるイヤリングです',
        4,
        1,
        DATE_SUB(now(), INTERVAL 6 DAY),
        null
    );

INSERT INTO
    products(
        -- product_id,
        product_name,
        price,
        stocks,
        orders,
        comment,
        genre_id,
        version,
        created_at,
        updated_at
    )
VALUES
    (
        -- 5,
        '火事場のブレスレット',
        10000,
        1,
        3000,
        '火事場を力を引き出すことのできるブレスレットです',
        5,
        1,
        DATE_SUB(now(), INTERVAL 5 DAY),
        null
    );

INSERT INTO
    products(
        -- product_id,
        product_name,
        price,
        stocks,
        orders,
        comment,
        genre_id,
        version,
        created_at,
        updated_at
    )
VALUES
    (
        -- 6,
        '絶壁のガントレット',
        600,
        1,
        500,
        '絶壁のような防御力を誇るガントレットです',
        6,
        1,
        now(),
        null
    );

-- ジャンルデータ作成
INSERT INTO
    genres(genre_id, genre_name)
VALUES
    (1, 'ネックレス');

INSERT INTO
    genres(genre_id, genre_name)
VALUES
    (2, 'リング');

INSERT INTO
    genres(genre_id, genre_name)
VALUES
    (3, 'ピアス');

INSERT INTO
    genres(genre_id, genre_name)
VALUES
    (4, 'イヤリング');

INSERT INTO
    genres(genre_id, genre_name)
VALUES
    (5, 'ブレスレット');

INSERT INTO
    genres(genre_id, genre_name)
VALUES
    (6, 'その他小物');

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
        'mgsystems.priv.co.ltd@gmail.com',
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
        'mgsystems.priv.co.ltd@gmail.com',
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