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

-- 어드민
CREATE SEQUENCE admin_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE admins (
    id INT DEFAULT NEXT VALUE FOR admin_seq,
    username VARCHAR(20) UNIQUE NOT NULL,
    password CHAR(64) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,

    PRIMARY_KEY (id)
);

CREATE SEQUENCE customer_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE customers (
    id INT DEFAULT NEXT VALUE FOR customer_seq,
    user_type VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY_KEY (id)
);

CREATE TABLE guests (
    customer_id INT,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    birth_date DATE NOT NULL,
    password CHAR(64) NOT NULL,

    PRIMARY KEY (customer_id),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- 영화
CREATE SEQUENCE movie_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE movies (
    id INT DEFAULT NEXT VALUE FOR movie_seq,
    title VARCHAR(255) NOT NULL,
    synopsis VARCHAR(255),
    running_time INT NOT NULL,
    rating VARCHAR(10) NOT NULL,
    release_date DATE NOT NULL,
    director VARCHAR(50) NOT NULL,
distributor VARCHAR(50) NOT NULL,
    poster_url VARCHAR(255) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,

    PRIMARY KEY (id)
);

-- 영화 제공 상영유형
CREATE TABLE movie_screen_types (
    movie_id INT,
    screen_type VARCHAR(50),

    PRIMARY KEY (movie_id, screen_type),
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (screen_type) REFERENCES screen_types(type)
);

-- 상영 일정
CREATE SEQUENCE screening_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE screenings (
    id INT DEFAULT NEXT VALUE FOR screening_seq,
    movie_id INT NOT NULL,
    theater_id INT NOT NULL,
    screen_type VARCHAR(50) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    duration INT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (theater_id) REFERENCES theaters(id),
    FOREIGN KEY (screen_type) REFERENCES screen_types(type)
);