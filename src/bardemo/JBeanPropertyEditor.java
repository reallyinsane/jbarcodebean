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
 * @Version      1.1
 */
package bardemo;

import javax.swing.*;
import java.awt.*;
import java.beans.*;
import java.lang.reflect.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * A generic Javabean property editing component.
 *
 * @version 1.0.2
 */
public class JBeanPropertyEditor extends JPanel {

  protected Object bean;
  protected GridLayout gridLayout;
  protected BeanInfo info;
  protected PropertyDescriptor[] descriptors;

  public JBeanPropertyEditor(Object bean) throws
      ClassNotFoundException, IllegalAccessException, InstantiationException {
    this.bean = bean;
    info = (BeanInfo) Class.forName(bean.getClass().getName() + "BeanInfo").newInstance();
    descriptors = info.getPropertyDescriptors();
    gridLayout = new GridLayout(descriptors.length , 2, 5, 5);
    init();
  }

  protected void init() {
    JPanel innerPanel = new JPanel();
    innerPanel.setLayout(gridLayout);
    setLayout(new FlowLayout());
    add(innerPanel);
    for (int row = 0; row < descriptors.length; row++) {
      JLabel propertyName = new JLabel(descriptors[row].getName());
      propertyName.setIcon(new ImageIcon(JBeanPropertyEditor.class.getResource("property_icon.gif")));
      propertyName.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      innerPanel.add(propertyName);
      Container editPanel;
      try {
        if (descriptors[row].getPropertyType() == String.class) {
          // String property.
          Method m = bean.getClass().getMethod("get" +
            descriptors[row].getName().substring(0,1).toUpperCase() +
            descriptors[row].getName().substring(1), null);
          editPanel = Box.createVerticalBox();
          JTextField textField = new JTextField((String)(m.invoke(bean, null)));
          propertyName.setLabelFor(textField);
          textField.addKeyListener(new PropertyKeyListener(descriptors[row], textField, bean));
          textField.setMaximumSize(new Dimension(32767, (int)textField.getPreferredSize().getHeight()));
          editPanel.add(Box.createVerticalGlue());
          editPanel.add(textField);
          editPanel.add(Box.createVerticalGlue());
        } else if (descriptors[row].getPropertyType() == int.class ||
                   descriptors[row].getPropertyType() == double.class ||
                   descriptors[row].getPropertyType() == float.class ||
                   descriptors[row].getPropertyType() == long.class ||
                   descriptors[row].getPropertyType() == byte.class ||
                   descriptors[row].getPropertyType() == short.class) {
          // numeric property.
          Method m = bean.getClass().getMethod("get" +
            descriptors[row].getName().substring(0,1).toUpperCase() +
            descriptors[row].getName().substring(1), null);
          editPanel = Box.createVerticalBox();
          JTextField textField = new JTextField(m.invoke(bean, null).toString());
          propertyName.setLabelFor(textField);
          textField.addKeyListener(new PropertyKeyListener(descriptors[row], textField, bean));
          textField.setMaximumSize(new Dimension(32767, (int)textField.getPreferredSize().getHeight()));
          editPanel.add(Box.createVerticalGlue());
          editPanel.add(textField);
          editPanel.add(Box.createVerticalGlue());
        } else if (descriptors[row].getPropertyType() == boolean.class) {
          // boolean property.
          Method m = bean.getClass().getMethod("is" +
            descriptors[row].getName().substring(0,1).toUpperCase() +
            descriptors[row].getName().substring(1), null);
          JCheckBox checkBox = new JCheckBox("", ((Boolean)(m.invoke(bean, null))).booleanValue());
          propertyName.setLabelFor(checkBox);
          checkBox.addItemListener(new BooleanPropertyItemListener(descriptors[row], checkBox, bean));
          editPanel = checkBox;
          if (descriptors[row].isBound()) {
            Method addMethod = bean.getClass().getMethod("addPropertyChangeListener",
                new Class[] {PropertyChangeListener.class});
            addMethod.invoke(bean, new Object[] {new BooleanPropertyChangeListener(checkBox, descriptors[row].getName())});
          }
        } else if (descriptors[row].getPropertyType() == Font.class) {
          // Font property.
          Method m = bean.getClass().getMethod("get" +
            descriptors[row].getName().substring(0,1).toUpperCase() +
            descriptors[row].getName().substring(1), null);
          Font f = (Font)m.invoke(bean, null);
          JComboBox fontNameComboBox = new JComboBox(); // Font name
          fontNameComboBox.addItem("Monospaced");
          fontNameComboBox.addItem("Serif");
          fontNameComboBox.addItem("SansSerif");
          fontNameComboBox.setSelectedItem(f.getName());
          JComboBox fontSizeComboBox = new JComboBox(); // Font size
          fontSizeComboBox.addItem("10");
          fontSizeComboBox.addItem("12");
          fontSizeComboBox.addItem("14");
          fontSizeComboBox.addItem("16");
          fontSizeComboBox.setSelectedItem(new Integer(f.getSize()).toString());
          JComboBox fontStyleComboBox = new JComboBox();  // Font style
          fontStyleComboBox.addItem("Plain");
          fontStyleComboBox.addItem("Bold");
          fontStyleComboBox.addItem("Italic");
          fontStyleComboBox.addItem("Bold Italic");
          fontStyleComboBox.setSelectedIndex(f.getStyle());
          editPanel = Box.createVerticalBox();
          Box fontPanel = Box.createHorizontalBox();
          propertyName.setLabelFor(fontPanel);
          fontPanel.add(fontNameComboBox);
          fontPanel.add(Box.createHorizontalGlue());
          fontPanel.add(Box.createHorizontalStrut(5));
          fontPanel.add(fontStyleComboBox);
          fontPanel.add(Box.createHorizontalGlue());
          fontPanel.add(Box.createHorizontalStrut(5));
          fontPanel.add(fontSizeComboBox);
          editPanel.add(Box.createVerticalGlue());
          editPanel.add(fontPanel);
          editPanel.add(Box.createVerticalGlue());
          FontPropertyItemListener fpil = new FontPropertyItemListener(fontNameComboBox,
              fontSizeComboBox, fontStyleComboBox, bean, descriptors[row]);
          fontNameComboBox.addItemListener(fpil);
          fontSizeComboBox.addItemListener(fpil);
          fontStyleComboBox.addItemListener(fpil);
        } else if (descriptors[row].getPropertyType() == Border.class) {
          // Border property
          Method m = bean.getClass().getMethod("get" +
            descriptors[row].getName().substring(0,1).toUpperCase() +
            descriptors[row].getName().substring(1), null);
          Border b = (Border)m.invoke(bean, null);
          editPanel = Box.createHorizontalBox();
          JLabel borderLabel = new JLabel(
            (b == null) ? "No border" : b.getClass().getName());
          propertyName.setLabelFor(borderLabel);
          editPanel.add(borderLabel);
          editPanel.add(Box.createHorizontalGlue());
          editPanel.add(Box.createHorizontalStrut(5));
          JButton editButton = new JButton("Edit");
          editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
              JOptionPane.showMessageDialog(JBeanPropertyEditor.this, "JBarcodeBean supports the full set of " +
                "JFC Swing borders.\nThis demo does not support Border editing.", "Edit Border",
                JOptionPane.INFORMATION_MESSAGE);
            }
          });
          editPanel.add(editButton);

        } else if (descriptors[row].getPropertyType() == Color.class) {
          // Color property.
          Method m = bean.getClass().getMethod("get" +
            descriptors[row].getName().substring(0,1).toUpperCase() +
            descriptors[row].getName().substring(1), null);
          Color c = (Color)m.invoke(bean, null);
          editPanel = Box.createHorizontalBox();
          JLabel colorLabel = new JLabel(
            (c == null) ? "Default color (null)" : "R:" + c.getRed() + " G:" + c.getGreen() + " B:" + c.getBlue()
          );
          propertyName.setLabelFor(colorLabel);
          editPanel.add(colorLabel);
          editPanel.add(Box.createHorizontalGlue());
          editPanel.add(Box.createHorizontalStrut(5));
          JButton editButton = new JButton("Edit");
          editButton.addActionListener(new ColorPropertyActionListener(descriptors[row],
            bean, this, colorLabel));
          editPanel.add(editButton);
        } else {
          Method m = bean.getClass().getMethod("get" +
            descriptors[row].getName().substring(0,1).toUpperCase() +
            descriptors[row].getName().substring(1), null);
          Object o = m.invoke(bean, null);
          // Check if there's a property editor for this type.
          Class propertyEditorClass = descriptors[row].getPropertyEditorClass();
          if (propertyEditorClass != null) {
            // Property has a PropertyEditor class.
            PropertyEditor pe = (PropertyEditor) propertyEditorClass.newInstance();
            pe.setValue(o);
            editPanel = Box.createVerticalBox();
            JComboBox comboBox = new JComboBox(pe.getTags());
            propertyName.setLabelFor(comboBox);
            comboBox.setSelectedItem(pe.getAsText());
            comboBox.addItemListener(new PropertyEditorItemListener(descriptors[row], comboBox, bean, pe, m.getReturnType()));
            comboBox.setMaximumSize(new Dimension(32767, (int)comboBox.getPreferredSize().getHeight()));
            editPanel.add(Box.createVerticalGlue());
            editPanel.add(comboBox);
            editPanel.add(Box.createVerticalGlue());
          } else {
            editPanel = new JLabel(
              (o == null) ? "Default " + descriptors[row].getName() + " (null)" : o.toString()
            );
            propertyName.setLabelFor(editPanel);
          }
        }
      } catch (Exception e) {
        editPanel = new JLabel("Unknown");
      }
      innerPanel.add(editPanel);
    }
  }
}

