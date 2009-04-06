function widgetTestPage(){
    $.getJSON('/servlets/WidgetTestPage', {}, function(json){
        // Widgets List
        var selectEl = [{
            tagName: 'select',
            id: "widgetListSelect",
            onchange: "javascript:getInfo(this)",
            childNodes: []
        }];
        
        for (i in json.widgets) {
            selectEl[0].childNodes.push({
                tagName: "option",
                value: json.widgets[i],
                innerHTML: json.widgets[i]
            });
        }
        
        var widgetsLabel = [{
            tagName: "div",
            className: "itemDescribe",
            innerHTML: "Select Widget"
        }];
        
        var widgetsDiv = [{
            tagName: "div",
            id: "widgetsList",
            childNodes: []
        }];
        
        widgetsDiv[0].childNodes.push(widgetsLabel[0]);
        widgetsDiv[0].childNodes.push(selectEl[0]);
        
        $("#menu").appendDom(widgetsDiv);
        
        // Common Widgets List
        var selectEl = [{
            tagName: 'select',
            id: "commonWidgetSelect",
            name: "commonWidgetSelect",
            disabled: "disabled", // as it optional option, disabled by default
            childNodes: []
        }];
        
        for (i in json.commonWidgets) {
            selectEl[0].childNodes.push({
                tagName: "option",
                value: json.commonWidgets[i],
                innerHTML: json.commonWidgets[i]
            });
        }
        
        var commonWidgetsCheckbox = [{
            tagName: "input",
            id: "toggleCommonWidgets",
            type: "checkbox",
            onclick: "javascript:toggleEl(this)"
        }];
        
        var commonWidgetsCheckboxLabel = [{
            tagName: "label",
            "for": "toggleCommonWidgets",
            innerHTML: "Enable"
        }]
        
        var commonWidgetsLabel = [{
            tagName: "div",
            className: "itemDescribe",
            innerHTML: "Select Common Widget"
        }];
        
        var commonWidgetsDiv = [{
            tagName: "div",
            id: "commonWidgetsList",
            childNodes: []
        }];
        
        var commonWidgetsDivContainer = [{
            tagName: "div",
            id: "commonWidgetsDivContainer",
            childNodes: []
        }];
        commonWidgetsDivContainer[0].childNodes.push(commonWidgetsLabel[0]);
        commonWidgetsDivContainer[0].childNodes.push(selectEl[0]);
        
        commonWidgetsDiv[0].childNodes.push(commonWidgetsDivContainer[0]);
        commonWidgetsDiv[0].childNodes.push(commonWidgetsCheckbox[0]);
        commonWidgetsDiv[0].childNodes.push(commonWidgetsCheckboxLabel[0]);
        
        $("#menu").appendDom(commonWidgetsDiv);
        
        // Widget information
        var widgetInformation = [{
            tagName: "div",
            id: "widgetInfo"
        }]
        
        $("#menu").appendDom(widgetInformation);
        
        // Submit button
        var submitButton = [{
            tagName: "div",
            id: "submitDiv",
            childNodes: [{
                tagName: "button",
                id: "submitButton",
                onclick: "javascript:testWidget()",
                innerHTML: "Submit"
            }]
        }]
        
        $("#menu").appendDom(submitButton);
    });
}

function toggleEl(param){
    if ($("#menu #toggleCommonWidgets").is(":checked")) {
        $("#menu #commonWidgetSelect").removeAttr("disabled");
    }
    else {
        $("#menu #commonWidgetSelect").attr("disabled", "disabled");
    }
}

function getInfo(param){
    $("#widgetInfo").html(""); // clear DOM
    var fileName = param.options[param.selectedIndex].value;
    $.getJSON('/servlets/XML2JSON', {
        fileName: fileName,
        type: "XMLINFO"
    }, function(json){
        for (i in json) {
            var infoSpanLabel = [{
                tagName: "span",
                className: "describeLabel",
                innerHTML: i + ": "
            }];
            var infoSpanDescr = [{
                tagName: "div",
                innerHTML: json[i]
            }];
            $("#widgetInfo").appendDom(infoSpanLabel);
            $("#widgetInfo").appendDom(infoSpanDescr);
        }
    });
}
