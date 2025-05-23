-- 상영유형
CREATE TABLE screen_types (
    type         VARCHAR(50),
    icon_url     VARCHAR(255),
    price        INT,
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
