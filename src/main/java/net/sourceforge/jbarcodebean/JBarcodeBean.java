/**
 *  This library is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License (LGPL) as
 *  published by the Free Software Foundation; either version 3.0 of the
 *  License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY of FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details. 
 */

/**
 * Title:        JBarcodeBean
 * Description:  Barcode JavaBeans Component
 * Copyright:    Copyright (C) 2004
 * Company:      Dafydd Walters
 */
package net.sourceforge.jbarcodebean;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import javax.accessibility.*;

import net.sourceforge.jbarcodebean.model.BarcodeStrategy;
import net.sourceforge.jbarcodebean.model.Codabar;
import net.sourceforge.jbarcodebean.model.Codabar_2to1;
import net.sourceforge.jbarcodebean.model.Code128;
import net.sourceforge.jbarcodebean.model.Code39;
import net.sourceforge.jbarcodebean.model.Code39_2to1;
import net.sourceforge.jbarcodebean.model.Ean13;
import net.sourceforge.jbarcodebean.model.Ean8;
import net.sourceforge.jbarcodebean.model.ExtendedCode39;
import net.sourceforge.jbarcodebean.model.ExtendedCode39_2to1;
import net.sourceforge.jbarcodebean.model.Interleaved25;
import net.sourceforge.jbarcodebean.model.Interleaved25_2to1;
import net.sourceforge.jbarcodebean.model.MSI;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.awt.image.*;

/**
 * <p>
 * JFC Swing-compatible JavaBeans
 * <sup><small>TM</small></sup>
 * component that renders barcodes in a variety of different formats.
 * <p>
 * [<a href='#skip_copyright'>skip over license and copyright</a>]
 * <hr>
 * <p><b>LICENSE INFORMATION</b></p>
 * <p>
 *  This library is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License (LGPL) as
 *  published by the Free Software Foundation; either version 3.0 of the
 *  License, or (at your option) any later version.
 * </p><p>
 *  This library is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY of FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *  </p>
 *  <p>See <a href='http://www.gnu.org/licenses/lgpl.html'
 *  >http://www.gnu.org/licenses/lgpl.html</a>.
 *  </p> 
 * <hr>
 * <A NAME="skip_copyright"><!-- --></A>
 * <p>
 * <strong>JBarcodeBean</strong> employs the <strong>Strategy</strong> design pattern<b>*</b>
 * to abstract the encoding algorithm.  The
 * {@link #setCodeType(BarcodeStrategy) codeType}
 * property can be set to any class that implements the {@link BarcodeStrategy}
 * interface to determine how the {@link #setCode(String) code} property is encoded.
 * This version of JBarcodeBean is bundled with the following concrete implementations
 * of {@link BarcodeStrategy}:
 *
 * <ul>
 * <li>{@link Code128}
 * <li>{@link Code39}
 * <li>{@link Code39_2to1}
 * <li>{@link ExtendedCode39}
 * <li>{@link ExtendedCode39_2to1}
 * <li>{@link Interleaved25}
 * <li>{@link Interleaved25_2to1}
 * <li>{@link Codabar}
 * <li>{@link Codabar_2to1}
 * <li>{@link MSI}
 * <li>{@link Ean8}
 * <li>{@link Ean13}
 * </ul>
 * <p>
 *
 * <blockquote><b>*</b>See <i>"Design Patterns Elements of Reusable Object-Oriented Software",
 * Erich Gamma et al.</i> for more information about design patterns.</blockquote>
 *
 * @version 1.1
 */
public class JBarcodeBean extends JComponent implements java.io.Serializable, Accessible {

    // Property change constants

