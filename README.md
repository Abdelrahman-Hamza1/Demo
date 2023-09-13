User Requirements 

This part will contain user requirements for an online Bookstore/Library that is built to support an existing library. This application aims to  allow the library to provide online users access to different information about products/services that are offered by the library. The Library will also provide users with other features such as Wishlist/s and a Book-Auction.
 
The system shall include three types of users:

Regular users: Un-Authenticated users

Registered Users: Authenticated users

Admins: Library owners/workers

Authentication:

Regular Users shall be able to become Registered Users after creating an account which includes a valid email address.
 
Library owned books:

Regular users shall be able to look-up library owned books, view details about different books.

Registered users shall be able to do everything a regular user can do.

Registered users shall be able to reserve books. [UN-SURE]

Admins shall be able to CRUD.
 
User Auction:

Regular users shall be able to view auctions (present or past)

Registered users shall be able to list an item (Book) for sale & receive offers (bids) on it.

Registered users shall be able to place bids on other users’ auctions.

Registered Users shall be able to place auction requests for books that do not exist in the library.
Admins shall be able to CRUD auctions
 
User Wishlist:

Registered users shall be able to create as many personal wishlists as they like.

Registered users shall be able to CRUD their OWN wishlists. 
Admins shall be able to CRUD
 
 Author Subscription:

Registered users shall be able to subscribe/unsubscribe to an author.

Registered users shall receive emails every time a book that belongs to an author they’re subscribed to is added to the system.



Use-Case Diagram



Class Diagram



System Architecture 



API Endpoints

All Endpoints currently use GET requests and transfer data in the URI. It should be modified later.
Book Service

Get	/Book/TestKeyCloak

Get	/Book/IsValid/{bookId}

Get	/Book/Add/{name}/{author}/{price}/{quantity}

Get	/Book/Update/{id}/{name}/{author}/{price}/{quantity}

Get	/Book/GetBooks

Get	/GetBooks/Like/{name}

Get	/Book/GetBookById/{bookId}

Get	/Delete/{bookId}

Get	/Book/ConfirmBook/{bookId}

Get	/Book/GetPendingBooks")


Auction Service

Get	/Auction/GetAll

Get	/Auction/CreateAuction/{title}/{bookId}

Get	/Auction/GetAuctions/{title}

Get	/Auction/AddBid/{auctionId}/{amount}/{comment}

Get	/Auction/AddNewBookAuction/{title}/{name}/{author}

Get	/Auction/SoldItem/{auctionId}

Get	/Auction/DeleteBid/{auctionId}/{bidId}

Get	/Auction/ConfirmAuction/{auctionId}


Wishlist Service

Get	/Wishlist/Create/{title}

Get	/Wishlist/GetAll

Get	/Wishlist/GetById/{id}

Get	/Wishlist/GetByUser

Get	/Wishlist/AddBook/{bookId}/{wishListId}

Get	/Wishlist/DeleteBook/{bookId}/{wishListId}

Get	/Wishlist/Delete/{wishListId}

Get	/Wishlist/UpdateTitle/{wishListId}/{title}


Author Service

Get	/Author/GetAll

Get	/Author/AddSub/{authorName}

Get	/Author/DeleteSub/{authorName}


Authorization Service

Get	/Authorization/GetAll

Get	/Authorization/IsAdmin/{username}

Get	/Authorization/AddAdmin/{username}

Get	/Authorization/DeleteAdmin/{username}