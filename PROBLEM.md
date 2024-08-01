# Problem Statement
You are tasked with developing a Spring Boot REST API for managing a simple in-memory catalog of books. The API should provide endpoints to perform CRUD (Create, Read, Update, Delete) operations on the catalog. This assignment will help you understand the basics of building RESTful services using Spring Boot without involving a database.

## Instructions
1.  Model Class:
   - Create a Book class with the following attributes:

    `public class Book {
    private String id;
    private String title;
    private String author;
    private String genre;
    private double price;
    }`
- Use Lombok annotations to generate getters, setters, and constructors.
2. Controller:

   - Create a BookController class with the following endpoints:
     - GET /books: Retrieve the list of all books.
     - GET /books/{id}: Retrieve a single book by its ID.
     - POST /books: Add a new book to the catalog.
     - PUT /books/{id}: Update an existing book's details.
     - DELETE /books/{id}: Remove a book from the catalog.
3. Service Layer:

   - Create a BookService class to handle the business logic. This class should use a List<Book> to store the books in memory.
   - Implement methods for each CRUD operation in the service class.
4. Exception Handling:

   - Create a custom exception BookNotFoundException to handle cases where a book is not found.
   - Implement a global exception handler using @ControllerAdvice to handle BookNotFoundException.

## Detailed Endpoint Requirements
1. GET /books

   - Should return a list of all books in the catalog.
   - Response example:

    `[
    {
    "id": "1",
    "title": "Book Title",
    "author": "Author Name",
    "genre": "Genre",
    "price": 19.99
    },
    ...
    ]`

2. GET /books/{id}

   - Should return the details of a single book identified by its ID.
   - Response example:
  
   `{
   "id": "1",
   "title": "Book Title",
   "author": "Author Name",
   "genre": "Genre",
   "price": 19.99
   }`

3. POST /books

   - Should accept a Book object and add it to the catalog.
   - Request body example:

    `{
    "title": "New Book",
    "author": "New Author",
    "genre": "New Genre",
    "price": 29.99
    }`
   - The id field should be generated within the service.
4. PUT /books/{id}

   - Should accept a Book object and update the details of the book identified by its ID.
   - Request body example:

    `{
    "title": "Updated Book Title",
    "author": "Updated Author",
    "genre": "Updated Genre",
    "price": 24.99
    }`
5. DELETE /books/{id}

   - Should remove the book identified by its ID from the catalog.
   - Response status: 204 No Content.
