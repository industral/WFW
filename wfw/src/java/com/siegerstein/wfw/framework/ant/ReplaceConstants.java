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

package com.siegerstein.wfw.framework.ant;

import static com.siegerstein.wfw.framework.util.Util.readFileToString;
import static com.siegerstein.wfw.framework.util.Util.readPropertieFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Read all (CSS/JS) files and replace constants.
 * 
 * @author Alex Ivasyuv
 */
public final class ReplaceConstants {

  // --------------------------------------------------------------------
  // Public methods
  // --------------------------------------------------------------------

  /**
   * @param args
   *          system command-line arguments.
   * @throws IOException
   *           if error occur.
   */
  public static void main(final String[] args) throws IOException {
    ReplaceConstants replaceConstants = new ReplaceConstants();
    List<File> filesList = new LinkedList<File>();
    List<String> listComponentFiles = new ArrayList<String>();
    replaceConstants.getFiles(new File(replaceConstants.properties
        .getProperty("CATALINA_HOME")
        + "/webapps/ROOT"
        + replaceConstants.properties.getProperty("widgetsWebDir")),
        listComponentFiles, filesList);
    replaceConstants.replaceConstants(filesList);
  }

  // --------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------

  /**
   * Constructor.
   */
  private ReplaceConstants() {
  }

  /**
   * Get list of files.
   * 
   * @param folder
   *          scanned folder name.
   * @param list
   *          accumulate collection of files.
   * @param filesList
   *          accumulate collection of files.
   */
  private void getFiles(final File folder, final List<String> list,
      final List<File> filesList) {
    File[] files = folder.listFiles();

    for (int j = 0; j < files.length; ++j) {
      if (files[j].isFile()
          && (files[j].getName().endsWith(".css")
              || files[j].getName().endsWith(".js") || files[j].getName()
              .endsWith(".xml"))) {
        filesList.add(files[j]);
      }

      // Add all widget folder that are not "common"
      if (files[j].getName().equals("css") || files[j].getName().equals("js")
          || files[j].getName().equals("xml")) {
        // Check if in folder present css-file
        if (files[j].listFiles().length != 0) {

          String fileName = files[j].getParent().toString().substring(
              (properties.getProperty("CATALINA_HOME") + "/webapps/ROOT")
                  .length());

          list.add(fileName);
        }
      }
      if (files[j].isDirectory()) {
        getFiles(files[j], list, filesList);
      }
    }

  }

  /**
   * Get widget path based on file location.
   * 
   * @param fileName
   *          file for which widget path should be determine.
   * @return widget path.
   */
  private String getWidgetPath(final File fileName) {
    String widgetPath = fileName.getParentFile().getParentFile().toString()
        .substring(
            (properties.getProperty("CATALINA_HOME") + "/webapps/ROOT")
                .length());
    return (widgetPath);
  }

  /**
   * Main method to replace constants.
   * 
   * @param fileList
   *          list with files that should be scanning for replacing.
   * @throws IOException
   *           if error occur.
   */
  private void replaceConstants(final List<File> fileList) throws IOException {
    for (File file : fileList) {
      String fileInString = readFileToString(file.toString());
      BufferedWriter writer = new BufferedWriter(
          new FileWriter(file.toString()));
      writer.write(fileInString.replace("@WIDGET_PATH@", getWidgetPath(file)));
      writer.flush();
      writer.close();
    }
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * Property instance.
   */
  private Properties properties = readPropertieFile();

}
