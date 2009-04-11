function flowCreationLoad(){
  $.getJSON('/servlets/Flows', {}, function(json){
    // Flows List
    var selectEl = [{
      tagName: 'select',
      id: "flowsListSelect",
      onchange: "javascript:loadFlow(this.options[this.selectedIndex].value)",
      childNodes: []
    }];
    
    selectEl[0].childNodes.push({
      tagName: "option",
      value: "",
      innerHTML: "Please select a flow..."
    });
    
    for (i in json) {
      selectEl[0].childNodes.push({
        tagName: "option",
        value: json[i],
        innerHTML: json[i]
      });
    }
    $("#f-menu").appendDom(selectEl);
    
    // Save button
    var saveButton = [{
      tagName: 'button',
      id: "f-saveButton",
      innerHTML: "Save",
      onclick: "javascript:window.frames[0].writeCord()"
    }];
    $("#f-menu").appendDom(saveButton);
  });
}

function loadFlow(flowName){
  if (flowName.length != 0) {
    $("#frameContent").attr('src', '/servlets/FlowTest?flowName=' + flowName);
    /*
     window.open('/servlets/FlowTest?flowName=' + flowName, null, 'height=800,width=1000');
     */
  }
}
