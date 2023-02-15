<?PHP
/* Connect database */
$user = "root";
$password = "";
$database = "user";

try {
    $bdd = new PDO('mysql:host=' . $host . ';dbname=' . $database, $user, $password);
} catch (PDOException $e) {
    print "Erreur: " . $e->getMessage();
    die();
}
?>