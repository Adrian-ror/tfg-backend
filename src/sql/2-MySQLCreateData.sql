-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "tfg" database.
-------------------------------------------------------------------------------

-- Inserciones de las categorías padre
INSERT INTO Category (name) VALUES ('Movies');
INSERT INTO Category (name) VALUES ('Music');
INSERT INTO Category (name) VALUES ('Books');
INSERT INTO Category (name) VALUES ('Clothing');
INSERT INTO Category (name) VALUES ('Technology');


-- Inserciones de subcategorías
INSERT INTO Category (name, parent_category_id) VALUES ('Action', 1);         -- Subcategoría de Movies
INSERT INTO Category (name, parent_category_id) VALUES ('Drama', 1);          -- Subcategoría de Movies
INSERT INTO Category (name, parent_category_id) VALUES ('Rock', 2);           -- Subcategoría de Music
INSERT INTO Category (name, parent_category_id) VALUES ('Pop', 2);            -- Subcategoría de Music
INSERT INTO Category (name, parent_category_id) VALUES ('Fiction', 3);        -- Subcategoría de Books
INSERT INTO Category (name, parent_category_id) VALUES ('Non-Fiction', 3);    -- Subcategoría de Books
INSERT INTO Category (name, parent_category_id) VALUES ('Computers', 5);      -- Subcategoría de Technology
INSERT INTO Category (name, parent_category_id) VALUES ('Mobile Devices', 5);  -- Subcategoría de Technology


-- Inserciones de los usuarios
-- password: securepassword
INSERT INTO User (userName, password, firstName, lastName, email, phoneNumber, role)
VALUES
    ('john', '$2a$12$xws5CeDq42416JwjvTQYe.bzhUkZd3UOzxLG0JX5tv4A.eY9LjEx.', 'John', 'Doe', 'john.doe@example.com', '123456789', 'CLIENT'),
    ('jane', '$2a$12$xws5CeDq42416JwjvTQYe.bzhUkZd3UOzxLG0JX5tv4A.eY9LjEx.', 'Jane', 'Smith', 'jane.smith@example.com', '987654321', 'PROVIDER'),
    ('admin','$2a$12$xws5CeDq42416JwjvTQYe.bzhUkZd3UOzxLG0JX5tv4A.eY9LjEx.', 'Admin', 'User', 'admin@example.com', '555555555', 'ADMIN'),
    ('review', '$2a$12$xws5CeDq42416JwjvTQYe.bzhUkZd3UOzxLG0JX5tv4A.eY9LjEx.', 'Review', 'Doe', 'review.doe@example.com', '123456785', 'CLIENT');


-- Inserciones de métodos de pago
INSERT INTO PaymentMethod (userId, stripeId, brand, country, exp_month, exp_year, last4, funding, fingerprint)
VALUES
    (1, 'stripe_1', 'Visa', 'US', 12, 2025, '1234', 'credit', 'fingerprint1'),
    (1, 'stripe_2', 'MasterCard', 'US', 11, 2024, '5678', 'credit', 'fingerprint2'),
    (1, 'stripe_3', 'American Express', 'CA', 6, 2026, '9876', 'credit', 'fingerprint3');


-- Inserciones de direcciones de usuario
INSERT INTO UserAddress (userId, addressLine1, city, state, postalCode, country)
VALUES
    (1, 'Calle Real, 50', 'A Coruña', 'Galicia', '15003', 'Spain'),
    (1, 'Rúa San Andrés, 67', 'A Coruña', 'Galicia', '15003', 'Spain'),
    (1, 'Avenida del Ejército, 25', 'A Coruña', 'Galicia', '15006', 'Spain');


-- Inserciones de métodos de envío
INSERT INTO ShippingMethod (name, description, shippingCost, estimatedDeliveryTime)
VALUES
    ('Standard Shipping', 'Delivery in 5-7 business days', 5.99, '5-7 days'),
    ('Express Shipping', 'Delivery in 1-2 business days', 15.99, '1-2 days');


INSERT INTO ShoppingCart(userId)
VALUES (1);


INSERT INTO ShoppingCart(userId)
VALUES (2);

-- Inserciones de listas de deseos
INSERT INTO WishList (userId) VALUES (1);
INSERT INTO WishList (userId) VALUES (2);

