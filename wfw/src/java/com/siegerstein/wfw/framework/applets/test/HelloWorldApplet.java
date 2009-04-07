package com.siegerstein.wfw.framework.applets.test;

import java.awt.EventQueue;

import javax.swing.JApplet;
import javax.swing.JLabel;

/**
 *Simple Hello World applet.
 * @author Alex Ivasyuv
 */
public class HelloWorldApplet extends JApplet {

  // --------------------------------------------------------------------
  // Public methods
  // --------------------------------------------------------------------

  /**
   * Main applet method.
   */
  public final void init() {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        JLabel label = new JLabel("Hello World!");
        add(label);
      }
    });
  }

  // --------------------------------------------------------------------
  // Private variables
  // --------------------------------------------------------------------

  /**
   * UUID.
   */
  private static final long serialVersionUID = -5253459980406551544L;
}
