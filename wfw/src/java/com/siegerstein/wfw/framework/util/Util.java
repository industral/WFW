/*********************************************************************************
 * Copyright (c) 2009, Alex Y. Ivasyuv                                           *
 * All rights reserved.                                                          *
 *                                                                               *
 * Redistribution and use in source and binary forms, with or without            *
 * modification, are permitted provided that the following conditions are met:   *
 *                                                                               *
 * 1. Redistributions of source code must retain the above copyright             *
 *    notice, this list of conditions and the following disclaimer.              *
 * 2. Redistributions in binary form must reproduce the above copyright          *
 *    notice, this list of conditions and the following disclaimer in the        *
 *    documentation and/or other materials provided with the distribution.       *
 *                                                                               *
 * THIS SOFTWARE IS PROVIDED BY Alex Y. Ivasyuv ''AS IS'' AND ANY                *
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED     *
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE        *
 * DISCLAIMED. IN NO EVENT SHALL Alex Y. Ivasyuv BE LIABLE FOR ANY               *
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES    *
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;  *
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND   *
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT    *
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS *
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                  *
 ********************************************************************************/

package com.siegerstein.wfw.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;

public class Util {

  // --------------------------------------------------------------------
  // Public methods
  // --------------------------------------------------------------------

  /**
   * Read properties file.
   */
  public static Properties readPropertieFile() {
    try {
      properties.load(new FileInputStream(propertieFile));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return (properties);
  }

  public static String dotToSlash(final String name) {
    return (name.replace(".", "/"));
  }

  public static String getPathToCurrentClassDir(String name) {
    readPropertieFile();
    return (dropClassName(properties.getProperty("classPath")
        + dotToSlash(name)));
  }

  public static String getPathToDatasetsDir(String name) {
    readPropertieFile();
    return (dropClassName(properties.getProperty("datasetsPath")
        + dotToSlash(name)));
  }

  public static boolean isPresent(String var) {
    return (var != null && !var.trim().equals("") && !var.equals("null"));
  }

  /**
   * Parse YAML file and convert it to JSON.
   * 
   * @param className class name of servlet where YAML file present.
   * @param file name of YAML file.
   * @param response {@link HttpServletResponse} response handle.
   * @throws UnsupportedEncodingException
   * @throws IOException
   */
  public static void YAML2JSON(String className, String file,
      HttpServletResponse response) throws UnsupportedEncodingException,
      IOException {

    PrintWriter writer = new PrintWriter(new OutputStreamWriter(response
        .getOutputStream(), "utf-8"));

    // Open YAML file and read it in LinkedList
    // Convert it into JSON and print
    writer.println(new Gson().toJson((LinkedList < Object >) new Yaml()
        .load(new FileInputStream(new File(Util.getPathToDatasetsDir(className)
            + "/" + file)))));

    writer.close();
  }

  public static String readFileToString(String fileName) {
    String outputLine = "";
    try {
      String sCurrentLine = null;
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      while ((sCurrentLine = br.readLine()) != null) {
        outputLine += sCurrentLine;
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return (outputLine);
  }
  
  // --------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------

  private static String dropClassName(String name) {
    return (name.substring(0, name.lastIndexOf("/")));
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * Property instance.
   */
  private static Properties   properties    = new Properties();

  /**
   * Path to flow property file.
   */
  private final static String propertieFile = "/data/siegerstein/WFW/apache-tomcat-6.0.18/webapps/ROOT/WEB-INF/flow.properties";
}
