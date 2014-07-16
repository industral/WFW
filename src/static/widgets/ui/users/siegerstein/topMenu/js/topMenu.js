/*jslint white: true, onevar: true, browser: true, undef: true, nomen: true, eqeqeq: true, plusplus: true, bitwise: true, regexp: true, strict: true, newcap: true, immed: true, indent: 2 */
/*global $: false*/

"use strict";

function loadTopMenu() {
  $.getJSON('/servlets/widgets/topMenu', {}, function (json) {
    this.ulElement = [ {
      tagName : 'ul',
      childNodes : []
    } ];

    for (var i in json) {
      if (json.hasOwnProperty(i)) {
        this.ulElement[0].childNodes.push({
          tagName : "li",
          childNodes : [ {
            tagName : "a",
            href : json[i].url,
            innerHTML : json[i].name
          } ]
        });
      }
    }
    $("#topMenuDiv").appendDom(this.ulElement);
  });
}