    /** Identifies a change in the <b>showText</b> property. */
    private static final String SHOW_TEXT_CHANGED_PROPERTY = "showText";
    /** Identifies a change in the <b>code</b> property. */
    private static final String CODE_CHANGED_PROPERTY = "code";
    /** Identifies a change in the <b>codeType</b> property. */
    private static final String CODE_TYPE_CHANGED_PROPERTY = "codeType";
    /** Identifies a change in the <b>checkDigit</b> property. */
    private static final String CHECK_DIGIT_CHANGED_PROPERTY = "checkDigit";
    /** Identifies a change in the <b>narrowestBarWidth</b> property. */
    private static final String NARROWEST_BAR_WIDTH_CHANGED_PROPERTY = "narrowestBarWidth";
    /** Identifies a change in the <b>barcodeHeight</b> property. */
    private static final String BARCODE_HEIGHT_CHANGED_PROPERTY = "barcodeHeight";
    /** Identifies a change in the <b>barcodeBackground</b> property. */
    private static final String BARCODE_BACKGROUND_CHANGED_PROPERTY = "barcodeBackground";
    /** Identifies a change in the <b>angleDegrees</b> property. */
    private static final String ANGLE_DEGREES_CHANGED_PROPERTY = "angleDegrees";
    
    /**
     * Identifies a change of the horizontal alignment property.
     */
    private static final String HORIZONTAL_ALIGNMENR_PROPERTY ="horizontalAlignment";
    
    /**
     * Constant indicating that the barcode should be left aligned along the
     * x-axis.
     */
    public static final int ALIGN_LEFT      =1;

    /**
     * Constant indicating that the barcode should be centered along the x-axis.
     * (This is the default behaviour)
     */
    public static final int ALIGN_CENTER    =2;

    /**
     * Constant indicating that the barcode should be right aligned along the
     * x-axis.
     */
    public static final int ALIGN_RIGHT     =3;

    
    /**
     * Identifies a change of the label position property.
     */
    private static final String LABEL_POSITION_PROPERTY ="labelPosition";
    
    /**
     * Constant indicating that the human readable barcode text should be
     * printed at the top of the barcode.
     */
    public static final int LABEL_TOP       = 1;

    /**
     * Constant indicating that the human readable barcode text should be
     * printed at the bottom of the barcode. (This is the default behaviour)
     */
    public static final int LABEL_BOTTOM    = 2;
    
    /**
     * Constant indicating that the human readable barcode text should not be
     * printed at all.
     */
    public static final int LABEL_NONE      = 0;
    
    
    
    // Private fields.
    private String code;
    private BarcodeStrategy codeType;
    private boolean checkDigit = false;
    private EncodedBarcode encoded = null;
    private int narrowestBarWidth = 1;
    private int barcodeHeight = 40;
    private Color barcodeBackground = Color.white;
    private Dimension minimumSize;
    private Dimension preferredSize;
    private double angleDegrees;
    private int labelHeight;          // calculated
    private int labelWidth;           // calculated
    private int barcodeWidth;         // calculated
    private String encodeError = "";
    private int horizontalAlignment = ALIGN_CENTER;
    private int labelPosition = LABEL_BOTTOM;

    /**
     * <p>Contructor that allows an initial barcode and code type to be specified.
     *
     * @param code The text to encode.
     * @param codeType The type of barcode.
     */
    public JBarcodeBean(String code, BarcodeStrategy codeType) {

        // Set some Component and JComponent properties.
        setForeground(Color.black);
        setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Set bean properties - don't fire property changes here since this is c'tor
        this.code = code;
        this.codeType = codeType;
        if (codeType.requiresChecksum() == BarcodeStrategy.MANDATORY_CHECKSUM) {
            checkDigit = true;
        } else if (codeType.requiresChecksum() == BarcodeStrategy.NO_CHECKSUM) {
            checkDigit = false;
        }

        // Disable double buffering, so that Java-2 printing is hi-res
        setDoubleBuffered(false);

        // Render the bean.
        encode();
        recalculateSizes();
        repaint();
    }

    /**
     * <p>
     * Parameterless constructor. Creates a <tt>JBarcodeBean</tt> object with default
     * property values, so that the bean will render as a typical barcode
     * when initially added to a GUI with a GUI Builder.
     * <p>
     * Defaulat values are:<b>
     * <p><code>code = "1234"</code><br>
     * <code>codeType = new Code39()</code></b><br>
     * <p>
     * @see Code39
     *
     */
    public JBarcodeBean() {
        this("1234", new Code39());
    }

    /**
     * Accessor method for the <tt><b>angleDegrees</b></tt> property,
     * which determines the angle (from horizontal) at which the barcode
     * is rendered.
     */
    public double getAngleDegrees() {
        return angleDegrees;
    }

