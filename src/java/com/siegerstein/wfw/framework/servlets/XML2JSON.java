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

import static com.siegerstein.wfw.framework.util.Util.isPresent;
import static com.siegerstein.wfw.framework.util.Util.readPropertieFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.google.gson.Gson;

public class XML2JSON extends HttpServlet {

  // --------------------------------------------------------------------
  // Public methods
  // --------------------------------------------------------------------

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter writer = new PrintWriter(new OutputStreamWriter(response
        .getOutputStream(), "utf-8"));

    String fileName = request.getParameter("fileName");
    String type = request.getParameter("type");

    if (isPresent(type) && isPresent(fileName) && type.equals("XMLINFO")) {
      SAXBuilder builder = new SAXBuilder();

      Document doc = null;
      try {
        doc = builder.build(new FileInputStream(this.properties
            .getProperty("widgetsDir")
            + fileName + "/" + properties.getProperty("XMLINFOFile")));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        throw new FileNotFoundException("Flow file not found");
      } catch (JDOMException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      HashMap < String, String > hm = new HashMap < String, String >();

      for (Element el : (List < Element >) doc.getRootElement().getChildren()) {
        hm.put(el.getName(), el.getValue());
      }
      writer.print(new Gson().toJson(hm));
    }
    writer.close();
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * Property instance.
   */
  private Properties        properties       = readPropertieFile();
}
