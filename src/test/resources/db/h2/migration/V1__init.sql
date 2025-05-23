-- 상영유형
CREATE TABLE screen_types (
    type         VARCHAR(50),
    icon_url     VARCHAR(255),
    price        INT NOT NULL,
    PRIMARY KEY (type)
);

-- 상영관
CREATE TABLE theaters (
    id           INT,
    name         VARCHAR(100) UNIQUE NOT NULL,
    layout       CLOB NOT NULL,

    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at   TIMESTAMP,
    PRIMARY KEY (id)
);

-- 상영관 좌석
CREATE TABLE theater_seats (
    theater_id   INT,
    seat_number  VARCHAR(10),
    is_available CHAR(1) DEFAULT 'Y',

    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at   TIMESTAMP,
    PRIMARY KEY (theater_id, seat_number),
    FOREIGN KEY (theater_id) REFERENCES theaters(id) ON DELETE CASCADE
);

-- 상영관 제공 상영유형
CREATE TABLE theater_screen_types (
    theater_id   INT,
    screen_type  VARCHAR(50),

    PRIMARY KEY (theater_id, screen_type),
    FOREIGN KEY (theater_id) REFERENCES theaters(id),
    FOREIGN KEY (screen_type) REFERENCES screen_types(type)
);

-- 어드민
CREATE SEQUENCE admin_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE admins (
    id INT DEFAULT NEXT VALUE FOR admin_seq PRIMARY KEY,
    username VARCHAR(20) UNIQUE NOT NULL,
    password CHAR(64) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- 장르
CREATE TABLE genres (
    name         VARCHAR(50),
    description  VARCHAR(255),
    image_url    VARCHAR(255),

    PRIMARY KEY (name)
);

-- 고객 유형
CREATE TABLE customer_types (
    type            VARCHAR(50),
    discount_amount INT NOT NULL,

    PRIMARY KEY (type)
);

-- 은행
CREATE TABLE banks (
    name            VARCHAR(50),
    logo_url        VARCHAR(255),
    discount_amount INT NOT NULL,

    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at      TIMESTAMP,

    PRIMARY KEY (name)
);

-- 카드사
CREATE TABLE card_companies (
    name            VARCHAR(50),
    logo_url        VARCHAR(255),
    discount_amount INT NOT NULL,

    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at      TIMESTAMP,

    PRIMARY KEY (name)
);