    /**
     * Mutator method for the <tt><b>angleDegrees</b></tt> property,
     * which determines the angle (from horizontal) at which the barcode
     * is rendered.
     */
    public void setAngleDegrees(double angleDegrees) {
        double oldValue = this.angleDegrees;
        this.angleDegrees = angleDegrees;
        firePropertyChange(ANGLE_DEGREES_CHANGED_PROPERTY, oldValue, angleDegrees);
        recalculateSizes();
        repaint();
    }

    /**
     * Accessor method for the <tt><b>showText</b></tt> property, which
     * determines whether or not the text caption is visible below the
     * barcode. <tt>true</tt> = visible, <tt>false</tt> = not visible.
     * @deprecated As of 1.2.0, replaced by {@link #getLabelPosition()}.
     * This will return <tt>true</tt> if label position is {@link #LABEL_BOTTOM}
     * and <tt>false</tt> otherwise.
     */
    public boolean isShowText() {
        return this.labelPosition==LABEL_BOTTOM;
    }

    /**
     * Mutator method for the <tt><b>showText</b></tt> property, which
     * determines whether or not the text caption is visible below the
     * barcode. <tt>true</tt> = visible, <tt>false</tt> = not visible.
     * @deprecated As of 1.2.0, replaced by {@link #setLabelPosition(int)}. When
     * setting this property to <tt>true</tt> the label position will be set to
     * {@link #LABEL_BOTTOM}, setting the property to <tt>false</tt> will set
     * the label position to {@link #LABEL_NONE}. 
     */
    public void setShowText(boolean showText) {
        boolean oldValue = isShowText();
        setLabelPosition(showText?LABEL_BOTTOM:LABEL_NONE);
        firePropertyChange(SHOW_TEXT_CHANGED_PROPERTY, oldValue, showText);
    }

    /**
     * Always returns <tt>true</tt>, as this component renders its entire
     * drawing area.
     */
    public boolean isOpaque() {
        return true;
    }

    /**
     * Accessor method for the <tt><b>foreground</b></tt> property, which
     * determines the color of the bars and caption text (typically black).
     */
    public Color getForeground() {
        return super.getForeground();
    }

    /**
     * Mutator method for the <tt><b>foreground</b></tt> property, which
     * determines the color of the bars and caption text (typically black).
     */
    public void setForeground(Color c) {
        super.setForeground(c);
        repaint();
    }

    /**
     * Accessor method for the <tt><b>background</b></tt> property, which
     * determines the control background color (the space around the barcode,
     * and behind the text caption).  Note that this is not the
     * same as the {@link #getBarcodeBackground barcodeBackground} property
     * (which is the color of the spaces between the bars in the barcode).
     */
    public Color getBackground() {
        return super.getBackground();
    }

    /**
     * Mutator method for the <tt><b>background</b></tt> property, which
     * determines the control background color (the space around the barcode,
     * and behind the text caption).  Note that this is not the
     * same as the {@link #setBarcodeBackground barcodeBackground} property
     * (which is the color of the spaces between the bars in the barcode).
     */
    public void setBackground(Color c) {
        super.setBackground(c);
        repaint();
    }

    /**
     * Accessor method for the <tt><b>barcodeBackground</b></tt> property, which
     * determines the color of the spaces between the bars in the barcode
     * (typically white).
     *
     * @see #setBackground
     */
    public Color getBarcodeBackground() {
        return barcodeBackground;
    }

    /**
     * Mutator method for the <tt><b>barcodeBackground</b></tt> property, which
     * determines the color of the spaces between the bars in the barcode
     * (typically white).
     *
     * @see #setBackground
     */
    public void setBarcodeBackground(Color barcodeBackground) {
        Color oldValue = this.barcodeBackground;
        this.barcodeBackground = barcodeBackground;
        firePropertyChange(BARCODE_BACKGROUND_CHANGED_PROPERTY, oldValue, barcodeBackground);
        repaint();
    }

    /**
     * Accessor methor for the <tt><b>border</b></tt> property.
     */
    public javax.swing.border.Border getBorder() {
        return super.getBorder();
    }

