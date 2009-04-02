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

package com.siegerstein.wfw.framework.ant;

import static com.siegerstein.wfw.framework.util.Util.readPropertieFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class ReplaceConstants {

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    ReplaceConstants replaceConstants = new ReplaceConstants();
    List < File > filesList = new LinkedList < File >();
    List < String > listComponentFiles = new ArrayList < String >();
    replaceConstants.getFiles(new File(replaceConstants.properties.getProperty("CATALINA_HOME")
        + "/webapps/ROOT" + replaceConstants.properties.getProperty("widgetsWebDir")),
        listComponentFiles, filesList);
    replaceConstants.replaceConstants(filesList);
  }

  private String readFileToString(String fileName) {
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

  private void getFiles(final File folder, final List < String > list,
      final List < File > filesList) {
    File[] files = folder.listFiles();

    for (int j = 0; j < files.length; ++j) {
      if (files[j].isFile() && files[j].getName().contains(".css")) {
        filesList.add(files[j]);
      }

      // Add all widget folder that are not "common"
      if (files[j].getName().equals("css")) {
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

  private String getWidgetPath(File fileName) {
    String widgetPath = fileName.getParentFile().getParentFile().toString()
        .substring(
            (properties.getProperty("CATALINA_HOME") + "/webapps/ROOT")
                .length());
    return (widgetPath);
  }

  private void replaceConstants(List < File > fileList) throws IOException {
    for (File file : fileList) {
      String fileInString = readFileToString(file.toString());
      BufferedWriter writer = new BufferedWriter(
          new FileWriter(file.toString()));
      writer.write(fileInString.replace("@WIDGET_PATH@", getWidgetPath(file)));
      writer.flush();
      writer.close();
    }
  }

  /**
   * Property instance.
   */
  public Properties properties = readPropertieFile();

}
