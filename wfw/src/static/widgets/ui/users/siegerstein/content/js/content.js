function Content(contentName){
  /*
   $.get('/servlets/widgets/content?', {}, function(json){
   
   });
   */
  if (contentName == "about") {
    $("#content").load("@WIDGET_PATH@/xml/about.xml");
  } else if (contentName == "contacts") {
    $("#content").load("@WIDGET_PATH@/xml/contacts.xml");
  } else if (contentName == "products") {
    $("#content").load("@WIDGET_PATH@/xml/products.xml");
  } else if (contentName == "projects") {
    $("#content").load("@WIDGET_PATH@/xml/projects.xml");
  } else if (contentName == "news") {
    $("#content").load("@WIDGET_PATH@/xml/news.xml");
  }
}
