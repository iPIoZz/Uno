<?PHP
require_once 'functions/global.php';
if (!isset($_COOKIE['username'])) {
  redirect("/index.php");
}

?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="styles/styles.profile.css" />
    <title>Mon profil - <?php echo $user['username']; ?></title>
  </head>
  <body>
    <div class="shadow">
      <div class="bend">
        <h2>Bonjour, <?php echo $user['username']; ?></h2>
        <h3>Choisissez le jeu auquel vous voulez jouer</h3>
        <a href="logout.php">Se déconnecter</a>
      </div>

      <div class="games">
          <div class="card" id="uno" onclick="javascript:void(1);">
            <div class="card__content">
                <div class="card__title">UNO: THE GAME</div>
                <div class="card__subtitle">
                    1 à 5 joueurs
                </div>
                <p class="card__description">
                    Créé en 1971. Rejouez au meilleur jeu de cartes à plusieurs. Cliquez pour jouer
                </p>
            </div>
          </div>

          <div class="card" id="belote">
            <div class="card__content">
                <div class="card__title">BELOTE</div>
                <div class="card__subtitle">
                    En développement
                </div>
                <p class="card__description">
                    Malheureusement, la belote est toujours en cours de développement
                </p>
            </div>
          </div>

          <div class="card" id="snake">
            <div class="card__content">
                <div class="card__title">SNAKE</div>
                <div class="card__subtitle">
                    En développement
                </div>
                <p class="card__description">
                    Malheureusement, le snake est toujours en cours de développement
                </p>
            </div>
          </div>
      </div>
    </div>

    <!-- animation background -->
    <canvas class="background"></canvas>

    <script src="scripts/particles.js"></script>
    <script src="scripts/animation.js"></script>
    <script src="scripts/profile.js"></script>
  </body>
</html>
