<?PHP
require_once 'functions/global.php';
if (!isset($_COOKIE['username'])) {
  redirect("/index.php");
}

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles/styles.uno.css">
    <title>Game - UNO</title>
</head>
<body>
    
    <div class="container">
        <div class="column">
            <h2>Rejoignez une partie en ligne</h2>
            <div class="games__list">
                <div class="games__card">
                    <div class="games__card__header">
                        Partie de Léo<br>
                        <i>2 joueurs sur 4 en ligne </i>
                        <div class="games__card__button">Rejoindre la partie</div>
                    </div>
                </div>

                <div class="games__card">
                    <div class="games__card__header">
                        Partie de Ewan<br>
                        <i>2 joueurs sur 4 en ligne </i>
                        <div class="games__card__button deactivated">COMPLET</div>
                    </div>
                </div>

                <div class="games__card">
                    <div class="games__card__header">
                        Partie de Ilyass<br>
                        <i>2 joueurs sur 4 en ligne </i>
                        <div class="games__card__button deactivated">COMPLET</div>
                    </div>
                </div>
            </div>
        </div>

        <div class="column">
            <div class="games__create">
                <div class="games__create__header">
                    <h3>Créer une partie</h3>
                </div>
                <div class="games__create__body">
                    <div class="games__create__body__input">
                        <label for="name">Nom de la partie</label>
                        <input type="text" name="name" id="name">
                    </div>
                    <div class="games__create__body__input">
                        <label for="password">Mot de passe</label>
                        <input type="password" name="password" id="password">
                    </div>
                    <div class="games__create__body__input">
                        <label for="password">Confirmer le mot de passe</label>
                        <input type="password" name="password" id="password">
                    </div>
                </div>
                <div class="games__create__footer">
                    <div class="games__create__button">Commencer une nouvelle partie</div>
                </div>
            </div>
        </div>
    </div>
    <!-- animation background -->
	<canvas class="background"></canvas>

	<script src="scripts/particles.js"></script>
	<script src="scripts/index.js"></script>
</body>
</html>