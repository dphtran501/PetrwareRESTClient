<?php
    require('db_conn.php');

    //CUSTOMER INFO
    if( isset($_POST['firstName']) && isset($_POST['lastName']) && isset($_POST['phone']) &&
        isset($_POST['country']) && isset($_POST['streetAddress']) && isset($_POST['city']) &&
        isset($_POST['state']) && isset($_POST['zipcode']) && isset($_POST['shipping']) && isset($_POST['email'])) {

        $sql = "INSERT INTO customers (firstName, lastName, phone, country, streetAddress, city, state, zipcode,
                shipping, email) VALUES(:firstName, :lastName, :phone, :country, :streetAddress, :city, :state, :zipcode,
                                        :shipping, :email)";

        $stmt = $conn->prepare($sql);
        $stmt->execute(array(
            ':firstName' => $_POST['firstName'],
            ':lastName' => $_POST['lastName'],
            ':phone' => $_POST['phone'],
            ':country' => $_POST['country'],
            ':streetAddress' => $_POST['streetAddress'],
            ':city' => $_POST['city'],
            ':state' => $_POST['state'],
            ':zipcode' => $_POST['zipcode'],
            ':shipping' => $_POST['shipping'],
            ':email' => $_POST['email'],));

    }

    //CREDIT CARD INFO
    if( isset($_POST['cardNumber']) && isset($_POST['expiration']) && isset($_POST['securityCode'])) {

        $sql = "INSERT INTO creditcards VALUES(LAST_INSERT_ID(), :cardNumber, :expiration, :securityCode)";

        $stmt = $conn->prepare($sql);
        $stmt->execute(array(
            ':cardNumber' => $_POST['cardNumber'],
            ':expiration' => $_POST['expiration'],
            ':securityCode' => $_POST['securityCode']));
    }

    // Update User-Cart Relational Table

    /* 
        BIG CHANGE NEEDED FOR DB, must have a primary key to join relational data customer_id, product_id, creditcard_it, purchase_id
        CREATE TABLE customer_cart (
            'customer_id' INT NOT NULL,
            'product_id' INT NOT NULL,
            'quantity' INT NOT NULL,
            PRIMARY_KEY('customer_id, product_id') 
        );
        SELECT c.*, p.*
        FROM customer c
        INNER JOIN customer_cart customCart
        ON customCart.customer_id = c.id
        INNER JOIN products p
        ON p.id = customCart.product_id
    */

    require('db_close.php');
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>

    <link rel="stylesheet" href="styles/main.css">
    <link rel="stylesheet" href="styles/placedOrder.css">
</head>
<body>
    <?php $filename = $_SERVER['SCRIPT_NAME']; ?>
    <?php include './header.php'; ?>

    <div class="placed">
        <h1 class="placedOrder">Thank You For Purchasing From Petrware</h1>
        <h1 class="emailReceipt">Your Receipt Should Be Sent To Your Email</h1>

        <!-- Update Page using AJAX to show User's Cart and Info -->
    </div>

    <script src="scripts/data.js"></script>
    <script src="scripts/main.js"></script>
</body>
</html>