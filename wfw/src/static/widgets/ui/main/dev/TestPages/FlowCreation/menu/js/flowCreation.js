window.onload = function(){
    $.getJSON('/servlets/FlowCreationPage', {}, function(json){
        // Template List
        var selectEl = [{
            tagName: 'select',
            id: "templatesListSelect",
            onchange: "javascript:loadTemplate(this)",
            childNodes: []
        }];
        
        for (i in json) {
            selectEl[0].childNodes.push({
                tagName: "option",
                value: json[i],
                innerHTML: json[i]
            });
        }
        $("#menu").appendDom(selectEl);
    });
}

function loadTemplate(param){
    var templateName = param.options[param.selectedIndex].value;
    $.get('/servlets/FileProxy', {
        requestType: "getFile",
        fileName: templateName,
        fileType: "templates"
    }, function(data){
        $("#codeContent").text(data);
        sh_highlightDocument();
    });
}
