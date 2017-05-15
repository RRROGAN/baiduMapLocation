package org.rogan.map.util;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtils extends org.apache.commons.lang3.StringUtils
{
  public static String lowerFirst(String str)
  {
    if (isBlank(str)) {
      return "";
    }
    return str.substring(0, 1).toLowerCase() + str.substring(1);
  }

  public static String upperFirst(String str)
  {
    if (isBlank(str)) {
      return "";
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  public static String replaceHtml(String html)
  {
    if (isBlank(html)) {
      return "";
    }
    String regEx = "<.+?>";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(html);
    String s = m.replaceAll("");
    return s;
  }

  public static String abbr(String str, int length)
  {
    if (str == null)
      return "";
    try
    {
      StringBuilder sb = new StringBuilder();
      int currentLength = 0;
      for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
        currentLength += String.valueOf(c).getBytes("GBK").length;
        if (currentLength <= length - 3) {
          sb.append(c);
        } else {
          sb.append("...");
          break;
        }
      }
      return sb.toString();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String rabbr(String str, int length)
  {
    return abbr(replaceHtml(str), length);
  }

  public static Double toDouble(Object val)
  {
    if (val == null)
      return Double.valueOf(0.0D);
    try
    {
      return Double.valueOf(trim(val.toString())); } catch (Exception e) {
    }
    return Double.valueOf(0.0D);
  }

  public static Float toFloat(Object val)
  {
    return Float.valueOf(toDouble(val).floatValue());
  }

  public static Long toLong(Object val)
  {
    return Long.valueOf(toDouble(val).longValue());
  }

  public static Integer toInteger(Object val)
  {
    return Integer.valueOf(toLong(val).intValue());
  }

  public static String setMaxDigits(Double percent, int digits)
  {
    NumberFormat format = NumberFormat.getNumberInstance();
    format.setMaximumFractionDigits(digits);
    String result = format.format(percent);
    return result;
  }

  public static String[] concat(String[] a, String[] b)
  {
    String[] c = new String[a.length + b.length];
    System.arraycopy(a, 0, c, 0, a.length);
    System.arraycopy(b, 0, c, a.length, b.length);
    return c;
  }

  public static boolean isnotblanck(String[] str) {
    String[] strlist = str;
    for (int i = 0; i < strlist.length; i++) {
      if (isBlank(strlist[i])) {
        return false;
      }
    }
    return true;
  }
}