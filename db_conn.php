<?php 
    require('db_credentials.php');

    $conn = new PDO("mysql:host=$hostname;port=$portNumber;dbname=$dbname", $username, $password);
    // Set PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

?>