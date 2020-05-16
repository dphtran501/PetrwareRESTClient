<?php

    require('db_conn.php');

    if (isset($_POST['cID']) && isset($_POST['pID']) && isset($_POST['quantity'])) {
        $cID = $_POST['cID'];
        $pID = $_POST['pID'];
        $quantity = $_POST['quantity'];

        $selectSql = "SELECT quantity FROM customer_cart WHERE customer_id=:cID AND product_id=:pID";
        $selectStmt = $conn->prepare($selectSql);
        $selectStmt->execute(array(':cID' => $cID, ':pID' => $pID));

        $record = $selectStmt->fetch(PDO::FETCH_ASSOC);
        // Update quantity if customer already has item in cart, else add item to cart
        if ($record) {
            $newQty = $record['quantity'] + $quantity;

            $updateSql = "UPDATE customer_cart SET quantity=:quantity WHERE customer_id=:cID AND product_id=:pID";
            $updateStmt = $conn->prepare($updateSql);
            $updateStmt->execute(array(':quantity' => $newQty, ':cID' => $cID, ':pID' => $pID));
        } 
        else {
            $insertSql = "INSERT INTO customer_cart (customer_id, product_id, quantity) VALUES (:cID, :pID, :quantity)";
            $insertStmt = $conn->prepare($insertSql);
            $insertStmt->execute(array(':cID' => $cID, ':pID' => $pID, ':quantity' => $quantity));
        }

    } else if (isset($_GET['cID'])) {
        $cID = $_GET['cID'];

        $sql = "SELECT * FROM customer_cart JOIN product ON product_id=id WHERE customer_id=:cID";
        $stmt = $conn->prepare($sql);
        $stmt->execute(array(':cID' => $cID));

        $vcSql = "SELECT customer_cart.*, product.*, product_video_card.gpu FROM customer_cart 
                    JOIN product ON customer_cart.product_id=product.id 
                    JOIN product_video_card ON product.id=product_video_card.product_id
                    WHERE customer_id=:cID AND product.id=:pID";
        $vcStmt = $conn->prepare($vcSql);

        $results = array();
        while ($record = $stmt->fetch(PDO::FETCH_ASSOC)) {
            // Need to get gpu for naming purposes
            if ($record['category'] == 'videoCard') {
                $vcID = $record['id'];
                $vcStmt->execute(array(':cID' => $cID, ':pID' => $vcID));
                while ($vsRecord = $vcStmt->fetch(PDO::FETCH_ASSOC)){
                    $results[] = $vsRecord;
                }
            } else {
                $results[] = $record;
            }
        }  

        print json_encode($results);
        
    }

    require('db_close.php');
?>