-- Productos para Movies
INSERT INTO Product (name, description, shortDescription, price, discount, stock, rating, brand, length, width, height, weight, categoryId, userId)
VALUES
    ('Inception', 'A mind-bending sci-fi action film directed by Christopher Nolan.', 'Sci-fi action film.', 12.99, 0, 50, 0, 'Warner Bros', 19, 14, 2, 0.2, 6, 2),
    ('The Godfather', 'An iconic crime drama about family and power.', 'Classic crime drama.', 9.99, 0, 70, 0, 'Paramount Pictures', 19, 14, 2, 0.2, 7, 2);

INSERT INTO ProductImage (productId, imageUrl, isPrimary) VALUES
      (1, 'https://i.ibb.co/X2f82f4/inception-main.jpg', TRUE),
      (1, 'https://i.ibb.co/4PfTgMH/inception.jpg', FALSE),
      (2, 'https://i.ibb.co/Gkvn6t7/Godfather-main.jpg', TRUE),
      (2, 'https://i.ibb.co/cCrrM3D/Godfather.jpg', FALSE);

-- Productos para Movies: Action y Drama
INSERT INTO Product (name, description, shortDescription, price, discount, stock, rating, brand, length, width, height, weight, categoryId, userId)
VALUES
    ('Avengers: Endgame', 'The ultimate showdown of the Marvel Cinematic Universe.', 'Epic superhero action.', 14.99, 10, 50, 0, 'Marvel Studios', 19, 14, 2, 0.2, 6, 2), -- Action
    ('Titanic', 'A timeless love story set against a tragic historical event.', 'Romantic drama.', 9.99, 5, 70, 0, '20th Century Fox', 19, 14, 2, 0.2, 7, 2); -- Drama

INSERT INTO ProductImage (productId, imageUrl, isPrimary) VALUES
    (3, 'https://i.ibb.co/S5sdvVd/avengers-main.jpg', TRUE),
    (3, 'https://i.ibb.co/kGBFWK2/avengers.jpg', FALSE),
    (4, 'https://i.ibb.co/g7V6Kjd/titanic-main.jpg', TRUE),
    (4, 'https://i.ibb.co/tX6f9kc/titanic.jpg', FALSE);

-- Productos para Music
INSERT INTO Product (name, description, shortDescription, price, discount, stock, rating, brand, length, width, height, weight, categoryId, userId)
VALUES
    ('Abbey Road - The Beatles', 'The last recorded album by The Beatles, featuring timeless classics.', 'Iconic rock album.', 15.99, 10, 30, 0, 'Apple Records', 12, 12, 0.5, 0.3, 8, 2),
    ('Thriller - Michael Jackson', 'The best-selling album of all time with hits like "Billie Jean".', 'Best-selling pop album.', 13.49, 5, 50, 0, 'Epic Records', 12, 12, 0.5, 0.3, 9, 2);

INSERT INTO ProductImage (productId, imageUrl, isPrimary) VALUES
      (5, 'https://i.ibb.co/fHGYXx9/beatels-main.jpg', TRUE),
      (5, 'https://i.ibb.co/Qp9kgxr/beatels.jpg', FALSE),
      (6, 'https://i.ibb.co/qpx2t5M/thriller-main.jpg', TRUE),
      (6, 'https://i.ibb.co/jDTD1zD/thriller.jpg', FALSE);

-- Productos para Music: Rock y Pop
INSERT INTO Product (name, description, shortDescription, price, discount, stock, rating, brand, length, width, height, weight, categoryId, userId)
VALUES
    ('Led Zeppelin IV', 'A masterpiece with hits like "Stairway to Heaven".', 'Classic rock album.', 15.99, 5, 30, 0, 'Atlantic Records', 12, 12, 0.5, 0.3, 8, 2), -- Rock
    ('1989 - Taylor Swift', 'Pop magic from one of the most popular artists.', 'Iconic pop album.', 13.49, 0, 50, 0, 'Big Machine Records', 12, 12, 0.5, 0.3, 9, 2); -- Pop

