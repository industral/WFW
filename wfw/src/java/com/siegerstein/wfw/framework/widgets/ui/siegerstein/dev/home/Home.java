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

package com.siegerstein.wfw.framework.widgets.ui.siegerstein.dev.home;

import static com.siegerstein.wfw.framework.util.Util.YAML2JSON;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Home extends HttpServlet {

  // --------------------------------------------------------------------
  // Public methods
  // --------------------------------------------------------------------

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    YAML2JSON(getClass().getName(), "links.yml", response);
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * UUID.
   */
  private static final long serialVersionUID = 1L;

}
