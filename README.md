# INF124 - PA 1 (Building a dynamic website using HTML, JavaScript, and CSS)

**_(NEED TO ADD WEBSITE DESCRIPTION)_**

Link to website: **_(NEED TO ADD)_**

## Requirements Checklist
**_(NEED TO SHOW PROOF)_**

- [x] **An overview of your business, the products you sell, the management team, and any other information that you think makes sense for the customers to know about your company.**

   All of the above items are addressed in the "About" page.

- [x] **Display a list of products (at least 10) available for sale in a table with multiple rows and column, where each product is shown within a separate cell.**

   Ten products are shown in the homepage.

- [x] **Display an image for each product available for sale in each cell.**

   Each product on the homepage has an image associated with it.
   
- [x] **Display the price and other key information (e.g., color, material, etc.) associated with each product in the corresponding table cell.**
   
   Each product on the page shows their price, name, and a bullet-point list of attributes specific for that product.
   
- [x] **The user should be able to choose a product from this table by clicking on the corresponding image, which should lead to a new page that provides additional details about the product, e.g., more images, detailed description, etc.** 

   Clicking on a product's image takes the user to the product's page, containing a detailed description of the product as well as a specifications table.

- [x] **On the detailed description page, the user should be able to order a product by filling a form. The form should allow the user to enter the product identifier, quantity, first name, last name, phone number, shipping address, shipping method (e.g., overnight, 2-days expedited, 6-days ground), credit card information, and anything else that you think makes sense for your business.**

   On a product's page, the user can specify the quantity of that product that they want to purchase by inputting a number in the box next to the price. When they click the "Add to Cart" button, the user will then be taken to the "Checkout" page which lists the products they currently have in the cart. On the "Checkout" page, there's a form for the user to input their first and last names, phone number, shipping address, shipping method, e-mail, and credit card information.

- [x] **Upon submitting the form, the website should send an email with the purchase order information included in the body of the email. Note that to really send an email, one needs to connect to the SMTP server, which is not possible with the client-side software. Thus, this requirement simply requires bringing up the email client with the purchase order information included in the body of the email and allowing the user to send the email.**

   On the "Checkout" page, if the form is filled out correctly, clicking the "Submit" button will open the user's e-mail client with the purchase order information in the e-mail's body. The receiver is set to the e-mail address provided in the form.

- [x] **Before submitting the form, it should be checked for proper formatting, including whether all fields are filled properly, whether the phone number, address, and credit card are properly formatted, etc. An alarm should be raised if a field is empty or not properly formatted to prevent submission of bad data.** 

   If the form on the "Checkout" page is not filled correctly, when the user clicks the "Submit" button the erroneous fields will be highlighted and text will show informing the user why the fields are erroneous.
   
- [x] **Your website should use CSS to specify at least 10 stylistic properties for your website, such as the background for your table, the color and size of your font, the size of your images, and other stylistic preferences you may have.**

   CSS was used for many stylistic properties, including:
   * Dynamically sized product table in home page
   * Borders between cells in product table in homepage and specification table in product page.
   * Dynamically sized product images
   * Color, size, and weight of fonts
   * Styling of buttons
   * Scrolling background in "About" page
   * Dynamically sized navigation bar
   * Underlining in navigation bar to inform user of currently viewed page
   * Dynamically sized item list in "Checkout" page
   * Styling of inputs in form in "Checkout" page
   * ...and much more!

- [x] **Provide the ability to track the mouse movement, such that when the mouse moves over a product image, the size of the image is increased, and when the mouse moves out, the size of the image is returned back to normal. This feature can be implemented on either the page that displays the various products, or on the pages that show the details of each product, or both.**

  On the homepage, when a user hovers their mouse over a product's image, the image's size is increased. When they move the mouse away from the image, it goes back to its normal size.

- [x] **Print the name and the StudentID of group members on the webpage.**

  The group member information is located in the "Team Members" section of the "About" page.

## Authors
Group 21: Jevford Barro (72114221), Peter Tang (45193375), Derek Tran (18491795)
