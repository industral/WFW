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
 * THIS SOFTWARE IS PROVIDED BY Alex Ivasyuv ''AS IS'' AND ANY                 *
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED   *
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE      *
 * DISCLAIMED. IN NO EVENT SHALL Alex Ivasyuv BE LIABLE FOR ANY DIRECT,        *
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES          *
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;*
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND *
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  *
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF    *
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.           *
 ******************************************************************************/
package com.siegerstein.wfw.framework.servlets;

import static com.siegerstein.wfw.framework.util.Util.readPropertieFile;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.siegerstein.wfw.framework.Flow;

/**
 * Write styles to flow.
 * @author Alex Ivasyuv
 */
public class WriteFlow extends HttpServlet {

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
  public final void service(final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {

    // parse flow
    parseInputValues(request);

    // write values to file
    writeStyleToFlow();

  }

  // --------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------

  /**
   * Parse input values.
   * @param request {@link HttpServletRequest} request.
   */
  private void parseInputValues(final HttpServletRequest request) {
    // Separate input string by pipe symbol
    final String split = "\\|";

    // get "id" and "style" values
    this.id = request.getParameter("id").split(split);
    this.style = request.getParameter("style").split(split);
    this.flowName = request.getParameter("flowName");
  }

  /**
   * Write styles value to flow file.
   * @throws IOException when flow file not found.
   */
  @SuppressWarnings("unchecked")
  private void writeStyleToFlow() throws IOException {
    Element flowRoot = new Flow().parseFlow();

    // run across all flow tags
    for (Element el : (List < Element >) flowRoot.getChildren("flow")) {
      // find appropriate flow
      if (el.getAttributeValue("name").equals(this.flowName)) {
        for (int i = 0; i < id.length; ++i) {
          for (Element widgetEl : (List < Element >) el.getChild("widgets")
              .getChildren("widget")) {
            if (widgetEl.getAttributeValue("id").equals(this.id[i])) {
              widgetEl.getAttribute("style").setValue(this.style[i]);
            }
          }
        }
      }
    }

    // prepare output format
    Format format = Format.getPrettyFormat();
    XMLOutputter outp = new XMLOutputter(format);

    // write DOM to file
    outp.output(flowRoot, new FileWriter(this.properties
        .getProperty("flowPath")));
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * UUID.
   */
  private static final long serialVersionUID = -1908382164674306278L;

  /**
   * Property instance.
   */
  private Properties        properties       = readPropertieFile();

  /**
   * Array of input id's separated by pipe symbol.
   */
  private String[]          id;

  /**
   * Array of input styles separated by pipe symbol.
   */
  private String[]          style;

  /**
   * Flow to apply styles changed.
   */
  private String            flowName;
}