    /**
     * Mutator methor for the <tt><b>border</b></tt> property.
     */
    public void setBorder(javax.swing.border.Border border) {
        super.setBorder(border);
        recalculateSizes();
        repaint();
    }

    /**
     * Accessor method for the <tt><b>preferredSize</b></tt> property.
     */
    public Dimension getPreferredSize() {
        return preferredSize();
    }

    /**
     * Mutator method for the <tt><b>preferredSize</b></tt> property.
     */
    public void setPreferredSize(Dimension preferredSize) {
        Dimension oldValue = this.preferredSize;
        this.preferredSize = preferredSize;
        firePropertyChange("preferredSize", oldValue, preferredSize);
    }

    /**
     * Returns the preferred size of the component.
     *
     * @deprecated Use {@link #getPreferredSize}.
     */
    public Dimension preferredSize() {
        if (preferredSize != null) {
            return preferredSize;
        } else {
            return new Dimension(getWidth(), getHeight());
        }
    }

    /**
     * Accessor method for <tt><b>minimumSize</b></tt> property.
     */
    public Dimension getMinimumSize() {
        return minimumSize();
    }

    /**
     * Mutator method for the <tt><b>minimumSize</b></tt> property.
     */
    public void setMinimumSize(Dimension minimumSize) {
        Dimension oldValue = this.minimumSize;
        this.minimumSize = minimumSize;
        firePropertyChange("minimumSize", oldValue, minimumSize);
    }

    /**
     * Returns the minimum size of the component.
     *
     * @deprecated Use {@link #getMinimumSize}.
     */
    public Dimension minimumSize() {
        if (minimumSize != null) {
            return minimumSize;
        } else {
            return new Dimension(getWidth(), getHeight());
        }
    }

    /**
     * Accessor method for the <tt><b>font</b></tt> property.
     */
    public Font getFont() {
        return super.getFont();
    }

    /**
     * Mutator method for the <tt><b>font</b></tt> property.
     */
    public void setFont(Font font) {
        super.setFont(font);
        recalculateSizes();
        repaint();
    }

    /**
     * Accessor method for the <tt><b>barcodeHeight</b></tt> property, which
     * determines the height of the barcode (excluding caption text) in pixels.
     */
    public int getBarcodeHeight() {
        return barcodeHeight;
    }

    /**
     * Mutator method for the <tt><b>barcodeHeight</b></tt> property, which
     * determines the height of the barcode (excluding caption text) in pixels.
     */
    public void setBarcodeHeight(int barcodeHeight) {
        int oldValue = this.barcodeHeight;
        this.barcodeHeight = barcodeHeight;
        firePropertyChange(BARCODE_HEIGHT_CHANGED_PROPERTY, oldValue, barcodeHeight);
        recalculateSizes();
        repaint();
    }

    /**
     * Accessor method for the <tt><b>narrowestBarWidth</b></tt> property,
     * which determines the width (in pixels) of the narrowest bar in the barcode.
     */
    public int getNarrowestBarWidth() {
        return narrowestBarWidth;
    }

    /**
     * Mutator method for the <tt><b>narrowestBarWidth</b></tt> property,
     * which determines the width (in pixels) of the narrowest bar in the barcode.
     */
    public void setNarrowestBarWidth(int narrowestBarWidth) {
        int oldValue = this.narrowestBarWidth;
        this.narrowestBarWidth = narrowestBarWidth;
        firePropertyChange(NARROWEST_BAR_WIDTH_CHANGED_PROPERTY, oldValue, narrowestBarWidth);
        recalculateSizes();
        repaint();
    }

    /**
     * Always returns <tt>false</tt>, as this control cannot receive focus.
     */
    public boolean isFocusTraversable() {
        return false;
    }

    /**
     * Always returns the value of the {@link #getCode code} property.
     */
    protected String paramString() {
        return code;
    }

    /**
     * Always returns the value of the {@link #getCode code} property.
     */
    public String toString() {
        return code;
    }

    /**
     * Accessor method for the <tt><b>code</b></tt> property,
     * which is the text encoded in the barcode.
     */
    public String getCode() {
        return code;
    }

