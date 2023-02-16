<?php
require_once 'functions/global.php';
if (isset($_COOKIE['username'])) {
    setcookie('username');
    redirect("/index.php");
}
redirect("/index.php");
?>