class PropertyKeyListener implements KeyListener {

  protected PropertyDescriptor descriptor;
  protected JTextField textField;
  protected Object bean;

  public PropertyKeyListener(PropertyDescriptor descriptor, JTextField textField, Object bean) {
    this.descriptor = descriptor;
    this.textField = textField;
    this.bean = bean;
  }

  public void keyPressed(KeyEvent event) {
  }

  public void keyReleased(KeyEvent event) {
    try {
      Method m;
      if (descriptor.getPropertyType() == String.class) {
        // String
        m = bean.getClass().getMethod("set" +
          descriptor.getName().substring(0,1).toUpperCase() +
          descriptor.getName().substring(1), new Class[] {String.class});
        m.invoke(bean, new Object[] {textField.getText()});
      } else if (descriptor.getPropertyType() == int.class) {
        // int
        m = bean.getClass().getMethod("set" +
          descriptor.getName().substring(0,1).toUpperCase() +
          descriptor.getName().substring(1), new Class[] {int.class});
        m.invoke(bean, new Object[] {new Integer(textField.getText())});
      } else if (descriptor.getPropertyType() == long.class) {
        // long
        m = bean.getClass().getMethod("set" +
          descriptor.getName().substring(0,1).toUpperCase() +
          descriptor.getName().substring(1), new Class[] {long.class});
        m.invoke(bean, new Object[] {new Long(textField.getText())});
      } else if (descriptor.getPropertyType() == short.class) {
        // short
        m = bean.getClass().getMethod("set" +
          descriptor.getName().substring(0,1).toUpperCase() +
          descriptor.getName().substring(1), new Class[] {short.class});
        m.invoke(bean, new Object[] {new Short(textField.getText())});
      } else if (descriptor.getPropertyType() == byte.class) {
        // byte
        m = bean.getClass().getMethod("set" +
          descriptor.getName().substring(0,1).toUpperCase() +
          descriptor.getName().substring(1), new Class[] {byte.class});
        m.invoke(bean, new Object[] {new Byte(textField.getText())});
      } else if (descriptor.getPropertyType() == double.class) {
        // double
        m = bean.getClass().getMethod("set" +
          descriptor.getName().substring(0,1).toUpperCase() +
          descriptor.getName().substring(1), new Class[] {double.class});
        m.invoke(bean, new Object[] {new Double(textField.getText())});
      } else if (descriptor.getPropertyType() == float.class) {
        // float
        m = bean.getClass().getMethod("set" +
          descriptor.getName().substring(0,1).toUpperCase() +
          descriptor.getName().substring(1), new Class[] {float.class});
        m.invoke(bean, new Object[] {new Float(textField.getText())});
      }
    } catch (Exception ex) {
    }
  }

