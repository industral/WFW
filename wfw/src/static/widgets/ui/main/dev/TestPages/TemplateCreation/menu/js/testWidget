function testWidget(){
    var widgetName = $("#widgetListSelect option")[$("#widgetListSelect")[0].selectedIndex].value;
    
    var additionalParams = "";
    
    if ($("#menu #toggleCommonWidgets").is(":checked")) {
        var commonWidgetName = $("#commonWidgetSelect option")[$("#commonWidgetSelect")[0].selectedIndex].value;
        additionalParams = "&commonWidgetName=" + commonWidgetName;
    }
    $("#frameContent").attr("src", "/servlets/WidgetTest?widgetName=" + widgetName + additionalParams)
}
