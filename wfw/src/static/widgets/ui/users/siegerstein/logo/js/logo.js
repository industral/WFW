function logoEffect(){
  var k = 333;
  var randVal = Math.random() * k;
  
  setTimeout(function(){
    var opacityVal = (randVal / k).toFixed(2);
    $("#textLogo").css("opacity", opacityVal < 0.15 ? 0.15 : opacityVal);
    logoEffect();
  }, randVal);
}

function setUpSound() {
  document.getElementById("sound").volume = 0.15;
  document.getElementById("sound").play();
}
