/***************************************************************************
 *                             WFW - Web Framework                         *
 *                      Copyright (C) 2009 Alex Y. Ivasyuv                 *
 *                                                                         *
 * This program is free software: you can redistribute it and/or modify    *
 * it under the terms of the GNU General Public License as published by    *
 * the Free Software Foundation, either version 3 of the License, or       *
 * (at your option) any later version.                                     *
 *                                                                         *
 * This program is distributed in the hope that it will be useful,         *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the           *
 * GNU General Public License for more details.                            *
 *                                                                         *
 * You should have received a copy of the GNU General Public License       *
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.   *
 **************************************************************************/

package com.siegerstein.wfw.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
