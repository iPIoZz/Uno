<?PHP
require_once 'bdd.php';

function redirect($url)
{
    header('location:' . $url);
}
?>