/**
 * $Id$
 *
 *  This code is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License (LGPL) as
 *  published by the Free Software Foundation; either version 2.1 of the
 *  License, or (at your option) any later version.
 *
 *  This code is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY of FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details. 
 */

/**
 * Title:        JBarcodeBean Demo
 * Description:  Demo/Test harness for Barcode JavaBeans Component
 * Copyright:    Copyright (C) 2004
 * Company:      Dafydd Walters
 * @Version      1.0.2
 */
package bardemo;

import javax.swing.*;
import java.awt.*;
import jbarcodebean.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.event.*;

/**
 * This class is a <tt>JDialog</tt> that displays JBarcodeBean HTML help.
 */
public class HelpViewer extends JDialog {
  JPanel buttonPanel = new JPanel();
  JButton okButton = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JEditorPane mainPanelEditorPane = new JEditorPane();

  public HelpViewer() {
    this(null, null, false);
  }

  public HelpViewer(JFrame parent, String title, boolean modal) {
    super(parent, title, modal);
    try {
      jbInit();
      mainPanelEditorPane.setEditorKit(new HTMLEditorKit());
      mainPanelEditorPane.setContentType("text/html");
      mainPanelEditorPane.addHyperlinkListener(new Hyperactive(mainPanelEditorPane));
      mainPanelEditorPane.setPage(Demo.class.getResource("JBarcodeBean.html"));
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    Dimension dim = getToolkit().getScreenSize();
    setLocation(50, 50);
    setSize((int)dim.getWidth() - 100, (int)dim.getHeight() - 100);
    show();
  }

  private void jbInit() throws Exception {
    okButton.setText("OK");
    okButton.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
      }
    });
    mainPanelEditorPane.setEditable(false);
    this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(mainPanelEditorPane, null);
    this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    buttonPanel.add(okButton, null);
  }

  void okButton_actionPerformed(ActionEvent e) {
    hide();
    dispose();
  }
}

class Hyperactive implements HyperlinkListener {

  protected JEditorPane pane;

  public Hyperactive(JEditorPane pane) {
    this.pane = pane;
  }

  public void hyperlinkUpdate(HyperlinkEvent e) {
    JEditorPane pane = (JEditorPane) e.getSource();
    if (e instanceof HTMLFrameHyperlinkEvent) {
      HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
      HTMLDocument doc = (HTMLDocument)pane.getDocument();
      doc.processHTMLFrameHyperlinkEvent(evt);
    } else if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
      try {
        pane.setCursor(Cursor.getDefaultCursor());
        pane.setPage(e.getURL());
      } catch (Throwable t) {
      }
    } else if (e.getEventType() == HyperlinkEvent.EventType.ENTERED) {
      // Change cursor to Hand
      pane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    } else if (e.getEventType() == HyperlinkEvent.EventType.EXITED) {
      // Restore cursor
      pane.setCursor(Cursor.getDefaultCursor());
    }
  }
}
