/*jslint white: true, onevar: true, browser: true, undef: true, nomen: true, eqeqeq: true, plusplus: true, bitwise: true, regexp: true, strict: true, newcap: true, immed: true, indent: 2 */
/*global $: false*/

"use strict";

function Content(contentName) {
  if (contentName === "about") {
    $("#content").load("@WIDGET_PATH@/xml/about.xml");
  } else if (contentName === "contacts") {
    $("#content").load("@WIDGET_PATH@/xml/contacts.xml");
  } else if (contentName === "products") {
    $("#content").load("@WIDGET_PATH@/xml/products.xml");
  } else if (contentName === "projects") {
    $("#content").load("@WIDGET_PATH@/xml/projects.xml");
  } else if (contentName === "news") {
    $("#content").load("@WIDGET_PATH@/xml/news.xml");
  }
}
