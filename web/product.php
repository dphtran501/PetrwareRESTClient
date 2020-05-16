<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product</title>
    
    <link rel="stylesheet" href="styles/main.css">
    <link rel="stylesheet" href="styles/product.css">
</head>
<body>
    <?php $filename = $_SERVER['SCRIPT_NAME']; ?>
    <?php include './header.php'; ?>

    <div class=product-details__container>
        <img id="product-image" src="images/default-product.png">
        <div id="price__container">
            <p id="product-price">Product Price</p>
            <div id="add-container">
                <input type="number" id="quantity-input" min="0" value="0" required>
                <button id="add-button">Add to Cart</button>
            </div>
        </div>
        <p id="product-name">Product Name</p>
        <p id="product-model">Product Model</p>
        <p id="product-details">Product Details</p>
        <table id="product-table"></table>
    </div>

    <script src="scripts/product.js"></script>
</body>
</html>