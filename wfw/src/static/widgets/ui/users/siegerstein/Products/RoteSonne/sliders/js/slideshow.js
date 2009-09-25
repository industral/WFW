/*jslint white: true, onevar: true, browser: true, undef: true, nomen: true, eqeqeq: true, plusplus: true, bitwise: true, regexp: true, strict: true, newcap: true, immed: true, indent: 2 */
/*global $: false*/

"use strict";

function SlideShow() {
  var self = this;

  this.timeEffect = 10000;
  this.currentAsset = 1;
  this.assetsArray = $("#slideShow").children().children();
  this.assetsCount = this.assetsArray.length;

  this.start = function () {
    $(self.assetsArray[0]).addClass('show').fadeTo(0, 1);

    setInterval(function () {
      self.next();
    }, this.timeEffect);
  };

  this.next = function () {
    if (self.currentAsset >= self.assetsCount) {
      self.currentAsset = 0;
    }

    if (self.currentAsset === 0) {
      if ($(self.assetsArray[self.assetsCount - 1]).hasClass('show')) {
        self.prevAsset = self.assetsCount - 1;
      } else {
        self.prevAsset = 0;
      }
    } else {
      self.prevAsset = self.currentAsset - 1;
    }

    if ($(self.assetsArray[self.prevAsset]).hasClass('show')) {
      self.currentAsset += 1;
      $(self.assetsArray[self.currentAsset]).addClass('show').fadeTo(
          1000,
          1,
          function () {
            $(self.assetsArray[self.prevAsset]).removeClass('show').attr(
                'style', '');
          });
    }
  };
}

setTimeout(function () {
  var slideShow = new SlideShow();
  slideShow.start();
}, 100);
