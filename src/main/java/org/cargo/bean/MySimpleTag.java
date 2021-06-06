package org.cargo.bean;

import javax.servlet.jsp.JspException;
import java.io.IOException;

public class MySimpleTag extends javax.servlet.jsp.tagext.SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().println("© 2021 cargo.org");
    }
}
