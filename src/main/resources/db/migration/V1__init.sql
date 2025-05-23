-- 상영 유형
CREATE TABLE screen_types (
    type         VARCHAR2(50),
    icon_url     VARCHAR2(255),
    price        NUMBER(10),

    PRIMARY KEY (type)
);

-- 상영관
CREATE TABLE theaters (
    id           NUMBER,
    name         VARCHAR2(100) UNIQUE NOT NULL,
    layout       CLOB CHECK (layout IS JSON) NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at   TIMESTAMP,

    PRIMARY KEY (id)
);

-- 상영관 좌석
CREATE TABLE theater_seats (
    theater_id   NUMBER,
    seat_number  VARCHAR2(10),
    is_available CHAR(1) DEFAULT 'Y' CHECK (is_available IN ('Y', 'N')),
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at   TIMESTAMP,

    PRIMARY KEY (theater_id, seat_number),
    FOREIGN KEY (theater_id) REFERENCES theaters(id)
);

-- 상영관 제공 상영유형
CREATE TABLE theater_screen_types (
    theater_id   NUMBER,
    screen_type  VARCHAR2(50),

    PRIMARY KEY (theater_id, screen_type),
    FOREIGN KEY (theater_id) REFERENCES theaters(id),
    FOREIGN KEY (screen_type) REFERENCES screen_types(type)
);