INSERT INTO ProductImage (productId, imageUrl, isPrimary) VALUES
      (7, 'https://i.ibb.co/xmD5DSR/ledzeppelin-main.jpg', TRUE),
      (7, 'https://i.ibb.co/yRDy2R8/ledzeppelin.jpg', FALSE),
      (8, 'https://i.ibb.co/9WxsLQb/taylor-main.jpg', TRUE),
      (8, 'https://i.ibb.co/Lz80zYQ/taylor.jpg', FALSE);

-- Productos para Books
INSERT INTO Product (name, description, shortDescription, price, discount, stock, rating, brand, length, width, height, weight, categoryId, userId)
VALUES
    ('To Kill a Mockingbird', 'A gripping story about racial injustice and childhood in the American South.', 'Classic fiction novel.', 10.99, 0, 20, 0, 'HarperCollins', 20, 13, 3, 0.4, 10, 2),
    ('Sapiens: A Brief History of Humankind', 'An exploration of human evolution and society.', 'Non-fiction book.', 19.99, 15, 15, 0, 'Penguin', 23, 15, 4, 0.5, 11, 2);

INSERT INTO ProductImage (productId, imageUrl, isPrimary) VALUES
      (9, 'https://i.ibb.co/f071pxM/bird-main.jpg', TRUE),
      (9, 'https://i.ibb.co/0BLh9H7/bird.jpg', FALSE),
      (10, 'https://i.ibb.co/1Lgdgbd/sapines-main.jpg', TRUE),
      (10, 'https://i.ibb.co/X8px0bk/sapines.jpg', FALSE);

-- Productos para Books: Fiction y Non-Fiction
INSERT INTO Product (name, description, shortDescription, price, discount, stock, rating, brand, length, width, height, weight, categoryId, userId)
VALUES
    ('The Great Gatsby', 'A tale of love and ambition in 1920s America.', 'Classic fiction novel.', 10.99, 0, 20, 0, 'Scribner', 20, 13, 3, 0.4, 10, 2), -- Fiction
    ('Atomic Habits', 'A guide to building good habits and breaking bad ones.', 'Self-help book.', 19.99, 15, 15, 0, 'Penguin Random House', 23, 15, 4, 0.5, 11, 2); -- Non-Fiction

INSERT INTO ProductImage (productId, imageUrl, isPrimary) VALUES
      (11, 'https://i.ibb.co/6HvntFc/great-main.jpg', TRUE),
      (11, 'https://i.ibb.co/RT9vM3f/great.jpg', FALSE),
      (12, 'https://i.ibb.co/5kqmXw3/atomic-main.jpg', TRUE),
      (12, 'https://i.ibb.co/8gzS9kY/atomic.jpg', FALSE);

-- Productos para Clothing
INSERT INTO Product (name, description, shortDescription, price, discount, stock, rating, brand, length, width, height, weight, categoryId, userId)
VALUES
    ('Mens Leather Jacket', 'A sleek leather jacket for casual and formal occasions.', 'Stylish leather jacket.', 49.99, 10, 40, 0, 'FashionLine', 60, 40, 5, 1.2, 4, 2),
    ('Womens Summer Dress', 'Lightweight and comfortable dress for the summer.', 'Light summer dress.', 29.99, 5, 60, 0, 'FashionLine', 80, 50, 2, 0.8, 4, 2);

INSERT INTO ProductImage (productId, imageUrl, isPrimary) VALUES
    (13, 'https://i.ibb.co/NSj4x2C/jacket-main.jpg', TRUE),
    (13, 'https://i.ibb.co/dmVQ40x/jacket.jpg', FALSE),
    (14, 'https://i.ibb.co/Nx9jDsS/dress-main.jpg', TRUE),
    (14, 'https://i.ibb.co/vx2HZx8/dress.jpg', FALSE);

-- Productos para Technology
INSERT INTO Product (name, description, shortDescription, price, discount, stock, rating, brand, length, width, height, weight, categoryId, userId)
VALUES
    ('MacBook Pro 14', 'The latest MacBook with the powerful M2 chip for professionals.', 'Powerful laptop.', 1999.99, 100, 10, 0, 'Apple', 32, 22, 2, 1.4, 12, 2),
    ('iPhone 14 Pro', 'Experience innovation with the advanced camera system and performance.', 'Latest smartphone.', 1099.99, 50, 20, 0, 'Apple', 15, 8, 0.7, 0.3, 13, 2);