  public void keyTyped(KeyEvent event) {
  }
}

class BooleanPropertyItemListener implements ItemListener {

  protected PropertyDescriptor descriptor;
  protected JCheckBox checkBox;
  protected Object bean;

  public BooleanPropertyItemListener(PropertyDescriptor descriptor, JCheckBox checkBox, Object bean) {
    this.descriptor = descriptor;
    this.checkBox = checkBox;
    this.bean = bean;
  }

  public void itemStateChanged(ItemEvent event) {
    try {
      Method m = bean.getClass().getMethod("set" +
        descriptor.getName().substring(0,1).toUpperCase() +
        descriptor.getName().substring(1), new Class[] {boolean.class});
      m.invoke(bean, new Object[] {new Boolean(checkBox.isSelected())});
    } catch (Exception ex1) {
      // Property may not have changed.
      try {
        Method getter = bean.getClass().getMethod("is" +
          descriptor.getName().substring(0,1).toUpperCase() +
          descriptor.getName().substring(1), null);
        checkBox.setSelected(((Boolean)(getter.invoke(bean, null))).booleanValue());
      } catch (Exception ex2) {
      }
    }
  }
}

class BooleanPropertyChangeListener implements PropertyChangeListener {

  protected JCheckBox checkBox;
  protected String propertyName;

