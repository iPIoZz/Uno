<?PHP
require_once 'bdd.php';

function redirect($url)
{
    header('location:' . $url);
}

if (isset($_COOKIE['username'])) {
    $prep = $bdd->prepare("SELECT * FROM users WHERE username = :username");
    $prep->execute(array(
        'username' => $_COOKIE['username']
    ));
    $user = $prep->fetch();
}
?>