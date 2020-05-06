<?php 
    require('db_conn.php');

    $results = array();    

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

        $sql = "SELECT * FROM product JOIN $categoryTable ON product.id=$categoryTable.product_id WHERE product.id=$id";     
        foreach($conn->query($sql, PDO::FETCH_ASSOC) as $record) {
            $results[] = $record;
        }

    } else if (isset($_GET['search'])) {
        $searchQuery = $_GET['search'];
        $wildcardSearchQuery = "%$searchQuery%";

        $sqlCPUStmt = $conn->prepare("SELECT * FROM product JOIN product_cpu ON product.id=product_cpu.product_id
            WHERE product.brand LIKE :brandQuery 
            OR product.name LIKE :nameQuery
            OR product.series LIKE :seriesQuery
            OR product.model LIKE :modelQuery");

        $sqlRAMStmt = $conn->prepare("SELECT * FROM product JOIN product_ram ON product.id=product_ram.product_id
            WHERE product.brand LIKE :brandQuery 
            OR product.name LIKE :nameQuery
            OR product.series LIKE :seriesQuery
            OR product.model LIKE :modelQuery");
        
        $sqlVideoCardStmt = $conn->prepare("SELECT * FROM product JOIN product_video_card ON product.id=product_video_card.product_id 
            WHERE product.brand LIKE :brandQuery 
            OR product.name LIKE :nameQuery
            OR product.series LIKE :seriesQuery
            OR product.model LIKE :modelQuery
            OR product_video_card.gpu LIKE :gpuQuery");

        $sqlCPUStmt->bindValue(':brandQuery', $wildcardSearchQuery);
        $sqlCPUStmt->bindValue(':nameQuery', $wildcardSearchQuery);
        $sqlCPUStmt->bindValue(':seriesQuery', $wildcardSearchQuery);
        $sqlCPUStmt->bindValue(':modelQuery', $wildcardSearchQuery);
        $sqlCPUStmt->execute();

        $sqlRAMStmt->bindValue(':brandQuery', $wildcardSearchQuery);
        $sqlRAMStmt->bindValue(':nameQuery', $wildcardSearchQuery);
        $sqlRAMStmt->bindValue(':seriesQuery', $wildcardSearchQuery);
        $sqlRAMStmt->bindValue(':modelQuery', $wildcardSearchQuery);
        $sqlRAMStmt->execute();

        $sqlVideoCardStmt->bindValue(':brandQuery', $wildcardSearchQuery);
        $sqlVideoCardStmt->bindValue(':nameQuery', $wildcardSearchQuery);
        $sqlVideoCardStmt->bindValue(':seriesQuery', $wildcardSearchQuery);
        $sqlVideoCardStmt->bindValue(':modelQuery', $wildcardSearchQuery);
        $sqlVideoCardStmt->bindValue(':gpuQuery', $wildcardSearchQuery);
        $sqlVideoCardStmt->execute();

        $results = array();
        while ($record = $sqlCPUStmt->fetch(PDO::FETCH_ASSOC)){
            $results[] = $record;
        }
        while ($record = $sqlRAMStmt->fetch(PDO::FETCH_ASSOC)){
            $results[] = $record;
        }
        while ($record = $sqlVideoCardStmt->fetch(PDO::FETCH_ASSOC)){
            $results[] = $record;
        }

    } else {
        $sqlStatements = array("SELECT * FROM product JOIN product_cpu ON product.id=product_cpu.product_id",
                            "SELECT * FROM product JOIN product_ram ON product.id=product_ram.product_id",
                            "SELECT * FROM product JOIN product_video_card ON product.id=product_video_card.product_id");

        foreach($sqlStatements as $sql){
            foreach($conn->query($sql, PDO::FETCH_ASSOC) as $record) {
                $results[] = $record;
            }
        }
    }

    print json_encode($results);

    require('db_close.php');
?>