function makeAllDragable(){
  $(".wfw-widget").draggable();
}

function writeCord(){
  for (var i = 0; i < $(".wfw-widget").length; ++i) {
    alert($($(".wfw-widget")[i]).attr("style"));
  }
}