INSERT INTO ProductImage (productId, imageUrl, isPrimary) VALUES
      (15, 'https://i.ibb.co/CJBKVd8/mac-main.jpg', TRUE),
      (15, 'https://i.ibb.co/Hd0dWdg/mac.jpg', FALSE),
      (16, 'https://i.ibb.co/XjMMQ79/phone-main.jpg', TRUE),
      (16, 'https://i.ibb.co/ngBQ5d8/phone.jpg', FALSE);

-- Productos para Technology: Computers y Mobile Devices
INSERT INTO Product (name, description, shortDescription, price, discount, stock, rating, brand, length, width, height, weight, categoryId, userId)
VALUES
    ('Dell XPS 15', 'A high-performance laptop with stunning visuals.', 'Professional laptop.', 1499.99, 10, 10, 0, 'Dell', 35, 24, 2, 2, 12, 2), -- Computers
    ('Samsung Galaxy S22 Ultra', 'High-end smartphone with a professional-grade camera.', 'Premium smartphone.', 1199.99, 5, 25, 0, 'Samsung', 16, 8, 0.8, 0.4, 13, 2); -- Mobile Devices

INSERT INTO ProductImage (productId, imageUrl, isPrimary) VALUES
      (17, 'https://i.ibb.co/HVjGwfs/dell-main.jpg', TRUE),
      (17, 'https://i.ibb.co/qdjcXh3/dell.jpg', FALSE),
      (18, 'https://i.ibb.co/TYFK9hf/samsung-main.jpg', TRUE),
      (18, 'https://i.ibb.co/5WzT2BG/samsung.jpg', FALSE);

-- Inserciones en la tabla ProductReview
INSERT INTO ProductReview (productId, userId, rating, comment)
VALUES
    -- Reviews para productos de Movies: Action y Drama
    (1, 1, 5.0, 'An incredible movie with stunning action scenes!'),
    (1, 2, 4.5, 'Really enjoyed it, but it felt a bit long.'),
    (2, 3, 4.8, 'A classic love story that never gets old.'),
    (2, 4, 5.0, 'Absolutely beautiful, a masterpiece!'),

    -- Reviews para productos de Music: Rock y Pop
    (3, 1, 4.9, 'Timeless rock album. Highly recommended!'),
    (3, 2, 5.0, 'The best Led Zeppelin album. Pure genius.'),
    (4, 3, 4.7, 'Great pop vibes, loved the production quality.'),
    (4, 4, 4.8, 'Taylor Swift at her finest. Amazing tracks!'),

    -- Reviews para productos de Books: Fiction y Non-Fiction
    (5, 1, 4.5, 'A beautifully written classic. Enjoyed every page.'),
    (5, 2, 4.0, 'Good story, but a bit slow in the middle.'),
    (6, 3, 4.9, 'Life-changing book! Practical and well-written.'),
    (6, 4, 5.0, 'A must-read for anyone wanting to improve their habits.'),

    -- Reviews para productos de Technology: Computers y Mobile Devices
    (7, 1, 4.8, 'Fantastic laptop, perfect for work and entertainment.'),
    (7, 2, 4.7, 'Great performance, but the battery life could be better.'),
    (8, 3, 4.9, 'One of the best smartphones I have ever used.'),
    (8, 4, 5.0, 'Amazing camera and smooth performance!');

-- Actualización del rating promedio en los productos según las reviews
UPDATE Product
SET rating = (
    SELECT AVG(rating)
    FROM ProductReview
    WHERE ProductReview.productId = Product.id
)
WHERE id IN (1, 2, 3, 4, 5, 6, 7, 8);

-- Inserciones de las órdenes para el usuario johnDoe (id = 1)
-- Orden 1 (Pedido realizado el 1 de noviembre de 2024)
INSERT INTO OrderTable (userId, paymentMethodId, shippingMethodId, userAddressId, date, state)
VALUES
    (1, 1, 1, 1, '2024-11-01 10:00:00', 'CONFIRMED'); -- Estado: Confirmado

-- Detalles de la Orden 1
INSERT INTO OrderItem (orderId, productId, productPrice, quantity)
VALUES
    (1, 1, 12.99, 2),  -- Producto 1 (Ej. Inception)
    (1, 5, 9.99, 1);   -- Producto 5 (Ej. Abbey Road)

