function SlideShow() {
  var self = this;
  
//  const effect = "scale";
  const timeEffect = 10000;

  this.currentAsset = 1;
  this.assetsArray = $("#slideShow").children().children();
  this.assetsCount = this.assetsArray.length;

  this.start = function() {
    $(self.assetsArray[0]).addClass('show').show(/*effect*/);

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
        this.prevAsset = self.assetsCount - 1;
      } else {
        this.prevAsset = 0;
      }
    } else {
      this.prevAsset = self.currentAsset - 1;
    }

    if ($(self.assetsArray[this.prevAsset]).hasClass('show')) {
      $(self.assetsArray[this.prevAsset]).removeClass('show').hide();
          /*effect,
          {},
          null,
          function() {*/
            $(self.assetsArray[self.currentAsset++]).addClass('show').show(
                /*effect*/);
          /*});*/
    }
  }
}

setTimeout(function() {
  var slideShow = new SlideShow();
  slideShow.start();
}, 100);
