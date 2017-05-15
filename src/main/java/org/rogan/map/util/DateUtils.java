package org.rogan.map.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
  private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
    "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "MM/dd/yyyy HH:mm:ss", "yyMMddHH", "yyyyMMddHHmmss", 
    "yyyy-MM-dd HH:mm:ss.SSS", "HH:mm:ss" };

  public static String getDate()
  {
    return getDate("yyyy-MM-dd");
  }

  public static String getDate(String pattern)
  {
    return DateFormatUtils.format(new java.util.Date(), pattern);
  }

  public static String formatDate(java.util.Date date, Object[] pattern)
  {
    String formatDate = null;
    if ((pattern != null) && (pattern.length > 0))
      formatDate = DateFormatUtils.format(date, pattern[0].toString());
    else {
      formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
    }
    return formatDate;
  }

  public static String formatDateTime(java.util.Date date)
  {
    return formatDate(date, new Object[] { "yyyy-MM-dd HH:mm:ss" });
  }

  public static String getTime()
  {
    return formatDate(new java.util.Date(), new Object[] { "HH:mm:ss" });
  }

  public static String getDateTime()
  {
    return formatDate(new java.util.Date(), new Object[] { "yyyy-MM-dd HH:mm:ss" });
  }

  public static String getYear()
  {
    return formatDate(new java.util.Date(), new Object[] { "yyyy" });
  }

  public static String getMonth()
  {
    return formatDate(new java.util.Date(), new Object[] { "MM" });
  }

  public static String getDay()
  {
    return formatDate(new java.util.Date(), new Object[] { "dd" });
  }

  public static String getWeek()
  {
    return formatDate(new java.util.Date(), new Object[] { "E" });
  }

  public static java.util.Date parseDate(Object str)
  {
    if (str == null)
      return null;
    try
    {
      return parseDate(str.toString(), parsePatterns); } catch (ParseException e) {
    }
    return null;
  }

  public static long pastDays(java.util.Date date)
  {
    long t = new java.util.Date().getTime() - date.getTime();
    return t / 86400000L;
  }

  public static java.util.Date getDateStart(java.util.Date date)
  {
    if (date == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      date = sdf.parse(formatDate(date, new Object[] { "yyyy-MM-dd" }) + " 00:00:00");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public static java.util.Date getDateEnd(java.util.Date date) {
    if (date == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      date = sdf.parse(formatDate(date, new Object[] { "yyyy-MM-dd" }) + " 23:59:59");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public static boolean isDate(String timeString)
  {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setLenient(false);
    try {
      format.parse(timeString);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public static String dateFormat(java.util.Date timestamp)
  {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format.format(timestamp);
  }

  public static Timestamp getSysTimestamp()
  {
    return new Timestamp(new java.util.Date().getTime());
  }

  public static java.util.Date getSysDate()
  {
    return new java.util.Date();
  }

  public static java.sql.Date getSqlDate()
  {
    return new java.sql.Date(System.currentTimeMillis());
  }

  public static String getDateRandom()
  {
    String s = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
    return s;
  }

  public static String formatStrToStr(String dateStr, String pattern, String pattern2)
  {
    if (StringUtils.isNotBlank(dateStr)) {
      try {
        java.util.Date date = parse(dateStr, pattern);
        return formatDate(date, new Object[] { pattern2 });
      }
      catch (ParseException localParseException)
      {
      }
    }
    return "";
  }

  public static java.util.Date parse(String strDate, String pattern)
    throws ParseException
  {
    return (strDate == null) || (strDate.equals("")) ? null : new SimpleDateFormat(pattern)
      .parse(strDate);
  }

  public static Boolean isinside(String time, String StartTime, String EndTime)
  {
    java.util.Date date = parseDate(time);
    java.util.Date start = parseDate(StartTime);
    java.util.Date end = parseDate(EndTime);
    if ((date.getTime() >= start.getTime()) && (date.getTime() <= end.getTime())) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }

  public static String parament(Timestamp ts)
  {
    String tsStr = "";
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      tsStr = sdf.format(ts);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tsStr;
  }
}