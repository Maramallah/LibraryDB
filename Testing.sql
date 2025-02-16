use Library;

SELECT * FROM Admins;
SELECT * FROM Members;

SELECT * FROM AvailableBooks;
SELECT * FROM AllBooks;

EXEC SignUp 
    @FirstName = 'Aliy',
    @LastName = 'Johnson',
    @Email = 'ali.johnson@example.com',
    @Phone = '01287868500',
    @Address = '789 Pine St',
    @Password = 'password067';

EXEC MemberSignIn 
    @Email = 'alice.johnson@example.com',
    @Password = 'password123';

EXEC BorrowBook 
    @UserID = 2,
    @BookName = 'Harry Potter and the Goblet of Fire';
-- admins cant buy
EXEC BuyBook
   @UserID = 2,
    @BookName = 'The Shining'
    ;
--error here
EXEC ReturnBook 
    @UserID = 2,
    @BookName = 'Harry Potter and the Goblet of Fire';
--smth wrong with update fine
EXEC PayFine 
    @UserID = 2;


EXEC AddNewAdmins 
    @FirstName = 'Bob',
    @LastName = 'Brown',
    @Email = 'bob.brown@example.com',
    @Phone = '5559876543',
    @Address = '321 Oak St',
    @Password = 'admin123';

EXEC AdminSignIn 
    @Email = 'bob.brown@example.com',
    @Password = 'admin123';

EXEC SearchBookByName 
    @BookName = 'Harry Potter';

EXEC SearchBookByAuthorName 
    @AuthorName = 'J.K. Rowling';

EXEC AddBook 
    @ISBN = '978-0439139596',
    @BookName = 'Harry Potter and the Order of the Phoenix',
    @AuthorName = 'J.K. Rowling',
    @PublisherName = 'Penguin Random House',
    @Category = 'Fantasy',
    @BorrowPrice = 5.99,
    @Price = 30.00,
    @PublishDate = '2003-06-21',
    @NumOfCopies = 5;

EXEC EditBookInfo 
    @BookName = 'Harry Potter and the Order of the Phoenix',
    @ToEdit = 3,
    @Input = '35.00';

EXEC RemoveBook 
    @BookName = 'Harry Potter and the Order of the Phoenix';

EXEC RemoveMember 
    @UserID = 1;
EXEC SearchForMember 
    @SearchKeyword = 'Bob';

EXEC ListOverdueBooks;

EXEC CalculateFine 
    @UserID = 3; 
	--returns same records for user 2  done
EXEC BorrowRecordeHistory 
    @UserID = 3;
	--smth wrong
EXEC CurrentBorrowRecords 
    @UserID = 2;
EXEC PayFine 
    @UserID = 2;
EXEC update_fine @UserID=2;


select * from BorrowRecords

EXEC sp_configure 'show advanced options', 1;
RECONFIGURE;
EXEC sp_configure 'identity cache', 0;
RECONFIGURE;
