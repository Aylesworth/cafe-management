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
            INSERT INTO [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer, IsApproved)
            VALUES ('admin@email.com', 'admin', 'Admin', 'Null', '1900-01-01', '0123456789', 'None', 'Why is dark humor like food?', 'Because not everybody gets it', 1);
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
            INSERT INTO Status (Id, Value) VALUES (1, 'Pending'), (2, 'Accepted'), (3, 'Delivering'), (4, 'Delivered');
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
                DELETE FROM Category WHERE Id = (SELECT Id FROM deleted);
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
    
    private static final String randomUsers = 
            """
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('vperris0@guardian.co.uk', 'vperris0@walmart.com', 'Vernice Perris', 'Female', '2019-12-17', '2263941387', '7886 Bashford Alley', 'Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo. Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis.', 'Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('dcourtier1@last.fm', 'dcourtier1@pcworld.com', 'Dun Courtier', 'Male', '2023-04-14', '7697049661', '59 Moulton Plaza', 'In quis justo. Maecenas rhoncus aliquam lacus. Morbi quis tortor id nulla ultrices aliquet. Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam.', 'Morbi ut odio.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('pcasley2@admin.ch', 'pcasley2@usatoday.com', 'Pascale Casley', 'Male', '2005-08-26', '8301363615', '70 Thackeray Center', 'Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est.', 'Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem. Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('dfitzsimmons3@indiegogo.com', 'dfitzsimmons3@goo.gl', 'Daryl Fitzsimmons', 'Female', '2012-01-29', '5755602257', '65 Packers Place', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus. Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio.', 'Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est. Phasellus sit amet erat. Nulla tempus.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('acamlin4@nymag.com', 'acamlin4@addthis.com', 'Alard Camlin', 'Male', '2018-06-10', '9309847348', '6 Talmadge Avenue', 'Nulla facilisi.', 'Vivamus vel nulla eget eros elementum pellentesque.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('phonywill5@theatlantic.com', 'phonywill5@ameblo.jp', 'Pincus Honywill', 'Male', '2002-06-22', '8079850278', '46 Reindahl Drive', 'Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero. Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh. In quis justo. Maecenas rhoncus aliquam lacus.', 'Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('eheymann6@theguardian.com', 'eheymann6@blogger.com', 'Everett Heymann', 'Male', '2000-05-25', '6321209945', '70 Lerdahl Avenue', 'Praesent lectus. Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis. Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor.', 'Morbi a ipsum. Integer a nibh. In quis justo. Maecenas rhoncus aliquam lacus.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('tmarcroft7@xinhuanet.com', 'tmarcroft7@paypal.com', 'Talyah Marcroft', 'Female', '2006-06-12', '6225654395', '37718 Kingsford Crossing', 'Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci. Nullam molestie nibh in lectus. Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien.', 'In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi. Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('bklimpt8@independent.co.uk', 'bklimpt8@google.com', 'Barbey Klimpt', 'Female', '2007-06-16', '5161955911', '07 Vera Junction', 'Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat. Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula.', 'Suspendisse accumsan tortor quis turpis. Sed ante. Vivamus tortor. Duis mattis egestas metus. Aenean fermentum.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('jmitchall9@barnesandnoble.com', 'jmitchall9@g.co', 'Justinian Mitchall', 'Male', '2014-11-12', '8907615586', '63 Warner Junction', 'Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus. Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst. Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem.', 'Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero. Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('ctrowa@bbb.org', 'ctrowa@pcworld.com', 'Cesar Trow', 'Male', '2015-05-27', '8429228160', '3866 Red Cloud Crossing', 'Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci.', 'Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst. Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat. Curabitur gravida nisi at nibh. In hac habitasse platea dictumst.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('lbelliveaub@redcross.org', 'lbelliveaub@deliciousdays.com', 'Leo Belliveau', 'Male', '2013-04-04', '6475965525', '6 Manley Street', 'Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris. Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis. Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl.', 'In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem. Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque. Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('kbaracloughc@reddit.com', 'kbaracloughc@mayoclinic.com', 'Kristal Baraclough', 'Female', '2021-05-29', '9164096144', '716 Memorial Park', 'Aliquam erat volutpat. In congue. Etiam justo. Etiam pretium iaculis justo.', 'Maecenas rhoncus aliquam lacus.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('prollittd@cloudflare.com', 'prollittd@biglobe.ne.jp', 'Pepi Rollitt', 'Female', '2011-05-30', '3801343192', '99 Fremont Center', 'In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi. Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque.', 'Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('jrobardete@census.gov', 'jrobardete@friendfeed.com', 'Jacquelyn Robardet', 'Female', '2000-09-15', '5677634071', '48 Merchant Pass', 'Etiam faucibus cursus urna. Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi. Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque. Quisque porta volutpat erat.', 'Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('idibnahf@redcross.org', 'idibnahf@hhs.gov', 'Irwin Dibnah', 'Male', '2004-12-17', '1012054564', '5686 Carpenter Place', 'Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo.', 'Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est. Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum. Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('dmorrellg@marketwatch.com', 'dmorrellg@aol.com', 'Denys Morrell', 'Male', '2009-03-11', '6182923500', '9831 Hoepker Parkway', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque. Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus. Suspendisse potenti.', 'Cras in purus eu magna vulputate luctus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('lstollmanh@ustream.tv', 'lstollmanh@flickr.com', 'Lela Stollman', 'Female', '2003-01-16', '2377283554', '55907 Burning Wood Hill', 'In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl. Aenean lectus.', 'Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh. In quis justo. Maecenas rhoncus aliquam lacus. Morbi quis tortor id nulla ultrices aliquet. Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui. Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('acristofoloi@reuters.com', 'acristofoloi@acquirethisname.com', 'Aleksandr Cristofolo', 'Male', '2009-05-09', '3689546104', '3 Dunning Alley', 'Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat. Praesent blandit. Nam nulla.', 'Pellentesque ultrices mattis odio. Donec vitae nisi.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('ulanganj@yelp.com', 'ulanganj@goodreads.com', 'Ursola Langan', 'Female', '2000-11-03', '1821737888', '612 Dennis Plaza', 'Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui.', 'Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('nhendricksonk@fema.gov', 'nhendricksonk@statcounter.com', 'Nicoli Hendrickson', 'Female', '2009-08-15', '2316120802', '7607 Melrose Park', 'Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum. Curabitur in libero ut massa volutpat convallis.', 'Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam vel augue.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('ipuesl@usa.gov', 'ipuesl@eventbrite.com', 'Irvine Pues', 'Male', '2014-09-06', '1038071775', '21 Monica Trail', 'Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.', 'Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus. Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('lfreegardm@biglobe.ne.jp', 'lfreegardm@printfriendly.com', 'Lauryn Freegard', 'Female', '2001-01-06', '4516420724', '25 Upham Way', 'Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis.', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque. Duis bibendum.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('itaylotn@soup.io', 'itaylotn@nba.com', 'Ivan Taylot', 'Male', '2022-09-05', '5762042908', '39 Golf Drive', 'Aenean sit amet justo. Morbi ut odio. Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo.', 'Duis at velit eu est congue elementum. In hac habitasse platea dictumst.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('mmerryo@parallels.com', 'mmerryo@msu.edu', 'Maighdiln Merry', 'Female', '2002-10-27', '8817016199', '98791 Kingsford Terrace', 'Duis at velit eu est congue elementum. In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo. Aliquam quis turpis eget elit sodales scelerisque.', 'Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros. Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat. In congue. Etiam justo. Etiam pretium iaculis justo.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('erodbournep@nifty.com', 'erodbournep@cbc.ca', 'Eben Rodbourne', 'Male', '2010-10-17', '1595925845', '47 Riverside Place', 'In hac habitasse platea dictumst.', 'Vestibulum sed magna at nunc commodo placerat.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('mrapinettq@arizona.edu', 'mrapinettq@amazonaws.com', 'Mile Rapinett', 'Male', '2015-06-22', '9194793845', '01847 Northridge Center', 'Vivamus in felis eu sapien cursus vestibulum.', 'Proin leo odio, porttitor id, consequat in, consequat ut, nulla. Sed accumsan felis.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('ckernerr@1und1.de', 'ckernerr@list-manage.com', 'Catherine Kerner', 'Female', '2014-03-04', '3198029267', '2536 Melvin Trail', 'Etiam pretium iaculis justo. In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi.', 'Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem. Praesent id massa id nisl venenatis lacinia.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('nallens@chicagotribune.com', 'nallens@webs.com', 'Nancie Allen', 'Female', '2011-11-03', '5419104339', '39816 Colorado Point', 'Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus.', 'Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem. Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio. Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('bwalthallt@mapy.cz', 'bwalthallt@ft.com', 'Bernarr Walthall', 'Male', '2001-03-27', '2728060788', '88876 Mockingbird Place', 'Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis. Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl.', 'Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('lcornehlu@shutterfly.com', 'lcornehlu@exblog.jp', 'Leigha Cornehl', 'Female', '2000-04-23', '3488879566', '7282 Farwell Court', 'Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus. Suspendisse potenti.', 'Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui. Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('dconiffv@apple.com', 'dconiffv@mlb.com', 'Diandra Coniff', 'Female', '2005-12-18', '5123761510', '3563 Barby Junction', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl. Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum. Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo.', 'Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat. Nulla nisl. Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('jdowlingw@aboutads.info', 'jdowlingw@php.net', 'Jenilee Dowling', 'Female', '2020-02-21', '1281413525', '2148 Swallow Court', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti. Nullam porttitor lacus at turpis.', 'Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui. Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti. Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris. Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('wboldenx@cargocollective.com', 'wboldenx@cloudflare.com', 'Wald Bolden', 'Male', '2005-12-15', '4854873104', '34668 Manitowish Lane', 'In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem. Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat. Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus.', 'Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat. Nulla nisl. Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('bwheelhousey@plala.or.jp', 'bwheelhousey@economist.com', 'Berkie Wheelhouse', 'Male', '2017-01-15', '8162708956', '36523 Springview Way', 'Quisque ut erat. Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem. Integer tincidunt ante vel ipsum.', 'Nulla nisl. Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo. Aliquam quis turpis eget elit sodales scelerisque.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('swayonz@vk.com', 'swayonz@house.gov', 'Sissie Wayon', 'Female', '2014-12-12', '1582115202', '5317 Helena Way', 'Donec quis orci eget orci vehicula condimentum. Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo.', 'Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero. Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('slis10@oracle.com', 'slis10@cnbc.com', 'Solly Lis', 'Male', '2021-10-13', '3668971862', '469 Messerschmidt Plaza', 'Vivamus vel nulla eget eros elementum pellentesque. Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla. Nunc purus. Phasellus in felis. Donec semper sapien a libero.', 'Donec semper sapien a libero. Nam dui. Proin leo odio, porttitor id, consequat in, consequat ut, nulla.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('dpickless11@addthis.com', 'dpickless11@nyu.edu', 'Dominick Pickless', 'Male', '2000-05-14', '1384125556', '35 Shopko Point', 'Nullam molestie nibh in lectus. Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam vel augue. Vestibulum rutrum rutrum neque.', 'Nunc rhoncus dui vel sem.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('pcollimore12@mlb.com', 'pcollimore12@linkedin.com', 'Poppy Collimore', 'Female', '2002-07-28', '1585302000', '3123 Mcbride Trail', 'Nullam sit amet turpis elementum ligula vehicula consequat.', 'Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi. Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('mellcome13@aol.com', 'mellcome13@columbia.edu', 'Merci Ellcome', 'Female', '2002-10-21', '3131001363', '2 Elmside Point', 'Curabitur convallis.', 'Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('jphalip14@paypal.com', 'jphalip14@dmoz.org', 'Jone Phalip', 'Male', '2008-12-21', '1922779744', '51 Westerfield Trail', 'Cras pellentesque volutpat dui. Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti. Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum.', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros. Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('cgibbin15@rambler.ru', 'cgibbin15@ucla.edu', 'Carlos Gibbin', 'Male', '2001-11-23', '2523963705', '0585 Texas Park', 'Morbi a ipsum. Integer a nibh. In quis justo. Maecenas rhoncus aliquam lacus.', 'Aenean fermentum. Donec ut mauris eget massa tempor convallis.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('dsergent16@bbc.co.uk', 'dsergent16@theatlantic.com', 'Delila Sergent', 'Female', '2018-07-28', '2887318519', '4891 Darwin Way', 'Suspendisse accumsan tortor quis turpis. Sed ante. Vivamus tortor. Duis mattis egestas metus. Aenean fermentum. Donec ut mauris eget massa tempor convallis.', 'Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis. Sed ante. Vivamus tortor. Duis mattis egestas metus. Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh. Quisque id justo sit amet sapien dignissim vestibulum.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('ebrannigan17@illinois.edu', 'ebrannigan17@nbcnews.com', 'Emelda Brannigan', 'Female', '2022-08-05', '3353033948', '812 Clarendon Way', 'Curabitur convallis. Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus. Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.', 'Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat. Nulla nisl. Nunc nisl.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('mbyrch18@artisteer.com', 'mbyrch18@gov.uk', 'Madelle Byrch', 'Female', '2011-05-20', '3184100519', '60 Logan Junction', 'Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat. Nulla nisl. Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus.', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin risus. Praesent lectus. Vestibulum quam sapien, varius ut, blandit non, interdum in, ante. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('truck19@fastcompany.com', 'truck19@umich.edu', 'Torey Ruck', 'Male', '2002-02-14', '5028664674', '6133 Huxley Court', 'Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat. Nulla nisl. Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante.', 'Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('rlilford1a@usda.gov', 'rlilford1a@discovery.com', 'Rolando Lilford', 'Male', '2013-07-07', '3799109166', '6 Annamark Road', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti. Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris. Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis. Fusce posuere felis sed lacus.', 'Etiam justo. Etiam pretium iaculis justo. In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi. Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('nheinlein1b@arizona.edu', 'nheinlein1b@columbia.edu', 'Nicolis Heinlein', 'Male', '2011-04-28', '2108925104', '4 Gina Center', 'Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis. Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem. Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci.', 'Pellentesque at nulla. Suspendisse potenti. Cras in purus eu magna vulputate luctus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam vel augue.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('asibthorpe1c@nymag.com', 'asibthorpe1c@dropbox.com', 'Angelo Sibthorpe', 'Male', '2000-04-16', '9346813343', '18 Pearson Place', 'In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem. Integer tincidunt ante vel ipsum.', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis. Duis consequat dui nec nisi volutpat eleifend.');
            insert into [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, Address, SecurityQuestion, Answer) values ('geye1d@disqus.com', 'geye1d@canalblog.com', 'Gabi Eye', 'Male', '2001-09-01', '7517563870', '4 Gerald Place', 'Aliquam sit amet diam in magna bibendum imperdiet.', 'Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus. Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam.');
            ?""";
    
    
    public static void main(String[] args) {
        try {
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
            DbOperations.updateData(defaultDeliveryInfoTrigger, "", false);
            DbOperations.updateData(altRankTrigger, "", false);
            DbOperations.updateData(delCategoryTrigger, "", false);
            DbOperations.updateData(incUserPointTrigger, "", false);
            DbOperations.updateData(giveVoucherTrigger, "Triggers created successfully", false);
//            DbOperations.updateData(randomUsers, "Random data inserted successfully");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
    }
}
