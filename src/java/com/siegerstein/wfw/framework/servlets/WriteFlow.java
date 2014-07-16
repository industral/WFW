package java.com.siegerstein.wfw.framework.servlets;

import static java.com.siegerstein.wfw.framework.util.Util.readPropertieFile;

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
              widgetEl.setAttribute("style", this.style[i]);
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
