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
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;

/**
 * Main Utility class.
 * @author Alex Ivasyuv
 */
public final class Util {

  // --------------------------------------------------------------------
  // Public methods
  // --------------------------------------------------------------------

  /**
   * Read properties file.
   * @return {@link Properties} instance.
   */
  public static Properties readPropertieFile() {
    try {
      properties.load(new FileInputStream(PROPERTIE_FILE));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return (properties);
  }

  /**
   * Replace in string dots with slashes. com.siegerstein.wfw.framework.util
   * will be com/siegerstein/wfw/framework/util
   * @param name input string.
   * @return replacing result.
   */
  public static String dotToSlash(final String name) {
    return (name.replace(".", "/"));
  }

  /**
   * Return path to current class directory.
   * @param name full class name (with package path).
   * @return path result.
   */
  public static String getPathToCurrentClassDir(final String name) {
    readPropertieFile();
    return (dropClassName(properties.getProperty("classPath")
        + dotToSlash(name)));
  }

  /**
   * Return path to dataset directory in current class path.
   * @param name full class name (with package path).
   * @return dataset path.
   */
  public static String getPathToDatasetsDir(final String name) {
    readPropertieFile();
    return (dropClassName(properties.getProperty("datasetsPath")
        + dotToSlash(name)));
  }

  /**
   * Change servlet request parameters availability.
   * @param param servlet request parameter.
   * @return true if it present.
   */
  public static boolean isPresent(final String param) {
    return (param != null && !param.trim().equals("") && !param.equals("null"));
  }

  /**
   * Parse YAML file and convert it to JSON.
   * @param className class name of servlet where YAML file present.
   * @param file name of YAML file.
   * @param response {@link HttpServletResponse} response handle.
   * @throws UnsupportedEncodingException
   * @throws IOException if any other error occur.
   */
  @SuppressWarnings("unchecked")
  public static void yaml2json(final String className, final String file,
      final HttpServletResponse response) throws UnsupportedEncodingException,
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

  /**
   * Read file to String.
   * @param fileName file name that should be read.
   * @return {@link String} with file content.
   */
  public static String readFileToString(final String fileName) {
    String outputLine = "";
    List < String > outputList = readFileToList(fileName);
    for (String s : outputList) {
      outputLine += s + "\r\n";
    }
    return (outputLine);
  }

  /**
   * Read file to {@link List}.
   * @param fileName file name that should be read.
   * @return {@link List} with file content.
   */
  public static List < String > readFileToList(final String fileName) {
    List < String > outputList = new LinkedList < String >();
    try {
      String sCurrentLine = null;
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      while ((sCurrentLine = br.readLine()) != null) {
        outputList.add(sCurrentLine);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return (outputList);
  }

  /**
   * Read content from wfw-ignore file.
   * @return list of ignored files.
   */
  public static Collection < String > getIgnoredFiles() {
    Collection < String > filesList = readFileToList(properties
        .getProperty("ignoreFile"));

    HashSet < String > outputHashSet = new HashSet < String >();
    for (String s : filesList) {
      if (new File(properties.getProperty("rootDir") + s).isFile()) {
        outputHashSet.add(s);
      } else {
        HashSet < String > hs = new HashSet < String >();
        getFiles(new File(properties.getProperty("rootDir") + s), hs);
        outputHashSet.addAll(hs);
      }
    }
    return (outputHashSet);
  }

  // --------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------

  /**
   * Constructor.
   */
  private Util() {
  }

  /**
   * Get list of files in folder.
   * @param folder folder that should be scanned.
   * @param list list of files.
   */
  private static void getFiles(final File folder, final HashSet < String > list) {
    File[] files = folder.listFiles();
    for (int j = 0; j < files.length; ++j) {
      if (files[j].isFile()
          && (files[j].getName().endsWith(".js") || files[j].getName()
              .endsWith(".css"))) {
        list.add(files[j].toString().substring(
            properties.getProperty("rootDir").length() - 1));
      }
      if (files[j].isDirectory()) {
        getFiles(files[j], list);
      }
    }
  }

  /**
   * Drop className from full class name. In this case we will get class
   * package.
   * @param name full className.
   * @return package path.
   */
  private static String dropClassName(final String name) {
    return (name.substring(0, name.lastIndexOf("/")));
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * Property instance.
   */
  private static Properties   properties     = new Properties();

  /**
   * Path to flow property file.
   */
  private static final String PROPERTIE_FILE = "/data/siegerstein/WFW/apache-tomcat-6.0.18/"
                                                 + "webapps/ROOT/WEB-INF/flow.properties";
}
