<?php 
    require('db_conn.php');

    $sqlStatements = array();

    if (isset($_GET['id']) && isset($_GET['category'])){
        $id = $_GET['id'];
        $category = $_GET['category'];

        $categoryTable = '';
        switch ($category) {
            case 'cpu':
                $categoryTable = 'product_cpu';
                break;
            case 'ram':
                $categoryTable = 'product_ram';
                break;
            case 'videoCard':
                $categoryTable = 'product_video_card';
        }

        $sqlStatements[] = "SELECT * FROM product JOIN $categoryTable ON product.id=$categoryTable.product_id WHERE product.id=$id";
    } else {
        $sqlStatements = array("SELECT * FROM product JOIN product_cpu ON product.id=product_cpu.product_id",
                            "SELECT * FROM product JOIN product_ram ON product.id=product_ram.product_id",
                            "SELECT * FROM product JOIN product_video_card ON product.id=product_video_card.product_id");
    }

    $results = array();

    foreach($sqlStatements as $sql){
        foreach($conn->query($sql, PDO::FETCH_ASSOC) as $record) {
            $results[] = $record;
        }
    }

    print json_encode($results);

    require('db_close.php');
?>