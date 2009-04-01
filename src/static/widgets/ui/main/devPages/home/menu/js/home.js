window.onload = function(){
    $.getJSON('/servlets/Home', {}, function(json){
        var ulElement = [{
            tagName: 'ul',
            childNodes: []
        }];
        
        for (i in json) {
            ulElement[0].childNodes.push({
                tagName: "li",
                childNodes: [{
                    tagName: "a",
                    href: json[i].url,
                    innerHTML: json[i].name
                }]
            });
        }
        $("#links").appendDom(ulElement);
    })
}
