/*******************************************************************************
 * Copyright (c) 2009, Alex Ivasyuv                                            *
 * All rights reserved.                                                        *
 *                                                                             *
 * Redistribution and use in source and binary forms, with or without          *
 * modification, are permitted provided that the following conditions are met: *
 *                                                                             *
 * 1. Redistributions of source code must retain the above copyright           *
 *    notice, this list of conditions and the following disclaimer.            *
 * 2. Redistributions in binary form must reproduce the above copyright        *
 *    notice, this list of conditions and the following disclaimer in the      *
 *    documentation and/or other materials provided with the distribution.     *
 *                                                                             *
 * THIS SOFTWARE IS PROVIDED BY Alex Y. Ivasyuv ''AS IS'' AND ANY              *
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED   *
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE      *
 * DISCLAIMED. IN NO EVENT SHALL Alex Y. Ivasyuv BE LIABLE FOR ANY             *
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES  *
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;*
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND *
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  *
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF    *
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.           *
 ******************************************************************************/

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

/**
 * XML to JSON convert servlet.
 * @author Alex Ivasyuv
 */
public class XML2JSON extends HttpServlet {

  // --------------------------------------------------------------------
  // Public methods
  // --------------------------------------------------------------------

  /**
   * Main servlet method.
   * @param request servlet request.
   * @param response servlet request.
   * @throws ServletException in servlet error.
   * @throws IOException in other case error.
   */
  @SuppressWarnings("unchecked")
  public final void service(final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {
    PrintWriter writer =
        new PrintWriter(new OutputStreamWriter(response.getOutputStream(),
            "utf-8"));

    response.setContentType("application/json; charset=utf-8");
    response.setCharacterEncoding("utf-8");

    String fileName = request.getParameter("fileName");
    String type = request.getParameter("type");

    if (isPresent(type) && isPresent(fileName) && type.equals("XMLINFO")) {
      SAXBuilder builder = new SAXBuilder();

      Document doc = null;
      try {
        doc =
            builder.build(new FileInputStream(this.properties
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

      HashMap<String, String> hm = new HashMap<String, String>();

      for (Element el : (List<Element>) doc.getRootElement().getChildren()) {
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
   * UUID.
   */
  private static final long serialVersionUID = -8005240963767458281L;

  /**
   * Property instance.
   */
  private Properties        properties       = readPropertieFile();
}
