package dao;

import javax.swing.JOptionPane;

public class DatabaseInit {

    private static final String dropTables
            = """
            DROP TABLE IF EXISTS CartItem;
            DROP TABLE IF EXISTS VoucherUsage;
            DROP TABLE IF EXISTS Voucher;
            DROP TABLE IF EXISTS OrderDetails;
            DROP TABLE IF EXISTS [Order];
            DROP TABLE IF EXISTS Status;
            DROP TABLE IF EXISTS DeliveryInfo;
            DROP TABLE IF EXISTS PaymentInfo;
            DROP TABLE IF EXISTS PaymentMethod;
            DROP TABLE IF EXISTS Staff;
            DROP TABLE IF EXISTS Product;
            DROP TABLE IF EXISTS Category;
            DROP TABLE IF EXISTS [User];
            DROP TABLE IF EXISTS Rank;
            """;

    private static final String userTable
            = """
            CREATE TABLE [User] (
                Id INT PRIMARY KEY IDENTITY(1,1),
                Email VARCHAR(100) UNIQUE NOT NULL,
                Password VARCHAR(100) NOT NULL,
                FullName VARCHAR(100) NOT NULL,
                Sex VARCHAR(10) NOT NULL,
                BirthDate DATE,
                PhoneNumber VARCHAR(12),
                Address VARCHAR(100),
                SecurityQuestion VARCHAR(200) NOT NULL,
                Answer VARCHAR(200) NOT NULL,
                CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
                IsApproved BIT DEFAULT 0,
                Point INT DEFAULT 0,
                Rank INT DEFAULT 1,
                FOREIGN KEY (Rank) REFERENCES Rank(Id)
            );
            INSERT INTO [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, SecurityQuestion, Answer, IsApproved)
            VALUES ('admin', 'admin', 'Admin', 'Null', '1900-01-01', '0123456789', 'Why is dark humor like food?', 'Because not everybody gets it', 1);
            """;

    private static final String rankTable
            = """
            CREATE TABLE Rank (
                Id INT PRIMARY KEY,
                Name VARCHAR(20),
                MinPoint INT
            );
            
            INSERT INTO Rank (Id, Name, MinPoint) 
            VALUES 
                (1, 'Bronze', 0),
                (2, 'Silver', 500),
                (3, 'Gold', 1000),
                (4, 'Platinum', 2000),
                (5, 'Diamond', 5000);
            """;

    private static final String deliveryInfoTable
            = """
            CREATE TABLE DeliveryInfo (
                Id INT PRIMARY KEY IDENTITY(1,1),
                UserId INT NOT NULL,
                RecipientName VARCHAR(100) NOT NULL,
                PhoneNumber VARCHAR(12) NOT NULL,
                Address VARCHAR(100) NOT NULL,
                FOREIGN KEY (UserId) REFERENCES [User](Id) ON DELETE CASCADE
            );
            """;

    private static final String paymentInfoTable
            = """
            CREATE TABLE PaymentInfo (
                Id INT PRIMARY KEY IDENTITY(1,1),
                UserId INT NOT NULL,
                CardNumber VARCHAR(20) NOT NULL,
                ExpMonth INT NOT NULL,
                ExpYear INT NOT NULL,
                SecurityCode VARCHAR(10) NOT NULL,
                OwnerName VARCHAR(100),
                BillingAddress1 VARCHAR(100),
                BillingAddress2 VARCHAR(100),
                City VARCHAR(50),
                ZipCode VARCHAR(6),
                Country VARCHAR(50),
                FOREIGN KEY (UserId) REFERENCES [User](Id) ON DELETE CASCADE
            );
            """;

    private static final String paymentMethodTable
            = """
            CREATE TABLE PaymentMethod (
                Id INT PRIMARY KEY,
                Name VARCHAR(50)
            );
            INSERT INTO PaymentMethod (Id, Name) VALUES (1, 'Credit card');
            INSERT INTO PaymentMethod (Id, Name) VALUES (2, 'Cash on delivery');
            """;

    private static final String categoryTable
            = """
            CREATE TABLE Category (
                Id INT PRIMARY KEY IDENTITY(1,1),
                Name VARCHAR(50) NOT NULL
            );
              
            INSERT INTO Category (Name)
            VALUES
                ('Beverages'),
                ('Pastries'),
                ('Sandwiches'),
                ('Salads'),
                ('Desserts'),
                ('Smoothies'),
                ('Breakfast'),
                ('Wraps'),
                ('Soup');
            """;

