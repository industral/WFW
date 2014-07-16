package java.com.siegerstein.wfw.framework.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import java.com.siegerstein.wfw.framework.util.Util;

/**
 * Class for providing page with widgets to test them.
 * @author Alex Ivasyuv
 */
public class Widgets extends HttpServlet {

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

    HashSet < String > widgetsList = new HashSet < String >();
    HashSet < String > commonWidgetsList = new HashSet < String >();

    getWidgets(new File(properties.getProperty("widgetsDir")), widgetsList);
    getCommonWidgets(new File(properties.getProperty("widgetsDir")),
        commonWidgetsList);

    HashMap < String, Object > outObj = new HashMap < String, Object >();
    outObj.put("widgets", widgetsList);
    outObj.put("commonWidgets", commonWidgetsList);

    writer.println(new Gson().toJson(outObj));
    writer.close();
  }

  // --------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------

  /**
   * Get availability widgets.
   * @param folder Path to folder that should be scanned.
   * @param list collection to array where widgets should be accumulate.
   */
  private void getWidgets(final File folder, final HashSet < String > list) {
    File[] files = folder.listFiles();
    for (int j = 0; j < files.length; ++j) {
      // Add all widget folder that are not "common"
      if (files[j].getName().equals("xml")) {
        // Check if in xml directory present widgetXMLFile
        if (files[j].listFiles().length != 0) {
          if (files[j].listFiles()[0].toString().contains(
              properties.getProperty("widgetXMLFile"))) {
            String widgetName = files[j].toString().substring(
                properties.getProperty("widgetsDir").length());

            // except "main" widgets
            if (!widgetName.startsWith("main/")) {
              list.add(files[j].getParent().substring(
                  properties.getProperty("widgetsDir").length()));
            }
          }
        }
      }
      if (files[j].isDirectory()) {
        getWidgets(files[j], list);
      }
    }
  }

  /**
   * Get availability common widgets.
   * @param folder where widgets present.
   * @param list collection which should be populate.
   */
  private void getCommonWidgets(final File folder, final HashSet < String > list) {
    File[] files = folder.listFiles();
    for (int j = 0; j < files.length; ++j) {
      // Add all widget folder that are not "common"
      if (!files[j].getName().equals("xml")) {
        // TODO: Add check if there some thing present
        String widgetName = files[j].toString().substring(
            properties.getProperty("widgetsDir").length());
        if (!widgetName.startsWith("common/") && widgetName.contains("/common")
            && files[j].isDirectory()) {
          if (files[j].getParent().contains("/common")) {
            list.add(files[j].getParent().substring(
                properties.getProperty("widgetsDir").length()));
          }
        }
      }
      if (files[j].isDirectory()) {
        getCommonWidgets(files[j], list);
      }
    }
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * UUID.
   */
  private static final long serialVersionUID = -2439117373264244597L;

  /**
   * Property instance.
   */
  private Properties        properties       = Util.readPropertieFile();
}
