/**
 * $Id$
 *
 *  This code is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License (LGPL) as
 *  published by the Free Software Foundation; either version 3.0 of the
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
 * @Version      1.2
 */
package bardemo;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import jbarcodebean.*;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;

import javax.swing.table.*;
import java.beans.*;
import java.lang.reflect.*;
import java.io.*;
import java.awt.print.*;
import java.util.*;
import java.net.*;
import java.awt.image.*;
import java.awt.geom.*;

/**
 * Demo application.
 *
 * @version 1.2
 */
public class Demo {

  /**
   * Application entry point.
   */
  public static void main(String[] args) {
    if (checkVersion() == false) {
      Frame f = new Frame("Error");
      f.add(new Label("Incorrect Java Version. Java 2 (1.2.2) or later is required.", Label.CENTER));
      f.setIconImage(f.getToolkit().getImage(Demo.class.getResource("demoicon.gif")));
      f.pack();
      f.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent evt) {
          System.exit(0);
        }
      });
      Dimension dim = f.getToolkit().getScreenSize();
      Rectangle abounds = f.getBounds();
      f.setLocation((dim.width - abounds.width) / 2, (dim.height - abounds.height) / 2);
      f.show();
    } else {
      DemoFrame demo1 = new DemoFrame();
      demo1.pack();
      Dimension dim = demo1.getToolkit().getScreenSize();
      Rectangle abounds = demo1.getBounds();
      demo1.setLocation((dim.width - abounds.width) / 2, (dim.height - abounds.height) / 2);
      demo1.show();
    }
  }

  private static boolean checkVersion() {
    String versionString = System.getProperty("java.version");
    int pos1 = versionString.indexOf(".", 0);
    int versionInt = Integer.parseInt(versionString.substring(0, pos1));
    if (versionInt < 1) {
      return false;
    } else if (versionInt > 1) {
      return true;
    }
    int pos2 = versionString.indexOf(".", pos1 + 1);
    versionInt = Integer.parseInt(versionString.substring(pos1 + 1, pos2));
    if (versionInt < 2) {
      return false;
    } else if (versionInt > 2) {
      return true;
    }
    versionInt = Integer.parseInt(versionString.substring(pos2 + 1));
    if (versionInt < 2) {
      return false;
    }
    return true;
  }
}

class DemoFrame extends JFrame {
  JPanel mainPanel = new JPanel();
  Border border1;
  Border border2;
  BorderLayout borderLayout1 = new BorderLayout();
  Border border3;
  Box lowerBox;
  JPanel barcodePanel = new JPanel();
  JPanel buttonPanel = new JPanel();
  Box box1;
  JButton printButton = new JButton();
  Component hStrut1;
  JButton helpButton = new JButton();
  Component hStrut2;
  JButton exitButton = new JButton();
  JBarcodeBean barcode = new JBarcodeBean();
  Border border4;
  JPanel outerPropertiesPanel = new JPanel();
  Border border5;
  BorderLayout borderLayout2 = new BorderLayout();
  Border border6;
  JPanel propertiesPanel = new JPanel();
  Border border7;
  JPanel topPanel = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  Border border8;
  Border border9;
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  JScrollPane propertiesScrollPane = new JScrollPane();
  BorderLayout borderLayout5 = new BorderLayout();
  JBeanPropertyEditor beanPropertyEditor;
  JButton gifButton = new JButton();
  JButton copyButton = new JButton();
  Component hStrut0;
  JLabel jLabel1 = new JLabel();
  JLabel codeTypeLabel = new JLabel();
  BarcodePropertyChangeListener bpcl;

