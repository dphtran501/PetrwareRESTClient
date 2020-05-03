<header>
        <h1 class="siteName"><a href="index.php">Petrware</a></h1>
        <nav>
            <ul class="nav__links">
                <?php 
                    $home = ''; $about = '';
                    if ($filename == '/inf124-project1/index.php'){
                        $home = 'current';
                    }
                    else if ($filename == '/inf124-project1/about.php'){
                        $about = 'current';
                    }
                ?>

                <li><a class="<?php echo $home ?>" href="index.php">Home</a></li>
                <li><a class="<?php echo $about ?>" href="about.php">About</a></li>
            </ul>
        </nav>
        <a href="checkout.php" class="cart"><button><img id="cart" src="icons/cart.png" alt="Cart Icon"> My Cart</button></a>
    </header>