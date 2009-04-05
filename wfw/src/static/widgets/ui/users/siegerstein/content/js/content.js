function Content(contentName){
    /*
     $.get('/servlets/widgets/content?', {}, function(json){
     
     });
     */
    if (contentName == "about") {
        $("#content").load("@WIDGET_PATH@/xml/about.xml");
    }
    else 
        if (contentName == "contacts") {
            $("#content").load("@WIDGET_PATH@/xml/contacts.xml");
        }
}
