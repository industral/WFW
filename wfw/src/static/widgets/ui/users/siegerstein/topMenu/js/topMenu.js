function loadTopMenu(){
    $.getJSON('/servlets/widgets/topMenu', {}, function(json){
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
        $("#topMenuDiv").appendDom(ulElement);
    })
}