  public DemoFrame() {
    super("JBarcodeBean Demo");
    setIconImage(getToolkit().getImage(DemoFrame.class.getResource("demoicon.gif")));
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    bpcl = new BarcodePropertyChangeListener(codeTypeLabel, barcode);
    barcode.addPropertyChangeListener(bpcl);
    bpcl.setCodeTypeCaption();

   helpButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent avt) {
      HelpViewer dialog = new HelpViewer(DemoFrame.this, "JBarcodeBean Documentation", true);
    }
   });
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEmptyBorder(5,5,5,5);
    border2 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(142, 142, 142),new Color(99, 99, 99)),BorderFactory.createEmptyBorder(5,5,5,5));
    lowerBox = Box.createVerticalBox();
    box1 = Box.createHorizontalBox();
    hStrut1 = Box.createHorizontalStrut(8);
    hStrut2 = Box.createHorizontalStrut(8);
    border4 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(142, 142, 142),new Color(99, 99, 99));
    border5 = BorderFactory.createEmptyBorder(0,0,5,0);
    border6 = BorderFactory.createBevelBorder(BevelBorder.RAISED,new Color(218, 218, 218),Color.white,new Color(107, 107, 107),new Color(74, 74, 74));
    border7 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(99, 99, 99),new Color(142, 142, 142)),BorderFactory.createEmptyBorder(5,5,5,5));
    border8 = BorderFactory.createEmptyBorder(5,0,5,0);
    border9 = BorderFactory.createEmptyBorder(5,5,5,5);
    hStrut0 = Box.createHorizontalStrut(8);
    mainPanel.setBorder(border1);
    mainPanel.setLayout(borderLayout1);
    printButton.setToolTipText("Print the barcode");
    printButton.setText("Print");
    printButton.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        printButton_actionPerformed(e);
      }
    });
    helpButton.setToolTipText("View documentation");
    helpButton.setText("Documentation");
    exitButton.setToolTipText("Exit this demo");
    exitButton.setText("Exit");
    exitButton.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        exitButton_actionPerformed(e);
      }
    });
    barcodePanel.setBorder(border4);
    barcodePanel.setLayout(flowLayout1);
    outerPropertiesPanel.setBorder(border5);
    outerPropertiesPanel.setLayout(borderLayout2);
    propertiesPanel.setBorder(border7);
    propertiesPanel.setLayout(borderLayout4);
    topPanel.setLayout(borderLayout3);
    barcode.setBorder(border9);
    beanPropertyEditor = new JBeanPropertyEditor(barcode);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    this.setDefaultCloseOperation(3);
    this.getContentPane().setLayout(borderLayout5);
    gifButton.setToolTipText("Write barcode GIF file");
    gifButton.setText("Output GIF");
    gifButton.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        gifButton_actionPerformed(e);
      }
    });
    copyButton.setToolTipText("Copy barcode image to clipboard");
    copyButton.setText("Copy");
    copyButton.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        copyButton_actionPerformed(e);
      }
    });
    jLabel1.setText("Bean Properties");
    codeTypeLabel.setAlignmentX((float) 0.5);
    codeTypeLabel.setToolTipText("");
    codeTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
    this.getContentPane().add(mainPanel, BorderLayout.CENTER);
    mainPanel.add(lowerBox, BorderLayout.SOUTH);
    lowerBox.add(codeTypeLabel, null);
    lowerBox.add(barcodePanel, null);
    barcodePanel.add(barcode, null);
    lowerBox.add(buttonPanel, null);
    buttonPanel.add(box1, null);
    box1.add(printButton, null);
    box1.add(hStrut0, null);
    box1.add(gifButton, null);
    box1.add(copyButton, null);
    box1.add(hStrut1, null);
    box1.add(helpButton, null);
    box1.add(hStrut2, null);
    box1.add(exitButton, null);
    mainPanel.add(outerPropertiesPanel, BorderLayout.CENTER);
    outerPropertiesPanel.add(propertiesPanel, BorderLayout.CENTER);
    mainPanel.add(topPanel, BorderLayout.NORTH);
    topPanel.add(jLabel1, BorderLayout.WEST);
    propertiesPanel.add(propertiesScrollPane, BorderLayout.CENTER);
    propertiesScrollPane.getViewport().add(beanPropertyEditor, null);
  }

  void exitButton_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void gifButton_actionPerformed(ActionEvent e) {
    // Prompt for file name, then output GIF file.
    if (barcode.getAngleDegrees() % 90 != 0) {
      if (JOptionPane.showConfirmDialog(this, "Generating GIFs for barcodes that are not " +
          "angled at a multiple of 90 degrees is not recommended.\nAre you sure?",
          "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION) {
        return;
      }
    }
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save GIF File");
    fileChooser.showSaveDialog(this);
    File f = fileChooser.getSelectedFile();
    if (f != null) {
      if (!f.getPath().toUpperCase().endsWith(".GIF")) {
        f = new File(f.getPath() + ".GIF");
      }
      try {
        FileOutputStream fos = new FileOutputStream(f);
        barcode.gifEncode(fos);
        fos.close();
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error writing GIF file", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

//Inner class is used to hold an image while on the clipboard.
  public static class ImageSelection
    implements Transferable 
  {
    // the Image object which will be housed by the ImageSelection
    private Image image;

    public ImageSelection(Image image) {
      this.image = image;
    }

    // Returns the supported flavors of our implementation
    public DataFlavor[] getTransferDataFlavors() 
    {
      return new DataFlavor[] {DataFlavor.imageFlavor};
    }
    
    // Returns true if flavor is supported
    public boolean isDataFlavorSupported(DataFlavor flavor) 
    {
      return DataFlavor.imageFlavor.equals(flavor);
    }

    // Returns Image object housed by Transferable object
    public Object getTransferData(DataFlavor flavor)
      throws UnsupportedFlavorException,IOException 
    {
      if (!DataFlavor.imageFlavor.equals(flavor)) 
      {
        throw new UnsupportedFlavorException(flavor);
      }
      // else return the payload
      return image;
    }

  }
  
  void copyButton_actionPerformed(ActionEvent e) {
	
  	
  	if (barcode.getAngleDegrees() % 90 != 0) {
        if (JOptionPane.showConfirmDialog(this, "Generating GIFs for barcodes that are not " +
            "angled at a multiple of 90 degrees is not recommended.\nAre you sure?",
            "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION) {
          return;
        }
      }

  	ByteArrayOutputStream fos = new ByteArrayOutputStream();
	try {
		barcode.gifEncode(fos);
		fos.close();
	} catch (IOException e1) {
		e1.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error writing GIF file", "Error", JOptionPane.ERROR_MESSAGE);
		return;
	}
  	
  	Image image = Toolkit.getDefaultToolkit().createImage( fos.toByteArray() );
  	ImageSelection imageSelection =
    new ImageSelection(image);
    Toolkit
      .getDefaultToolkit()
      .getSystemClipboard()
      .setContents(imageSelection, null);
	
  }
  
  
  void printButton_actionPerformed(ActionEvent e) {
    PrinterJob pj = PrinterJob.getPrinterJob();
    PageFormat pf = pj.defaultPage();
    pj.setPrintable(new Printable() {
      public int print(Graphics g, PageFormat format, int pageNum) throws PrinterException {
        if (pageNum > 0) {
          return Printable.NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(format.getImageableX(), format.getImageableY());
        Color oldBackground = barcode.getBackground();
        barcode.setBackground(barcode.getBarcodeBackground());
        barcode.paint(g2d);
        barcode.setBackground(oldBackground);
        return Printable.PAGE_EXISTS;
      }
    }, pf);
    try {
      pj.print();
      JOptionPane.showMessageDialog(this, "Barcode has been printed", "JBarcodeBean", JOptionPane.INFORMATION_MESSAGE);
    } catch (PrinterException ex) {
      JOptionPane.showMessageDialog(this, "Error printing barcode", "JBarcodeBean", JOptionPane.ERROR_MESSAGE);
    }
  }

  private BufferedImage getBarcodeImage(JBarcodeBean bean) throws IOException {
    Color oldBackground = bean.getBackground();
    bean.setBackground(bean.getBarcodeBackground());
    Border oldBorder = bean.getBorder();
    bean.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
    Dimension preferredSize  = bean.getPreferredSize();
    BufferedImage bi = (BufferedImage)bean.createImage(preferredSize.width, preferredSize.height);
    if (bi == null) {
      // We are running server-side or component has not yet been displayed,
      // so a different method is needed to create the buffered image.
      bi = new BufferedImage(preferredSize.width, preferredSize.height, BufferedImage.TYPE_INT_RGB);
    }
    Graphics2D g = bi.createGraphics();
    bean.paint(g);
    bean.setBackground(oldBackground);
    bean.setBorder(oldBorder);
    return bi;
  }
}

class BarcodePropertyChangeListener implements PropertyChangeListener {

  protected JLabel label;
  protected HashMap codeTypes = new HashMap();
  protected JBarcodeBean barcode;

  public BarcodePropertyChangeListener(JLabel label, JBarcodeBean barcode) {
    this.label = label;
    this.barcode = barcode;
    // Code 128
    codeTypes.put("jbarcodebean.Code128",
        "<html><center>" +
        "Code 128 is a compact, error checking code that can encode the<br>" +
        "full 128 character ASCII set and four special function codes.<br>" +
        "Check digit is mandatory.</center></html>"
    );
    // Code 3 of 9 3:1
    codeTypes.put("jbarcodebean.Code39",
        "<html><center>" +
        "Code 3 of 9 can encode the digits 0 through 9, the<br>" +
        "upper case letters A through Z, and the characters minus(-)<br>" +
        "dot(.) space( ) dollar($) slash(/) plus(+)<br>" +
        "and percent(%). Check digit is optional.</center></html>"
    );
    // Code 3 of 9 3.0
    codeTypes.put("jbarcodebean.Code39_2to1",
        "<html><center>" +
        "Code 3 of 9 can encode the digits 0 through 9, the<br>" +
        "upper case letters A through Z, and the characters minus(-)<br>" +
        "dot(.) space( ) dollar($) slash(/) plus(+)<br>" +
        "and percent(%). Check digit is optional.</center></html>"
    );
    // Codabar 3:1
    codeTypes.put("jbarcodebean.Codabar",
        "<html><center>" +
        "Codabar can encode the digits 0 through 9, and the<br>" +
        "characters minus(-) dot(.) dollar($) slash(/) plus(+)<br>" +
        "and colon(:). Codabar has no check digit.</center></html>"
    );
    // Codabar 3.0
    codeTypes.put("jbarcodebean.Codabar_2to1",
        "<html><center>" +
        "Codabar can encode the digits 0 through 9, and the<br>" +
        "characters minus(-) dot(.) dollar($) slash(/) plus(+)<br>" +
        "and colon(:). Codabar has no check digit.</center></html>"
    );
    // Extended Code 3 of 9 3:1
    codeTypes.put("jbarcodebean.ExtendedCode39",
        "<html><center>" +
        "Extended Code 3 of 9 can encode the full set of 128 ASCII<br>" +
        "characters. Check digit is optional.</center></html>"
    );
    // Extended Code 3 of 9 3.0
    codeTypes.put("jbarcodebean.ExtendedCode39_2to1",
        "<html><center>" +
        "Extended Code 3 of 9 can encode the full set of 128 ASCII<br>" +
        "characters. Check digit is optional.</center></html>"
    );
    // Interleaved 2 of 5 3:1
    codeTypes.put("jbarcodebean.Interleaved25",
        "<html><center>" +
        "Interleaved 2 of 5 can encode the digits 0 through 9 only.<br>" +
        "Check digit is optional.</center></html>"
    );
    // Interleaved 2 of 5 3.0
    codeTypes.put("jbarcodebean.Interleaved25_2to1",
        "<html><center>" +
        "Interleaved 2 of 5 can encode the digits 0 through 9 only.<br>" +
        "Check digit is optional.</center></html>"
    );
    // MSI
    codeTypes.put("jbarcodebean.MSI",
        "<html><center>" +
        "MSI can encode the digits 0 through 9 only.<br>" +
        "Check digit is mandatory.</center></html>"
    );
    // EAN-13
    codeTypes.put("jbarcodebean.Ean13",
        "<html><center>" +
        "EAN-13 can encode the digits 0 through 9 only.<br>" +
        "The string to encode MUST be EXACTLY 12 digits long.<br>" +
        "Check digit is mandatory.</center></html>"
    );
    // EAN-8
    codeTypes.put("jbarcodebean.Ean8",
        "<html><center>" +
        "EAN-8 can encode the digits 0 through 9 only.<br>" +
        "The string to encode MUST be EXACTLY 7 digits long.<br>" +
        "Check digit is mandatory.</center></html>"
    );
  }

  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("codeType")) {
      setCodeTypeCaption();
    }
  }

  public void setCodeTypeCaption() {
    String caption = (String)codeTypes.get(barcode.getCodeType().getClass().getName());
    if (caption != null) {
      label.setText(caption);
    } else {
      label.setText("");
    }
    label.setPreferredSize(new Dimension(400, (int)label.getPreferredSize().getHeight()));
  }
}

