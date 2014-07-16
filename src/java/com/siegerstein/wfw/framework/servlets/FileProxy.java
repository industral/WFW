package java.com.siegerstein.wfw.framework.servlets;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.com.siegerstein.wfw.framework.util.Util.readFileToString;
import static java.com.siegerstein.wfw.framework.util.Util.readPropertieFile;
import static java.com.siegerstein.wfw.framework.util.Util.isPresent;

/**
 * Provide file exchange mechanism (download/upload).
 * @author Alex Ivasyuv
 */
public class FileProxy extends HttpServlet {

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

    if (request.getParameter("requestType").equals("getFile")) {
      String fileName = request.getParameter("fileName");
      String fileType = request.getParameter("fileType");
      if (isPresent(fileName) && isPresent(fileType)) {
        writer.println(getFile(fileName, FileType.valueOf(fileType)));
      }
    }
    writer.close();
  }

  // --------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------

  /**
   * Get file.
   * @param fileName require file.
   * @param fileType a type of file.
   * @return file content.
   */
  private String getFile(final String fileName, final FileType fileType) {
    String pathToTemplate = properties.getProperty("commonTypesDir")
        + fileType.toString() + "/" + fileName;
    return (readFileToString(pathToTemplate));
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * UUID.
   */
  private static final long serialVersionUID = 7295463857790227273L;

  /**
   * File types that can be pass in query.
   */
  private enum FileType {
    /**
     * Template.
     */
    templates;
  };

  /**
   * Property instance.
   */
  private Properties properties = readPropertieFile();
}
