<?php
    require('db_conn.php');

    $results = array();

    if (isset($_GET['zipcode'])) {
        $stmt = $conn->prepare("SELECT state, city FROM zip_codes WHERE zip=:zip");
        $stmt->execute(array(':zip' => $_GET['zipcode']));
        
        while($record = $stmt->fetch(PDO::FETCH_ASSOC)){
            $results[] = $record;
        }
    }

    print json_encode($results);

    require('db_close.php');
?>