/*jslint white: true, onevar: true, browser: true, undef: true, nomen: true, eqeqeq: true, plusplus: true, bitwise: true, regexp: true, strict: true, newcap: true, immed: true, indent: 2 */
/*global $: false*/

"use strict";

function logoEffect() {
  var self = this;

  this.k = 333;
  this.randVal = Math.random() * this.k;

  setTimeout(function () {
    var opacityVal = (self.randVal / self.k).toFixed(2);
    $("#textLogo").css("opacity", opacityVal < 0.15 ? 0.15 : opacityVal);
    logoEffect();
  }, self.randVal);
}

function setUpSound() {
  document.getElementById("sound").volume = 0.15;
  document.getElementById("sound").play();
}
