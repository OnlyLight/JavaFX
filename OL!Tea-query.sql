USE CoffeeShop
GO

SELECT * FROM dbo.LoaiMon ORDER BY id
GO

CREATE TABLE Role (
	idRole INT IDENTITY PRIMARY KEY,
	roleName NVARCHAR(50) NOT NULL
)
GO

CREATE TABLE NhanVien (
	idNV INT IDENTITY PRIMARY KEY,
	userName VARCHAR(50) NOT NULL,
	passWord VARCHAR(50) NOT NULL
)
GO

ALTER TABLE dbo.NhanVien ADD CONSTRAINT UQ_USER UNIQUE(userName)
GO

ALTER TABLE dbo.NhanVien ADD role INT NOT NULL CONSTRAINT FK_NV_ROLE REFERENCES dbo.Role
GO

CREATE TABLE Bill (
	idBill INT IDENTITY PRIMARY KEY,
	idNhanVien INT CONSTRAINT FK_Bill_NV REFERENCES dbo.NhanVien,
	tongTien INT NOT NULL,
	ngayLap DATE
)
GO

CREATE TABLE Member (
	idMember INT IDENTITY PRIMARY KEY,
	tenLoaiMember NVARCHAR(50) NOT NULL UNIQUE,
	giamGia INT NOT NULL
)
GO

CREATE TABLE Customer (
	idCustomer INT IDENTITY PRIMARY KEY,
	tenCus NVARCHAR(50) NOT NULL,
	sdt VARCHAR(12) NOT NULL,
	idMember INT CONSTRAINT FK_CUS_MEM REFERENCES dbo.Member
)
GO

CREATE TABLE DKMember (
	idDK INT IDENTITY PRIMARY KEY,
	idNhanVien INT CONSTRAINT FK_DK_NV REFERENCES dbo.NhanVien,
	idCustomer INT CONSTRAINT FK_DK_CUS REFERENCES dbo.Customer
)
GO

SELECT * FROM dbo.Role
GO

SELECT * FROM dbo.NhanVien
GO

SELECT * FROM dbo.Bill
GO

-- Thong ke Bill
SELECT dbo.NhanVien.userName, dbo.Bill.tongTien, dbo.Bill.ngayLap, dbo.Role.roleName
FROM dbo.Role JOIN dbo.NhanVien ON dbo.Role.idRole = dbo.NhanVien.role JOIN dbo.Bill ON dbo.NhanVien.idNV = dbo.Bill.idNhanVien
GO

-- Thong ke tao the member
SELECT dbo.NhanVien.userName, dbo.Customer.tenCus, dbo.Customer.sdt, dbo.Member.tenLoaiMember
FROM dbo.Member JOIN dbo.Customer ON Customer.idMember = Member.idMember JOIN dbo.DKMember ON DKMember.idCustomer = Customer.idCustomer JOIN dbo.NhanVien ON dbo.DKMember.idNhanVien = dbo.NhanVien.idNV
GO
