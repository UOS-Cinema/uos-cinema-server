-- password: password123!
INSERT INTO admins (id, username, password) VALUES (-1, 'administrator', 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK');
INSERT INTO admins (id, username, password) VALUES (-2, 'administrator2', 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK');
-- deleted admin
INSERT INTO admins (id, username, password, deleted_at) VALUES (-3, 'deletedAdministrator', 'icqpMV7H/CrwpF8VyQtyVhBdoY+5tWhGE3DjJcAKcFRDUM0QOpu686oiRa/gn3FK', SYSDATE);
