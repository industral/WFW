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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.siegerstein.wfw.framework.FlowCollectionParser;

public class WidgetTest extends HttpServlet {

  // --------------------------------------------------------------------
  // Public methods
  // --------------------------------------------------------------------

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter writer = new PrintWriter(new OutputStreamWriter(response
        .getOutputStream(), "utf-8"));

    response.setContentType("application/xhtml+xml; charset=utf-8");
    response.setCharacterEncoding("utf-8");

    writer.println("<?xml version='1.0' encoding='utf-8'?>");

    writer
        .println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");

    writer
        .println("<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='en' lang='en'>");

    FlowCollectionParser fc = new FlowCollectionParser(writer);
    String commonWidgetName = request.getParameter("commonWidgetName");

    fc.testWidget(request.getParameter("widgetName"), commonWidgetName);

    writer.println("</html>");

    writer.close();
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * UUID.
   */
  private static final long serialVersionUID = 1L;
}
