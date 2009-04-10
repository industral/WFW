package com.siegerstein.wfw.framework.test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple Hello World servlet.
 */
public class HelloWorldServlet extends HttpServlet {

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
    PrintWriter writer = new PrintWriter(new OutputStreamWriter(response
        .getOutputStream(), "utf-8"));

    writer.println(request.getParameter("flowId"));
    writer.close();
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * UUID.
   */
  private static final long serialVersionUID = -1622027941373174676L;
}