    private static final String productTable
            = """
            CREATE TABLE Product (
                Id INT PRIMARY KEY IDENTITY(1,1),
                Name VARCHAR(50) NOT NULL,
                CategoryId INT NOT NULL,
                Price DECIMAL(10,2) NOT NULL,
                FOREIGN KEY (CategoryId) REFERENCES Category(Id)
            );
            INSERT INTO Product (Name, Price, CategoryId)
            VALUES
                ('Espresso', 2.50, 1),
                ('Cappuccino', 3.00, 1),
                ('Latte', 3.50, 1),
                ('Croissant', 2.00, 2),
                ('Chocolate Muffin', 2.50, 2),
                ('Ham and Cheese', 4.50, 3),
                ('Club Sandwich', 5.50, 3),
                ('Caesar Salad', 6.00, 4),
                ('Greek Salad', 5.50, 4),
                ('Cheesecake', 4.50, 5),
                ('Apple Pie', 3.50, 5),
                ('Strawberry Smoothie', 4.00, 6),
                ('Mango Madness Smoothie', 4.50, 6),
                ('Pancakes', 5.50, 7),
                ('Omelette', 6.00, 7),
                ('Chicken Wrap', 5.50, 8),
                ('Vegetable Wrap', 4.50, 8),
                ('Tomato Soup', 3.50, 9),
                ('Mushroom Soup', 3.00, 9),
                ('Iced Tea', 2.50, 1),
                ('Fruit Salad', 4.50, 5),
                ('Blueberry Muffin', 2.50, 2),
                ('Tuna Sandwich', 5.50, 3),
                ('Chicken Caesar Salad', 6.50, 4),
                ('Brownie', 3.00, 5),
                ('Banana Smoothie', 4.00, 6),
                ('Bagel', 2.50, 7),
                ('Turkey Wrap', 5.50, 8),
                ('Minestrone Soup', 3.50, 9),
                ('Frappe', 4.50, 1),
                ('Donut', 1.50, 2),
                ('Grilled Cheese Sandwich', 4.00, 3),
                ('Cobb Salad', 7.00, 4),
                ('Mango Tango Smoothie', 5.50, 6),
                ('French Toast', 5.00, 7),
                ('Veggie Wrap', 4.50, 8),
                ('Lentil Soup', 3.00, 9),
                ('Mocha', 3.50, 1),
                ('Cherry Pie', 4.00, 5),
                ('Blueberry Smoothie', 4.50, 6),
                ('Scone', 2.00, 2),
                ('Roast Beef Sandwich', 6.00, 3),
                ('Caprese Salad', 5.50, 4),
                ('Ice Cream Sundae', 5.50, 5),
                ('Green Smoothie', 4.00, 6),
                ('Egg Sandwich', 4.50, 7),
                ('Chicken Caesar Wrap', 6.50, 8),
                ('Clam Chowder', 4.50, 9),
                ('Hot Chocolate', 3.00, 1),
                ('Lemon Tart', 3.50, 5);
            """;

    private static final String orderTable
            = """
            CREATE TABLE [Order] (
                Id INT PRIMARY KEY IDENTITY(1,1),
                UserId INT NOT NULL,
                CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
                TotalCost DECIMAL(10,2) NOT NULL,
                ShipCost DECIMAL(10,2),
                Discount DECIMAL(10,2),
                FinalCost AS (TotalCost + ShipCost - Discount),
                DeliveryInfoId INT NOT NULL,
                PaymentMethod INT NOT NULL,
                PaymentInfoId INT,
                Note VARCHAR(100),
                ShipperId INT,
                StatusId INT,
                FOREIGN KEY (UserId) REFERENCES [User](Id) ON DELETE CASCADE,
                FOREIGN KEY (DeliveryInfoId) REFERENCES DeliveryInfo(Id),
                FOREIGN KEY (PaymentMethod) REFERENCES PaymentMethod(Id),
                FOREIGN KEY (PaymentInfoId) REFERENCES PaymentInfo(Id),
                FOREIGN KEY (ShipperId) REFERENCES Staff(Id) ON DELETE SET NULL,
                FOREIGN KEY (StatusId) REFERENCES Status(Id)
            );
            """;

