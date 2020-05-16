# INF124 - PA 2: Building a web application using PHP, Ajax, and MySQL 

Petrware opens up on the home page which shows the list of products available for purchase. 

A navigation bar is at the top of the page with links to the homepage, "About" page, and "Checkout" page. Clicking on either "Petrware" or "Home" will take the user to the home page. Clicking on "About" will take the user to the "About" page. Clicking o the "My Cart" button will take the user to the "Checkout" page.

If a user clicks on a product's image in the homepage, they will be taken to that product's page. There they will find more information about the product, as well as add a specifed quantity of that product to their online shopping cart. Clicking the "Add to Cart" button will take the user to the "Checkout" page.

The "Checkout" page will show the products the user added to their online shopping cart, the running total cost of all items in the shopping cart, and a form to fill to complete the purchasing of items in the list. Filling out the form correctly and clicking the "Submit" button will direct the user to a summary page of the purchase.

The "About" page contains information pertaining to Petrware, their business, their members, and other information.

## Usage
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

- [x] **You want to use PHP and MySQL database to generate the product information dynamically. The information about available products should be read from one or more tables in your database and the corresponding HTML pages describing the details of your products should be generated dynamically. You will use PHP to query your MySQL database to obtain the details of a product and generate the proper content in HTML format.** 

    When the home page is loaded, [main.js](../web/scripts/main.js) uses [db_product_query.php](../web/db_product_query.php) to retrieve product data from the database top populate the product table. Similarly, [product.js](../web/scripts/product.js) uses the same PHP file to fill a specific product's page with that product's information. 

- [x] **When the user submits a form to order a product, instead of sending an email from the client-side, as you did in first assignment, the request should be sent to a server-side PHP script that stores that information in a database table. The form should be validated to prevent insertion of bad data in your database.** 

    Created php scripts for sanitization/validation of user input in case of sql injections and other attacks on the database.  Form information is sent to the database via PHP files that utilize PDO.

- [x] **After successfully storing the order information in a database table, a dynamically generated confirmation page should to be displayed to the user with the details of the order.**

    A summary page of the user's form information as well as the items purchased is dynamically generated after validation and submission of data to the database. The summary conveys the user's personal info, credit card number, items purchased, and the total amount charged to the customer's card.

- [x] **Use Ajax to make your website dynamic and interactive. Among others, you could use Ajax to assist the user with filling the order forms, e.g., when the user chooses a particular state for delivery, obtain the corresponding tax rate from the backend database to update the total price for the product dynamically, or as another example, provide auto complete capability, such as suggesting states as the user types the name of a state. You can use these files to help with this task: zip codes and tax rates. You have freedom in identifying other opportunities for using Ajax in making your website dynamic and interactive. At the very least, your website should make use of Ajax for two non-trivial features that the grader can verify.** 

    Ajax is used for the following dynamic features:
    * Automatically fill in city and state input fields in checkout form when valid zipcode is entered.
    * Automatically calculate tax based on zipcode entered in checkout form.
    * Automatically generates the user's cart on the summary page.

## Authors
Group 21: Jevford Barro (72114221), Peter Tang (45193375), Derek Tran (18491795)