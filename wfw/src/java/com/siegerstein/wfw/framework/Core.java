/*******************************************************************************
 * Copyright (c) 2009, Alex Ivasyuv                                            *
 * All rights reserved.                                                        *
 *                                                                             *
 * Redistribution and use in source and binary forms, with or without          *
 * modification, are permitted provided that the following conditions are met: *
 *                                                                             *
 * 1. Redistributions of source code must retain the above copyright           *
 *    notice, this list of conditions and the following disclaimer.            *
 * 2. Redistributions in binary form must reproduce the above copyright        *
 *    notice, this list of conditions and the following disclaimer in the      *
 *    documentation and/or other materials provided with the distribution.     *
 *                                                                             *
 * THIS SOFTWARE IS PROVIDED BY Alex Y. Ivasyuv ''AS IS'' AND ANY              *
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED   *
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE      *
 * DISCLAIMED. IN NO EVENT SHALL Alex Y. Ivasyuv BE LIABLE FOR ANY             *
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES  *
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;*
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND *
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  *
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF    *
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.           *
 ******************************************************************************/

package com.siegerstein.wfw.framework;

import static com.siegerstein.wfw.framework.util.Util.getIgnoredFiles;
import static com.siegerstein.wfw.framework.util.Util.isPresent;
import static com.siegerstein.wfw.framework.util.Util.readFileToList;
import static com.siegerstein.wfw.framework.util.Util.readPropertieFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Class for parsing flow XML file and putting widgets in appropriate places.
 * @author Alex Ivasyuv
 */
// TODO: make more separate methods.
public class Core {

  // --------------------------------------------------------------------
  // Public methods
  // --------------------------------------------------------------------

  /**
   * Constructor.
   * @param writer JSP writer to provide XHTML outputting document.
   */
  public Core(final PrintWriter writer) {
    log.log(Level.INFO, "In Constructor");
    this.out = writer;
  }

  /**
   * Initialized method to became parsing files and create XML output.
   * @param flowId Flow name.
   * @throws FileNotFoundException when some thing is missing in flow.
   */
  public final void process(final String flowId) throws FileNotFoundException {
    this.createFlowHashMap(flowId);
    this.outputBuilder();
  }

  /**
   * Test widget in Test Widget Page.
   * @param widgetName widget name.
   * @param aCommonWidgetName common widget.
   * @throws FileNotFoundException when some thing is missing in flow.
   */
  public final void testWidget(final String widgetName,
      final String aCommonWidgetName) throws FileNotFoundException {
    this.flowTemplate = this.properties.getProperty("testTemplate");

    this.commonWidgetName = aCommonWidgetName;

    // Create flow hashMap ("testContent" => "widgetName")
    this.flowHash = new HashMap<String, String>();
    flowHash.put("testContent", widgetName);

    this.outputBuilder();
  }

  // --------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------

