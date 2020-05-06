<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>

    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
    <?php $filename = $_SERVER['SCRIPT_NAME']; ?>
    <?php include './header.php'; ?>

    <div class="search-div">
        <form id="search-form">
            <input type="search" id="search-input" name="searchQuery" placeholder="What do you want to search?">
            <input type="submit" id="search-submit" name="searchSubmit" value = "Search">
        </form>
    </div>

    <div class="productlist-container">
        <div class="productgrid-container"></div>
    </div>

    <script src="scripts/data.js"></script>
    <script src="scripts/main.js"></script>
</body>
</html>