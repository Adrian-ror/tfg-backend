-- Drop tables in reverse dependency order to avoid issues with foreign key constraints
DROP INDEX UserIndexByUserName ON User;
DROP INDEX CategoryIndexByName ON Category;
DROP INDEX ProductIndexByName ON Product;

DROP TABLE IF EXISTS
    WishListItem,
    WishList,
    OrderItem,
    OrderTable,
    ShoppingCartItem,
    ShoppingCart,
    ProductImage,
    ProductReview,
    UserReport,
    Product,
    Category,
    PaymentMethod,
    Message,
    Chat,
    UserAddress,
    ShippingMethod,
    User
    CASCADE;

-- User table
CREATE TABLE IF NOT EXISTS User (
        id BIGINT NOT NULL AUTO_INCREMENT,
        userName VARCHAR(60) COLLATE latin1_bin NOT NULL,
        password VARCHAR(60) NOT NULL,
        firstName VARCHAR(60) NOT NULL,
        lastName VARCHAR(60) NOT NULL,
        email VARCHAR(60) NOT NULL,
        phoneNumber VARCHAR(20) DEFAULT NULL,
        image TEXT,
        status ENUM('ACTIVE', 'BANNED') DEFAULT 'ACTIVE',
        role ENUM('CLIENT', 'PROVIDER', 'ADMIN') NOT NULL,
        PRIMARY KEY (id),
        UNIQUE KEY UserNameUniqueKey (userName)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX UserIndexByUserName ON User (userName);


-- Chat table
CREATE TABLE IF NOT EXISTS Chat (
        id BIGINT NOT NULL AUTO_INCREMENT,
        initiatorUserId BIGINT NOT NULL,
        participantUserId BIGINT NOT NULL,
        createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (id),
        FOREIGN KEY (initiatorUserId) REFERENCES User(id),
        FOREIGN KEY (participantUserId) REFERENCES User(id),
        UNIQUE KEY UniqueChat (initiatorUserId, participantUserId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Message table
CREATE TABLE IF NOT EXISTS Message (
       id BIGINT NOT NULL AUTO_INCREMENT,
       chatId BIGINT NOT NULL,
       senderId BIGINT NOT NULL,
       content TEXT NOT NULL,
       sentAt DATETIME DEFAULT CURRENT_TIMESTAMP,
       PRIMARY KEY (id),
       FOREIGN KEY (chatId) REFERENCES Chat(id) ON DELETE CASCADE,
       FOREIGN KEY (senderId) REFERENCES User(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Category table
CREATE TABLE IF NOT EXISTS Category (
        id BIGINT NOT NULL AUTO_INCREMENT,
        name VARCHAR(60) NOT NULL,
        parent_category_id BIGINT DEFAULT NULL,
        PRIMARY KEY (id),
        UNIQUE KEY CategoryNameUniqueKey (name),
        FOREIGN KEY (parent_category_id) REFERENCES Category (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX CategoryIndexByName ON Category (name);

-- Product table
CREATE TABLE IF NOT EXISTS Product (
        id BIGINT NOT NULL AUTO_INCREMENT,
        name VARCHAR(60) NOT NULL,
        description VARCHAR(2000) NOT NULL,
        shortDescription VARCHAR(255) DEFAULT NULL,
        price DECIMAL(11, 2) NOT NULL,
        discount DECIMAL(11, 2) DEFAULT NULL,
        createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        stock INT NOT NULL DEFAULT 0,
        rating DECIMAL(3, 2) DEFAULT NULL,
        brand VARCHAR(100) DEFAULT NULL,
        length DECIMAL(10, 2) DEFAULT NULL,
        width DECIMAL(10, 2) DEFAULT NULL,
        height DECIMAL(10, 2) DEFAULT NULL,
        weight DECIMAL(10, 2) DEFAULT NULL,
        isVisible BOOLEAN DEFAULT TRUE,
        isService BOOLEAN DEFAULT FALSE,
        categoryId BIGINT NOT NULL,
        userId BIGINT NOT NULL,
        PRIMARY KEY (id),
        FOREIGN KEY (categoryId) REFERENCES Category (id),
        FOREIGN KEY (userId) REFERENCES User (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX ProductIndexByName ON Product (name);

-- ProductImage table
CREATE TABLE IF NOT EXISTS ProductImage (
        id BIGINT NOT NULL AUTO_INCREMENT,
        productId BIGINT NOT NULL,
        imageUrl TEXT NOT NULL,
        isPrimary BOOLEAN DEFAULT FALSE,
        PRIMARY KEY (id),
        FOREIGN KEY (productId) REFERENCES Product (id) ON DELETE CASCADE,
        UNIQUE KEY UniqueProductImage (productId, imageUrl(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ProductReview table
CREATE TABLE IF NOT EXISTS ProductReview (
         id BIGINT NOT NULL AUTO_INCREMENT,
         productId BIGINT NOT NULL,
         userId BIGINT NOT NULL,
         rating DECIMAL(3, 2) DEFAULT NULL,
         comment TEXT,
         reviewDate DATETIME DEFAULT CURRENT_TIMESTAMP,
             PRIMARY KEY (id),
                 FOREIGN KEY (productId) REFERENCES Product(id) ON DELETE CASCADE,
                 FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- UserReport table
CREATE TABLE IF NOT EXISTS UserReport (
      id BIGINT NOT NULL AUTO_INCREMENT,
      userId BIGINT NOT NULL,
      reportedProductId BIGINT DEFAULT NULL,
      reportedUserId BIGINT DEFAULT NULL,
      reportType ENUM('PRODUCT', 'USER') NOT NULL,
      description TEXT NOT NULL,
      status ENUM('PENDING', 'IN_PROGRESS', 'RESOLVED', 'REJECTED') DEFAULT 'PENDING',
      reportedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
          PRIMARY KEY (id),
              FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE,
              FOREIGN KEY (reportedProductId) REFERENCES Product(id) ON DELETE SET NULL,
              FOREIGN KEY (reportedUserId) REFERENCES User(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ShoppingCart table
CREATE TABLE IF NOT EXISTS ShoppingCart (
        id BIGINT NOT NULL AUTO_INCREMENT,
        userId BIGINT NOT NULL,
        PRIMARY KEY (id),
        FOREIGN KEY (userId) REFERENCES User (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ShoppingCartItem table
CREATE TABLE IF NOT EXISTS ShoppingCartItem (
        id BIGINT NOT NULL AUTO_INCREMENT,
        productId BIGINT NOT NULL,
        quantity SMALLINT NOT NULL,
        shoppingCartId BIGINT NOT NULL,
        PRIMARY KEY (id),
        FOREIGN KEY (productId) REFERENCES Product (id),
        FOREIGN KEY (shoppingCartId) REFERENCES ShoppingCart (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- PaymentMethod table
CREATE TABLE IF NOT EXISTS PaymentMethod (
         id BIGINT NOT NULL AUTO_INCREMENT,
         userId BIGINT NOT NULL,
         stripeId VARCHAR(255) NOT NULL,
         brand VARCHAR(50),
         country VARCHAR(2),
         exp_month INT NOT NULL,
         exp_year INT NOT NULL,
         last4 VARCHAR(4) NOT NULL,
         funding VARCHAR(20),
         fingerprint VARCHAR(255),
         byDefault TINYINT NOT NULL DEFAULT 0,
         PRIMARY KEY (id),
         FOREIGN KEY (userId) REFERENCES User(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ShippingMethod table
CREATE TABLE IF NOT EXISTS ShippingMethod (
          id BIGINT NOT NULL AUTO_INCREMENT,
          name VARCHAR(100) NOT NULL,
          description VARCHAR(255) DEFAULT NULL,
          shippingCost DECIMAL(11, 2) NOT NULL,
          estimatedDeliveryTime VARCHAR(50) DEFAULT NULL,
          PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- UserAddress table
CREATE TABLE IF NOT EXISTS UserAddress (
           id BIGINT NOT NULL AUTO_INCREMENT,
           userId BIGINT NOT NULL,
           addressLine1 VARCHAR(200) NOT NULL,
           addressLine2 VARCHAR(200) DEFAULT NULL,
           city VARCHAR(100) NOT NULL,
           state VARCHAR(100) NOT NULL,
           postalCode VARCHAR(20) NOT NULL,
           country VARCHAR(100) NOT NULL,
           phoneNumber VARCHAR(20) DEFAULT NULL,
           isDefault TINYINT NOT NULL DEFAULT 0,
           PRIMARY KEY (id),
           FOREIGN KEY (userId) REFERENCES User (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- OrderTable table
CREATE TABLE IF NOT EXISTS OrderTable (
          id BIGINT NOT NULL AUTO_INCREMENT,
          userId BIGINT NOT NULL,
          paymentMethodId BIGINT NOT NULL,
          shippingMethodId BIGINT NOT NULL,
          userAddressId BIGINT NOT NULL,
          date DATETIME NOT NULL,
          state ENUM('PRE_ORDER', 'IN_TRANSIT', 'CONFIRMED', 'CANCELLED') NOT NULL,
          PRIMARY KEY (id),
          FOREIGN KEY (paymentMethodId) REFERENCES PaymentMethod (id),
          FOREIGN KEY (userId) REFERENCES User (id),
          FOREIGN KEY (shippingMethodId) REFERENCES ShippingMethod (id),
          FOREIGN KEY (userAddressId) REFERENCES UserAddress (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- OrderItem table
CREATE TABLE IF NOT EXISTS OrderItem (
         id BIGINT NOT NULL AUTO_INCREMENT,
         productId BIGINT NOT NULL,
         productPrice DECIMAL(11, 2) NOT NULL,
         quantity SMALLINT NOT NULL,
         orderId BIGINT NOT NULL,
         PRIMARY KEY (id),
         FOREIGN KEY (productId) REFERENCES Product (id),
         FOREIGN KEY (orderId) REFERENCES OrderTable (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Wishlist table
CREATE TABLE IF NOT EXISTS WishList (
        id BIGINT NOT NULL AUTO_INCREMENT,
        userId BIGINT NOT NULL,
        PRIMARY KEY (id),
        FOREIGN KEY (userId) REFERENCES User (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- WishlistItem table
CREATE TABLE IF NOT EXISTS WishListItem (
        id BIGINT NOT NULL AUTO_INCREMENT,
        productId BIGINT NOT NULL,
        addedDate DATETIME DEFAULT CURRENT_TIMESTAMP,
        wishlistId BIGINT NOT NULL,
        PRIMARY KEY (id),
        FOREIGN KEY (productId) REFERENCES Product (id),
        FOREIGN KEY (wishlistId) REFERENCES WishList (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