  /**
   * Parsing flow file.
   * @return {@link Element} of main flow data-file.
   * @throws FileNotFoundException when flow file not found.
   */
  private Element parseFlow() throws FileNotFoundException {
    SAXBuilder builder = new SAXBuilder();

    Document doc = null;
    try {
      doc =
          builder.build(new FileInputStream(this.properties
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

  /**
   * Create hashMap with "id" => "widgetName".
   * @param flowId flow name.
   * @throws FileNotFoundException when flow file not found.
   */
  @SuppressWarnings("unchecked")
  private void createFlowHashMap(final String flowId)
      throws FileNotFoundException {
    // Create flow hashMap ("id" => "widgetName")
    this.flowHash = new HashMap<String, String>();

    // Find flow with flowId name
    for (Element obj : (List<Element>) this.parseFlow().getChildren("flow")) {
      if ((obj.getAttributeValue("name").equals(flowId))) {
        this.flowTemplate = obj.getAttributeValue("template");
        this.commonWidgetName = obj.getAttributeValue("commonWidget");
        // If flow found, run in cycle and populate hash
        for (Element widgetObj : (List<Element>) obj.getChild("widgets")
            .getChildren("widget")) {
          // Populate hash with "id" => "widgetName"
          this.flowHash.put(widgetObj.getAttributeValue("id"), widgetObj
              .getValue());
        }
      }
    }
  }

  /**
   * Parse template file.
   * @return {@link Element} of template root.
   * @throws FileNotFoundException when template file not found.
   */
  private Element getTemplateRoot() throws FileNotFoundException {
    // Start builder for template.
    SAXBuilder templateBuilder = new SAXBuilder();
    Document docTemplate = null;
    try {
      docTemplate =
          templateBuilder.build(new FileInputStream(this.properties
              .getProperty("templateDir")
              + this.flowTemplate + ".xml"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new FileNotFoundException("Template file not found");
    } catch (JDOMException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return (docTemplate.getRootElement());
  }

  /**
   * Create XML output document from widgets.
   * @throws FileNotFoundException when some thing is missing in flow.
   */
  @SuppressWarnings("unchecked")
  private void outputBuilder() throws FileNotFoundException {
    Element templateRoot = this.getTemplateRoot();
    List<Element> templateRootList = (List<Element>) templateRoot.getChildren();
    Element templateHEAD = templateRootList.get(0);
    this.templateBODY = templateRootList.get(1);

    // Add common widget files. Should be the first before users widgets
    this.addHEADFiles(this.properties.getProperty("commonWidgetName"));

    // If present common user widget add it to HEAD
    if (isPresent(this.commonWidgetName)) {
      this.addHEADFiles(this.commonWidgetName);
    }

    for (Element divObj : (List<Element>) (this.templateBODY.getChildren())) {
      // Ensure that it's ID tag
      if (divObj.getName().equals("div")) {
        String idName = divObj.getAttributeValue("id");
        if (this.flowHash.get(idName) != null) {
          Document widgetDoc = null;

          String widgetName = this.flowHash.get(idName);
          String widgetPath =
              this.properties.getProperty("widgetsDir") + widgetName + "/";

          try {
            widgetDoc =
                new SAXBuilder().build(new FileInputStream(widgetPath
                    + this.properties.getProperty("widgetXMLDir") + "/"
                    + this.properties.getProperty("widgetXMLFile")));
          } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException("One of widget can't be found."
                + "Ensure that you defined right file names"
                + "in 'flow/flow.xml'");
          } catch (JDOMException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }

          // Add widget HEAD
          this.addHEADFiles(widgetName);
          // Add widget content
          divObj.setContent(widgetDoc.getRootElement().cloneContent());
        }
      }
    }

    this.createHeadElements(templateHEAD);

    Format format = Format.getPrettyFormat();
    XMLOutputter outp = new XMLOutputter(format);
    try {
      outp.output(templateHEAD, out);
      outp.output(this.templateBODY, out);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Populate {@link List} with files that present in widgets (CSS/JS).
   * @param folder Path to folder that should be scanned.
   * @param list {@link List} which should be populate with data.
   * @param widgetName widget name which files should be found.
   */
  private void getFiles(final File folder, final List<String> list,
      final String widgetName) {
    File[] files = folder.listFiles();
    for (int j = 0; j < files.length; ++j) {
      if (files[j].isFile()
          && (files[j].getName().endsWith(".js") || files[j].getName()
              .endsWith(".css"))) {

        // Widget path, for example: ../webapps/ROOT/widgets/ui/siegerstein/logo
        String widgetPath = properties.getProperty("widgetsDir") + widgetName;
        // File path, for example: js/jquery/jquery-1.3.2.min.js
        String realFilePath =
            files[j].toString().substring(widgetPath.length() + 2);
        list.add(realFilePath);
      }
      if (files[j].isDirectory()) {
        getFiles(files[j], list, widgetName);
      }
    }
  }

  /**
   * Add present CSS/JS files to HEAD if they present.
   * @param widgetName widget Name that should be use.
   * @return
   */
  private void addHEADFiles(final String widgetName) {
    for (HeadFiles headFiles : HeadFiles.values()) {
      // Add JS/CSS-file to HEAD if it present
      String headComponentPath =
          widgetName + "/"
              + this.properties.getProperty("widget" + headFiles + "Dir");
      String widgetComponentPathLocal =
          this.properties.getProperty("widgetsDir") + headComponentPath;
      String widgetComponentPathWeb =
          this.properties.getProperty("widgetsWebDir") + headComponentPath;

      // variable to check if present in folder order file
      boolean order = false;

      // Check if in folder present file ".wfw-order"
      // We need to check if folder is exists, because CSS and JS folder is
      // optional
      if (new File(widgetComponentPathLocal).exists()) {
        File[] fileList = new File(widgetComponentPathLocal).listFiles();
        for (int i = 0; i < fileList.length; ++i) {
          if (fileList[i].getName().equals(".wfw-order")) {
            order = true;
          }
          if (fileList[i].getName().equals(".wfw-onload")) {
            this.addJSOnload(fileList[i]);
          }
        }
      }

      // if order file present follow him
      if (order) {
        List<String> list =
            readFileToList(widgetComponentPathLocal + "/" + ".wfw-order");
        for (String s : list) {
          if (!s.trim().isEmpty()) {
            String localPath = widgetComponentPathLocal + "/" + s;
            if (new File(localPath).isFile()) {
              headFilesList.add(widgetComponentPathWeb + "/" + s);
            } else {
              List<String> listComponentFiles = new ArrayList<String>();
              getFiles(new File(localPath), listComponentFiles, widgetName
                  + this.properties.getProperty("widget" + headFiles + "Dir"));
              for (String file : listComponentFiles) {
                String componentWebFileName =
                    widgetComponentPathWeb + "/" + file;
                log.log(Level.INFO, "Found widget " + headFiles + " file: "
                    + componentWebFileName);
                headFilesList.add(componentWebFileName);
              }
            }
          }
        }
      }

      // if no order file just run recursive across all folders inside
      if (!order) {
        if (new File(widgetComponentPathLocal).exists()) {
          List<String> listComponentFiles = new ArrayList<String>();
          getFiles(new File(widgetComponentPathLocal), listComponentFiles,
              widgetName
                  + this.properties.getProperty("widget" + headFiles + "Dir"));

          for (String file : listComponentFiles) {
            String componentWebFileName = widgetComponentPathWeb + "/" + file;
            log.log(Level.INFO, "Found widget " + headFiles + " file: "
                + componentWebFileName);
            headFilesList.add(componentWebFileName);
          }
        }
      }
    }
  }

  /**
   * Add onload function to BODY tag.
   * @param fileName file where onload function(s) defined.
   */
  // TODO: function like shit, need to be rewrited!
  private void addJSOnload(final File fileName) {
    List<String> onloadFunctionList = readFileToList(fileName.toString());
    for (String jsFunction : onloadFunctionList) {
      String onloadBodyAttr = this.templateBODY.getAttributeValue("onload");
      String resultOnload = "";
      if (isPresent(onloadBodyAttr)) {
        resultOnload = onloadBodyAttr + jsFunction + "; ";
      } else {
        resultOnload += jsFunction + "; ";
      }
      this.templateBODY.setAttribute("onload", resultOnload);
    }
  }

  /**
   * Create HEAD elements and add them to HEAD.
   * @param headElement HEAD element in which will be populate with elements.
   */
  private void createHeadElements(final Element headElement) {
    // remove from list all ignored files
    this.headFilesList.removeAll(getIgnoredFiles());

    List<Element> cssList = new LinkedList<Element>();
    List<Element> jsList = new LinkedList<Element>();

    for (String headFile : this.headFilesList) {
      String elementType = null;
      if (headFile.endsWith(".css")) {
        elementType = "link";
      } else if (headFile.endsWith(".js")) {
        elementType = "script";
      }

      // Create new LINK/SCRIPT tag
      Element styleElement = new Element(elementType);

      // Add necessary parameters
      if (elementType.equals("link")) {
        styleElement.setAttribute("rel", "stylesheet");
        styleElement.setAttribute("type", "text/css");
        styleElement.setAttribute("href", headFile);
        cssList.add(styleElement);
      } else {
        styleElement.setAttribute("type", "text/javascript");
        styleElement.setAttribute("src", headFile);
        styleElement.setText("&nbsp;"); // need to have close tag
        jsList.add(styleElement);
      }
    }

    for (Element el : cssList) {
      headElement.addContent(el);
    }
    for (Element el : jsList) {
      headElement.addContent(el);
    }
  }

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

  /**
   * Output writer to JSP page.
   */
  private PrintWriter  out        = null;

  /**
   * HEAD tag files that should be included automatically if they present.
   */
  private enum HeadFiles {
    /**
     * CSS, JS.
     */
    CSS, JS;
  };

  /**
   * HashMap of "id" => "widget".
   */
  private HashMap<String, String> flowHash         = null;

  /**
   * Name of template that should use to create DOM.
   */
  private String                  flowTemplate     = null;

  /**
   * Name of common widget that should include if it present.
   */
  private String                  commonWidgetName = null;

  /**
   * List of HEAD files.
   */
  private List<String>            headFilesList    = new LinkedList<String>();

  /**
   * BODY element.
   */
  private Element                 templateBODY     = null;
}