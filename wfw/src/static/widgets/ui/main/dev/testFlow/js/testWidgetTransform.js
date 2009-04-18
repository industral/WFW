// make all elements dragable. Need for Flow Creation Page
function makeAllDragable(){
  $(".wfw-widget").draggable();
}

// send styles values to servlet, to write it
function writeCord(flowName){
  var id = "";
  var style = "";
  
  for (var i = 0; i < $(".wfw-widget").length; ++i) {
    if ($($(".wfw-widget")[i]).attr("style") != undefined) {
      id += $($(".wfw-widget")[i]).attr("id") + "|";
      style += $($(".wfw-widget")[i]).attr("style") + "|";
    }
  }
  
  if (id.length > 0 && style.length > 0) {
    $.post('/servlets/WriteFlow', {
      id: id,
      style: style,
      flowName: flowName
    }, function(){
    });
  }
}
