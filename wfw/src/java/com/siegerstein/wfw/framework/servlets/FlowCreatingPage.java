/*********************************************************************************
 * Copyright (c) 2009, Alex Y. Ivasyuv                                           *
 * All rights reserved.                                                          *
 *                                                                               *
 * Redistribution and use in source and binary forms, with or without            *
 * modification, are permitted provided that the following conditions are met:   *
 *                                                                               *
 * 1. Redistributions of source code must retain the above copyright             *
 *    notice, this list of conditions and the following disclaimer.              *
 * 2. Redistributions in binary form must reproduce the above copyright          *
 *    notice, this list of conditions and the following disclaimer in the        *
 *    documentation and/or other materials provided with the distribution.       *
 *                                                                               *
 * THIS SOFTWARE IS PROVIDED BY Alex Y. Ivasyuv ''AS IS'' AND ANY                *
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED     *
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE        *
 * DISCLAIMED. IN NO EVENT SHALL Alex Y. Ivasyuv BE LIABLE FOR ANY               *
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES    *
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;  *
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND   *
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT    *
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS *
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                  *
 ********************************************************************************/

package com.siegerstein.wfw.framework.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.siegerstein.wfw.framework.FlowCollectionParser;

public class FlowCreatingPage {

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
    fc.process(request.getParameter("flowId"));

    writer.println("</html>");

    writer.close();
  }

  // --------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------

  private void getTemplates(final File folder, final HashSet < String > list) {
//    File[] files = folder.listFiles();
//    for (int j = 0; j < files.length; ++j) {
//      // Add all widget folder that are not "common"
//      if (files[j].getName().equals("xml")) {
//        String widgetName = files[j].toString().substring(
//            properties.getProperty("widgetsDir").length());
//
//        // except "main" widgets
//        if (!widgetName.startsWith("main/")) {
//          list.add(files[j].getParent().substring(
//              properties.getProperty("widgetsDir").length()));
//        }
//      }
//      if (files[j].isDirectory()) {
//        getWidgets(files[j], list);
//      }
//    }
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

}