    /**
     * Mutator method for the <tt><b>code</b></tt> property,
     * which is the text encoded in the barcode.
     */
    public void setCode(String code) {
        String oldValue = this.code;
        this.code = code;
        firePropertyChange(CODE_CHANGED_PROPERTY, oldValue, code);
        encode();
        recalculateSizes();
        repaint();
    }

    /**
     * Accessor method for the <tt><b>codeType</b></tt> property,
     * which is the barcode type.
     */
    public BarcodeStrategy getCodeType() {
        return codeType;
    }

    /**
     * Mutator method for the <tt><b>codeType</b></tt> property,
     * which is the barcode type.
     */
    public void setCodeType(BarcodeStrategy codeType) {
        BarcodeStrategy oldValue = this.codeType;
        this.codeType = codeType;
        firePropertyChange(CODE_TYPE_CHANGED_PROPERTY, oldValue, codeType);
        if (codeType != null && codeType.requiresChecksum() == BarcodeStrategy.MANDATORY_CHECKSUM && checkDigit == false) {
            setCheckDigit(true);
            // encode(), recalculateSizes(), and repaint() will be done in setChecked().
        } else if (codeType != null && codeType.requiresChecksum() == BarcodeStrategy.NO_CHECKSUM && checkDigit == true) {
            setCheckDigit(false);
            // encode(), recalculateSizes(), and repaint() will be done in setChecked().
        } else {
            encode();
            recalculateSizes();
            repaint();
        }
    }

    /**
     * Accessor method for the <tt><b>checkDigit</b></tt> property,
     * which determines whether a check digit is encoded in the
     * barcode.  <tt>true</tt> = check digit is encoded, <tt>false</tt>
     * = check digit is not encoded.
     */
    public boolean isCheckDigit() {
        return checkDigit;
    }

    /**
     * Mutator method for the <tt><b>checkDigit</b></tt> property,
     * which determines whether a check digit is encoded in the
     * barcode.  <tt>true</tt> = check digit is encoded, <tt>false</tt>
     * = check digit is not encoded.
     */
    public void setCheckDigit(boolean checkDigit){
        if (codeType != null) {
            if (codeType.requiresChecksum() == BarcodeStrategy.MANDATORY_CHECKSUM  && checkDigit == false) {
                // Cannot disable checksum
                // although the checksum cannot be disabled there should be no exception
                // the call should simply be ignored (requestid 1329396)
                return;
            } else if (codeType.requiresChecksum() == BarcodeStrategy.NO_CHECKSUM  && checkDigit == true) {
                // Cannot enable checksum
                // although the checksum cannot be enabled there should be no exception
                // the call should simply be ignored (requestid 1329396)
                return;
            }
        }
        boolean oldValue = this.checkDigit;
        this.checkDigit = checkDigit;
        firePropertyChange(CHECK_DIGIT_CHANGED_PROPERTY, oldValue, checkDigit);
        encode();
        recalculateSizes();
        repaint();
    }

    /**
     * Component paint method.
     */
    protected void paintComponent(Graphics graphics) {
        doPaint(graphics, getSize(), getInsets());
    }

