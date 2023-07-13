package dao;

import javax.swing.JOptionPane;

public class TablesCreation {

    private static final String dropTables
            = """
            DROP TABLE IF EXISTS CartItem;
            DROP TABLE IF EXISTS VoucherUsage;
            DROP TABLE IF EXISTS Voucher;
            DROP TABLE IF EXISTS OrderDetails;
            DROP TABLE IF EXISTS [Order];
            DROP TABLE IF EXISTS DeliveryInfo;
            DROP TABLE IF EXISTS PaymentInfo;
            DROP TABLE IF EXISTS PaymentMethod;
            DROP TABLE IF EXISTS Staff;
            DROP TABLE IF EXISTS Product;
            DROP TABLE IF EXISTS Category;
            DROP TABLE IF EXISTS [User];
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
                SecurityQuestion VARCHAR(200) NOT NULL,
                Answer VARCHAR(200) NOT NULL,
                CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
                IsApproved BIT DEFAULT 0,
                Point INT DEFAULT 0,
                Rank VARCHAR(20) DEFAULT 'Regular'
            );
            INSERT INTO [User] (Email, Password, FullName, Sex, BirthDate, PhoneNumber, SecurityQuestion, Answer, IsApproved)
            VALUES ('admin', 'admin', 'Admin', 'Null', '1900-01-01', '0123456789', 'Why is dark humor like food?', 'Because not everybody gets it', 1);
            """;

    private static final String deliveryInfoTable
            = """
            CREATE TABLE DeliveryInfo (
                Id INT PRIMARY KEY IDENTITY(1,1),
                UserId INT NOT NULL,
                RecipientName VARCHAR(200) NOT NULL,
                PhoneNumber VARCHAR(12) NOT NULL,
                Address VARCHAR(100) NOT NULL,
                FOREIGN KEY (UserId) REFERENCES [User](Id)
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
                FOREIGN KEY (UserId) REFERENCES [User](Id)
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
                FinalCost DECIMAL(10,2),
                DeliveryInfoId INT NOT NULL,
                PaymentMethod INT NOT NULL,
                PaymentInfoId INT,
                Note VARCHAR(100),
                ShipperId INT,
                DeliveredAt DATETIME,
                FOREIGN KEY (UserId) REFERENCES [User](Id),
                FOREIGN KEY (DeliveryInfoId) REFERENCES DeliveryInfo(Id),
                FOREIGN KEY (PaymentMethod) REFERENCES PaymentMethod(Id),
                FOREIGN KEY (PaymentInfoId) REFERENCES PaymentInfo(Id),
                FOREIGN KEY (ShipperId) REFERENCES Staff(Id)
            );
            """;

    private static final String orderDetailsTable
            = """
            CREATE TABLE OrderDetails (
                Id INT PRIMARY KEY IDENTITY(1,1),
                OrderId INT NOT NULL,
                ProductId INT NOT NULL,
                Quantity INT NOT NULL,
                UnitPrice DECIMAL(10,2),
                TotalAmount AS (UnitPrice * Quantity),
                FOREIGN KEY (OrderId) REFERENCES [Order](Id),
                FOREIGN KEY (ProductId) REFERENCES Product(Id)
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
                Name VARCHAR(100) NOT NULL,
                MinPoint DECIMAL(10,2),
                MinQuantity INT,
                DiscountPercentage DECIMAL(10,2),
                DiscountAmount DECIMAL(10,2)
            );
            """;

    private static final String voucherUsageTable
            = """
            CREATE TABLE VoucherUsage (
                Id INT PRIMARY KEY IDENTITY(1,1),
                VoucherId INT NOT NULL,
                UserId INT NOT NULL,
                OrderId INT
                FOREIGN KEY (VoucherId) REFERENCES Voucher(Id),
                FOREIGN KEY (UserId) REFERENCES [User](Id),
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
                FOREIGN KEY (UserId) REFERENCES [User](Id),
                FOREIGN KEY (ProductId) REFERENCES Product(Id),
            );
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
            DbOperations.updateData(userTable, "User table created successfully");
            DbOperations.updateData(staffTable, "Staff table created successfully");
            DbOperations.updateData(categoryTable, "Category table created successfully");
            DbOperations.updateData(productTable, "Product table created successfully");
            DbOperations.updateData(deliveryInfoTable, "Delivery info table created successfully");
            DbOperations.updateData(paymentMethodTable, "Payment method table created successfully");
            DbOperations.updateData(paymentInfoTable, "Payment info table created successfully");
            DbOperations.updateData(orderTable, "Order table created successfully");
            DbOperations.updateData(orderDetailsTable, "Order details table created successfully");
            DbOperations.updateData(voucherTable, "Voucher table created successfully");
            DbOperations.updateData(voucherUsageTable, "Voucher usage table created successfully");
            DbOperations.updateData(cartItemTable, "Cart item table created successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
    }
}