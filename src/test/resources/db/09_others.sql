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
(2, 'Sci-Fi'),
(2, 'Thriller'),
(3, 'Drama'),
(3, 'Western'),
(4, 'Fantasy'),
(4, 'Romance'),
(5, 'Drama'),
(5, 'Romance');

-- 기생충 (봉준호)
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES
(1, 1, '기택', 'LEAD'),  -- 송강호
(1, 2, '동익', 'SUPPORTING');  -- 이선균

-- 인셉션 (스필버그 대역)
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES
(2, 4, 'Dom Cobb', 'LEAD'),      -- 레오나르도 디카프리오
(2, 3, 'Arthur', 'SUPPORTING');       -- 앤서니 스타

-- 좋은 놈 나쁜 놈 이상한 놈 (김지운)
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES
(3, 6, '박도원', 'LEAD'),       -- 이병헌
(3, 5, '송이', 'SUPPORTING');         -- 전혜진

-- 가위손 (팀 버튼)
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES
(4, 7, 'Edward', 'LEAD'),       -- 조니 뎁
(4, 8, 'Kim', 'SUPPORTING');          -- 위노나 라이더

-- 비몽 (홍상수)
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES
(5, 6, '용수', 'LEAD'),         -- 이병헌
(5, 5, '란희', 'SUPPORTING');         -- 전혜진

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

UPDATE movies SET cumulative_bookings = 2728 WHERE id = 1;
UPDATE movies SET cumulative_bookings = 1276 WHERE id = 2;
UPDATE movies SET cumulative_bookings = 6078 WHERE id = 3;
UPDATE movies SET cumulative_bookings = 9339 WHERE id = 4;
UPDATE movies SET cumulative_bookings = 2425 WHERE id = 5;


-- 감독 추가 (7번)
INSERT INTO directors (id, name, photo_url) VALUES 
(7, '켈시 맨', 'https://image.tmdb.org/t/p/w500/fJqInoNOaJJ1C6i5dgnr3zTr2s.jpg');

-- 배우 추가 (11번, 12번)
INSERT INTO actors (id, name, photo_url) VALUES
(11, '에이미 포엘러', 'https://image.tmdb.org/t/p/w500/rwmvQ11HvoKxXORb321Vv3bI04z.jpg');
INSERT INTO actors (id, name, photo_url) VALUES
(12, '마야 호크', 'https://image.tmdb.org/t/p/w500/wET1c2prY13d2t9O3a5sYv1Dk0m.jpg');

