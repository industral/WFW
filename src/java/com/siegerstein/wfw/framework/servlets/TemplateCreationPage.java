package java.com.siegerstein.wfw.framework.servlets;

import static java.com.siegerstein.wfw.framework.util.Util.readPropertieFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Create Flow Page servlet.
 * @author Alex Ivasyuv
 */
public class TemplateCreationPage extends HttpServlet {

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

    HashSet < String > listOfTemplates = new HashSet < String >();

    getTemplates(new File(properties.getProperty("templateDir")),
        listOfTemplates);

    writer.println(new Gson().toJson(listOfTemplates));

    writer.close();
  }

  // --------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------

  /**
   * Get availability templates.
   * @param folder folder where should be templates files search.
   * @param list collection that should be populate.
   */
  private void getTemplates(final File folder, final HashSet < String > list) {
    File[] files = folder.listFiles();
    for (int j = 0; j < files.length; ++j) {
      // add all .xml files
      if (files[j].getName().contains(".xml") && files[j].isFile()) {
        String templateFile = files[j].toString().substring(
            properties.getProperty("templateDir").length());
        // Skip main folder
        if (!templateFile.startsWith("main/")) {
          list.add(templateFile);
        }
      }
      if (files[j].isDirectory()) {
        getTemplates(files[j], list);
      }
    }
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * UUID.
   */
  private static final long serialVersionUID = -4111920390608057802L;

  /**
   * Property instance.
   */
  private Properties        properties       = readPropertieFile();
}
