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
 * THIS SOFTWARE IS PROVIDED BY Alex Ivasyuv ''AS IS'' AND ANY                 *
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED   *
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE      *
 * DISCLAIMED. IN NO EVENT SHALL Alex Ivasyuv BE LIABLE FOR ANY DIRECT,        *
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES          *
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;*
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND *
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  *
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF    *
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.           *
 ******************************************************************************/

package com.siegerstein.wfw.framework;

import static com.siegerstein.wfw.framework.util.Util.readPropertieFile;

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
