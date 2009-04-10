function flowCreationLoad(){
  $.getJSON('/servlets/Flows', {}, function(json){
    // Flows List
    var selectEl = [{
      tagName: 'select',
      id: "flowsListSelect",
      onchange: "javascript:loadFlow(this.options[this.selectedIndex].value)",
      childNodes: []
    }];
    
    for (i in json) {
      selectEl[0].childNodes.push({
        tagName: "option",
        value: json[i],
        innerHTML: json[i]
      });
    }
    $("#f-menu").appendDom(selectEl);
    var flowName = document.getElementById("flowsListSelect").options[document.getElementById("flowsListSelect").selectedIndex].value;
    loadFlow(flowName);
  });
}

function loadFlow(flowName){
  $("#frameContent").attr('src', '/servlets/FlowTest?flowName=' + flowName);
}
