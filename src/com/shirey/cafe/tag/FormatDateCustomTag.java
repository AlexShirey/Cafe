package com.shirey.cafe.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The {@code FormatDateCustomTag} class
 * is a custom tag for representation {@code Date}
 * to a String value. A representation depends on a chosen type and locale.
 * <p>
 * The output will be one of next formats:
 * <ul>
 * <li>short type: 1/24/18 (depends on locale)</li>
 * <li>medium type: Jan 24, 2018 10:41 PM (depends on locale)</li>
 * <li>long type: January 9, 2018 10:41:35 PM (depends on locale)</li>
 * <li>ISO 8601 type (default, if a type doesn't match previous one): 2017-12-31 12:48:55</li>
 * </ul>
 * <p>
 *
 * @author Alex Shirey
 */

public class FormatDateCustomTag extends TagSupport {

    /**
     * A {@code Date} that should be passed to the tag,  required attribute.
     */
    private Date date;

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int doStartTag() throws JspException {

        String localeString = (String) pageContext.getSession().getAttribute("locale");
        Locale locale;

        if ("ru_RU".equals(localeString)) {
            locale = new Locale("ru", "RU");
        } else {
            locale = Locale.US;
        }

        DateFormat dateFormat = null;
        SimpleDateFormat simpleDateFormat = null;
        String dateFormatStyle = (String) pageContext.getSession().getAttribute("dateFormatStyle");

        switch (dateFormatStyle) {
            case "SHORT":
                dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
                break;
            case "MEDIUM":
                dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale);
                break;
            case "LONG":
                dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, locale);
                break;
            default:
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        JspWriter out = pageContext.getOut();
        try {
            if (dateFormat != null) {
                out.write(dateFormat.format(date));
            } else {
                out.write(simpleDateFormat.format(date));
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

}
