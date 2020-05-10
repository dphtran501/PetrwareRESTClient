<?php
    require('db_conn.php');

    //CUSTOMER INFO
    if( isset($_POST['firstName']) && isset($_POST['lastName']) && isset($_POST['phone']) &&
        isset($_POST['country']) && isset($_POST['streetAddress']) && isset($_POST['city']) &&
        isset($_POST['zipcode']) && isset($_POST['shipping']) && isset($_POST['email'])) {

        $sql = "INSERT INTO customers (firstName, lastName, phone, country, streetAddress, city, zipcode,
                shipping, email) VALUES(:firstName, :lastName, :phone, :country, :streetAddress, :city, :zipcode,
                                        :shipping, :email)";

        $stmt = $conn->prepare($sql);
        $stmt->execute(array(
            ':firstName' => $_POST['firstName'],
            ':lastName' => $_POST['lastName'],
            ':phone' => $_POST['phone'],
            ':country' => $_POST['country'],
            ':streetAddress' => $_POST['streetAddress'],
            ':city' => $_POST['city'],
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
    </div>

    <script src="scripts/data.js"></script>
    <script src="scripts/main.js"></script>
</body>
</html>