    private static final String orderDetailsTable
            = """
            CREATE TABLE OrderDetails (
                Id INT PRIMARY KEY IDENTITY(1,1),
                OrderId INT NOT NULL,
                ProductId INT,
                Quantity INT NOT NULL,
                UnitPrice DECIMAL(10,2),
                TotalAmount AS (UnitPrice * Quantity),
                FOREIGN KEY (OrderId) REFERENCES [Order](Id) ON DELETE CASCADE,
                FOREIGN KEY (ProductId) REFERENCES Product(Id) ON DELETE SET NULL
            );
            """;

    private static final String staffTable
            = """
            CREATE TABLE Staff (
                Id INT PRIMARY KEY IDENTITY(1,1),
                FullName VARCHAR(100) NOT NULL,
                Sex VARCHAR(10) NOT NULL,
                BirthDate DATE NOT NULL,
                PhoneNumber VARCHAR(12),
                Position VARCHAR(20),
                StartDate DATE,
                EndDate DATE,
                MonthlySalary DECIMAL(10,2)
            );
            """;

    private static final String voucherTable
            = """
            CREATE TABLE Voucher (
                Id INT PRIMARY KEY IDENTITY(1,1),
                MinRank INT,
                MinCost DECIMAL(10,2),
                DiscountPercentage DECIMAL(10,2),
                MaxDiscountAmount DECIMAL(10,2),
                ExpDate DATE NOT NULL,
                FOREIGN KEY (MinRank) REFERENCES Rank(Id) ON DELETE CASCADE
            );
            """;

    private static final String voucherUsageTable
            = """
            CREATE TABLE VoucherUsage (
                Id INT PRIMARY KEY IDENTITY(1,1),
                VoucherId INT NOT NULL,
                UserId INT NOT NULL,
                OrderId INT
                FOREIGN KEY (VoucherId) REFERENCES Voucher(Id) ON DELETE CASCADE,
                FOREIGN KEY (UserId) REFERENCES [User](Id) ON DELETE CASCADE,
                FOREIGN KEY (OrderId) REFERENCES [Order](Id),
            );
            """;

    private static final String cartItemTable
            = """
            CREATE TABLE CartItem (
                Id INT PRIMARY KEY IDENTITY(1,1),
                UserId INT NOT NULL,
                ProductId INT NOT NULL,
                Quantity INT NOT NULL,
                FOREIGN KEY (UserId) REFERENCES [User](Id) ON DELETE CASCADE,
                FOREIGN KEY (ProductId) REFERENCES Product(Id) ON DELETE CASCADE,
            );
            """;

    private static final String statusTable
            = """
            CREATE TABLE Status (
                Id INT PRIMARY KEY,
                Value VARCHAR(50)
            );
            INSERT INTO Status (Id, Value) VALUES (1, 'Received'), (2, 'Delivering'), (3, 'Delivered');
            """;

    private static final String altRankTrigger = 
            """
            CREATE TRIGGER tg_AltRank ON Rank INSTEAD OF DELETE
            AS
            IF (SELECT Id FROM deleted) = 1
            BEGIN
                    PRINT 'Cannot delete the lowest rank'
                    ROLLBACK
            END
            ELSE
            BEGIN
                    UPDATE [User] SET Rank = Rank - 1
                    WHERE Rank = (SELECT Id FROM deleted);
                    DELETE FROM Rank WHERE Id = (SELECT Id FROM deleted);
            END;
            """;
    
    private static final String delCategoryTrigger =
            """
            CREATE TRIGGER tg_DelCategory ON Category INSTEAD OF DELETE
            AS
            IF (SELECT COUNT(*) FROM Product WHERE CategoryId = (SELECT Id FROM deleted)) > 0
            BEGIN
              IF (SELECT COUNT(*) FROM Category WHERE Name = 'None') = 0
                      INSERT INTO Category (Name) VALUES ('None');
              UPDATE Product SET CategoryId = (SELECT Id FROM Category WHERE Name = 'None')
              WHERE CategoryId = (SELECT Id FROM deleted);
            END
            """;
    
