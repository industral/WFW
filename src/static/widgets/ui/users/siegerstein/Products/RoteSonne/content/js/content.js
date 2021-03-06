/*jslint white: true, onevar: true, browser: true, undef: true, nomen: true, eqeqeq: true, plusplus: true, bitwise: true, regexp: true, strict: true, newcap: true, immed: true, indent: 2 */
/*global $: false*/

"use strict";

function Content(contentName) {
  if (contentName === "download") {
    $("#content").load("@WIDGET_PATH@/xml/download.xml");
  } else if (contentName === "overview") {
    $("#content").load("@WIDGET_PATH@/xml/overview.xml");
  } else if (contentName === "features") {
    $("#content").load("@WIDGET_PATH@/xml/features.xml");
  } else if (contentName === "screenshots") {
    $("#content").load("@WIDGET_PATH@/xml/screenshots.xml");
  }
}
