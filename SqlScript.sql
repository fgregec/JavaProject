USE [master]
GO
/****** Object:  Database [BazaJava]    Script Date: 9/7/2022 6:20:16 PM ******/
CREATE DATABASE [BazaJava]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BazaJava', FILENAME = N'E:\SQLTEST\MSSQL15.MSSQLSERVER\MSSQL\DATA\BazaJava.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'BazaJava_log', FILENAME = N'E:\SQLTEST\MSSQL15.MSSQLSERVER\MSSQL\DATA\BazaJava_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [BazaJava] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BazaJava].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BazaJava] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BazaJava] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BazaJava] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BazaJava] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BazaJava] SET ARITHABORT OFF 
GO
ALTER DATABASE [BazaJava] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [BazaJava] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BazaJava] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BazaJava] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BazaJava] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BazaJava] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BazaJava] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BazaJava] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BazaJava] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BazaJava] SET  DISABLE_BROKER 
GO
ALTER DATABASE [BazaJava] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BazaJava] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BazaJava] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BazaJava] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BazaJava] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BazaJava] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BazaJava] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BazaJava] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [BazaJava] SET  MULTI_USER 
GO
ALTER DATABASE [BazaJava] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BazaJava] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BazaJava] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BazaJava] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [BazaJava] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [BazaJava] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [BazaJava] SET QUERY_STORE = OFF
GO
USE [BazaJava]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[IDAccount] [int] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](20) NULL,
	[Password] [nvarchar](20) NULL,
	[IsAdmin] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[IDAccount] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FavouriteMovie]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FavouriteMovie](
	[IDFavouriteMovie] [int] IDENTITY(1,1) NOT NULL,
	[MovieID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[IDFavouriteMovie] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Movie]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Movie](
	[IDMovie] [int] IDENTITY(1,1) NOT NULL,
	[Title] [nvarchar](300) NULL,
	[PublishedDate] [nvarchar](50) NULL,
	[Description] [nvarchar](max) NULL,
	[OriginalTitle] [nvarchar](100) NULL,
	[PersonID] [int] NULL,
	[Duration] [nvarchar](50) NULL,
	[Genre] [nvarchar](100) NULL,
	[PicturePath] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[IDMovie] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MovieActor]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MovieActor](
	[IDMovieActor] [int] IDENTITY(1,1) NOT NULL,
	[MovieID] [int] NULL,
	[PersonID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[IDMovieActor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Person]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Person](
	[IDPerson] [int] IDENTITY(1,1) NOT NULL,
	[FirstName] [nvarchar](50) NULL,
	[LastName] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[IDPerson] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[FavouriteMovie]  WITH CHECK ADD FOREIGN KEY([MovieID])
REFERENCES [dbo].[Movie] ([IDMovie])
GO
ALTER TABLE [dbo].[MovieActor]  WITH CHECK ADD FOREIGN KEY([MovieID])
REFERENCES [dbo].[Movie] ([IDMovie])
GO
ALTER TABLE [dbo].[MovieActor]  WITH CHECK ADD FOREIGN KEY([PersonID])
REFERENCES [dbo].[Person] ([IDPerson])
GO
/****** Object:  StoredProcedure [dbo].[addFavouriteMovie]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[addFavouriteMovie]
@MovieID int
as
insert into FavouriteMovie(MovieID) values (@MovieID)


GO
/****** Object:  StoredProcedure [dbo].[createAccount]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE procedure [dbo].[createAccount]
	@username NVARCHAR(20),
	@password NVARCHAR(20),
	@ID INT OUTPUT
AS 
BEGIN 
	INSERT INTO Account(Username, Password, IsAdmin) VALUES(@username, @password, 0)
	SET @ID = SCOPE_IDENTITY()
END

GO
/****** Object:  StoredProcedure [dbo].[createMovie]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[createMovie]
	@Title NVARCHAR(300),
	@PublishedDate NVARCHAR(90),
	@Description NVARCHAR(900),
	@OriginalTitle NVARCHAR(300),
	@PersonID INT,
	@Duration NVARCHAR(50),
	@Genre NVARCHAR(300),
	@PicturePath NVARCHAR(300),
	@ID INT OUTPUT
AS 
	BEGIN 
		INSERT INTO Movie VALUES(@Title, @PublishedDate, @Description, @OriginalTitle, @PersonID, @Duration, @Genre, @PicturePath)
		SET @ID = SCOPE_IDENTITY()
	END
GO
/****** Object:  StoredProcedure [dbo].[createMovieActor]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[createMovieActor]
	@MovieID int,
	@PersonID int,
	@ID INT OUTPUT
AS 
	if not exists(select*from MovieActor where @PersonID=PersonID and @MovieID=@PersonID)
	begin
		INSERT INTO MovieActor(MovieID,PersonID) values (@MovieID, @PersonID) 
		SET @ID = SCOPE_IDENTITY()
	END		
GO
/****** Object:  StoredProcedure [dbo].[createPerson]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[createPerson]
	@FirstName NVARCHAR(300),
	@LastName NVARCHAR(300),
	@ID INT OUTPUT
AS 
	BEGIN 
		IF exists (select * from Person as p where p.FirstName = @FirstName and p.LastName = @LastName)
			BEGIN
				select @ID = p.IDPerson from Person as p where p.FirstName = @FirstName and p.LastName = @LastName
			END
		ELSE
			BEGIN
				INSERT INTO Person VALUES(@FirstName, @LastName)
				SET @ID = SCOPE_IDENTITY()
			END		
	END
GO
/****** Object:  StoredProcedure [dbo].[deleteMovie]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[deleteMovie]
	@IDMovie INT	 
AS 
	BEGIN 
		DELETE FROM MovieActor WHERE MovieID = @IDMovie
		DELETE FROM Movie WHERE IDMovie = @IDMovie
	END
GO
/****** Object:  StoredProcedure [dbo].[deleteMovies]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[deleteMovies]
AS 
	BEGIN
		DELETE FROM MovieActor
		DELETE FROM Movie
		DELETE FROM Person		
	END
GO
/****** Object:  StoredProcedure [dbo].[deletePerson]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[deletePerson]
	@IDPerson INT	 
AS 
	BEGIN 
	Delete from MovieActor where MovieActor.PersonID=@IDPerson
		DELETE  FROM Person	WHERE IDPerson = @IDPerson
	END


	delete from Person
GO
/****** Object:  StoredProcedure [dbo].[removeFavoriteMovie]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

	create proc [dbo].[removeFavoriteMovie]
	@IDMovie int as 
	delete from FavouriteMovie where FavouriteMovie.MovieID=@IDMovie
GO
/****** Object:  StoredProcedure [dbo].[selectAccount]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[selectAccount]
@username nvarchar(20)
as
select * from Account where username = @username

GO
/****** Object:  StoredProcedure [dbo].[selectFavouriteMovies]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[selectFavouriteMovies]
as
select Movie.IDMovie,Movie.Title from FavouriteMovie 
inner join Movie on Movie.IDMovie=FavouriteMovie.MovieID
GO
/****** Object:  StoredProcedure [dbo].[selectMovie]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[selectMovie]
	@IDMovie INT
AS 
	BEGIN 
		SELECT m.IDMovie, m.Title, m.PublishedDate, m.Description, m.OriginalTitle, m.PersonID, m.Duration, m.Genre, m.PicturePath, p.IDPerson, p.LastName, p.FirstName FROM Movie as m	
		INNER JOIN Person as p on m.PersonID = p.IDPerson
		WHERE IDMovie = @IDMovie
	END
GO
/****** Object:  StoredProcedure [dbo].[selectMovieActors]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create procedure [dbo].[selectMovieActors]
	@MovieID int
AS
	BEGIN
		SELECT Person.IDPerson, Person.FirstName, Person.LastName FROM MovieActor INNER JOIN Person on Person.IDPerson = MovieActor.PersonID
		WHERE MovieActor.MovieID = @MovieID
	END
GO
/****** Object:  StoredProcedure [dbo].[selectMovies]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[selectMovies]
AS
BEGIN
	SELECT m.IDMovie, m.Title, m.PublishedDate, m.Description, m.OriginalTitle, m.Duration, m.Genre, m.PicturePath, m.PersonID, p.FirstName, p.LastName
	
	FROM Movie AS m
	FULL JOIN Person AS p
		ON m.PersonID = p.IDPerson	
	WHERE m.IDMovie IS NOT NULL and p.IDPerson IS NOT NULL
END
GO
/****** Object:  StoredProcedure [dbo].[selectNotFavouriteMovies]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[selectNotFavouriteMovies]
AS
BEGIN
	SELECT *
	
	FROM Movie AS m
	left JOIN Person AS p
		ON m.PersonID = p.IDPerson	
	 where m.IDMovie not IN 
(
select FavouriteMovie.MovieID from FavouriteMovie
)
END
GO
/****** Object:  StoredProcedure [dbo].[selectPerson]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[selectPerson]
	@IDPerson INT
AS 
	BEGIN 
		SELECT * FROM Person WHERE IDPerson = @IDPerson
	END
GO
/****** Object:  StoredProcedure [dbo].[selectPersonByName]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[selectPersonByName]
@fName nvarchar(20),
@lName nvarchar(20)
as
select * from Person where FirstName = @fName and LastName = @lName

GO
/****** Object:  StoredProcedure [dbo].[selectPersons]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[selectPersons]
AS 
	BEGIN 
		SELECT * FROM Person
	END
GO
/****** Object:  StoredProcedure [dbo].[updateMovie]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create procedure [dbo].[updateMovie]
	@IDMovie INT,
	@Title NVARCHAR(300),
	@PublishedDate NVARCHAR(90),
	@Description NVARCHAR(900),
	@OriginalTitle NVARCHAR(300),
	@PersonID INT,
	@Duration NVARCHAR(50),
	@Genre NVARCHAR(300),
	@PicturePath NVARCHAR(300)
	 
AS 
	BEGIN 
		UPDATE Movie SET 
			Title = @Title,
			PublishedDate = @PublishedDate,		
			Description = @Description,
			OriginalTitle = @OriginalTitle,
			PersonID = @PersonID,
			Duration = @Duration,
			Genre = @Genre,
			PicturePath = @PicturePath
		WHERE IDMovie = @IDMovie
	END
GO
/****** Object:  StoredProcedure [dbo].[updatePerson]    Script Date: 9/7/2022 6:20:16 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
	create procedure [dbo].[updatePerson]
	@FirstName NVARCHAR(300),
	@LastName NVARCHAR(300),
	@IDPerson INT
	 
AS 
	BEGIN 
		UPDATE Person SET 
			FirstName = @FirstName,
			LastName = @LastName
				
		WHERE IDPerson = @IDPerson
	END
GO
USE [master]
GO
ALTER DATABASE [BazaJava] SET  READ_WRITE 
GO
