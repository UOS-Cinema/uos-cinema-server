-- Guest 테스트 데이터, password: password123!
INSERT INTO customers (id, user_type, created_at) VALUES (-1, 'GUEST', CURRENT_TIMESTAMP);
INSERT INTO guests (customer_id, name, phone, birth_date, password) VALUES 
(-1, '테스트계정', '01012345678', '1990-01-01', 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK');

