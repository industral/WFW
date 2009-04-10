package com.siegerstein.wfw.framework.servlets;

import static com.siegerstein.wfw.framework.util.Util.readPropertieFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Element;

import com.google.gson.Gson;
import com.siegerstein.wfw.framework.Flow;

/**
 * Get list of flows.
 */
public class Flows extends HttpServlet {

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

    response.setContentType("application/json; charset=utf-8");
    response.setCharacterEncoding("utf-8");

    writer.println(new Gson().toJson(getListofFlows()));

    writer.close();
  }

  // --------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------

  /**
   * Get collection of available flow.
   * @return List of flows.
   * @throws FileNotFoundException if flow file missing.
   */
  @SuppressWarnings("unchecked")
  private List < String > getListofFlows() throws FileNotFoundException {
    List < String > flowsList = new LinkedList < String >();
    // parse flow file
    Element flowRoot = flow.parseFlow();
    for (Element el : (List < Element >) flowRoot.getChildren("flow")) {
      if (el.getAttributeValue("name").startsWith(
          properties.getProperty("commonUserDef"))) {
        flowsList.add(el.getAttributeValue("name"));
      }
    }
    return (flowsList);
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * UUID.
   */
  private static final long serialVersionUID = -8826025682394062144L;

  /**
   * Property instance.
   */
  private Properties        properties       = readPropertieFile();

  /**
   * Flow object.
   */
  private Flow              flow             = new Flow();
}
