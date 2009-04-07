function logoEffect(){
  var k = 1000;
  var randVal = Math.random() * k;
  
  setTimeout(function(){
    var opacityVal = (randVal / k).toFixed(2);
    $("#textLogo").css("opacity", opacityVal < 0.25 ? 0.25 : opacityVal);
    logoEffect();
  }, randVal);
}
