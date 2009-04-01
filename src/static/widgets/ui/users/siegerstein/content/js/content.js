function Content(contentName){
    /*
     $.get('/servlets/widgets/content?', {}, function(json){
     
     });
     */
    if (contentName == "about") {
        $("#content").load("/widgets/ui/siegerstein/content/xml/about.xml");
    }
    else 
        if (contentName == "contacts") {
            $("#content").load("/widgets/ui/siegerstein/content/xml/contacts.xml");
        }
}