-- Orden 2 (Pedido realizado el 5 de noviembre de 2024)
INSERT INTO OrderTable (userId, paymentMethodId, shippingMethodId, userAddressId, date, state)
VALUES
    (1, 2, 2, 2, '2024-11-05 15:30:00', 'IN_TRANSIT'); -- Estado: En tránsito

-- Detalles de la Orden 2
INSERT INTO OrderItem (orderId, productId, productPrice, quantity)
VALUES
    (2, 3, 14.99, 1),  -- Producto 3 (Ej. Avengers: Endgame)
    (2, 7, 15.99, 1),  -- Producto 7 (Ej. Led Zeppelin IV)
    (2, 15, 1999.99, 1); -- Producto 15 (Ej. MacBook Pro)

-- Orden 3 (Pedido realizado el 10 de noviembre de 2024)
INSERT INTO OrderTable (userId, paymentMethodId, shippingMethodId, userAddressId, date, state)
VALUES
    (1, 3, 2, 3, '2024-11-10 12:00:00', 'PRE_ORDER'); -- Estado: Pedido anticipado

-- Detalles de la Orden 3
INSERT INTO OrderItem (orderId, productId, productPrice, quantity)
VALUES
    (3, 2, 9.99, 2),   -- Producto 2 (Ej. The Godfather)
    (3, 6, 19.99, 1),  -- Producto 6 (Ej. Sapiens)
    (3, 9, 10.99, 3);   -- Producto 9 (Ej. To Kill a Mockingbird)

-- Creación del chat entre el usuario 1 (johnDoe) y el usuario 2
INSERT INTO Chat (initiatorUserId, participantUserId, createdAt)
VALUES
    (1, 2, '2024-11-01 12:00:00'); -- Iniciado el 1 de noviembre

-- Creación del chat entre el usuario 1 (johnDoe) y el usuario 3
INSERT INTO Chat (initiatorUserId, participantUserId, createdAt)
VALUES
    (1, 3, '2024-11-05 14:00:00'); -- Iniciado el 5 de noviembre

-- Inserción de mensajes en el chat entre el usuario 1 (johnDoe) y el usuario 2
-- Mensaje 1: Usuario 1 (johnDoe) a usuario 2
INSERT INTO Message (chatId, senderId, content, sentAt)
VALUES
    (1, 1, '¡Hola! ¿Cómo estás?', '2024-11-01 12:01:00');

-- Mensaje 2: Usuario 2 a usuario 1
INSERT INTO Message (chatId, senderId, content, sentAt)
VALUES
    (1, 2, '¡Hola! Estoy bien, ¿y tú?', '2024-11-01 12:02:00');

-- Inserción de mensajes en el chat entre el usuario 1 (johnDoe) y el usuario 3
-- Mensaje 1: Usuario 1 (johnDoe) a usuario 3
INSERT INTO Message (chatId, senderId, content, sentAt)
VALUES
    (2, 1, '¡Buenas! Quería saber más sobre el producto que vendes.', '2024-11-05 14:01:00');

-- Mensaje 2: Usuario 3 a usuario 1
INSERT INTO Message (chatId, senderId, content, sentAt)
VALUES
    (2, 3, '¡Hola! Claro, te cuento más detalles.', '2024-11-05 14:02:00');


-- Reporte de usuario (usuario 1 reporta al usuario 2)
INSERT INTO UserReport (userId, reportedUserId, reportType, description, status, reportedAt)
VALUES
    (1, 2, 'USER', 'Este usuario me está enviando mensajes inapropiados', 'PENDING', '2024-11-01 12:05:00');

-- Reporte de producto (usuario 1 reporta el producto del usuario 2)
INSERT INTO UserReport (userId, reportedUserId, reportedProductId, reportType, description, status, reportedAt)
VALUES
    (1, 2, 3, 'PRODUCT', 'Este producto tiene información falsa', 'PENDING', '2024-11-01 12:10:00');

-- Reporte de usuario (usuario 1 reporta al usuario 3)
INSERT INTO UserReport (userId, reportedUserId, reportType, description, status, reportedAt)
VALUES
    (1, 3, 'USER', 'Este usuario está vendiendo productos defectuosos', 'PENDING', '2024-11-05 14:05:00');
