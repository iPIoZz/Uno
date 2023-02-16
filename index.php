<?PHP
require_once 'functions/global.php';
$error = false;
$errorMessage = "";
$token = bin2hex(random_bytes(32));

if (isset($_COOKIE['username'])) {
	redirect("/profile.php");
}

/* Get POST */
if (isset($_POST['submit'])) {

	/* Get POST */
	$username = $_POST['username'];
	$password = $_POST['password'];

	/* Check if username is empty */
	if (empty($username) || $username == "") {
		$error = true;
		$errorMessage = "Le nom d'utilisateur est vide.";
	}

	/* Check if password is empty */
	if (empty($password)) {
		$error = true;
		$errorMessage = "Le mot de passe est vide.";
	}

	/* Check if username exists */
	$checkUsername = $bdd->prepare("SELECT * FROM users WHERE username = :username");
	$checkUsername->execute(array(
		'username' => $username
	));
	$checkUsername = $checkUsername->fetch();

	if (!$checkUsername) {
		$error = true;
		$errorMessage = "Le nom d'utilisateur n'existe pas.";
	}

	/* Check if password is correct */
	if (!password_verify($password, $checkUsername['password'])) {
		$error = true;
		$errorMessage = "Le mot de passe est incorrect.";
	}

	/* If no error, login */
	if (!$error) {
		//Set token
		$prep = $bdd->prepare("UPDATE users SET token = :token WHERE username = :username");
		$prep->execute(array(
			'token' => $token,
			'username' => $username
		));
		setcookie('username', $username, time() + 365 * 24 * 3600, null, null, false, true);
		setcookie('token', $token, time() + 365 * 24 * 3600, null, null, false, true);
		redirect("/profile.php");
	}
}

if (isset($_POST['register'])) {
	/* Get POST */
	$username = $_POST['usernameRegister'];
	$password = $_POST['passwordRegister'];

	/* Check if username is empty */
	if (empty($username) || $username == "") {
		$error = true;
		$errorMessage = "Le nom d'utilisateur est vide.";
	}

	/* Username length */
	if (strlen($username) < 3) {
		$error = true;
		$errorMessage = "Le nom d'utilisateur est trop court.";
	}

	/* Username length */
	if (strlen($username) > 20) {
		$error = true;
		$errorMessage = "Le nom d'utilisateur est trop long.";
	}

	/* Check if password is empty */
	if (empty($password) or $password == "") {
		$error = true;
		$errorMessage = "Le mot de passe est vide.";
	}

	/* Password length */
	if (strlen($password) < 8) {
		$error = true;
		$errorMessage = "Le mot de passe est trop court.";
	}

	/* Check if username exists */
	$checkUsername = $bdd->prepare("SELECT * FROM users WHERE username = :username");
	$checkUsername->execute(array(
		'username' => $username
	));
	$checkUsername = $checkUsername->fetch();

	if ($checkUsername) {
		$error = true;
		$errorMessage = "Le nom d'utilisateur existe déjà.";
	}

	/* If no error, register */
	if (!$error) {
		$register = $bdd->prepare("INSERT INTO users (username, password, addr_ip, date_signup) VALUES (:username, :password, :addr_ip, :date_signup)");
		$register->execute(array(
			'username' => $username,
			'password' => password_hash($password, PASSWORD_DEFAULT),
			'addr_ip' => $_SERVER['REMOTE_ADDR'],
			'date_signup' => date("Y-m-d H:i:s")
		));
		redirect("/profile.php");
		setcookie('username', $username, time() + 365 * 24 * 3600, null, null, false, true);
		setcookie('token', $token, time() + 365 * 24 * 3600, null, null, false, true);
	}
}
?>
<!DOCTYPE html>
<html lang="en">

<head>
	<link rel="stylesheet" href="./styles/styles.index.css">
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Uno.. Dos.. Tres - Games</title>
</head>

<body>

	<?PHP if ($error) { ?>
		<div class="error">
			<p>Erreur!
				<?PHP print $errorMessage; ?>
			</p>
		</div>
	<?PHP } else {
	} ?>
	<!-- login/signup form -->
	<div class="card" id="login">
		<div class="card-title">
			Bienvenue sur le site
		</div>

		<div class="card-desc">
			<i>Connectez-vous ou inscrivez-vous pour pouvoir jouer...</i>
		</div>

		<div class="sep"></div>

		<div class="card-content">
			<form method="POST" action="">
				<div class="input-form">
					<label>Nom d'utilisateur</label>
					<input type="text" name="username" placeholder="Entrez votre nom" class="input">
				</div>

				<div class="input-form">
					<label>Mot de passe</label>
					<input name="password" type="password" placeholder="Entrez votre mot de passe" class="input">
				</div>

				<div class="input-form">
					<button name="submit" type="submit" class="btn">Se connecter</button>
					<a href="#" onclick="toggleSignup();" class="button">S'inscrire</a>
				</div>
			</form>
		</div>
	</div>

	<!-- signup form -->
	<div class="hidden" id="signup">
		<div class="card-title">
			Inscription
		</div>

		<div class="card-desc">
			<i>Remplissez les champs ci-dessous pour vous inscrire...</i>
		</div>

		<div class="sep"></div>

		<div class="card-content">
			<form method="POST" action="">
				<div class="input-form">
					<label>Choisir un pseudo</label>
					<input name="usernameRegister" type="text" placeholder="Entrez votre nom" class="input">
				</div>

				<div class="input-form">
					<label>Choisir un mot de passe</label>
					<input name="passwordRegister" type="password" placeholder="Entrez votre mot de passe" class="input">
				</div>

				<div class="input-form">
					<button name="register" type="submit" class="btn">Valider l'inscription</button>
					<a href="#" onclick="toggleSignup();" class="button return">Retour</a>
				</div>
			</form>
		</div>
	</div>

	<!-- animation background -->
	<canvas class="background"></canvas>

	<script src="scripts/particles.js"></script>
	<script src="scripts/index.js"></script>
</body>

</html>