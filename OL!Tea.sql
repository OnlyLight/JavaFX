USE [master]
GO
/****** Object:  Database [CoffeeShop]    Script Date: 7/22/2018 7:50:35 PM ******/
CREATE DATABASE [CoffeeShop]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'CoffeeShop', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\CoffeeShop.mdf' , SIZE = 3264KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'CoffeeShop_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\CoffeeShop_log.ldf' , SIZE = 816KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [CoffeeShop] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [CoffeeShop].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [CoffeeShop] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [CoffeeShop] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [CoffeeShop] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [CoffeeShop] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [CoffeeShop] SET ARITHABORT OFF 
GO
ALTER DATABASE [CoffeeShop] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [CoffeeShop] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [CoffeeShop] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [CoffeeShop] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [CoffeeShop] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [CoffeeShop] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [CoffeeShop] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [CoffeeShop] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [CoffeeShop] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [CoffeeShop] SET  ENABLE_BROKER 
GO
ALTER DATABASE [CoffeeShop] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [CoffeeShop] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [CoffeeShop] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [CoffeeShop] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [CoffeeShop] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [CoffeeShop] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [CoffeeShop] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [CoffeeShop] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [CoffeeShop] SET  MULTI_USER 
GO
ALTER DATABASE [CoffeeShop] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [CoffeeShop] SET DB_CHAINING OFF 
GO
ALTER DATABASE [CoffeeShop] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [CoffeeShop] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [CoffeeShop] SET DELAYED_DURABILITY = DISABLED 
GO
USE [CoffeeShop]
GO
/****** Object:  Table [dbo].[Bill]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bill](
	[idBill] [int] IDENTITY(1,1) NOT NULL,
	[idNhanVien] [int] NULL,
	[tongTien] [int] NOT NULL,
	[ngayLap] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[idBill] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Customer]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Customer](
	[idCustomer] [int] IDENTITY(1,1) NOT NULL,
	[tenCus] [nvarchar](50) NOT NULL,
	[sdt] [varchar](12) NOT NULL,
	[idMember] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idCustomer] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[DKMember]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DKMember](
	[idDK] [int] IDENTITY(1,1) NOT NULL,
	[idNhanVien] [int] NULL,
	[idCustomer] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idDK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[DVT]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DVT](
	[idDVT] [int] NOT NULL,
	[DVT] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idDVT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[LoaiMon]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiMon](
	[id] [int] NOT NULL,
	[loaiMon] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[LoaiNX]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiNX](
	[idLoaiNX] [int] NOT NULL,
	[tenLoaiNX] [nvarchar](50) NOT NULL,
	[idDVT] [int] NOT NULL,
 CONSTRAINT [PK__LoaiNX__5BB350D064DC7C24] PRIMARY KEY CLUSTERED 
(
	[idLoaiNX] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Member]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Member](
	[idMember] [int] IDENTITY(1,1) NOT NULL,
	[tenLoaiMember] [nvarchar](50) NOT NULL,
	[giamGia] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idMember] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Mon]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Mon](
	[idMon] [int] IDENTITY(1,1) NOT NULL,
	[tenMon] [nvarchar](50) NOT NULL,
	[donGia] [int] NOT NULL,
	[idLoaiMon] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idMon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[MonOrder]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MonOrder](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[soLuong] [int] NOT NULL,
	[idMon] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[NhanVien](
	[idNV] [int] IDENTITY(1,1) NOT NULL,
	[userName] [varchar](50) NOT NULL,
	[passWord] [varchar](50) NOT NULL,
	[role] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Nhap]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Nhap](
	[idNhap] [int] IDENTITY(1,1) NOT NULL,
	[tenSpNhap] [nvarchar](50) NULL,
	[idLoaiNX] [int] NULL,
	[donGia] [int] NOT NULL,
	[soLuong] [int] NOT NULL,
	[ngayNhap] [date] NOT NULL,
 CONSTRAINT [PK__Nhap__AD03B9247552E29C] PRIMARY KEY CLUSTERED 
(
	[idNhap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Role]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[idRole] [int] IDENTITY(1,1) NOT NULL,
	[roleName] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idRole] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Xuat]    Script Date: 7/22/2018 7:50:35 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Xuat](
	[idXuat] [int] IDENTITY(1,1) NOT NULL,
	[tenSpXuat] [nvarchar](50) NULL,
	[idLoaiNX] [int] NULL,
	[soLuong] [int] NOT NULL,
	[ngayXuat] [date] NOT NULL,
 CONSTRAINT [PK__Xuat__1F6929DEB796A690] PRIMARY KEY CLUSTERED 
(
	[idXuat] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET IDENTITY_INSERT [dbo].[Bill] ON 

INSERT [dbo].[Bill] ([idBill], [idNhanVien], [tongTien], [ngayLap]) VALUES (1, 1, 56000, CAST(N'2018-07-21' AS Date))
INSERT [dbo].[Bill] ([idBill], [idNhanVien], [tongTien], [ngayLap]) VALUES (2, 2, 150000, CAST(N'2018-07-22' AS Date))
SET IDENTITY_INSERT [dbo].[Bill] OFF
SET IDENTITY_INSERT [dbo].[Customer] ON 

INSERT [dbo].[Customer] ([idCustomer], [tenCus], [sdt], [idMember]) VALUES (1, N'Nguyễn Thanh Hào', N'0905123456', 1)
INSERT [dbo].[Customer] ([idCustomer], [tenCus], [sdt], [idMember]) VALUES (2, N'Nguyễn Văn Hòa', N'0905123457', 2)
INSERT [dbo].[Customer] ([idCustomer], [tenCus], [sdt], [idMember]) VALUES (3, N'Võ Nguyễn Hoàng Phi', N'0905123458', 3)
SET IDENTITY_INSERT [dbo].[Customer] OFF
SET IDENTITY_INSERT [dbo].[DKMember] ON 

INSERT [dbo].[DKMember] ([idDK], [idNhanVien], [idCustomer]) VALUES (1, 1, 3)
INSERT [dbo].[DKMember] ([idDK], [idNhanVien], [idCustomer]) VALUES (2, 2, 1)
SET IDENTITY_INSERT [dbo].[DKMember] OFF
INSERT [dbo].[DVT] ([idDVT], [DVT]) VALUES (1, N'Thùng')
INSERT [dbo].[LoaiMon] ([id], [loaiMon]) VALUES (2, N'Coffee')
INSERT [dbo].[LoaiMon] ([id], [loaiMon]) VALUES (1, N'Milk Tea')
INSERT [dbo].[LoaiMon] ([id], [loaiMon]) VALUES (4, N'Nước Cam')
INSERT [dbo].[LoaiMon] ([id], [loaiMon]) VALUES (3, N'Sữa chua')
INSERT [dbo].[LoaiNX] ([idLoaiNX], [tenLoaiNX], [idDVT]) VALUES (1, N'Cà phê Arabica', 1)
INSERT [dbo].[LoaiNX] ([idLoaiNX], [tenLoaiNX], [idDVT]) VALUES (2, N'Cà phê Robusta', 1)
INSERT [dbo].[LoaiNX] ([idLoaiNX], [tenLoaiNX], [idDVT]) VALUES (3, N'Cà phê Culi', 1)
INSERT [dbo].[LoaiNX] ([idLoaiNX], [tenLoaiNX], [idDVT]) VALUES (4, N'Cà phê Cherry', 1)
INSERT [dbo].[LoaiNX] ([idLoaiNX], [tenLoaiNX], [idDVT]) VALUES (5, N'Cà phê Moka', 1)
SET IDENTITY_INSERT [dbo].[Member] ON 

INSERT [dbo].[Member] ([idMember], [tenLoaiMember], [giamGia]) VALUES (1, N'Bronze', 15)
INSERT [dbo].[Member] ([idMember], [tenLoaiMember], [giamGia]) VALUES (2, N'Silver', 25)
INSERT [dbo].[Member] ([idMember], [tenLoaiMember], [giamGia]) VALUES (3, N'Gold', 35)
INSERT [dbo].[Member] ([idMember], [tenLoaiMember], [giamGia]) VALUES (4, N'Diamond', 40)
SET IDENTITY_INSERT [dbo].[Member] OFF
SET IDENTITY_INSERT [dbo].[Mon] ON 

INSERT [dbo].[Mon] ([idMon], [tenMon], [donGia], [idLoaiMon]) VALUES (1, N'Cà phê đen', 12000, 2)
INSERT [dbo].[Mon] ([idMon], [tenMon], [donGia], [idLoaiMon]) VALUES (2, N'Cà phê sữa', 28000, 2)
INSERT [dbo].[Mon] ([idMon], [tenMon], [donGia], [idLoaiMon]) VALUES (4, N'Sữa chua CaCao', 15000, 3)
INSERT [dbo].[Mon] ([idMon], [tenMon], [donGia], [idLoaiMon]) VALUES (7, N'Nước cam vắt', 10000, 4)
INSERT [dbo].[Mon] ([idMon], [tenMon], [donGia], [idLoaiMon]) VALUES (8, N'Nước cam ép', 13000, 4)
INSERT [dbo].[Mon] ([idMon], [tenMon], [donGia], [idLoaiMon]) VALUES (9, N'Hồng trà sữa', 32000, 1)
SET IDENTITY_INSERT [dbo].[Mon] OFF
SET IDENTITY_INSERT [dbo].[NhanVien] ON 

INSERT [dbo].[NhanVien] ([idNV], [userName], [passWord], [role]) VALUES (1, N'duy', N'1234', 1)
INSERT [dbo].[NhanVien] ([idNV], [userName], [passWord], [role]) VALUES (2, N'hoa', N'1234', 2)
SET IDENTITY_INSERT [dbo].[NhanVien] OFF
SET IDENTITY_INSERT [dbo].[Nhap] ON 

INSERT [dbo].[Nhap] ([idNhap], [tenSpNhap], [idLoaiNX], [donGia], [soLuong], [ngayNhap]) VALUES (1, N'Cà phê', 1, 56000, 10, CAST(N'2018-07-17' AS Date))
INSERT [dbo].[Nhap] ([idNhap], [tenSpNhap], [idLoaiNX], [donGia], [soLuong], [ngayNhap]) VALUES (2, N'Cà phê', 3, 86000, 5, CAST(N'2018-07-17' AS Date))
SET IDENTITY_INSERT [dbo].[Nhap] OFF
SET IDENTITY_INSERT [dbo].[Role] ON 

INSERT [dbo].[Role] ([idRole], [roleName]) VALUES (1, N'admin')
INSERT [dbo].[Role] ([idRole], [roleName]) VALUES (2, N'staff')
SET IDENTITY_INSERT [dbo].[Role] OFF
SET IDENTITY_INSERT [dbo].[Xuat] ON 

INSERT [dbo].[Xuat] ([idXuat], [tenSpXuat], [idLoaiNX], [soLuong], [ngayXuat]) VALUES (1, N'Cà phê', 1, 2, CAST(N'2018-07-17' AS Date))
INSERT [dbo].[Xuat] ([idXuat], [tenSpXuat], [idLoaiNX], [soLuong], [ngayXuat]) VALUES (2, N'Cà phê', 3, 4, CAST(N'2018-07-17' AS Date))
SET IDENTITY_INSERT [dbo].[Xuat] OFF
SET ANSI_PADDING ON

GO
/****** Object:  Index [U_DVT]    Script Date: 7/22/2018 7:50:35 PM ******/
ALTER TABLE [dbo].[DVT] ADD  CONSTRAINT [U_DVT] UNIQUE NONCLUSTERED 
(
	[DVT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__DVT__C030C7867BB687DE]    Script Date: 7/22/2018 7:50:35 PM ******/
ALTER TABLE [dbo].[DVT] ADD UNIQUE NONCLUSTERED 
(
	[DVT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [U_LoaiMon]    Script Date: 7/22/2018 7:50:35 PM ******/
ALTER TABLE [dbo].[LoaiMon] ADD  CONSTRAINT [U_LoaiMon] UNIQUE NONCLUSTERED 
(
	[loaiMon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__Member__811858F9B5FD95B8]    Script Date: 7/22/2018 7:50:35 PM ******/
ALTER TABLE [dbo].[Member] ADD UNIQUE NONCLUSTERED 
(
	[tenLoaiMember] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ_USER]    Script Date: 7/22/2018 7:50:35 PM ******/
ALTER TABLE [dbo].[NhanVien] ADD  CONSTRAINT [UQ_USER] UNIQUE NONCLUSTERED 
(
	[userName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Bill]  WITH CHECK ADD  CONSTRAINT [FK_Bill_NV] FOREIGN KEY([idNhanVien])
REFERENCES [dbo].[NhanVien] ([idNV])
GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FK_Bill_NV]
GO
ALTER TABLE [dbo].[Customer]  WITH CHECK ADD  CONSTRAINT [FK_CUS_MEM] FOREIGN KEY([idMember])
REFERENCES [dbo].[Member] ([idMember])
GO
ALTER TABLE [dbo].[Customer] CHECK CONSTRAINT [FK_CUS_MEM]
GO
ALTER TABLE [dbo].[DKMember]  WITH CHECK ADD  CONSTRAINT [FK_DK_CUS] FOREIGN KEY([idCustomer])
REFERENCES [dbo].[Customer] ([idCustomer])
GO
ALTER TABLE [dbo].[DKMember] CHECK CONSTRAINT [FK_DK_CUS]
GO
ALTER TABLE [dbo].[DKMember]  WITH CHECK ADD  CONSTRAINT [FK_DK_NV] FOREIGN KEY([idNhanVien])
REFERENCES [dbo].[NhanVien] ([idNV])
GO
ALTER TABLE [dbo].[DKMember] CHECK CONSTRAINT [FK_DK_NV]
GO
ALTER TABLE [dbo].[LoaiNX]  WITH CHECK ADD  CONSTRAINT [FK_LoaiNX_DVT] FOREIGN KEY([idDVT])
REFERENCES [dbo].[DVT] ([idDVT])
GO
ALTER TABLE [dbo].[LoaiNX] CHECK CONSTRAINT [FK_LoaiNX_DVT]
GO
ALTER TABLE [dbo].[Mon]  WITH CHECK ADD  CONSTRAINT [Mon_Loai] FOREIGN KEY([idLoaiMon])
REFERENCES [dbo].[LoaiMon] ([id])
GO
ALTER TABLE [dbo].[Mon] CHECK CONSTRAINT [Mon_Loai]
GO
ALTER TABLE [dbo].[MonOrder]  WITH CHECK ADD  CONSTRAINT [FK_MonOrder_Mon1] FOREIGN KEY([idMon])
REFERENCES [dbo].[Mon] ([idMon])
GO
ALTER TABLE [dbo].[MonOrder] CHECK CONSTRAINT [FK_MonOrder_Mon1]
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD  CONSTRAINT [FK_NV_ROLE] FOREIGN KEY([role])
REFERENCES [dbo].[Role] ([idRole])
GO
ALTER TABLE [dbo].[NhanVien] CHECK CONSTRAINT [FK_NV_ROLE]
GO
ALTER TABLE [dbo].[Nhap]  WITH CHECK ADD  CONSTRAINT [FK_Nhap_LoaiNX] FOREIGN KEY([idLoaiNX])
REFERENCES [dbo].[LoaiNX] ([idLoaiNX])
GO
ALTER TABLE [dbo].[Nhap] CHECK CONSTRAINT [FK_Nhap_LoaiNX]
GO
ALTER TABLE [dbo].[Xuat]  WITH CHECK ADD  CONSTRAINT [FK_Xuat_LoaiNX] FOREIGN KEY([idLoaiNX])
REFERENCES [dbo].[LoaiNX] ([idLoaiNX])
GO
ALTER TABLE [dbo].[Xuat] CHECK CONSTRAINT [FK_Xuat_LoaiNX]
GO
USE [master]
GO
ALTER DATABASE [CoffeeShop] SET  READ_WRITE 
GO
