/**
 * Application example
 */

package net.sourceforge.jbarcodebean.demo;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;

import net.sourceforge.jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Code128;
import jbarcodebean.*;

/**
 * Application example code.
 */
public class SimpleDemo extends JFrame {

  private static String titleString = "Simple JBarcodeBean Demo";

  JBarcodeBean bb = new JBarcodeBean();
  JButton displayButton = new JButton("Display");
  JButton printButton = new JButton("Print");
  JLabel labelCode = new JLabel("Text to encode");
  JTextField codeTextBox = new JTextField(20);

  // Application Entry Point
  public static void main(String[] args) {
    SimpleDemo simpleDemo = new SimpleDemo();
  }

  // Constructor
  public SimpleDemo() {

    super(titleString);

    // Set some JBarcodeBean properties
    bb.setCodeType(new Code128());
    bb.setShowText(true);
    bb.setCode("");

    JPanel tempPanel;

    // Create a button panel at the bottom of the form
    tempPanel = new JPanel();
    tempPanel.add(displayButton);
    tempPanel.add(printButton);
    getContentPane().add(tempPanel, "South");

    // Text field at the top of the form
    tempPanel = new JPanel();
    tempPanel.add(labelCode);
    tempPanel.add(codeTextBox);
    labelCode.setLabelFor(codeTextBox);
    getContentPane().add(tempPanel, "North");

    // Barcode in the center
    getContentPane().add(bb, "Center");

    // Set component listeners
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        System.exit(0);
      }
    });
    displayButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        bb.setCode(codeTextBox.getText());
      }
    });
    printButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        bb.setCode(codeTextBox.getText());
        printBarcode();
      }
    });

    // Display form
    pack();
    show();
  }

  // This method prints the barcode on the default printer
  private void printBarcode() {
    PrinterJob pj = PrinterJob.getPrinterJob();
    PageFormat pf = pj.defaultPage();
    pj.setPrintable(new Printable() {
      public int print(Graphics g, PageFormat format, int pageNum) throws PrinterException {
        if (pageNum > 0) {
          return Printable.NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;
        // Move origin to take account of Top and Left page margins.
        g2d.translate(format.getImageableX(), format.getImageableY());
        // Calculate position of top-left corner of barcode based on page
        // size, so that barcode appears in the center of the printed page.
        double xPos, yPos;
        xPos = (format.getImageableWidth() - bb.getWidth()) / 2;
        yPos = (format.getImageableHeight() - bb.getHeight()) / 2;
        // Print the barcode
        printBarcode(g2d, bb, xPos, yPos);
        return Printable.PAGE_EXISTS;
      }
    }, pf);
    try {
      pj.print();
      JOptionPane.showMessageDialog(this, "Barcode has been printed", titleString, JOptionPane.INFORMATION_MESSAGE);
    } catch (PrinterException ex) {
      JOptionPane.showMessageDialog(this, "Error printing barcode", titleString, JOptionPane.ERROR_MESSAGE);
    }
  }

  // Private method to print a barcode at a specific location on the page.
  private void printBarcode(Graphics2D printGraphics, JBarcodeBean barcode,
                            double xPos, double yPos) {
    Color oldBackground = bb.getBackground();
    bb.setBackground(bb.getBarcodeBackground());
    printGraphics.translate(xPos, yPos);
    bb.paint(printGraphics);
    printGraphics.translate(-xPos, -yPos);
    bb.setBackground(oldBackground);
  }
}
