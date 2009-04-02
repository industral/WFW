/***************************************************************************
 *                             WFW - Web Framework                         *
 *                      Copyright (C) 2009 Alex Y. Ivasyuv                 *
 *                                                                         *
 * This program is free software: you can redistribute it and/or modify    *
 * it under the terms of the GNU General Public License as published by    *
 * the Free Software Foundation, either version 3 of the License, or       *
 * (at your option) any later version.                                     *
 *                                                                         *
 * This program is distributed in the hope that it will be useful,         *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the           *
 * GNU General Public License for more details.                            *
 *                                                                         *
 * You should have received a copy of the GNU General Public License       *
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.   *
 **************************************************************************/

package com.siegerstein.wfw.framework.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.siegerstein.wfw.framework.util.Util;

/**
 * Class for providing page with widgets to test them.
 * 
 * @author Alex Y. Ivasyuv.
 */
public class WidgetTestPage extends HttpServlet {

  // --------------------------------------------------------------------
  // Public methods
  // --------------------------------------------------------------------

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter writer = new PrintWriter(new OutputStreamWriter(response
        .getOutputStream(), "utf-8"));

    HashSet < String > widgetsList = new HashSet < String >();
    HashSet < String > commonWidgetsList = new HashSet < String >();

    getWidgets(new File(properties.getProperty("widgetsDir")), widgetsList);
    getCommonWidgets(new File(properties.getProperty("widgetsDir")),
        commonWidgetsList);

    HashMap < String, Object > outObj = new HashMap < String, Object >();
    outObj.put("widgets", widgetsList);
    outObj.put("commonWidgets", commonWidgetsList);

    writer.println(new Gson().toJson(outObj));
    writer.close();
  }

  // --------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------

  /**
   * Populate {@link List} with widgets.
   * 
   * @param folder Path to folder that should be scanned.
   * @param collection to array where widgets should be accumulate.
   */
  private void getWidgets(final File folder, final HashSet < String > list) {
    File[] files = folder.listFiles();
    for (int j = 0; j < files.length; ++j) {
      // Add all widget folder that are not "common"
      if (files[j].getName().equals("xml")) {
        // Check if in xml directory present widgetXMLFile
        if (files[j].listFiles().length != 0) {
          if (files[j].listFiles()[0].toString().contains(
              properties.getProperty("widgetXMLFile"))) {
            String widgetName = files[j].toString().substring(
                properties.getProperty("widgetsDir").length());

            // except "main" widgets
            if (!widgetName.startsWith("main/")) {
              list.add(files[j].getParent().substring(
                  properties.getProperty("widgetsDir").length()));
            }
          }
        }
      }
      if (files[j].isDirectory()) {
        getWidgets(files[j], list);
      }
    }
  }

  private void getCommonWidgets(final File folder, final HashSet < String > list) {
    File[] files = folder.listFiles();
    for (int j = 0; j < files.length; ++j) {
      // Add all widget folder that are not "common"
      if (!files[j].getName().equals("xml")) {
        // TODO: Add check if there some thing present
        String widgetName = files[j].toString().substring(
            properties.getProperty("widgetsDir").length());
        if (!widgetName.startsWith("common/") && widgetName.contains("/common")
            && files[j].isDirectory()) {
          if (files[j].getParent().contains("/common")) {
            list.add(files[j].getParent().substring(
                properties.getProperty("widgetsDir").length()));
          }
        }
      }
      if (files[j].isDirectory()) {
        getCommonWidgets(files[j], list);
      }
    }
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * UUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Property instance.
   */
  private Properties        properties       = Util.readPropertieFile();
}
