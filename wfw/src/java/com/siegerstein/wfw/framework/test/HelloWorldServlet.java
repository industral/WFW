package com.siegerstein.wfw.framework.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = -1622027941373174676L;

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter writer = response.getWriter();
    writer.println(request.getParameter("flowId"));
    writer.close();
  }
}
