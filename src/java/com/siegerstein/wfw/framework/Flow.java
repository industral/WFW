package java.com.siegerstein.wfw.framework;

import static java.com.siegerstein.wfw.framework.util.Util.readPropertieFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Class for parsing flow XML file.
 * @author Alex Ivasyuv
 */
public class Flow {

  // --------------------------------------------------------------------
  // Public methods
  // --------------------------------------------------------------------

  /**
   * Constructor.
   */
  public Flow() {
    log.log(Level.INFO, "In Constructor");
  }

  /**
   * Parsing flow file.
   * @return {@link Element} of main flow data-file.
   * @throws FileNotFoundException when flow file not found.
   */
  public final Element parseFlow() throws FileNotFoundException {
    SAXBuilder builder = new SAXBuilder();

    Document doc = null;
    try {
      doc = builder.build(new FileInputStream(this.properties
          .getProperty("flowPath")));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new FileNotFoundException("Flow file not found");
    } catch (JDOMException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return (doc.getRootElement());
  }

  // --------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * Logger instance.
   */
  private final Logger log        = Logger.getLogger(getClass().toString());

  /**
   * Property instance.
   */
  private Properties   properties = readPropertieFile();
}
