package java.com.siegerstein.wfw.framework.widgets.ui.main.dev.home;

import static java.com.siegerstein.wfw.framework.util.Util.yaml2json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Home page widget Servlet. Return appropriate data in JSON format.
 */

public class Home extends HttpServlet {

  // --------------------------------------------------------------------
  // Public methods
  // --------------------------------------------------------------------

  /**
   * Main servlet method.
   * @param request - servlet request.
   * @param response - servlet request.
   * @throws ServletException in servlet error.
   * @throws IOException in other case error.
   */

  public final void service(final HttpServletRequest request,
      final HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("application/json; charset=utf-8");
    response.setCharacterEncoding("utf-8");

    yaml2json(getClass().getName(), "links.yml", response);
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * UUID.
   */
  private static final long serialVersionUID = 1L;

}
