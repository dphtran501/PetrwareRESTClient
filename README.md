# INF124 - PA 2: Building a web application using PHP, Ajax, and MySQL 
**_NEED URL TO NEW SITE_**

Petrware opens up on the home page which shows the list of products available for purchase. 

A navigation bar is at the top of the page with links to the homepage, "About" page, and "Checkout" page. Clicking on either "Petrware" or "Home" will take the user to the home page. Clicking on "About" will take the user to the "About" page. Clicking o the "My Cart" button will take the user to the "Checkout" page.

If a user clicks on a product's image in the homepage, they will be taken to that product's page. There they will find more information about the product, as well as add a specifed quantity of that product to their online shopping cart. Clicking the "Add to Cart" button will take the user to the "Checkout" page.

The "Checkout" page will show the products the user added to their online shopping cart, the running total cost of all items in the shopping cart, and a form to fill to complete the purchasing of items in the list. Filling out the form correctly and clicking the "Submit" button will open the user's e-mail client with e-mail body containing details about the purchase.

The "About" page contains information pertaining to Petrware, their business, their members, and other information.

**_NEED REVISED DB PASSWORD SPECIFIED (probably don't push to Github)_** 

## Requirements Checklist

- [ ] **You want to use PHP and MySQL database to generate the product information dynamically. The information about available products should be read from one or more tables in your database and the corresponding HTML pages describing the details of your products should be generated dynamically. You will use PHP to query your MySQL database to obtain the details of a product and generate the proper content in HTML format.** 

- [ ] **When the user submits a form to order a product, instead of sending an email from the client-side, as you did in first assignment, the request should be sent to a server-side PHP script that stores that information in a database table. The form should be validated to prevent insertion of bad data in your database.** 

- [ ] **After successfully storing the order information in a database table, a dynamically generated confirmation page should to be displayed to the user with the details of the order.**

- [ ] **Use Ajax to make your website dynamic and interactive. Among others, you could use Ajax to assist the user with filling the order forms, e.g., when the user chooses a particular state for delivery, obtain the corresponding tax rate from the backend database to update the total price for the product dynamically, or as another example, provide auto complete capability, such as suggesting states as the user types the name of a state. You can use these files to help with this task: zip codes and tax rates. You have freedom in identifying other opportunities for using Ajax in making your website dynamic and interactive. At the very least, your website should make use of Ajax for two non-trivial features that the grader can verify.** 

## Authors
Group 21: Jevford Barro (72114221), Peter Tang (45193375), Derek Tran (18491795)

## Project Status
- [x] [PA 1: Building a dynamic website using HTML, JavaScript, and CSS](docs/pa1.md)
- [ ] PA 2: Building a web application using PHP, Ajax, and MySQL 
- [ ] PA 3: Building a web application using Java Servlets 
- [ ] PA 4: Building a web application using JSP and RESTful web services 
