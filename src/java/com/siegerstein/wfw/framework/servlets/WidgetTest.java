package java.com.siegerstein.wfw.framework.servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.siegerstein.wfw.framework.Core;

/**
 * Widget Test Page servlet.
 * @author Alex Ivasyuv
 */
public class WidgetTest extends HttpServlet {

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

    response.setContentType("application/xhtml+xml; charset=utf-8");
    response.setCharacterEncoding("utf-8");

    writer.println("<?xml version='1.0' encoding='utf-8'?>");

    writer.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN'"
        + " 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");

    writer.println("<html xmlns='http://www.w3.org/1999/xhtml'"
        + " xml:lang='en' lang='en'>");

    Core fc = new Core(writer);
    String[] commonWidgetName = request.getParameterValues("commonWidgetName");

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
  private static final long serialVersionUID = 3199502133601203228L;
}
