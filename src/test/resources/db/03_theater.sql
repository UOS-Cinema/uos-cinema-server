
INSERT INTO theaters (id, name, layout) VALUES (1, '1관', '[[0, 1, 1, 1, 0], [1, 1, 2, 1, 1], [1, 1, 2, 1, 1]]');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (1, 'A1');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (1, 'A2');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (1, 'A3');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (1, 'B1');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (1, 'B2');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (1, 'B3');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (1, 'B4');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (1, 'C1');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (1, 'C2');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (1, 'C3');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (1, 'C4');
INSERT INTO theater_screen_types (theater_id, screen_type) VALUES (1, '2D');
INSERT INTO theater_screen_types (theater_id, screen_type) VALUES (1, '3D');
INSERT INTO theater_screen_types (theater_id, screen_type) VALUES (1, '4D');

INSERT INTO theaters (id, name, layout) VALUES (2, '2관', '[[1, 1, 1], [2, 2, 2], [1, 1, 1]]');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (2, 'A1');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (2, 'A2');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (2, 'A3');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (2, 'C1');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (2, 'C2');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (2, 'C3');
INSERT INTO theater_screen_types (theater_id, screen_type) VALUES (2, '2D');

INSERT INTO theaters (id, name, layout) VALUES (3, '3관', '[[1, 0, 1], [1, 2, 1], [1, 0, 1]]');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (3, 'A1');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (3, 'A2');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (3, 'B1');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (3, 'B2');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (3, 'C1');
INSERT INTO theater_seats (theater_id, seat_number) VALUES (3, 'C2');
INSERT INTO theater_screen_types (theater_id, screen_type) VALUES (3, '2D');
INSERT INTO theater_screen_types (theater_id, screen_type) VALUES (3, '3D');