    private static final String incUserPointTrigger = 
            """
            CREATE TRIGGER tg_IncUserPoint ON [Order] FOR INSERT
            AS
            UPDATE u SET u.Point = u.Point + CAST(ROUND(o.FinalCost, 0) AS INT)
            FROM [User] u INNER JOIN inserted o ON u.Id = o.UserId;
            UPDATE u SET u.Rank = u.Rank + 1
            FROM [User] u
            WHERE u.Rank <> (SELECT Id FROM Rank r WHERE r.MinPoint = (SELECT MAX(MinPoint) FROM Rank))
            AND u.Point >= (SELECT MinPoint FROM Rank r WHERE r.Id = u.Rank + 1);
            """;
    
    private static final String giveVoucherTrigger = 
            """
            CREATE TRIGGER tg_GiveVoucher ON Voucher FOR INSERT
            AS
            INSERT INTO VoucherUsage (VoucherId, UserId, OrderId)
            SELECT v.Id, u.Id, NULL
            FROM [User] u, inserted v
            WHERE u.Rank >= v.MinRank;
            """;
    
    private static final String defaultDeliveryInfoTrigger = 
            """
            CREATE TRIGGER tg_DefaultDeliveryInfo ON [User] FOR INSERT
            AS
            INSERT INTO DeliveryInfo (UserId, RecipientName, PhoneNumber, Address)
            SELECT Id, FullName, PhoneNumber, Address FROM inserted;
            """;
    
    public static void main(String[] args) {
        try {
//            String userTable = " CREATE TABLE [user] " + "( id INT IDENTITY(1,1) PRIMARY KEY," + "  name VARCHAR(200),"
//					+ "  email VARCHAR(200) UNIQUE," + "  mobilenumber VARCHAR(200)," + "  address VARCHAR(200),"
//					+ " password VARCHAR(200)," + " securityQuestion VARCHAR(200)," + " answer VARCHAR(200),"+"status VARCHAR(200) )";
//			DbOperations.updateData(userTable, "Create Table successfully");

//                        String adminDetails = "INSERT INTO [user] (name,email,mobilenumber,address,password,securityQuestion,answer,status) "
//                                + " VALUES('admin','admin@gmail.com','0999999999','Earth','12345678','Biet bo may la ai khong?','Admin','true')";
//                        String productTable = "drop if exists table product; create table product(id int IDENTITY(1,1) primary key,name varchar(200),category varchar(200),price decimal(10,2))";
//                        String categoryTable = "create table category (id int IDENTITY(1,1) primary key,name varchar(200))";
//            String billTable = "DROP IF EXISTS TABLE bill; "
//            		+ "CREATE TABLE bill ( "
//                    + "id INT PRIMARY KEY, "
//                    + "name VARCHAR(200), "
//                    + "mobileNumber VARCHAR(200), "
//                    + "email VARCHAR(200), "
//                    + "date DATE, "
//                    + "total DECIMAL(10,2), "
//                    + "createdBy VARCHAR(200) )";
//                        DbOperations.updateData(adminDetails, " Add admin details successfully");
//                        DbOperations.updateData(categoryTable, " Category Table Created successfully");
//                        DbOperations.updateData(productTable, " Product Table Created successfully");
//            DbOperations.updateData(billTable, "Bill table created successfully");
            DbOperations.updateData(dropTables, "Tables dropped successfully");
            DbOperations.updateData(rankTable, "");
            DbOperations.updateData(userTable, "");
            DbOperations.updateData(staffTable, "");
            DbOperations.updateData(categoryTable, "");
            DbOperations.updateData(productTable, "");
            DbOperations.updateData(deliveryInfoTable, "");
            DbOperations.updateData(paymentMethodTable, "");
            DbOperations.updateData(paymentInfoTable, "");
            DbOperations.updateData(statusTable, "");
            DbOperations.updateData(orderTable, "");
            DbOperations.updateData(orderDetailsTable, "");
            DbOperations.updateData(voucherTable, "");
            DbOperations.updateData(voucherUsageTable, "");
            DbOperations.updateData(cartItemTable, "Tables created successfully");
            DbOperations.updateData(defaultDeliveryInfoTrigger, "");
            DbOperations.updateData(altRankTrigger, "");
            DbOperations.updateData(delCategoryTrigger, "");
            DbOperations.updateData(incUserPointTrigger, "");
            DbOperations.updateData(giveVoucherTrigger, "Triggers created successfully");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
    }
}
