<?PHP
$error = true;
$errorMessage = "";

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
		</div>
	<?PHP } else { } ?>
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
					<input type="text" placeholder="Entrez votre nom" class="input">
				</div>

				<div class="input-form">
					<label>Mot de passe</label>
					<input type="password" placeholder="Entrez votre mot de passe" class="input">
				</div>

				<div class="input-form">
					<button type="submit" class="btn">Se connecter</button>
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
					<input type="text" placeholder="Entrez votre nom" class="input">
				</div>

				<div class="input-form">
					<label>Choisir un mot de passe</label>
					<input type="password" placeholder="Entrez votre mot de passe" class="input">
				</div>

				<div class="input-form">
					<button type="submit" class="btn">Valider l'inscription</button>
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