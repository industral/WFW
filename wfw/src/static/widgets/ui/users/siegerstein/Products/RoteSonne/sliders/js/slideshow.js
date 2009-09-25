function SlideShow() {
  var self = this;

  const timeEffect = 10000;

  this.currentAsset = 1;
  this.assetsArray = $("#slideShow").children().children();
  this.assetsCount = this.assetsArray.length;

  this.start = function() {
    $(self.assetsArray[0]).addClass('show').fadeTo(0, 1);

    setInterval(function() {
      self.next();
    }, timeEffect);
  }

  this.next = function() {
    if (self.currentAsset >= self.assetsCount) {
      self.currentAsset = 0;
    }

    if (self.currentAsset == 0) {
      if ($(self.assetsArray[self.assetsCount - 1]).hasClass('show')) {
        self.prevAsset = self.assetsCount - 1;
      } else {
        self.prevAsset = 0;
      }
    } else {
      self.prevAsset = self.currentAsset - 1;
    }

    if ($(self.assetsArray[self.prevAsset]).hasClass('show')) {
      $(self.assetsArray[self.currentAsset++]).addClass('show').fadeTo(
          1000,
          1,
          function() {
            $(self.assetsArray[self.prevAsset]).removeClass('show').attr(
                'style', '');
          });
    }
  }
}

setTimeout(function() {
  var slideShow = new SlideShow();
  slideShow.start();
}, 100);
