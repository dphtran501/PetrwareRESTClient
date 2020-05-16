# INF124 - PA 3: Building a web application using Java Servlets

Petrware opens up on the home page which shows the list of products available for purchase. 

A navigation bar is at the top of the page with links to the homepage, "About" page, and "Checkout" page. Clicking on either "Petrware" or "Home" will take the user to the home page. Clicking on "About" will take the user to the "About" page. Clicking o the "My Cart" button will take the user to the "Checkout" page.

If a user clicks on a product's image in the homepage, they will be taken to that product's page. There they will find more information about the product, as well as add a specifed quantity of that product to their online shopping cart. Clicking the "Add to Cart" button will take the user to the "Checkout" page.

The "Checkout" page will show the products the user added to their online shopping cart, the running total cost of all items in the shopping cart, and a form to fill to complete the purchasing of items in the list. Filling out the form correctly and clicking the "Submit" button will direct the user to a summary page of the purchase.

The "About" page contains information pertaining to Petrware, their business, their members, and other information.

## Usage

**_NEED TO CHANGE???_**

To connect to the MySQL database, the source code must include a "db_credentials.php" file containing the user's database credentials. The file is structured as so:

```php
<?php 
    $hostname = <YOUR_HOST_NAME>;
    $portNumber = <YOUR_PORT_NUMBER>;
    $username = <YOUR_USERNAME>;
    $password = <YOUR_PASSWORD>;
    $dbname = "petrware_db";
?>
```
`$portNumber` is the port number used for MySQL.

## Requirements Checklist

- [ ] **Include the output of two servlets to create the homepage for your e-commerce site: the first servlet should handle the displaying of the list of products obtained from a backend database, and the second servlet should use session tracking to display the last 5 products that the user has visited (viewed the product details page). In case this number is less than 5, show whatever amount of information you have stored in the session. You are required to use servlet "include" feature to implement this requirement.** 



- [ ] **Using servlets create a "product details" page. This page should take a product identifier as a parameter and show the product details after getting the relevant information from the database. This page should NOT have an order form, only a button to "Add to Cart". Use servlet "session" to store the products in a shopping cart.** 

    

- [ ] **Using servlets create a "check out" page, which allows the user to place an order. The page should show all the products in the shopping cart and the total price. This page should have a form which will allow the user to do the following:** 

    - [ ] **Enter shipping information: name, shipping address, phone number, credit card number, etc.**

    - [ ] **Submit the order for storage in the backend database**
    
    - [ ] **On successful submission, forward to the order details page. You are required to use servlet "forward" feature to implement this requirement.**

## Authors
Group 21: Jevford Barro (72114221), Peter Tang (45193375), Derek Tran (18491795)

## Project Status
- [x] [PA 1: Building a dynamic website using HTML, JavaScript, and CSS](docs/pa1.md)
- [x] [PA 2: Building a web application using PHP, Ajax, and MySQL](docs/pa2.md) 
- [ ] PA 3: Building a web application using Java Servlets 
- [ ] PA 4: Building a web application using JSP and RESTful web services 
