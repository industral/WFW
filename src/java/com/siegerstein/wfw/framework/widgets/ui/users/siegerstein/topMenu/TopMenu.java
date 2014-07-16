package java.com.siegerstein.wfw.framework.widgets.ui.users.siegerstein.topMenu;

import static java.com.siegerstein.wfw.framework.util.Util.yaml2json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TopMenu widget Servlet. Return appropriate data in JSON format.
 */
public class TopMenu extends HttpServlet {

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
  private static final long serialVersionUID = -8946117878773312370L;

}