-- 영화 추가 (7번)
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (7, '인사이드 아웃 2', '라일리의 머릿속 감정 컨트롤 본부에 ‘불안’, ‘당황’, ‘따분’, ‘부럽’의 낯선 감정들이 새롭게 등장하면서 평화롭던 일상이 깨지고 다시 시작된 위기와 모험을 다룬 애니메이션', 96, 'ALL', '["https://image.tmdb.org/t/p/original/bIe12A72B4gPaD6S4Qpjd4N55d.jpg"]', TO_DATE('2024-06-12T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '월트 디즈니 컴퍼니 코리아', 7);

-- 통계 추가 (7번 영화)
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 7, 2500000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 7, 2850000);

-- 상영유형 연결 (7번 영화)
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (7, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (7, '3D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (7, 'IMAX');

-- 장르 연결 (7번 영화)
INSERT INTO movie_genres (movie_id, genre_name) VALUES (7, 'Animation');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (7, 'Comedy');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (7, 'Drama');

-- 출연진 연결 (7번 영화)
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (7, 11, '기쁨 (목소리)', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (7, 12, '불안 (목소리)', 'LEAD');

UPDATE movies SET cumulative_bookings = 2850000 WHERE id = 7;
-- ====================================================================================================================
-- ====================================================================================================================
-- 감독 추가 (8번)
INSERT INTO directors (id, name, photo_url) VALUES (8, '딘 데블로이스', 'https://search.pstatic.net/common?type=n&size=174x196&quality=100&direct=true&src=https%3A%2F%2Fssl.pstatic.net%2Fimgmovie%2Fmdi%2Fpi%2F000000062%2FPM6214_181002_000.jpg');

-- 배우 추가 (13번, 14번)
INSERT INTO actors (id, name, photo_url) VALUES (13, '메이슨 테임즈', 'https://search.pstatic.net/common?type=n&size=174x196&quality=100&direct=true&src=https%3A%2F%2Fssl.pstatic.net%2Fimgmovie%2Fmdi%2Fpi%2F000004555%2FPM455553_181112_000.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (14, '제라드 버틀러', 'https://search.pstatic.net/common?type=n&size=174x196&quality=100&direct=true&src=https%3A%2F%2Fssl.pstatic.net%2Fimgmovie%2Fmdi%2Fpi%2F000000033%2FPM3326_181424_000.jpg');

-- 영화 추가 (8번)
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (8, '드래곤 길들이기', '오랜 전쟁 속, 드래곤을 없애야 하는 바이킹 족장의 아들 ‘히컵’이 무리와 다른 신념으로 인해 갈등을 겪는다', 125, 'ALL', '["https://cf.lottecinema.co.kr//Media/MovieFile/MovieImg/202506/22014_201_1.jpg","https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fmovie-phinf.pstatic.net%2F20250512_149%2F1747012921958HDq8h_JPEG%2Fmovie_image.jpg"]', TO_DATE('2025-06-06T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '유니버설 픽쳐스', 8);

-- 통계 추가 (8번 영화)
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 8, 150000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 8, 905000);

-- 상영유형 연결 (8번 영화)
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (8, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (8, '3D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (8, 'IMAX');

-- 장르 연결 (8번 영화)
INSERT INTO movie_genres (movie_id, genre_name) VALUES (8, 'Animation');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (8, 'Fantasy');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (8, 'Action');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (8, 'Adventure');

-- 출연진 연결 (8번 영화)
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (8, 13, '히컵', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (8, 14, '스토이크', 'SUPPORTING');

UPDATE movies SET cumulative_bookings = 905000  WHERE id = 8;

INSERT INTO directors (id, name, photo_url) VALUES (9, '크리스토퍼 놀란', 'https://search.pstatic.net/common?type=b&size=216&expire=1&refresh=true&quality=100&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2F25%2F201411101802253101.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (10, '강형철', 'https://search.pstatic.net/common?type=n&size=174x196&quality=100&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2F26%2F201812191710182511.png');
INSERT INTO directors (id, name, photo_url) VALUES (11, '그레타 거윅', 'https://upload.wikimedia.org/wikipedia/commons/thumb/7/78/Greta_Gerwig_at_Barbie_Movie_Reception_%28headshot%29.jpg/500px-Greta_Gerwig_at_Barbie_Movie_Reception_%28headshot%29.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (12, '제임스 카메론', 'https://upload.wikimedia.org/wikipedia/commons/thumb/7/77/James_Cameron_October_2012.jpg/375px-James_Cameron_October_2012.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (13, '스티븐 스필버그', 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/MKr25402_Steven_Spielberg_%28Berlinale_2023%29.jpg/500px-MKr25402_Steven_Spielberg_%28Berlinale_2023%29.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (14, '미야자키 하야오', 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/e2/Hayao_Miyazaki_cropped_1_Hayao_Miyazaki_201211.jpg/500px-Hayao_Miyazaki_cropped_1_Hayao_Miyazaki_201211.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (15, '라이언 쿠글러', 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/Ryan_Coogler_by_Gage_Skidmore.jpg/330px-Ryan_Coogler_by_Gage_Skidmore.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (16, '알폰소 쿠아론', 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Alfonso_Cuar%C3%B3n_at_the_2024_Toronto_International_Film_Festival_2_%28cropped%29.jpg/500px-Alfonso_Cuar%C3%B3n_at_the_2024_Toronto_International_Film_Festival_2_%28cropped%29.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (17, '드니 빌뇌브', 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Denis_Villeneuve_by_Gage_Skidmore.jpg/500px-Denis_Villeneuve_by_Gage_Skidmore.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (18, '클로이 자오', 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/96/Chloezhao.jpg/500px-Chloezhao.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (19, '크리스 벅', 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Chris_Buck%2C_Frozen_premiere%2C_2013.jpg/500px-Chris_Buck%2C_Frozen_premiere%2C_2013.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (20, '필 로드', 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Phil_Lord_by_Gage_Skidmore.jpg/330px-Phil_Lord_by_Gage_Skidmore.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (21, '론 클레멘츠', 'https://upload.wikimedia.org/wikipedia/commons/e/e2/Ron_Clements_2.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (22, '앤드류 스탠튼', 'https://search.pstatic.net/common?type=n&size=174x196&quality=100&direct=true&src=https%3A%2F%2Fssl.pstatic.net%2Fimgmovie%2Fmdi%2Fpi%2F000000062%2FPM6214_181002_000.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (23, '피트 닥터', 'https://upload.wikimedia.org/wikipedia/commons/f/fd/Pete_Docter_cropped_2009.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (24, '브래드 버드', 'https://upload.wikimedia.org/wikipedia/commons/e/e6/Bradbird.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (25, '게리 트러스데일', 'https://upload.wikimedia.org/wikipedia/commons/d/d7/Gary_Trousdale.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (26, '로저 알러스', 'https://search.pstatic.net/common?type=n&size=174x196&quality=100&direct=true&src=https%3A%2F%2Fssl.pstatic.net%2Fimgmovie%2Fmdi%2Fpi%2F000000062%2FPM6214_181002_000.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (27, '고레에다 히로카즈', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/Hirokazu_Kore-eda_Cannes_2015.jpg/375px-Hirokazu_Kore-eda_Cannes_2015.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (28, '미카엘 하네케', 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Michael_Haneke_2009.jpg/375px-Michael_Haneke_2009.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (29, '페드로 알모도바르', 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Pedro_Almodovar_C%C3%A9sars_2017.jpg/375px-Pedro_Almodovar_C%C3%A9sars_2017.jpg');
INSERT INTO directors (id, name, photo_url) VALUES (30, '자비에 돌란', 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/2e/Xavier_Dolan_Cannes_2016.jpg/375px-Xavier_Dolan_Cannes_2016.jpg');

INSERT INTO actors (id, name, photo_url) VALUES (15, '티모시 샬라메', 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Timoth%C3%A9e_Chalamet-63482_%28cropped%29.jpg/500px-Timoth%C3%A9e_Chalamet-63482_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (16, '젠데이아', 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/Zendaya_-_2019_by_Glenn_Francis.jpg/500px-Zendaya_-_2019_by_Glenn_Francis.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (17, '이재인', 'https://search.pstatic.net/common?type=n&size=174x196&quality=100&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F202301%2F20230114115814432.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (18, '안재홍', 'https://search.pstatic.net/common?type=n&size=174x196&quality=100&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2Fportrait%2F202203%2F20220307184022934.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (19, '마고 로비', 'https://search.pstatic.net/common?type=b&size=216&expire=1&refresh=true&quality=100&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2F30%2F201312261648585701.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (20, '라이언 고슬링', 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/f6/Ryan_Gosling_in_2018.jpg/500px-Ryan_Gosling_in_2018.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (21, '레오나르도 디카프리오', 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Leonardo_Dicaprio_Cannes_2019.jpg/330px-Leonardo_Dicaprio_Cannes_2019.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (22, '케이트 윈슬렛', 'https://i.namu.wiki/i/A9NgOBKbIbCA_PrlxK0d9gZRiLkLrLMFq-6xbaELxwtxMXIMmzUtamtexCbfq-KrcrNlNMEK81keWk9h7O11seDvNB4mP7ZmfGN3COC2ACPE3GGfB6EtWpJtGk1Zj9i0XIo0ACV-nS5hrFE4DrnqLg.webp');
INSERT INTO actors (id, name, photo_url) VALUES (23, '톰 행크스', 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Tom_Hanks_TIFF_2019.jpg/330px-Tom_Hanks_TIFF_2019.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (24, '엠마 왓슨', 'https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/Emma_Watson_Circle.png/500px-Emma_Watson_Circle.png');
INSERT INTO actors (id, name, photo_url) VALUES (25, '다니엘 래드클리프', 'https://search.pstatic.net/common?type=n&size=174x196&quality=100&direct=true&src=https%3A%2F%2Fssl.pstatic.net%2Fimgmovie%2Fmdi%2Fpi%2F000000062%2FPM6214_181002_000.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (26, '크리스 프랫', 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/99/Chris_Pratt_2018.jpg/500px-Chris_Pratt_2018.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (27, '조 샐다나', 'https://i.namu.wiki/i/p_GreEyUmt9IXgLXTlkHbkRuG_KaO7H7XO0zBzZ4UmTBom-apnCofpsISw5daqobgV6zuWZWYXG4k4-JhZHVQm_NDEexsFt69Id7-8lDIaAD7F9M--oGqgwiRG6kA4sRlQGSgGqdDufSRbNBnq3a9Q.webp');
INSERT INTO actors (id, name, photo_url) VALUES (28, '크리스 에반스', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Chris_Evans_2020_%28cropped%29.jpg/375px-Chris_Evans_2020_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (29, '스칼렛 요한슨', 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/Scarlett_Johansson-8588.jpg/500px-Scarlett_Johansson-8588.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (30, '로버트 다우니 주니어', 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Robert_Downey_Jr_2014_Comic_Con_%28cropped%29.jpg/500px-Robert_Downey_Jr_2014_Comic_Con_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (31, '베네딕트 컴버배치', 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/1d/Benedict_Cumberbatch-67218_%28cropped%29.jpg/500px-Benedict_Cumberbatch-67218_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (32, '엠마 스톤', 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/9b/Emma_Stone_at_the_2025_Cannes_Film_Festival_03_%28cropped%29.jpg/500px-Emma_Stone_at_the_2025_Cannes_Film_Festival_03_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (33, '라이언 레이놀즈', 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Deadpool_2_Japan_Premiere_Red_Carpet_Ryan_Reynolds_%28cropped%29.jpg/500px-Deadpool_2_Japan_Premiere_Red_Carpet_Ryan_Reynolds_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (34, '블레이크 라이블리', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8e/Blake_Lively_Cannes_2016_3.jpg/500px-Blake_Lively_Cannes_2016_3.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (35, '휴 잭맨', 'https://i.namu.wiki/i/LvDoDld97VmWIQQ1yj3CS9jJwsQLj2Y7IG15CNwFkbhJeJkQ3-RQSHKICQANrS6vqlg0riyhlJHKEI4Hl9LaU3NTKimBS6ZBSkSFlKOU0XQutk6p9nOpbzfxg08MMWv28x7nGqUgw3yKPozp2BpjgQ.webp');
INSERT INTO actors (id, name, photo_url) VALUES (36, '앤 해서웨이', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/Anne_Hathaway-68408_%28cropped%29.jpg/500px-Anne_Hathaway-68408_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (37, '매튜 맥커너히', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/8e/Matthew_McConaughey_-_Goldene_Kamera_2014_-_Berlin.jpg/500px-Matthew_McConaughey_-_Goldene_Kamera_2014_-_Berlin.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (38, '제시카 차스테인', 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/11/Jessica_Chastain-64631_%28cropped%29.jpg/500px-Jessica_Chastain-64631_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (39, '크리스찬 베일', 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/cc/Christian_Bale-7834.jpg/500px-Christian_Bale-7834.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (40, '게리 올드만', 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Gary_Oldman_%2813948780853%29_%28cropped%29.jpg/500px-Gary_Oldman_%2813948780853%29_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (41, '나탈리 포트만', 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Natalie_Portman_2023.jpg/500px-Natalie_Portman_2023.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (42, '빈 디젤', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Vin_Diesel_by_Gage_Skidmore_2.jpg/500px-Vin_Diesel_by_Gage_Skidmore_2.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (43, '폴 워커', 'https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/PaulWalkerEdit-1.jpg/375px-PaulWalkerEdit-1.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (44, '에밀리 블런트', 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/23/Emily_Blunt_SAG_Awards_2019.png/375px-Emily_Blunt_SAG_Awards_2019.png');
INSERT INTO actors (id, name, photo_url) VALUES (45, '존 크래신스키', 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/13/John_Krasinski_2018.png/375px-John_Krasinski_2018.png');
INSERT INTO actors (id, name, photo_url) VALUES (46, '엘르 패닝', 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3d/Elle_Fanning-0257_%28cropped%29.jpg/500px-Elle_Fanning-0257_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (47, '안젤리나 졸리', 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/JolieMariaBFILFF181024_%2818_of_28%29_%2854081064591%29_%28cropped%29.jpg/500px-JolieMariaBFILFF181024_%2818_of_28%29_%2854081064591%29_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (48, '베네딕트 웡', 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Benedict_Wong_%2828352087500%29_%28cropped%29.jpg/500px-Benedict_Wong_%2828352087500%29_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (49, '리암 니슨', 'https://search.pstatic.net/common?type=n&size=174x196&quality=100&direct=true&src=https%3A%2F%2Fssl.pstatic.net%2Fimgmovie%2Fmdi%2Fpi%2F000004555%2FPM455553_181112_000.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (50, '아만다 사이프리드', 'https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Amanda_Seyfried_at_Berlinale_2024%2C_cropped_%283x4_cropped%29.jpg/500px-Amanda_Seyfried_at_Berlinale_2024%2C_cropped_%283x4_cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (51, '메릴 스트립', 'https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Meryl_Streep_December_2018_%28cropped%29.jpg/500px-Meryl_Streep_December_2018_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (52, '톰 홀랜드', 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/500px-Tom_Holland_by_Gage_Skidmore.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (53, '젠데이아 콜먼', 'https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/Zendaya_-_2019_by_Glenn_Francis.jpg/500px-Zendaya_-_2019_by_Glenn_Francis.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (54, '제이크 질렌할', 'https://search.pstatic.net/common?type=n&size=174x196&quality=100&direct=true&src=https%3A%2F%2Fssl.pstatic.net%2Fimgmovie%2Fmdi%2Fpi%2F000000109%2FPM10996_181121_000.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (55, '틸다 스윈튼', 'https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Tilda_Swinton-60999_%28cropped%29.jpg/500px-Tilda_Swinton-60999_%28cropped%29.jpg');
-- INSERT INTO actors (id, name, photo_url) VALUES (56, '틸다 스윈튼', 'https://search.pstatic.net/common?type=n&size=174x196&quality=100&direct=true&src=https%3A%2F%2Fssl.pstatic.net%2Fimgmovie%2Fmdi%2Fpi%2F000000030%2FPM3019_181121_000.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (57, '레이첼 와이즈', 'https://commons.wikimedia.org/wiki/File:Rachel_Weisz_2018.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (58, '루피타 뇽오', 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/34/SXSW_2019_4_%2847282558132%29_%28cropped%29.jpg/500px-SXSW_2019_4_%2847282558132%29_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (59, '채드윅 보즈먼', 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/e8/Chadwick_Boseman_by_Gage_Skidmore_July_2017_%28cropped%29.jpg/500px-Chadwick_Boseman_by_Gage_Skidmore_July_2017_%28cropped%29.jpg');
INSERT INTO actors (id, name, photo_url) VALUES (60, '톰 히들스턴', 'https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Tom_Hiddleston_at_the_2024_Toronto_International_Film_Festival_%28cropped%29.jpg/500px-Tom_Hiddleston_at_the_2024_Toronto_International_Film_Festival_%28cropped%29.jpg');

INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (9, '인터스텔라', '황폐해져 가는 지구를 살리기 위해 새로운 행성을 찾아 떠나는 우주 탐험대 이야기', 169, 'FIFTEEN', '["https://upload.wikimedia.org/wikipedia/ko/b/b7/%EC%9D%B8%ED%84%B0%EC%8A%A4%ED%85%94%EB%9D%BC.jpg"]', TO_DATE('2025-06-10T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '워너 브라더스 코리아', 9);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (10, '하이파이브', '장기 이식 후 초능력을 얻은 5인이 팀을 이뤄, 절대자를 꿈꾸는 또 다른 이식자에 맞서는 이야기.', 119, 'FIFTEEN', '["https://i.namu.wiki/i/a1iOqVMugOS5KPqFmPn1UfhBZo0sc25WTE71e-HFvsPpdZfM_48EcY3XAxUvylXYwaoEmS4bzUYyGKYchqftDGrO1yCj5RmFnLXLCbiNW7GHqM2gdjX5zXIeFAKWRswtM5M9r3uGHxuHBFrRWiBTqw.webp"]', TO_DATE('2025-06-15T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '넥스트 엔터테인먼트 월드', 10);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (11, '바비', '바비랜드에서 살던 바비가 현실 세계로 오게 되면서 벌어지는 이야기', 114, 'ALL', '["https://i.namu.wiki/i/j-uk6qHtcRQWPxGXlWkua8Uy6e9RxMMp36PHRUuqwyAbIF6FDUuckOR1x--2rhcTnCrfegbFgmgmKu5VHn3hSwURygeeKO9v8C0NMvneDU22J76naJ4IMNkHIed23-CkmSjNNKGOYO6t9gzk1aHLWg.webp"]', TO_DATE('2025-06-20T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '워너 브라더스 코리아', 11);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (12, '아바타: 물의 길', '판도라 행성에서 가족을 이루고 살던 제이크 설리에게 위기가 닥치고, 새로운 터전을 찾아 떠나는 모험', 192, 'FIFTEEN', '["https://i.namu.wiki/i/N1wN6u-PAyFQb5aHPKf4OUZ5d0ZX6V-CYd0nhX_H4beICdRYTLu58h9mxWXPHRVC3ncLygg3oN-6NdKwrrl0AjL1A635QwD4Kqsn3LLn25TvIiftar-LDl8Ju9akBMPqP54caV7IqDw5DIF9zu3qMg.webp"]', TO_DATE('2025-06-25T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '월트 디즈니 컴퍼니 코리아', 12);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (13, '쥬라기 공원', '공룡이 되살아난 테마파크에서 벌어지는 아찔한 생존 스릴러', 127, 'FIFTEEN', '["https://i.namu.wiki/i/pX7N3DdxfyKsxifAxABhiVz6Pw4wGBiGMbnGhdb9c_BGV4f5A8mdu1bt02i4Nx0ZxqE5_o_PsQjkIRcvC13D0Kf59V4g4BMdCCZZuTQoJNBlygRD9yPqx-nw4c7GYqmOg2TBxTFhr-ZrXe4chewbAQ.webp"]', TO_DATE('2025-07-01T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '유니버설 픽쳐스', 13);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (14, '센과 치히로의 행방불명', '평범한 소녀 치히로가 신비한 세계에 발을 들이면서 벌어지는 환상적인 이야기', 124, 'ALL', '["https://i.namu.wiki/i/s510F-djGrbkdSXZxT7RbvnerHUSO9S39_oCURw4R4DfIEyNs1tXm7J-FNnBlkw44ze3m3UM8JcvtFsC2sl9DvkCchBjQEi7LMoy8J1M7LIoP08xAt1K7PKi0QdQXBf1BNSws1u4ofCmD7JMYXskOg.webp"]', TO_DATE('2025-07-05T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '대원미디어', 14);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (15, '블랙 팬서', '와칸다의 국왕이자 블랙 팬서인 티찰라가 위협에 맞서 왕국을 지키는 이야기', 134, 'FIFTEEN', '["https://i.namu.wiki/i/3yPryAWj5S1j_YGwusUdldbIXZZJvzwbp9KBg-u2i6IMsGGjRffDHuhmFpLUdjzLZLYGOkI6ZuJQGBlxqme_epVP9w5MoNghxci72oQ2yrGhEBUoejCtOCJPLYzqvMz5JhQT4ikE_fLUUvBA6nHN9Q.webp"]', TO_DATE('2025-07-10T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '월트 디즈니 컴퍼니 코리아', 15);

INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (16, '그래비티', '우주 공간에 홀로 남겨진 우주인이 생존을 위해 사투를 벌이는 이야기', 91, 'FIFTEEN', '["https://i.namu.wiki/i/I3I-NaoOprB9YJQ5i1C5NApVfOKBAL-Hw9lK4N1VXtOb-exPqi_SR1p_7mGF5R_licvuNAm3jk1-oSPTiaCgkv6NfcLFdJV3e-RXYX-92NqjsG6oxXqyIYSApr28hsYxUs6GIrt70zD7VlKd8r1KbA.webp"]', TO_DATE('2025-07-15T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '워너 브라더스 코리아', 16);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (17, '컨택트', '어느 날 지구에 도착한 외계 존재들과 소통하려는 언어학자의 이야기', 116, 'FIFTEEN', '["https://i.namu.wiki/i/m3jyBmatVtaTadxBTrkezm0BaCXFpVPfPqFvmc8CJ0c83VYgq1egyX0eRuuPqrbEnpFPygxW_H_ZX2IfMeIVkgWkaH5W8yKWDY_79FrHz6SgizKs9-BMbXor2pTQv5MLZibQ18rci4z1NF0awKJO6w.webp"]', TO_DATE('2025-07-20T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), 'UPI 코리아', 17);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (18, '노매드랜드', '모든 것을 잃은 여인이 현대판 유목민으로 살아가며 겪는 여정', 107, 'FIFTEEN', '["https://i.namu.wiki/i/xUNrjKuwLShAXFWO6TtvM3fVdp2LC2vMS21lE72PVXGpRSQfHOU0whdq8FKgwyUMKLiNCsqiqv_gFnmzwVhEC3vDmfzO-iZ3m1NdXylCEB_LomvxVP-lZjM57EL2EBJnXi0wwRMUQSqwWLe1r2JI2Q.webp"]', TO_DATE('2025-07-25T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '월트 디즈니 컴퍼니 코리아', 18);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (19, '겨울왕국', '얼어붙은 왕국을 녹이기 위한 자매의 환상적인 모험', 102, 'ALL', '["https://i.namu.wiki/i/uYIY3bJv_odtlA0DUollB7KCCNoGhbMlBFfs7cBYziVAVGI2wjUMhcpjPYDgOfi_WradBt26fsxyCAoEIXeFs-we-3pFzEYgJxSlZgzvpIQ02iK9i7ldWkJsRzv6aHEgVtgbyYWlJ2ddWsdCRTgNBw.webp"]', TO_DATE('2025-08-01T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '월트 디즈니 컴퍼니 코리아', 19);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (20, '스파이더맨: 뉴 유니버스', '평행 우주의 스파이더맨들이 한곳에 모여 세상을 구하는 이야기', 117, 'ALL', '["https://i.namu.wiki/i/bFUBRzWNtTZqfYsGavmNs-l4BuLGiKZsn4MvUf3OhxQ7EN49SXO6Ikjr1FzaKiJ6pqgC0uT2V41M-ejKDVpfiQUyGIXKAPvoFaPr1A-1Pcmy15EDmDOZquThVjqZtRbOqvhSALnuV--86Ba39Jh1Mg.webp"]', TO_DATE('2025-08-05T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '소니 픽쳐스 코리아', 20);

INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (21, '모아나', '선택받은 소녀 모아나가 저주받은 섬을 구하기 위해 떠나는 환상적인 모험', 107, 'ALL', '["https://i.namu.wiki/i/d7NY9ej8rQ4aZjObQgRyah8SCql3ZDmJuqj0HIv_MQPPjoK3HZE0v1wwWDV1oe5G3nfC_JHDXh0t_BNXr2PLHf02u9NdHEuqUbv3msflRPZCM18qwP5I4f6aTSETR6ZmwUtCXKz5hZXHdv_aD5G93Q.webp"]', TO_DATE('2025-08-10T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '월트 디즈니 컴퍼니 코리아', 21);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (22, '월-E', '지구에 홀로 남은 청소 로봇 월-E가 새로운 임무를 위해 우주로 떠나는 모험', 103, 'ALL', '["https://i.namu.wiki/i/lSYVRZ9uzavtG-5DjdUSSP_ZUcd_0HkUjavME5lECAhTE4axyTKxIy6nNRyUimwoaUQ91EGt84qY60rbaBTFIb8DG4Hs7iRA-skEkKLcwiCGm5XV9w6TCkBtGLszppNU_UlAFTceVupTAovba71uzA.webp"]', TO_DATE('2025-08-15T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '월트 디즈니 컴퍼니 코리아', 22);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (23, '인사이드 아웃', '사람의 감정을 의인화하여 보여주는 독특한 세계에서 벌어지는 이야기', 94, 'ALL', '["https://i.namu.wiki/i/zoBtwL5nI1Esi2s7SBCv7buG_psvN9uYyWo1NItp7DkfDKYOwnk9gYS2gopmGBCK-74BQo366ATZT9g_TSVw3N_jZ7Ts2zCLk2oo19B5Kk-vf8BqboNh4sX2r8VPyqEAJgKeloilGok18lNwR6hh8w.webp"]', TO_DATE('2025-08-20T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '월트 디즈니 컴퍼니 코리아', 23);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (24, '토이 스토리', '장난감들이 살아 움직이는 세상에서 우정, 모험, 그리고 이별을 겪는 이야기', 81, 'ALL', '["https://i.namu.wiki/i/ElZsRlhoRG9eoZkax_AWFUm1UmpMUZ_VCYnPpIRWaHGpKWZP2HYXcAGa9LvMnREg7G1AsURHfTDkdcdSvCGPL3C8hMj40G6NOMoTKCsSbrn8HmY99H0M-whgkMly4LCdR4ZTzXka0tkxRw5y1LIuww.webp"]', TO_DATE('2025-08-25T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '월트 디즈니 컴퍼니 코리아', 23);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (25, '미녀와 야수', '저주에 걸린 야수와 아름다운 벨의 사랑 이야기', 90, 'ALL', '["https://i.namu.wiki/i/HrO6p7Jhh7lFaYUdjTk-jxy9dcgkYtEhaGv9KfbIKlyUc4gTO5Q-YNRgZAtHoElgmpUZ7UAKSPaKTGOnurqH0_YJOKMt11qMnOrMFyyG5pDGqQUfaLIdI-dsBqTAOhz1ACIwsI6sbSS8tnnQ_smaSA.webp"]', TO_DATE('2025-09-01T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '월트 디즈니 컴퍼니 코리아', 25);

INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (26, '라이언 킹', '아프리카 초원의 왕이 될 심바의 성장 이야기', 89, 'ALL', '["https://i.namu.wiki/i/NScJ4IRuKoOhfSTkzpgbzs6RuEkP_lE-T6NgNBz3Q9dh_ePZhQw5uQfRspLmbBiIfNQ1QavamWCqrupWtCJhxIeBk0pj4QX9CrtUhKNXJHmrZ8gkAulTt03oqKrSxfJqSXrk2_y_RX_Hr0viB3q0tA.webp"]', TO_DATE('2025-09-05T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '월트 디즈니 컴퍼니 코리아', 26);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (27, '어느 가족', '도둑질로 생계를 유지하는 한 가족의 특별한 이야기', 121, 'FIFTEEN', '["https://i.namu.wiki/i/qgWV_5oH4WZJcl8Z1dw4dZbWpfmE4gOTRR_OZdep1PYZHcTGt4PskRnI8miGLFy2MuFeoRWZNdAfByC_gaenxnPMCafuP1ZR_5TOer9aG_CgYxH-dHsoS5N1cZ5vbUdj-e8FU9c4Iqgme2NtMwMCPA.webp"]', TO_DATE('2025-09-10T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '티캐스트', 27);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (28, '아무르', '늙은 부부의 사랑과 이별에 대한 아름답고 슬픈 이야기', 127, 'FIFTEEN', '["https://i.namu.wiki/i/ZmBl8a-XzzLGBvrmIqgGU7bJU1CDPc18WHmLDdSlER1yzHe-U-9r3XuP4lPd9OlCm_4s2Dile_dppwYh4N4NS911yJTL4Zzia3R1HiJED6CFM9NbhOR_jk3DfUA9gPOP0hRKAwN29-E2B8bP-NE2SA.webp"]', TO_DATE('2025-09-15T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '엣나인필름', 28);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (29, '페인 앤 글로리', '과거의 기억과 현재의 고통 속에서 자신을 찾아가는 영화감독의 이야기', 113, 'FIFTEEN', '["https://i.namu.wiki/i/XZ4VeYY0R64UsW0Ih26-bJzrVfrVelAtx7NwibhtZk0TlT25pyWmyjQ5srEEHLjaqxZHVEHwEcXRTrQZ6PqkMQwpT2XnRbWTTq7QIMWh1iFV6awMrUtGjE6QRiMkc6K5tj5atxpidbVJpC4Yj3sTIw.webp"]', TO_DATE('2025-09-20T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '찬란', 29);
INSERT INTO movies (id, title, synopsis, running_time, rating, poster_urls, release_date, distributor, director_id) VALUES (30, '마미', '통제 불능 아들과 예측 불가능한 엄마의 특별한 관계를 그린 이야기', 139, 'FIFTEEN', '["https://i.namu.wiki/i/lmjyb2seaOeOkWL503NQE-0pHrB9R0NsxDXmiFJETaxazIKqpu2uHegj4EOXyW-C7AdZoyLl9hVxvdHBiHwpCTKQWQlR-DO4vR8n3ASHNUz7rDZCdvdWAavevEFHfHLRkAPdVELTJF5J8xvmWs_g8Q.webp"]', TO_DATE('2025-09-25T00:00:00', 'YYYY-MM-DD"T"HH24:MI:SS'), '그린나래미디어', 30);


INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 9, 300000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 9, 350000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 10, 250000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 10, 280000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 11, 180000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 11, 200000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 12, 500000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 12, 550000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 13, 120000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 13, 140000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 14, 200000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 14, 220000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 15, 320000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 15, 360000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 16, 100000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 16, 115000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 17, 90000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 17, 105000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 18, 70000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 18, 80000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 19, 400000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 19, 450000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 20, 280000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 20, 310000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 21, 150000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 21, 170000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 22, 130000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 22, 150000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 23, 220000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 23, 240000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 24, 200000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 24, 215000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 25, 180000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 25, 195000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 26, 250000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 26, 270000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 27, 60000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 27, 70000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 28, 50000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 28, 55000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 29, 40000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 29, 45000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-22', 30, 30000);
INSERT INTO movie_statistics (statistic_date, movie_id, cumulative_bookings) VALUES (DATE '2025-06-23', 30, 35000);

INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (9, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (9, 'IMAX');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (9, '4D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (10, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (11, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (12, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (12, '3D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (12, 'IMAX');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (13, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (14, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (15, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (15, 'IMAX');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (16, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (16, 'IMAX');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (17, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (18, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (19, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (19, '3D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (20, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (20, '3D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (20, '4D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (21, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (22, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (23, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (24, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (25, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (26, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (27, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (28, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (29, '2D');
INSERT INTO movie_screen_types (movie_id, screen_type) VALUES (30, '2D');

INSERT INTO movie_genres (movie_id, genre_name) VALUES (9, 'Sci-Fi');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (9, 'Drama');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (10, 'Drama');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (10, 'Comedy');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (11, 'Comedy');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (11, 'Fantasy');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (12, 'Sci-Fi');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (12, 'Action');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (13, 'Adventure');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (13, 'Sci-Fi');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (14, 'Animation');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (14, 'Fantasy');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (15, 'Action');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (15, 'Sci-Fi');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (16, 'Sci-Fi');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (16, 'Thriller');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (17, 'Sci-Fi');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (17, 'Drama');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (18, 'Drama');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (19, 'Animation');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (19, 'Fantasy');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (20, 'Animation');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (20, 'Action');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (21, 'Animation');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (21, 'Adventure');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (22, 'Animation');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (22, 'Sci-Fi');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (23, 'Animation');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (23, 'Comedy');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (24, 'Animation');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (24, 'Adventure');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (25, 'Animation');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (25, 'Fantasy');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (26, 'Animation');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (26, 'Adventure');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (27, 'Drama');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (28, 'Drama');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (29, 'Drama');
INSERT INTO movie_genres (movie_id, genre_name) VALUES (30, 'Drama');

INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (9, 37, '쿠퍼', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (9, 38, '머피', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (10, 17, '기택', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (10, 18, '기우', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (11, 19, '바비', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (11, 20, '켄', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (12, 26, '제이크 설리', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (12, 27, '네이티리', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (13, 23, '앨런 그랜트', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (14, 24, '치히로', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (15, 59, '트찰라', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (15, 58, '나키아', 'SUPPORTING');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (16, 44, '라이언 스톤', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (16, 20, '맷 코왈스키', 'SUPPORTING');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (17, 41, '루이스 뱅크스', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (17, 25, '이안 던넬리', 'SUPPORTING');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (18, 46, '펀', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (19, 50, '엘사', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (19, 51, '안나', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (20, 52, '마일스 모랄레스', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (20, 53, '그웬 스테이시', 'SUPPORTING');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (21, 50, '모아나', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (22, 23, '월-E', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (23, 32, '기쁨', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (24, 23, '우디', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (25, 47, '벨', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (26, 49, '무파사', 'SUPPORTING');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (27, 17, '오사무', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (28, 55, '안느', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (29, 39, '살바도르 마요', 'LEAD');
INSERT INTO movie_casts (movie_id, actor_id, role, casting_type) VALUES (30, 57, '디아네 데스프레', 'LEAD');

UPDATE movies SET cumulative_bookings = 350000 WHERE id = 9;
UPDATE movies SET cumulative_bookings = 280000 WHERE id = 10;
UPDATE movies SET cumulative_bookings = 200000 WHERE id = 11;
UPDATE movies SET cumulative_bookings = 550000 WHERE id = 12;
UPDATE movies SET cumulative_bookings = 140000 WHERE id = 13;
UPDATE movies SET cumulative_bookings = 220000 WHERE id = 14;
UPDATE movies SET cumulative_bookings = 360000 WHERE id = 15;
UPDATE movies SET cumulative_bookings = 115000 WHERE id = 16;
UPDATE movies SET cumulative_bookings = 105000 WHERE id = 17;
UPDATE movies SET cumulative_bookings = 80000 WHERE id = 18;
UPDATE movies SET cumulative_bookings = 450000 WHERE id = 19;
UPDATE movies SET cumulative_bookings = 310000 WHERE id = 20;
UPDATE movies SET cumulative_bookings = 170000 WHERE id = 21;
UPDATE movies SET cumulative_bookings = 150000 WHERE id = 22;
UPDATE movies SET cumulative_bookings = 240000 WHERE id = 23;
UPDATE movies SET cumulative_bookings = 215000 WHERE id = 24;
UPDATE movies SET cumulative_bookings = 195000 WHERE id = 25;
UPDATE movies SET cumulative_bookings = 270000 WHERE id = 26;
UPDATE movies SET cumulative_bookings = 70000 WHERE id = 27;
UPDATE movies SET cumulative_bookings = 55000 WHERE id = 28;
UPDATE movies SET cumulative_bookings = 45000 WHERE id = 29;
UPDATE movies SET cumulative_bookings = 35000 WHERE id = 30;

-- ====================================================================================================================
-- 포인트 및 예매/결제 내역 테스트 데이터
-- ====================================================================================================================

-- 회원 포인트 테스트용 예매 데이터 (PointAcceptanceTest 용)
INSERT INTO reservations (id, customer_id, screening_id, status, created_at, seat_snapshot, customer_count_snapshot)
VALUES (1001, 1, 1, 'COMPLETED', TO_DATE('2025-06-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 
        '["A1"]', '{"ADULT": 1}');

INSERT INTO reservations (id, customer_id, screening_id, status, created_at, seat_snapshot, customer_count_snapshot)
VALUES (1002, 1, 2, 'COMPLETED', TO_DATE('2025-06-21 14:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        '["B1", "B2"]', '{"ADULT": 2}');

INSERT INTO reservations (id, customer_id, screening_id, status, created_at, seat_snapshot, customer_count_snapshot)
VALUES (1003, 1, 3, 'COMPLETED', TO_DATE('2025-06-22 18:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        '["C1"]', '{"ADULT": 1}');

-- 회원 포인트 테스트용 결제 데이터
INSERT INTO payments (id, reservation_id, requested_at, approved_at, payment_method, 
                     approval_number, original_price, discount_amount, final_price)
VALUES (1001, 1001, TO_DATE('2025-06-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 
        TO_DATE('2025-06-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 
        'CARD', 'APPROVAL1001', 12000, 0, 12000);

INSERT INTO payments (id, reservation_id, requested_at, approved_at, payment_method, 
                     approval_number, original_price, discount_amount, final_price)
VALUES (1002, 1002, TO_DATE('2025-06-21 14:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_DATE('2025-06-21 14:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        'BANK', 'APPROVAL1002', 24000, 0, 24000);

INSERT INTO payments (id, reservation_id, requested_at, approved_at, payment_method, 
                     approval_number, original_price, discount_amount, final_price)
VALUES (1003, 1003, TO_DATE('2025-06-22 18:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_DATE('2025-06-22 18:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        'CARD', 'APPROVAL1003', 12000, 0, 12000);

-- 회원 포인트 거래 데이터 (최종 잔액: 1120 포인트)
INSERT INTO point_transactions (payment_id, customer_id, point, total_point)
VALUES (1001, 1, 500, 500);

INSERT INTO point_transactions (payment_id, customer_id, point, total_point)
VALUES (1002, 1, -200, 300);

INSERT INTO point_transactions (payment_id, customer_id, point, total_point)
VALUES (1003, 1, 100, 400);

-- 예매/결제 내역 테스트용 데이터 (CustomerHistoryAcceptanceTest 용)
-- 회원의 완료된 예매
INSERT INTO reservations (id, customer_id, screening_id, status, created_at, seat_snapshot, customer_count_snapshot)
VALUES (2001, 1, 11, 'COMPLETED', TO_DATE('2025-06-22 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 
        '["A1", "A2"]', '{"ADULT": 2}');

-- 회원의 취소된 예매 (결제 없음)
INSERT INTO reservations (id, customer_id, screening_id, status, created_at, seat_snapshot, customer_count_snapshot)
VALUES (2002, 1, 12, 'CANCELED', TO_DATE('2025-06-21 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        '["B1"]', '{"ADULT": 1}');

-- 비회원의 완료된 예매
INSERT INTO reservations (id, customer_id, screening_id, status, created_at, seat_snapshot, customer_count_snapshot)
VALUES (2003, 2, 13, 'COMPLETED', TO_DATE('2025-06-20 20:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        '["C1", "C2", "C3"]', '{"ADULT": 2, "CHILD": 1}');

-- 예매/결제 내역 테스트용 결제 데이터 (완료된 예매에만)
INSERT INTO payments (id, reservation_id, requested_at, approved_at, payment_method, 
                     approval_number, original_price, discount_amount, final_price)
VALUES (3001, 2001, TO_DATE('2025-06-22 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 
        TO_DATE('2025-06-22 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 
        'CARD', 'APPROVAL001', 24000, 2000, 22000);

INSERT INTO payments (id, reservation_id, requested_at, approved_at, payment_method, 
                     approval_number, original_price, discount_amount, final_price)
VALUES (3003, 2003, TO_DATE('2025-06-20 20:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_DATE('2025-06-20 20:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        'BANK', 'APPROVAL003', 36000, 3000, 33000);

-- 예매/결제 내역 테스트용 포인트 데이터 (회원 결제에만)
INSERT INTO point_transactions (payment_id, customer_id, point, total_point)
VALUES (3001, 1, -500, 900);  -- 포인트 사용

INSERT INTO point_transactions (payment_id, customer_id, point, total_point)
VALUES (3001, 1, 220, 1120);  -- 포인트 적립
