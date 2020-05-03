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