  public BooleanPropertyChangeListener(JCheckBox checkBox, String propertyName) {
    this.checkBox = checkBox;
    this.propertyName = propertyName;
  }

  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals(propertyName)) {
      checkBox.setSelected(((Boolean)evt.getNewValue()).booleanValue());
    }
  }
}

class PropertyEditorItemListener implements ItemListener {

  protected PropertyDescriptor descriptor;
  protected JComboBox comboBox;
  protected Object bean;
  protected PropertyEditor pe;
  protected Class propertyClass;

  public PropertyEditorItemListener(PropertyDescriptor descriptor, JComboBox comboBox, Object bean,
      PropertyEditor pe, Class propertyClass) {
    this.descriptor = descriptor;
    this.comboBox = comboBox;
    this.bean = bean;
    this.pe = pe;
    this.propertyClass = propertyClass;
  }

  public void itemStateChanged(ItemEvent event) {
    try {
      pe.setAsText((String)comboBox.getSelectedItem());
      Method m = bean.getClass().getMethod("set" +
        descriptor.getName().substring(0,1).toUpperCase() +
        descriptor.getName().substring(1), new Class[] {propertyClass});
      m.invoke(bean, new Object[] {pe.getValue()});
    } catch (Exception ex) {
    }
  }
}

class ColorPropertyActionListener implements ActionListener {

  protected PropertyDescriptor descriptor;
  protected Object bean;
  protected JPanel propertyPanel;
  protected JLabel colorLabel;

  public ColorPropertyActionListener(PropertyDescriptor descriptor, Object bean,
      JPanel propertyPanel, JLabel colorLabel) {
    this.descriptor = descriptor;
    this.bean = bean;
    this.propertyPanel = propertyPanel;
    this.colorLabel = colorLabel;
  }

  public void actionPerformed(ActionEvent e) {
    try {
      Method m = bean.getClass().getMethod("get" +
        descriptor.getName().substring(0,1).toUpperCase() +
        descriptor.getName().substring(1), null);
      Color c = (Color)m.invoke(bean, null);
      c = JColorChooser.showDialog(propertyPanel, "Select Color", c);
      m = bean.getClass().getMethod("set" +
        descriptor.getName().substring(0,1).toUpperCase() +
        descriptor.getName().substring(1), new Class[] {Color.class});
      if (c != null) {
        m.invoke(bean, new Object[] {c});
        colorLabel.setText(
          (c == null) ? "Default color (null)" : "R:" + c.getRed() + " G:" + c.getGreen() + " B:" + c.getBlue()
        );
      }
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }
}

class FontPropertyItemListener implements ItemListener {

  protected JComboBox fontNameComboBox;
  protected JComboBox fontSizeComboBox;
  protected JComboBox fontStyleComboBox;
  protected Object bean;
  protected PropertyDescriptor descriptor;

  public FontPropertyItemListener(JComboBox fontNameComboBox,
              JComboBox fontSizeComboBox, JComboBox fontStyleComboBox,
              Object bean, PropertyDescriptor descriptor) {

    this.fontNameComboBox = fontNameComboBox;
    this.fontSizeComboBox = fontSizeComboBox;
    this.fontStyleComboBox = fontStyleComboBox;
    this.bean = bean;
    this.descriptor = descriptor;
  }

  public void itemStateChanged(ItemEvent event) {
    try {
      Method m = bean.getClass().getMethod("get" +
        descriptor.getName().substring(0,1).toUpperCase() +
        descriptor.getName().substring(1), null);
      Font f = (Font)m.invoke(bean, null);

      if (event.getSource() == fontNameComboBox) {
        // Name changed
        f = new Font((String)fontNameComboBox.getSelectedItem(), f.getStyle(), f.getSize());
      } else if (event.getSource() == fontSizeComboBox) {
        // Size changed
        f = new Font(f.getName(), f.getStyle(), Integer.parseInt((String)fontSizeComboBox.getSelectedItem()));
      } else if (event.getSource() == fontStyleComboBox) {
        // Style changed
        f = new Font(f.getName(), fontStyleComboBox.getSelectedIndex(), f.getSize());
      }

      m = bean.getClass().getMethod("set" +
        descriptor.getName().substring(0,1).toUpperCase() +
        descriptor.getName().substring(1), new Class[] {Font.class});
      m.invoke(bean, new Object[] {f});
    } catch (Exception ex) {
    }
  }
}
