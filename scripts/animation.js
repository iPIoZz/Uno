var particles = Particles.init({
	selector: '.background',
  sizeVariations: 4,
  maxParticles: 200,
  color: ['#DBEDF3', '#DBEDF3', '#DBEDF3'],
  connectParticles: true
});

var loginUp = true;
var toggleSignup = function()
{
    if (loginUp)
    {
        let el = document.querySelector("#login");
        el.classList.add("hidden");
        el.classList.remove("card");

        let sign = document.querySelector("#signup");
        sign.classList.remove("hidden");
        sign.classList.add("card");

        loginUp = false;
    }
    else
    {
        let el = document.querySelector("#login");
        el.classList.remove("hidden");
        el.classList.add("card");

        let sign = document.querySelector("#signup");
        sign.classList.add("hidden");
        sign.classList.remove("card");
        loginUp = true;
    }
};