# INF124 - PA 3: Building a web application using Java Servlets

Petrware opens up on the home page which shows the list of products available for purchase. 

A navigation bar is at the top of the page with links to the homepage, "About" page, and "Checkout" page. Clicking on either "Petrware" or "Home" will take the user to the home page. Clicking on "About" will take the user to the "About" page. Clicking o the "My Cart" button will take the user to the "Checkout" page.

If a user clicks on a product's image in the homepage, they will be taken to that product's page. There they will find more information about the product, as well as add a specifed quantity of that product to their online shopping cart. Clicking the "Add to Cart" button will take the user to the "Checkout" page.

The "Checkout" page will show the products the user added to their online shopping cart, the running total cost of all items in the shopping cart, and a form to fill to complete the purchasing of items in the list. Filling out the form correctly and clicking the "Submit" button will direct the user to a summary page of the purchase.

The "About" page contains information pertaining to Petrware, their business, their members, and other information.

## Installation

**_NEED TO CHANGE?_**

This project requires installation of [MariaDB Connector/J 2.6.0](https://mariadb.com/kb/en/about-mariadb-connector-j/#installing-mariadb-connectorj)
and [Gson 2.8.6](https://github.com/google/gson). Both were installed using Maven (by updating [`pom.xml`](pom.xml)). 

To connect to the MySQL database, the source code must include a `Credentials.java` file containing the user's database 
credentials. The file is structured as so:

```java
public class Credentials {
    public static final String DB_USERNAME = "YOUR_USERNAME";
    public static final String DB_PASSWORD = "YOUR_PASSWORD";
    public static final String DATABASE = "petrware_db";
    public static final String HOSTNAME = "YOUR_HOST_NAME";
    public static final int PORT_NUMBER = "YOUR_PORT_NUMBER";
}
```
`PORT_NUMBER` is the port number used for MySQL. `Credentials.java` must be added to the [`java`](src/main/java) folder.

## Requirements Checklist

- [ ] **Using JSP reimplement the product list page. This is the page that contains the list of your products.** 

- [ ] **Create REST services to allow for interaction with the order and product resources stored in your application 
database. You will need to implement services that use the following verbs**
    - **GET**
    - **PUT**
    - **POST**
    - **DELETE**
    
    **You only need to implement the services that your web app needs, e.g.,  if you do not need to delete data, you do 
    not need to implement the corresponding DELETE web service.**
  
    **Ensure that proper REST principles and conventions are followed while creating your REST services. For example: A 
    GET method should be used only for retrieving an existing resource, A POST method should be used for creating a new 
    resource, etc. Do not forget to perform validation for certain methods. For instance, when implementing methods like
     GET, PUT and DELETE to interact with existing resources, you will need to verify if the resource being queried 
     actually exists. If the resource is not found. You will need to throw a 404 - Not Found response.**
  
    **You are required to create your new REST service application in Java. It is mandatory that you use the Jersey REST 
    framework.  For the scope of this assignment, it is sufficient that your REST services communicate in JSON. (You are 
    free to implement support for other media types, but you will have to make sure that your e-commerce web application 
    communicates successfully with the REST service.)**
    
- [ ] **You will now need to replace all the database interactions in your web application with REST calls. Your web 
application will now act as a REST client and retrieve the MySQL data indirectly through the new RESTful web service. 
That is, you will have two applications: (1) a backend application that provides RESTful APIs that essentially exposes 
the available operations in your database, and (2) an application that is the client of the RESTful APIs, generates the 
HTML pages, and handles requests from the user.  While in this assignment you are developing both applications yourself, 
in practice, each application may be developed by a separate company. For example, companies such as Google, Amazon, and 
PayPal may develop the RESTful APIs that allow others to leverage their services in building their web applications.** 

    **In the readme file, provide proper documentation highlighting the details for each RESTful service method that you 
implement. Your documentation should include the following at the very least**
    - **Method Type**
    - **Request URL**
    - **Sample Response**
    - **Sample Request (if applicable)**
      
    **To aid you with implementing this assignment, a Todo service and its client are made available on Canvas. Each zip 
    file is a NetBeans project that has been exported. You should be able to download the zip file and then use the 
    Import feature of Netbeans to reconstruct the project. You will then have to update the database connection 
    parameters and URL patterns to match your environment before deploying and testing the projects.** 

## Authors
Group 21: Jevford Barro (72114221), Peter Tang (45193375), Derek Tran (18491795)

## Project Status
- [x] [PA 1: Building a dynamic website using HTML, JavaScript, and CSS](docs/pa1.md)
- [x] [PA 2: Building a web application using PHP, Ajax, and MySQL](docs/pa2.md) 
- [X] [PA 3: Building a web application using Java Servlets](docs/pa3.md)
- [ ] PA 4: Building a web application using JSP and RESTful web services 