    private void doPaint(Graphics graphics, Dimension d, Insets insets) {

        Graphics2D g = (Graphics2D) graphics;

        // Save graphics properties
        AffineTransform tran = g.getTransform();
        Shape oldClip = g.getClip();
        Color oldColor = g.getColor();
        Font oldFont = g.getFont();

        // Fill control background
        g.setColor(getBackground());
        g.fillRect(0, 0, d.width, d.height);

        // Set clip rectangle to prevent barcode overwriting border

        // also regard the clip the graphics object already has to avoid paint
        // outside the clip of the graphics. therefore we make an intersect of both
        // clips
        Area areaOldClip = null;
        Rectangle newClip = new Rectangle(insets.left, insets.top,
                d.width - insets.left - insets.right, d.height - insets.top - insets.bottom);
        Area areaNewClip = new Area(newClip);
        if (oldClip != null)
        {
            areaOldClip = new Area(oldClip);
            areaOldClip.intersect(areaNewClip);
        }
        else
        {
            areaOldClip = areaNewClip;
        }
        g.setClip(areaOldClip);

        // Apply rotate and transale transform
        g.rotate(angleDegrees / 180 * Math.PI, d.width / 2.0, d.height / 2.0);

        int barcodeTop = (d.height - (barcodeHeight + labelHeight)) / 2;
        if(labelPosition!=LABEL_TOP){
            barcodeTop=(d.height - (barcodeHeight + labelHeight)) / 2;
        } else{
            barcodeTop=(d.height - (barcodeHeight + labelHeight)) / 2+labelHeight;
        }

        // Draw barcode
        if (encoded != null) {
            int x;
            if(horizontalAlignment==ALIGN_LEFT){
                x=0;
            } else if (horizontalAlignment==ALIGN_RIGHT){
                x=d.width-barcodeWidth;
            } else {
                x=(d.width-barcodeWidth)/2;
            }
            for(int i = 0; i < encoded.elements.length; i++) {
                if (encoded.elements[i].getType()==BarcodeElement.TYPE_BAR) {
                    // bar
                    g.setColor(getForeground());
                } else {
                    // space
                    g.setColor(barcodeBackground);
                }
                int barWidth = encoded.elements[i].getWidth() * narrowestBarWidth;
                g.fillRect(x, barcodeTop, barWidth, barcodeHeight);
                x += barWidth;
            }

            // Draw text
            if (labelPosition!=LABEL_NONE) {
                g.setFont(this.getFont());
                g.setColor(getForeground());
                FontMetrics fm = getFontMetrics(g.getFont());
                int labelTop;
                if(labelPosition==LABEL_BOTTOM) {
                    labelTop = barcodeTop + barcodeHeight + fm.getAscent();
                } else {
                    labelTop = barcodeTop - labelHeight + fm.getAscent();
                }
                g.drawString(encoded.barcodeLabelText,
                        (d.width - labelWidth) / 2, labelTop);
            }
        } else if (!encodeError.equals("")) {
            // Display error
            g.setFont(new Font("Monospaced", Font.PLAIN, 10));
            FontMetrics fm = getFontMetrics(g.getFont());
            int labelWidth = fm.stringWidth(encodeError);
            g.setColor(getBarcodeBackground());
            g.fillRect((d.width - labelWidth) / 2, barcodeTop,
                    labelWidth, fm.getHeight());
            g.setColor(getForeground());
            g.drawString(encodeError,
                    (d.width - labelWidth) / 2,
                    barcodeTop + fm.getAscent()
            );
        }

        // Restore graphics properties
        g.setTransform(tran);
        g.setClip(oldClip);
        g.setColor(oldColor);
        g.setFont(oldFont);
    }

    private void recalculateSizes() {

        if (labelPosition!=LABEL_NONE) {
            FontMetrics fm = getFontMetrics(getFont());
            labelHeight = fm.getAscent() + fm.getDescent();
        } else {
            labelHeight = 0;
        }

        Dimension d = calculateControlSize(getRequiredWidth(), barcodeHeight + labelHeight);
        setPreferredSize(d);
        setMinimumSize(d);
        revalidate();
    }

    private Dimension calculateControlSize(int rectWidth, int rectHeight) {

        Insets insets = getInsets();
        Dimension d = new Dimension();
        double angleRadians = angleDegrees / 180.0 * Math.PI;

        d.height = (int) (Math.abs(rectWidth * Math.sin(angleRadians)) + Math.abs(rectHeight * Math.cos(angleRadians)))
        + insets.top + insets.bottom;

        d.width = (int) (Math.abs(rectWidth * Math.cos(angleRadians)) + Math.abs(rectHeight * Math.sin(angleRadians)))
        + insets.left + insets.right;

        return d;
    }

    private int getRequiredWidth() {

        this.barcodeWidth = 0;
        this.labelWidth = 0;

        if (encoded != null) {

            FontMetrics fm = getFontMetrics(getFont());
            labelWidth = fm.stringWidth(encoded.barcodeLabelText);

            for(int i = 0; i < encoded.elements.length; i++) {
                barcodeWidth += encoded.elements[i].getWidth() * narrowestBarWidth;
            }
        } else if (!encodeError.equals("")) {
            // error message only
            FontMetrics fm = getFontMetrics(getFont());
            labelWidth = fm.stringWidth(encodeError);
        }

        int width = (barcodeWidth > labelWidth) ? barcodeWidth : labelWidth;
        return width;
    }

