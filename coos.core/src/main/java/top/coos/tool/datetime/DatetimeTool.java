package top.coos.tool.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import top.coos.tool.base.BaseTool;

public class DatetimeTool {

    public static long systemNanoTime() {
        return System.nanoTime();
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);// 获取年份
    }

    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;// 获取月份
    }

    public static int getDate() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DATE);// 获取日
    }

    public static int getHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR);// 获取小时
    }

    public static int getMinute() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MINUTE);// 获取分
    }

    public static int getSecond() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.SECOND);// 获取秒
    }

    public static int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK);// 一周的第几天
    }

    /**
     * yyyyMMddHHmmss
     */
    public static SimpleDateFormat getDateTimeFormat() {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }

    /**
     * yyyyMMdd
     */
    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    /**
     * 获取系统当前时间 14位 yyyyMMddHHmmss
     * 
     * @return
     */
    public static String getSystemTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(date);
    }

    /**
     * 转换时间显示格式 14位转换成19位页面显示
     * 
     * @param time
     * @return
     */
    public static String formatTime(String time) {
        if (time != null && time.trim().length() == 14) {
            time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " + time.substring(8, 10) + ":" + time.substring(10, 12) + ":" + time.substring(12, 14);
        }
        return time;
    }

    /**
     * 转换时间显示格式 8位转换成10位页面显示
     * 
     * @param time
     * @return
     */
    public static String formatTimeTo10(String time) {

        if (time != null && time.trim().length() == 8) {
            time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
        }
        return time;

    }

    /**
     * 过滤时间字符串中的特殊字符
     * 
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date parseDateTimeStr(String time) {
        time = BaseTool.clearNoNumber(time);
        try {
            if (time.length() == 8) {
                return new SimpleDateFormat("yyyyMMdd").parse(time);
            }
            if (time.length() == 10) {
                return new SimpleDateFormat("yyyyMMddHH").parse(time);
            }
            if (time.length() == 12) {
                return new SimpleDateFormat("yyyyMMddHHmm").parse(time);
            }
            if (time.length() == 14) {
                return new SimpleDateFormat("yyyyMMddHHmmss").parse(time);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换时间显示格式 8位转换成10位页面显示
     * 
     * @param time
     * @return
     */
    public static String formatTimeTo8(String time) {

        if (time != null && time.trim().length() == 8) {
            time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
        }
        return time;

    }

    /**
     * 转换时间显示格式 转为****.**.**格式
     * 
     * @param time
     * @return
     */
    public static String formatTimeTo8_Point(String time) {

        if (time != null && time.trim().length() == 8) {
            time = time.substring(0, 4) + "." + time.substring(4, 6) + "." + time.substring(6, 8);
        }
        return time;

    }

    /**
     * 过滤时间字符串中的特殊字符
     * 
     * @param time
     * @return
     * @throws ParseException
     */
    public static String filterTime(String time) {
        if (time != null) {
            time = time.replaceAll("/", "").replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "").trim();
        }
        return time;
    }

    /**
     * 转换时间显示格式 12位转换成17位页面显示
     * 
     * @param time
     * @return
     */
    public static String formatTimeWith12(String time) {
        if (time != null && time.trim().length() == 12) {
            time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " + time.substring(8, 10) + ":" + time.substring(10, 12);
        }
        return time;
    }

    /**
     * 将XMLGregorianCalendar类型的时间 转为前台需要的格式 例：2014-07-13 14:12
     * 
     * @param cal
     * @return
     */
    public static String formatTime(XMLGregorianCalendar cal) {
        // 将XMLGregorianCalendar类型转成Date类型
        Date enrollDate = convertToDate(cal);

        // 将Date类型 格式化成yyyy-MM-dd HH:mm
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = format.format(enrollDate);

        // 去除时间中的字符“T”
        time = time.replace(" ", "T");

        return time;
    }

    /**
     * 将XMLGregorianCalendar类型的时间 转为前台需要的格式 例：2014.07.13 14:12:11
     * 
     * @param cal
     * @return
     */
    public static String formatTime_2(XMLGregorianCalendar cal) {
        // 将XMLGregorianCalendar类型转成Date类型
        Date enrollDate = convertToDate(cal);

        // 将Date类型 格式化成yyyy-MM-dd HH:mm
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String time = format.format(enrollDate);

        return time;
    }

    /**
     * 将XMLGregorianCalendar类型转为2014.6.10的String类型
     * 
     * @param cal
     * @return
     */
    public static String formatTimeDay(XMLGregorianCalendar cal) {
        // 将XMLGregorianCalendar类型转成Date类型
        Date enrollDate = convertToDate(cal);

        // 将Date类型 格式化成yyyy-MM-dd HH:mm
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String time = format.format(enrollDate);

        return time;
    }

    /**
     * 将XMLGregorianCalendar类型转为2014年6月10日的String类型
     * 
     * @param cal
     * @return
     */
    public static String formatTimeChinese(XMLGregorianCalendar cal) {
        // 将XMLGregorianCalendar类型转成Date类型
        Date enrollDate = convertToDate(cal);

        // 将Date类型 格式化成yyyy-MM-dd HH:mm
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String time = format.format(enrollDate);

        return time;
    }

    /**
     * 将Date类型转为2014年6月10日的String类型
     * 
     * @param cal
     * @return
     */
    public static String formatTimeChinese2(Date date) {
        // 将Date类型 格式化成yyyy-MM-dd HH:mm
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String time = format.format(date);

        return time;
    }

    /**
     * 判断一个时间是否超过当前时间
     * 
     * @param endDate
     * @return true： 有效 false：过期
     */
    public static boolean JudgeDate(Date endDate) {
        // 当前时间
        Date current = new Date();

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(endDate);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        cal2.setTime(current);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.MILLISECOND, 0);

        // 分别获得终保时间和当前时间毫秒数，比较大小，从而区分哪些是当前保单和历史保单
        if (cal1.after(cal2) || cal1.equals(cal2)) {
            // 有效
            return true;
        } else {
            // 过期
            return false;
        }
    }

    /**
     * 判断保单生效时间是否有效
     * 
     * @param endDate
     * @return true： //达到生效时间（可以兑换） false：//未达到生效时间（不可兑换）
     */
    public static boolean JudgeDateEffective(Date endDate) {
        // 当前时间
        Date current = new Date();

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(endDate);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        cal2.setTime(current);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.MILLISECOND, 0);

        // 分别获得终保时间和当前时间毫秒数，比较大小，从而区分哪些是当前保单和历史保单
        if (cal1.after(cal2)) {
            // 未达到生效时间（不可兑换）
            return true;
        } else {
            // 达到生效时间（可以兑换）
            return false;
        }
    }

    /**
     * 得到某个日期的后一天日期
     * 
     * @param d
     * @return
     */
    public static Date getAfterDate(Date d) {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 或去当前日期与结束日期的相差天数
     * 
     * @param date
     * @return
     */
    public static int getSurplusDay(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        long time_end = cal1.getTimeInMillis();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time_current = cal.getTimeInMillis();
        long between_days = (time_end - time_current) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * Date类型转成XMLGregorianCalendar类型
     * 
     * @param date
     * @return
     */
    public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return gc;
    }

    /**
     * XMLGregorianCalendar类型转成Date类型
     * 
     * @param XMLGregorianCalendar
     * @return
     */
    public static Date convertToDate(XMLGregorianCalendar cal) {
        GregorianCalendar ca = cal.toGregorianCalendar();
        return ca.getTime();
    }

    /**
     * 将时间字符串转为Date
     * 
     * @param time
     * @return
     */
    public static Date convertStrToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}
