-- customers 테이블 INSERT 
INSERT INTO customers (id, user_type) VALUES 
(1, 'MEMBER'),
(2, 'GUEST'),
(3, 'MEMBER'),
(4, 'GUEST'),
(5, 'MEMBER'),
(6, 'GUEST'),
(7, 'MEMBER'),
(8, 'GUEST'),
(9, 'MEMBER'),
(10, 'GUEST');

-- members 테이블 INSERT
-- password: password123!
INSERT INTO members (customer_id, username, password, name, phone, birth_date, profile_image_url) VALUES
(1, 'user1', 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK', '회원1', '010-0000-0001', TO_DATE('2004-01-07', 'YYYY-MM-DD'), 'https://example.com/profile/user1.jpg'),
(3, 'user3', 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK', '회원3', '010-0000-0003', TO_DATE('2002-01-07', 'YYYY-MM-DD'), 'https://example.com/profile/user3.jpg'),
(5, 'user5', 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK', '회원5', '010-0000-0005', TO_DATE('2000-01-08', 'YYYY-MM-DD'), 'https://example.com/profile/user5.jpg'),
(7, 'user7', 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK', '회원7', '010-0000-0007', TO_DATE('1998-01-08', 'YYYY-MM-DD'), 'https://example.com/profile/user7.jpg'),
(9, 'user9', 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK', '회원9', '010-0000-0009', TO_DATE('1996-01-09', 'YYYY-MM-DD'), 'https://example.com/profile/user9.jpg');

-- guests 테이블 INSERT
-- password: password123!
INSERT INTO guests (customer_id, name, phone, birth_date, password) VALUES
(2, '비회원2', '010-9999-9902', TO_DATE('1998-01-08', 'YYYY-MM-DD'), 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK'),
(4, '비회원4', '010-9999-9904', TO_DATE('1996-01-09', 'YYYY-MM-DD'), 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK'),
(6, '비회원6', '010-9999-9906', TO_DATE('1994-01-09', 'YYYY-MM-DD'), 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK'),
(8, '비회원8', '010-9999-9908', TO_DATE('1992-01-10', 'YYYY-MM-DD'), 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK'),
(10, '비회원10', '010-9999-9910', TO_DATE('1990-01-10', 'YYYY-MM-DD'), 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK');

-- directors 테이블 INSERT
INSERT INTO directors (id, name, photo_url) VALUES 
(1, '봉준호', 'https://i.namu.wiki/i/yrJhbe1Ncd9f4pQc139x65ZYD6qI2W0BX8nuXVhX1jyBFbhTF5RbJnBsiBXL0pl1_69QcZePV4wnbBpsuEMrd5RCrPYJNFxZk7jsPoMlbLB1csFLhdmiBigW56zmnClkxfA_CibqRBrzZJeQ0oOkCw.webp'),
(2, 'Steven Spielberg', 'https://namu.wiki/w/%EC%8A%A4%ED%8B%B0%EB%B8%90%20%EC%8A%A4%ED%95%84%EB%B2%84%EA%B7%B8'),
(3, '김지운', 'https://namu.wiki/w/%EA%B9%80%EC%A7%80%EC%9A%B4'),
(4, 'Tim Burton', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.imdb.com%2Fname%2Fnm0000318%2Fbio%2F&psig=AOvVaw1fQ9k1OPYoShhu_AEHIkLg&ust=1749988569943000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCODJuqXu8I0DFQAAAAAdAAAAABAE'),
(5, '홍상수', 'https://namu.wiki/w/%ED%99%8D%EC%83%81%EC%88%98');

-- actors 테이블 INSERT
INSERT INTO actors (id, name, photo_url) VALUES
(1, '송강호', 'https://upload.wikimedia.org/wikipedia/commons/f/fa/Song_Kang-ho_at_Paris_Premiere_of_Secretly_Greatly_01.jpg'),
(2, '이선균', 'https://upload.wikimedia.org/wikipedia/commons/9/9a/Lee_Sun-kyun_in_2018.jpg'),
(3, '앤서니 스타', 'https://upload.wikimedia.org/wikipedia/commons/4/46/Antony_Starr_2018.jpg'),
(4, '레오나르도 디카프리오', 'https://upload.wikimedia.org/wikipedia/commons/5/53/Leonardo_DiCaprio_2016.jpg'),
(5, '전혜진', 'https://upload.wikimedia.org/wikipedia/commons/8/8f/Jeon_Hye-jin_%287%29.jpg'),
(6, '이병헌', 'https://upload.wikimedia.org/wikipedia/commons/3/31/Lee_Byung-hun_at_Oppenheimer_Event.jpg'),
(7, '조니 뎁', 'https://upload.wikimedia.org/wikipedia/commons/3/3b/Johnny_Depp_2018.jpg'),
(8, '위노나 라이더', 'https://upload.wikimedia.org/wikipedia/commons/3/30/Winona_Ryder_at_2018.jpg'),
(9, '조이 데이비스', 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Jodie_Foster_2016.jpg/220px-Jodie_Foster_2016.jpg'),
(10, '조 피셔', 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Jodie_Foster_2016.jpg/220px-Jodie_Foster_2016.jpg');


-- movies 테이블 INSERT
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES
(1, '기생충', '빈부 격차와 계층 갈등을 블랙코미디적 풍자로 담아낸 사회파 드라마', 132, 'FIFTEEN',
   '["https://image.tmdb.org/t/p/original/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg"]',
   TO_DATE('2019-05-30T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'),
   '바른손 COVID', 1),
(2, '인셉션', '꿈 속 여러 층위를 오가며 현실과 환상의 경계를 흔드는 SF 심리 스릴러', 148, 'FIFTEEN',
   '["https://i.namu.wiki/i/O2uuv7bO0Hc33rLHS7t3OGhc5_guUiIyY6VThmyKSP8lC1kwtN6vS8KsiUIbda5n46DAVy7_Z2_c2KRR_mUYEw.webp"]',
   TO_DATE('2010-07-16T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'),
   '워너브라더스', 2),
(3, '좋은 놈, 나쁜 놈, 이상한 놈', '한국식 서부극으로, 전쟁 직후 혼란 속 유쾌한 모험을 그린 웨스턴 드라마', 159, 'FIFTEEN',
   '["https://i.namu.wiki/i/K0asY-_xCplVJXasAStJAq3IjDQb7KedpeIrfhEFLsuzYPGTAa3mKMoA09rk9ZpCI7Akz8O01sjI-nIy5kkvLQ.webp"]',
   TO_DATE('2008-11-27T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'),
   'CINE2000', 3),
(4, '가위손', '팀 버튼 감독이 만든 고딕 판타지 로맨스로, 인간과 괴물의 경계를 넘나드는 감성 이야기', 98, 'ALL',
   '["https://i.namu.wiki/i/rrLprXPrJm_QXEdODO6368g8odFN7BFtlqri6RiV9BoemjYRUATiXC1VNyGWTNxZebJ3IwzdtnXV5jaEKQqzFb56u5aIb_JxNhKynEdd47WNddqHD9N_Hz58mziPa_OQZ6TaMOP2SAfAdCGE9kovnw.webp"]',
   TO_DATE('1990-12-06T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'),
   '워너브라더스', 4),
(5, '비몽', '홍상수 감독 특유의 일상적 대사와 감성으로 엮은 이별과 회복의 드라마', 100, 'ALL',
   '["https://i.namu.wiki/i/iDk0tW0utvNEouzW6CkN0zYRAPYP44ACaad-ntF8yl51C2NnYyz8VirnkzBeo3bZxhy0i9XSoA62CKv-N-fIw2HEy9YxxaE0SE6bMftK3jF9O6A7n7pe_RLL3vd3npi4_BqEKsZpXziUn6O6TuxLOQ.webp"]',
   TO_DATE('2020-03-15T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'),
   '인디플러스', 5);

INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES 
(DATE '2025-01-01', 1, 7252),
(DATE '2025-01-02', 1, 9982),
(DATE '2025-01-03', 1, 2728),
(DATE '2025-01-01', 2, 6846),
(DATE '2025-01-02', 2, 5417),
(DATE '2025-01-03', 2, 1276),
(DATE '2025-01-01', 3, 9051),
(DATE '2025-01-02', 3, 9130),
(DATE '2025-01-03', 3, 6078),
(DATE '2025-01-01', 4, 4048),
(DATE '2025-01-02', 4, 2377),
(DATE '2025-01-03', 4, 9339),
(DATE '2025-01-01', 5, 3203),
(DATE '2025-01-02', 5, 3690),
(DATE '2025-01-03', 5, 2425);

INSERT INTO movie_screen_types (movie_id, screen_type) VALUES 
(1, '4D'),
(1, '3D'),
(1, '2D'),
(2, '4D'),
(2, '3D'),
(2, '2D'),
(3, '2D'),
(3, '3D'),
(4, '2D'),
(4, '3D'),
(5, '2D');

INSERT INTO movie_genres (movie_id, genre_name) VALUES 
(1, 'Drama'),
(1, 'Thriller'),
(2, 'Sci‑Fi'),
(2, 'Thriller'),
(3, 'Drama'),
(3, 'Western'),
(4, 'Fantasy'),
(4, 'Romance'),
(5, 'Drama'),
(5, 'Romance');

-- 기생충 (봉준호)
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES
(1, 1, '기택', '주연'),  -- 송강호
(1, 2, '동익', '조연');  -- 이선균

-- 인셉션 (스필버그 대역)
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES
(2, 4, 'Dom Cobb', '주연'),      -- 레오나르도 디카프리오
(2, 3, 'Arthur', '조연');       -- 앤서니 스타

-- 좋은 놈 나쁜 놈 이상한 놈 (김지운)
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES
(3, 6, '박도원', '주연'),       -- 이병헌
(3, 5, '송이', '조연');         -- 전혜진

-- 가위손 (팀 버튼)
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES
(4, 7, 'Edward', '주연'),       -- 조니 뎁
(4, 8, 'Kim', '조연');          -- 위노나 라이더

-- 비몽 (홍상수)
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES
(5, 6, '용수', '주연'),         -- 이병헌
(5, 5, '란희', '조연');         -- 전혜진

-- screenings 테이블 INSERT
INSERT INTO screenings (id, movie_id, theater_id, screen_type, start_time, duration) VALUES 
(1, 1, 1, '2D', TO_DATE('2025-06-15 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 105),
(2, 1, 2, '3D', TO_DATE('2025-06-16 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 105),
(3, 1, 3, '4D', TO_DATE('2025-06-17 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 105),
(4, 1, 1, '2D', TO_DATE('2025-06-18 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 105),
(5, 1, 2, '3D', TO_DATE('2025-06-19 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 105),
(6, 1, 3, '4D', TO_DATE('2025-06-20 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 105),
(7, 1, 1, '2D', TO_DATE('2025-06-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 105),
(8, 1, 2, '3D', TO_DATE('2025-06-22 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 105),
(9, 1, 3, '4D', TO_DATE('2025-06-23 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 105),
(10, 1, 1, '2D', TO_DATE('2025-06-24 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 105),
(11, 2, 1, '2D', TO_DATE('2025-06-15 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 110),
(12, 2, 2, '3D', TO_DATE('2025-06-16 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 110),
(13, 2, 3, '4D', TO_DATE('2025-06-17 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 110),
(14, 2, 1, '2D', TO_DATE('2025-06-18 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 110),
(15, 2, 2, '3D', TO_DATE('2025-06-19 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 110),
(16, 2, 3, '4D', TO_DATE('2025-06-20 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 110),
(17, 2, 1, '2D', TO_DATE('2025-06-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 110),
(18, 2, 2, '3D', TO_DATE('2025-06-22 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 110),
(19, 2, 3, '4D', TO_DATE('2025-06-23 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 110),
(20, 2, 1, '2D', TO_DATE('2025-06-24 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 110),
(21, 3, 1, '2D', TO_DATE('2025-06-15 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 115),
(22, 3, 2, '3D', TO_DATE('2025-06-16 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 115),
(23, 3, 3, '4D', TO_DATE('2025-06-17 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 115),
(24, 3, 1, '2D', TO_DATE('2025-06-18 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 115),
(25, 3, 2, '3D', TO_DATE('2025-06-19 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 115),
(26, 3, 3, '4D', TO_DATE('2025-06-20 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 115),
(27, 3, 1, '2D', TO_DATE('2025-06-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 115),
(28, 3, 2, '3D', TO_DATE('2025-06-22 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 115),
(29, 3, 3, '4D', TO_DATE('2025-06-23 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 115),
(30, 3, 1, '2D', TO_DATE('2025-06-24 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 115),
(31, 4, 1, '2D', TO_DATE('2025-06-15 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 120),
(32, 4, 2, '3D', TO_DATE('2025-06-16 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 120),
(33, 4, 3, '4D', TO_DATE('2025-06-17 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 120),
(34, 4, 1, '2D', TO_DATE('2025-06-18 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 120),
(35, 4, 2, '3D', TO_DATE('2025-06-19 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 120),
(36, 4, 3, '4D', TO_DATE('2025-06-20 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 120),
(37, 4, 1, '2D', TO_DATE('2025-06-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 120),
(38, 4, 2, '3D', TO_DATE('2025-06-22 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 120),
(39, 4, 3, '4D', TO_DATE('2025-06-23 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 120),
(40, 4, 1, '2D', TO_DATE('2025-06-24 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 120),
(41, 5, 1, '2D', TO_DATE('2025-06-15 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 125),
(42, 5, 2, '3D', TO_DATE('2025-06-16 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 125),
(43, 5, 3, '4D', TO_DATE('2025-06-17 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 125),
(44, 5, 1, '2D', TO_DATE('2025-06-18 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 125),
(45, 5, 2, '3D', TO_DATE('2025-06-19 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 125),
(46, 5, 3, '4D', TO_DATE('2025-06-20 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 125),
(47, 5, 1, '2D', TO_DATE('2025-06-21 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 125),
(48, 5, 2, '3D', TO_DATE('2025-06-22 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 125),
(49, 5, 3, '4D', TO_DATE('2025-06-23 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), 125),
(50, 5, 1, '2D', TO_DATE('2025-06-24 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 125);