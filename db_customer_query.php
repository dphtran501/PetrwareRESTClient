<?php

    require('db_conn.php');

    $results = array();

    if (isset($_GET['cID'])) {
        $cID = $_GET['cID'];
        if ($cID == -1) {
            $sql = 'INSERT into customers (firstname, lastname) VALUES (\'UNSAVED\', \'CUSTOMER\')';
            $count = $conn->exec($sql);
            if ($count > 0) {
                $sql = 'SELECT id FROM customers WHERE id=LAST_INSERT_ID()';
                foreach($conn->query($sql, PDO::FETCH_ASSOC) as $record) {
                    $results[] = $record;
                }
            }
        }
    }

    print json_encode($results);

    require('db_close.php');
?>