    private void encode() {
        encodeError = "";
        if ((codeType != null) && (!code.equals(""))) {
            try {
                encoded = codeType.encode(code, checkDigit);
            } catch (BarcodeException e) {
                encoded = null;
                encodeError = e.getMessage();
            }
        } else {
            encoded = null;
        }
    }

    /**
     * Returns a simple subclass of <tt>AccessibleContext</tt>.
     */
    public AccessibleContext getAccessibleContext() {
        return new JComponent.AccessibleJComponent() {
            public String getAccessibleName() {
                return "barcode " + code;
            }
            public AccessibleRole getAccessibleRole() {
                return AccessibleRole.LABEL;
            }
        };
    }

    /**
     * Returns the version of this JBarcodeBean implementation.
     * @return The version string in format: <i>major.minor[.micro]</i>
     */
    public static String getVersion() {
        
        Class cl=JBarcodeBean.class;
        URL url = cl.getResource("/META-INF/maven/net.sourceforge.jbarcodebean/jbarcodebean/pom.properties");
        Properties props=new Properties();
        try {
            if(url==null){
                return "unknown";
            }
            InputStream in=url.openStream();
            props.load(in);
            in.close();
        } catch (IOException e) {
            return "unknown";
        }
        return props.getProperty("version");
    }

    /**
     * Draws the current barcode into the given image and returns it. 
     * @param image The image the barcode will be drawn into or
     * <code>null</code> if a new image should be created using the preferred
     * size of the barcode.
     * @return The image containing the barcode.
     */
    public BufferedImage draw(BufferedImage image) {
        if(image==null) {
            Dimension pref=getPreferredSize();
            image=(BufferedImage) createImage(pref.width, pref.height);
        }
        encode();
        recalculateSizes();
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        doPaint(g, new Dimension(image.getWidth(), image.getHeight()), new Insets(0, 0, 0, 0));
        return image;
    }

    /**
     * Returns the alignment of the barcode along the X axis. 
     * @return The value of the horizontalAlignment property, one of the
     * following constants: {@link #ALIGN_LEFT}, {@link #ALIGN_RIGHT},
     * {@link #ALIGN_CENTER}
     * @see #setHorizontalAlignment(int)
     * @since 1.2.0
     */
    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    /**
     * Sets the alignment of the barcode along the X axis.
     * This is a JavaBeans bound property. 
     * @param horizontalAlignment One of the following constants : 
     * {@link #ALIGN_LEFT}, {@link #ALIGN_RIGHT},
     * {@link #ALIGN_CENTER} (the default)
     * @see #getHorizontalAlignment()
     * @since 1.2.0
     */
    public void setHorizontalAlignment(int horizontalAlignment) {
        int oldValue=this.horizontalAlignment;
        this.horizontalAlignment = horizontalAlignment;
        firePropertyChange(HORIZONTAL_ALIGNMENR_PROPERTY , oldValue, horizontalAlignment);
        recalculateSizes();
        repaint();
    }

    /**
     * Returns the label position of the human readable barcode text.
     * @return The value of the labelPosition property, one of the following
     * constants: {@link #LABEL_BOTTOM}, {@link #LABEL_TOP} or
     * {@link #LABEL_NONE}.
     * @see #setLabelPosition(int)
     * @since 1.2.0
     */
    public int getLabelPosition() {
        return labelPosition;
    }

    /**
     * Sets the label position of the human readable bacode text.
     * This is a JavaBeans bound property.
     * @param labelPosition One of the following constants:
     * {@link #LABEL_BOTTOM}, {@link #LABEL_TOP} or {@link #LABEL_NONE}.
     * @see #getLabelPosition()
     * @since 1.2.0
     */
    public void setLabelPosition(int labelPosition) {
        int oldValue=this.labelPosition;
        this.labelPosition = labelPosition;
        firePropertyChange(LABEL_POSITION_PROPERTY, oldValue, labelPosition);
        recalculateSizes();
        repaint();
    }
    
}

