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



Auction Service

Wishlist Service

Author Service



Authorization Service


