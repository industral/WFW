/*jslint white: true, onevar: true, browser: true, undef: true, nomen: true, eqeqeq: true, plusplus: true, bitwise: true, regexp: true, strict: true, newcap: true, immed: true, indent: 2 */
/*global $: false*/

"use strict";

function addSearchEvent() {
  $("#searchInputId").click(
      function () {
        $(this).removeClass('searchInputNormal').addClass('searchInputSelect')
            .attr('value', '');
      });
  $("#searchInputId").blur(
      function () {
        $(this).removeClass('searchInputSelect').addClass('searchInputNormal')
            .attr('value', 'Search');
      });
}
