/**
 * Servlet test example
 */

package net.sourceforge.jbarcodebean.demo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.ExtendedCode39;

/**
 * Servlet example code.
 */
public class ServletTest extends HttpServlet {

    private JBarcodeBean bb;

    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        bb = new JBarcodeBean();
        bb.setCodeType(new ExtendedCode39());
        bb.setShowText(true);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("image/png");
        OutputStream out = resp.getOutputStream();
        bb.setCode(req.getParameter("code"));
        BufferedImage img=bb.draw(null);
        ImageIO.write(img, "png", out);
        out.flush();
    }

}
