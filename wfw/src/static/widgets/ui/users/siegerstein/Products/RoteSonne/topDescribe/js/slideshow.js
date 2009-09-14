function SlideShow() {
  var self = this;

  this.currentAsset = 1;
  this.assetsArray = $("#slideShow").children().children();
  this.assetsCount = this.assetsArray.length;

  this.start = function() {
    $('#slideShow li').removeClass('show').addClass('hide');
    $(self.assetsArray[0]).removeClass('hide').addClass('show');
    
    setInterval(function() {
      self.next();
    }, 5000);
  }

  this.next = function() {
    if (self.currentAsset >= self.assetsCount) {
      self.currentAsset = 0;
    }

    $('#slideShow li').removeClass('show').addClass('hide');
    $(self.assetsArray[self.currentAsset++]).removeClass('hide').addClass('show');
  }
}

setTimeout(function() {
  var slideShow = new SlideShow();
  slideShow.start();
}, 100);
