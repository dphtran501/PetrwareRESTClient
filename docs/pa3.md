# PA 3: Building a web application using Java Servlets

Petrware opens up on the home page which shows the list of products available for purchase. 

A navigation bar is at the top of the page with links to the homepage, "About" page, and "Checkout" page. Clicking on either "Petrware" or "Home" will take the user to the home page. Clicking on "About" will take the user to the "About" page. Clicking o the "My Cart" button will take the user to the "Checkout" page.

If a user clicks on a product's image in the homepage, they will be taken to that product's page. There they will find more information about the product, as well as add a specifed quantity of that product to their online shopping cart. Clicking the "Add to Cart" button will take the user to the "Checkout" page.

The "Checkout" page will show the products the user added to their online shopping cart, the running total cost of all items in the shopping cart, and a form to fill to complete the purchasing of items in the list. Filling out the form correctly and clicking the "Submit" button will direct the user to a summary page of the purchase.

The "About" page contains information pertaining to Petrware, their business, their members, and other information.

## Installation

This project requires installation of [MariaDB Connector/J 2.6.0](https://mariadb.com/kb/en/about-mariadb-connector-j/#installing-mariadb-connectorj)
and [Gson 2.8.6](https://github.com/google/gson). Both were installed using Maven (by updating [`pom.xml`](../pom.xml)). 

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
`PORT_NUMBER` is the port number used for MySQL. `Credentials.java` must be added to the [`java`](../src/main/java) folder.

## Requirements Checklist

- [x] **Include the output of two servlets to create the homepage for your e-commerce site: the first servlet should 
handle the displaying of the list of products obtained from a backend database, and the second servlet should use session 
tracking to display the last 5 products that the user has visited (viewed the product details page). In case this number 
is less than 5, show whatever amount of information you have stored in the session. You are required to use servlet 
"include" feature to implement this requirement.** 

    For the home page, [`main.js`](/scripts/main.js) makes a request to [`ProductListServlet`](/ProductListServlet.java) 
    to retrieve the list of products from the database. `ProductListServlet` then "includes" 
    [`LastViewedServlet`](../src/main/java/LastViewedServlet.java) to retrieve the last 5 (or less) products that the user 
    viewed on the product details page. `LastViewedServlet` is only "included" when the user has viewed at least one product.

- [x] **Using servlets create a "product details" page. This page should take a product identifier as a parameter and 
show the product details after getting the relevant information from the database. This page should NOT have an order 
form, only a button to "Add to Cart". Use servlet "session" to store the products in a shopping cart.** 

    When the user clicks a product on the  home page, `main.js` sends a request to `ProductListServlet` to add the ID of 
    the clicked product to the list of recently viewed products (used by `LastViewedServlet` above) stored in the 
    HttpSession. When the product details page loads, [`product.js`](../web/scripts/product.js) sends a request to 
    [`ProductServlet`](../src/main/java/ProductServlet.java) to retrieve the details of that product from the database. 
    `ProductServlet` uses the last product ID added to the aforementioned stored list of recently viewed products as a 
    parameter for retrieving the details from the database. Since the product details page loads right after a product 
    on the home page is clicked, the last product ID should be the product ID of the clicked product.
    
    When the user clicks the "Add to Cart" button, `product.js` sends a request to [`CartServlet`](../src/main/java/CartServlet.java) 
    to add ID of the product viewed on the product details page and the user-specified quantity to the cart list stored 
    in the HttpSession.

- [x] **Using servlets create a "check out" page, which allows the user to place an order. The page should show all the 
products in the shopping cart and the total price.** 

    [`checkout.js`](../web/scripts/checkout.js) sends a request to [`CartServlet`](../src/main/java/CartServlet.java) to retrieve 
    the cart items stored in the HttpSession. `checkout.js` then uses the data sent by `CartServlet` to calculate the subtotal.

    **This page should have a form which will allow the user to do the following:** 

    - [x] **Enter shipping information: name, shipping address, phone number, credit card number, etc.**
    
        The checkout page contains the form (in [`checkout.html`](../web/checkout.html)) where the user can enter their shipping information and more.

    - [x] **Submit the order for storage in the backend database**
        
        When the user clicks "Submit" on the form with valid information, the form sends a POST request to 
        [`CheckoutServlet`](../src/main/java/CheckoutServlet.java) which stores the inputted customer information in the database.
    
    - [x] **On successful submission, forward to the order details page. You are required to use servlet "forward" feature to implement this requirement.**
    
        After `CheckoutServlet` stores the customer information in the database as stated above, it forwards to the 
        "Checkout Summary" page ([`placedOrder.html`](../web/placedOrder.html)), where it populates the page with the order 
        details.

## Authors
Group 21: Jevford Barro (72114221), Peter Tang (45193375), Derek Tran (18491795)
