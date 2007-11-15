/**
 * Servlet test example
 */

package net.sourceforge.jbarcodebean.demo;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sourceforge.jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.ExtendedCode39;

import java.io.*;
import jbarcodebean.*;

/**
 * Servlet example code.
 */
public class ServletTest extends HttpServlet {

  JBarcodeBean bb;

  public void init(ServletConfig conf) throws ServletException {
    super.init(conf);
    bb = new JBarcodeBean();
    bb.setCodeType(new ExtendedCode39());
    bb.setShowText(true);
  }

  public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

    resp.setContentType("image/gif");
    OutputStream out = resp.getOutputStream();

    bb.setCode(req.getParameter("code"));
    bb.gifEncode(out);
  }

}
