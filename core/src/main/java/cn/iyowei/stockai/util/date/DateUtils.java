package cn.iyowei.stockai.util.date;

import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 提供常用日期工具. <p>
 * 该类继承了 apache dateUtils，可以自行扩展.<p>
 *
 * @author Marco.hu(hzg139@163.com)
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {
    /**
     * 日期格式 yyyy-MM-dd
     */
    public static final String PATTREN_DATE = "yyyy-MM-dd";

    /**
     * 以日期命名用来创建文件路径
     */
    public static final String PATTREN_PATH_DATE = "yyyy_MM";

    /**
     * 日期格式 年月日
     */
    public static final String PATTREN_DATE_CN = "yyyy年MM月dd日";

    /**
     * 时间格式 HH:mm:ss
     */
    public static final String PATTREN_TIME = "HH:mm:ss";

    /**
     * 日期时间格式类 yyyy-MM-dd HH:mm:ss
     */
    public static final String PATTREN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期时间格式类 yyyy-MM-dd HH:mm
     */
    public static final String PATTREN_DATE_TIME2 = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式类 yyyy-MM-dd
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat(PATTREN_DATE, java.util.Locale.CHINA);

    /**
     * 中国格式日期
     */
    public static final DateFormat DATE_FORMAT_CN = new SimpleDateFormat(PATTREN_DATE_CN, java.util.Locale.CHINA);

    /**
     * 系统自动创建的以年月命名的文件夹
     */
    public static final DateFormat DATE_PATH_FORMAT = new SimpleDateFormat(PATTREN_PATH_DATE, java.util.Locale.CHINA);

    /**
     * 时间格式类 HH:mm:ss
     */
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat(PATTREN_TIME, java.util.Locale.CHINA);

    /**
     * 日期时间格式类 yyyy-MM-dd HH:mm:ss
     */
    public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat(PATTREN_DATE_TIME, java.util.Locale.CHINA);

    /**
     * 返回当前时间
     *
     * @return 返回当前时间
     */
    public static Date getCurrentDateTime() {
        java.util.Calendar calNow = java.util.Calendar.getInstance();
        java.util.Date dtNow = calNow.getTime();
        return dtNow;
    }

    public static String getNowTime() {
        Date date = new Date();
        String nowTime = DATE_TIME_FORMAT.format(date);
        return nowTime;
    }

    /**
     * @return 返回今天日期，时间部分为0。例如：2006-4-8 00:00:00
     */
    public static Date getToday() {
        return truncate(new Date(), Calendar.DATE);
    }

    /**
     * @return 返回今天日期，时间部分为23:59:59.999。例如：2006-4-19 23:59:59.999
     */
    public static Date getTodayEnd() {
        return getDayEnd(new Date());
    }

    /**
     * 将字符串转换为日期（不包括时间）
     *
     * @param dateString "yyyy-MM-dd"格式的日期字符串
     * @return 日期
     */
    public static Date convertToDate(String dateString) {
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 检查字符串是否为日期格式yyyy-MM-dd
     *
     * @param dateString
     * @return true=是；false=否
     */
    public static boolean checkDateString(String dateString) {
        return (convertToDate(dateString) != null);
    }

    /**
     * 将字符串转换为日期（包括时间）
     *
     * @param dateTimeString "yyyy-MM-dd HH:mm:ss"格式的日期字符串
     * @return 日期时间
     */
    public static Date convertToDateTime(String dateTimeString) {
        try {
            return DATE_TIME_FORMAT.parse(dateTimeString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 检查字符串是否为日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @param dateTimeString
     * @return true=是；false=否
     */
    public static boolean checkDateTimeString(String dateTimeString) {
        return (convertToDateTime(dateTimeString) != null);
    }

    /**
     * 获取月底
     *
     * @param year  年 4位年度
     * @param month 月 1~12
     * @return 月底的Date对象。例如：2006-3-31 23:59:59.999
     */
    public static Date getMonthEnd(int year, int month) {
        StringBuffer sb = new StringBuffer(10);
        Date date;
        if (month < 12) {
            sb.append(Integer.toString(year));
            sb.append("-");
            sb.append(month + 1);
            sb.append("-1");
            date = convertToDate(sb.toString());
        } else {
            sb.append(Integer.toString(year + 1));
            sb.append("-1-1");
            date = convertToDate(sb.toString());
        }
        date.setTime(date.getTime() - 1);
        return date;
    }

    /**
     * 获取月底
     *
     * @param when 要计算月底的日期
     * @return 月底的Date对象。例如：2006-3-31 23:59:59.999
     */
    public static Date getMonthEnd(Date when) {
        Assert.notNull(when, "date must not be null !");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(when);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return getMonthEnd(year, month);
    }

    /**
     * 获取给定日的最后一刻。
     *
     * @param when 给定日
     * @return 最后一刻。例如：2006-4-19 23:59:59.999
     */
    public static Date getDayEnd(Date when) {
        Date date = truncate(when, Calendar.DATE);
        date = addDays(date, 1);
        date.setTime(date.getTime() - 1);
        return date;
    }

    /**
     * 获取给定日的第一刻。
     *
     * @param when 给定日
     * @return 最后一刻。例如：2006-4-19 00:00:00.000
     */
    public static Date getDay(Date when) {
        Date date = truncate(when, Calendar.DATE);
        return date;
    }

    /**
     * 日期加法
     *
     * @param when   被计算的日期
     * @param field  the time field. 在Calendar中定义的常数，例如Calendar.DATE
     * @param amount 加数
     * @return 计算后的日期
     */
    public static Date add(Date when, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(when);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 计算给定的日期加上给定的天数。
     *
     * @param when   给定的日期
     * @param amount 给定的天数
     * @return 计算后的日期
     */
    public static Date addDays(Date when, int amount) {
        return add(when, Calendar.DAY_OF_YEAR, amount);
    }

    /**
     * 计算给定的日期加上给定的月数。
     *
     * @param when   给定的日期
     * @param amount 给定的月数
     * @return 计算后的日期
     */
    public static Date addMonths(Date when, int amount) {
        return add(when, Calendar.MONTH, amount);
    }

    /**
     * 取得指定日期的同一周内日期
     *
     * @param Date      日期字符串 格式: yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd
     * @param dateValue 要显示的日期(周一到周日)
     * @param flag      标志,1-向前查找日期 2-向后查找日期
     * @return 指定周几的日期字符串
     * <code>
     * getWeekDay("2009-5-25", Calendar.MONDAY,1) 返回: 2009-05-25 .
     * getWeekDay("2009-5-25", Calendar.SUNDAY,2) 返回: 2009-05-31 .
     * <code>
     * @throws ParseException
     * @author： ctf 陈廷峰
     * @since： 2009-5-25
     */
    public static String getWeekDay(String Date, int dateValue, int flag) throws ParseException {
        Calendar calObj = Calendar.getInstance();
        SimpleDateFormat sfObj = new SimpleDateFormat("yyyy-MM-dd");
        calObj.setTime(sfObj.parse(Date));
        if (dateValue != Calendar.SATURDAY) {
            if (flag == 1) {//周一
                calObj.setFirstDayOfWeek(dateValue);
            } else { //周日
                calObj.setFirstDayOfWeek(dateValue + 1);
            }
        }
        calObj.set(Calendar.DAY_OF_WEEK, dateValue);
        return sfObj.format(calObj.getTime());
    }

    /**
     * 查找指定日期的月初和月未日期
     *
     * @param date 格式: yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd
     * @param flag 1:月初(1号),2:月未
     *             <code>
     *             getMonth("2009-02-25",1); 返回 2009-02-01
     *             getMonth("2009-02-25",2); 返回 2009-02-28
     *             </code>
     * @return
     * @throws Exception
     * @author： ctf 陈廷峰
     * @since： 2009-5-25
     */
    public static Date getMonth(String date, int flag) throws Exception {
        Calendar ca = Calendar.getInstance();
        SimpleDateFormat sfObj = new SimpleDateFormat("yyyy-MM-dd");
        ca.setTime(sfObj.parse(date));   //   someDate   为你要获取的那个月的时间
        Date rtDate = null;
        if (flag == 1) {//月初
            ca.set(Calendar.DAY_OF_MONTH, 1);
            rtDate = ca.getTime();
        } else { //月底
            ca.set(Calendar.DAY_OF_MONTH, 1);
            rtDate = ca.getTime();
            ca.add(Calendar.MONTH, 1);
            ca.add(Calendar.DAY_OF_MONTH, -1);
            rtDate = ca.getTime();
        }
        return rtDate;
    }

    /**
     * @param date 格式: yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd
     * @param flag
     * @return
     * @throws Exception
     * @主要功能：查找指定日期的月初和月未日期
     * @author： ctf 陈廷峰
     * @since： 2009-5-25
     */
    public static String getStrMonth(String date, int flag) throws Exception {
        SimpleDateFormat sformatObj = new SimpleDateFormat("yyyy-MM-dd");
        Date returnStr = getMonth(date, flag);
        return sformatObj.format(returnStr);
    }

    /**
     * @param date1 日期1 格式: yyyy-MM-dd HH:mm:ss 要求比日期2大
     * @param date2 日期2 格式: yyyy-MM-dd HH:mm:ss
     * @return 分钟差, 如: 2009-06-12 09:30:00 与 2009-06-12 08:00:00 结果为90
     * @throws Exception
     * @主要功能：计算两个日期之间的分钟数
     * @author: Snoopy Chen (ctfzh@yahoo.com.cn)
     * @since： Jun 12, 2009
     */
    public static int calMinutes(Date date1, Date date2) throws Exception {
        int times = 0;
        times = (int) ((date1.getTime() - date2.getTime()) / (60 * 1000));
        return times;
    }

    /**
     * @param date
     * @param amount 要增加或者减少的天数
     *               <code>
     *               calDay(2009-06-12,2)    返回: 2009-06-14 .(例子中没有把日期参数的时间写上)
     *               calDay(2009-06-12,-3)    返回: 2009-06-09 .(例子中没有把日期参数的时间写上)
     *               <code>
     * @return
     * @throws Exception
     * @主要功能：将指定的日期加减n天数
     * @author: Snoopy Chen (ctfzh@yahoo.com.cn)
     * @since： Jun 12, 2009
     */
    public static Date calDay(Date date, int amount) throws Exception {
        Calendar tempCalen = Calendar.getInstance();
        tempCalen.setTime(date);
        tempCalen.add(Calendar.DATE, amount);
        return tempCalen.getTime();
    }

    /**
     * 解析时间间隔，并计算好相加后的时间
     *
     * @param date
     * @param additStr
     * @return
     */
    public static Date dateAddition(Date date, String additStr) {
        Date reDate = null;
        String[] strs = additStr.split("\\:");
        if ("m".equals(strs[0].toString())) {//分钟
            reDate = DateUtils.add(date, Calendar.MINUTE, Integer.parseInt(strs[1].toString()));
        } else if ("h".equals(strs[0].toString())) {//小时
            reDate = DateUtils.add(date, Calendar.HOUR_OF_DAY, Integer.parseInt(strs[1].toString()));
        } else if ("d".equals(strs[0].toString())) {//天
            reDate = DateUtils.add(date, Calendar.DAY_OF_MONTH, Integer.parseInt(strs[1].toString()));
        }
        return reDate;
    }


    /**
     * 获取某个时间段内星期几的日期点列表
     *
     * @param startDate
     * @param endDate
     * @param day
     * @return
     * @author cp
     * @since 2009-6-24 上午11:21:05
     */
    public static List<Date> getDatesOnWeek(Date startDate, Date endDate, int day) {
        List<Date> dates = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        cal.setTime(startDate);
        int startday = cal.get(Calendar.DAY_OF_WEEK);
        // 如果两个都不为0时，并开始时间的星期小于day时，得向前移一个星期才有day
        if (startday != 0 && day != 0 && startday > day) {
            cal.add(Calendar.WEEK_OF_YEAR, 1);
        }
        cal.set(Calendar.DAY_OF_WEEK, day);
        while (cal.compareTo(endCal) <= 0) {
            dates.add(cal.getTime());
            cal.add(Calendar.WEEK_OF_YEAR, 1);
        }
        return dates;
    }

    /**
     * 获取每隔两周某个时间段内星期几的日期点列表
     *
     * @param startDate
     * @param endDate
     * @param day
     * @return
     * @author cp
     * @since 2009-6-24 下午02:16:56
     */
    public static List<Date> getDatesOnDoubleWeek(Date startDate, Date endDate, int day) {
        List<Date> dates = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        cal.setTime(startDate);
        int startday = cal.get(Calendar.DAY_OF_WEEK);
        // 如果两个都不为0时，并开始时间的星期小于day时，得向前移一个星期才有day
        if (startday != 0 && day != 0 && startday > day) {
            cal.add(Calendar.WEEK_OF_YEAR, 1);
        }
        cal.set(Calendar.DAY_OF_WEEK, day);
        while (cal.compareTo(endCal) <= 0) {
            dates.add(cal.getTime());
            cal.add(Calendar.WEEK_OF_YEAR, 2);
        }
        return dates;
    }

    /**
     * 获取每月周某个时间段内几号的日期点列表
     *
     * @param startDate
     * @param endDate
     * @param date
     * @return
     * @author cp
     * @since 2009-6-24 下午02:22:49
     */
    public static List<Date> getDatesOnMonth(Date startDate, Date endDate, int date) {
        List<Date> dates = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        cal.setTime(startDate);
        int startdate = cal.get(Calendar.DATE);
        // 如果开始的日期大于参数date，即往前移一个月
        if (startdate > date) {
            cal.add(Calendar.MONTH, 1);
        }
        cal.set(Calendar.DATE, date);
        while (cal.compareTo(endCal) <= 0) {
            dates.add(cal.getTime());
            cal.add(Calendar.MONTH, 1);
        }
        return dates;
    }


    /**
     * 获取当前日期在指定日期中为第几周
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getCurrentWeekNum(Date startDate, Date endDate) {

        return 0;
    }

    /**
     * 根据某种格式，把字符串转成日期类型
     *
     * @param dateStr
     * @param pattern
     * @return
     * @throws ParseException
     * @author cp
     * @since 2009-6-24 下午02:38:49
     */
    public static Date parseDate(String dateStr, String pattern) throws ParseException {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.parse(dateStr);
    }

    /**
     * 根据某种格式，把US时间字符串转成日期类型
     *
     * @param dateStr
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parseUSDate(String dateStr, String pattern) throws ParseException {
        DateFormat df = new SimpleDateFormat(pattern, java.util.Locale.US);
        return df.parse(dateStr);
    }

    /**
     * 根据日期样式,格式化日期类
     *
     * @param src
     * @param pattern
     * @return
     * @throws ParseException
     * @author Terry, 政企软件中心
     * @since 2009-9-24,下午12:06:39
     */
    public static synchronized Date formatDate(Date src, final String pattern) throws ParseException {
        DateFormat df = new SimpleDateFormat(pattern);
        String dateStr = df.format(src);
        Date date = null;
        date = df.parse(dateStr);

        return date;
    }

    /**
     * java里面星期几的值表示为：日：1,一：2,....六：0. 而javascript里面星期几的值表示为：日：0,一：1,....六：6
     * 改办法是把星期几的值表示为：日：0,一：1,....六：6转成java的表示形式
     *
     * @param day
     * @return
     * @author cp
     * @since 2009-6-24 上午11:49:15
     */
    public static int getJavaDay(int day) {
        if (day == 6)
            return 0;
        else
            return day + 1;
    }

    /**
     * 转换某天为星期几
     *
     * @param day
     * @return
     */
    public static String convertDay2Week(Date day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        int iday = cal.get(Calendar.DAY_OF_WEEK);
        String dayStr = "";
        if (iday == 1) {
            dayStr = "星期天";
        } else if (iday == 2) {
            dayStr = "星期一";
        } else if (iday == 3) {
            dayStr = "星期二";
        } else if (iday == 4) {
            dayStr = "星期三";
        } else if (iday == 5) {
            dayStr = "星期四";
        } else if (iday == 6) {
            dayStr = "星期五";
        } else if (iday == 7) {
            dayStr = "星期六";
        }
        return dayStr;
    }

    /**
     * 计算两天的天数
     *
     * @param d1
     * @param d2
     * @return
     * @throws ParseException
     */
    public static long getDateDiffNum(String d1, String d2) throws ParseException {
        return getDateDiffNum(DATE_FORMAT.parse(d1), DATE_FORMAT.parse(d2));
    }

    /**
     * 计算两天的天数
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static long getDateDiffNum(Date startDate, Date endDate) {
        long result = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
        return result;
    }

    /**
     * 获取当四位数年份 如:2010
     */
    public static long getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static long getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static long getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DATE);
    }

    public static String formatDateStr(Date src, final String pattern) throws ParseException {
        DateFormat df = new SimpleDateFormat(pattern);
        String dateStr = df.format(src);
        return dateStr;
    }

    /**
     * 获取第N天的时间
     */
    public static Date getNextDay(Date startTime, int n) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, n);
        return calendar.getTime();
    }

    /**
     * 比较两个日期时间区间是否有重叠，时间格式精确到分秒
     *
     * @param leftStartDate
     * @param leftEndDate
     * @param rightStartDate
     * @param rightEndDate
     * @return true为区间重叠，false为区间不重叠
     */
    public static boolean isOverlap(Date leftStartDate, Date leftEndDate, Date rightStartDate, Date rightEndDate) {

        Assert.notNull(leftStartDate, "leftStartDate is required; it must not be null");
        Assert.notNull(leftEndDate, "leftEndDate is required; it must not be null");
        Assert.notNull(rightStartDate, "rightStartDate is required; it must not be null");
        Assert.notNull(rightEndDate, "rightEndDate is required; it must not be null");

        Assert.isTrue(!leftStartDate.after(leftEndDate), "leftStartDate must before leftEndDate");
        Assert.isTrue(!rightStartDate.after(rightEndDate), "rightStartDate must before rightEndDate");

        boolean isOverlap = ((!leftStartDate.before(rightStartDate)) && leftStartDate.before(rightEndDate))
                || (leftStartDate.after(rightStartDate) && !leftStartDate.after(rightEndDate))
                || (!rightStartDate.before(leftStartDate) && rightStartDate.before(leftEndDate))
                || (rightStartDate.after(leftStartDate) && !rightStartDate.after(leftEndDate));
        return isOverlap;
    }

    public static void main(String[] args) throws Exception {
        Date leftStartDate = parseDate("2012-11-16", "yyyy-MM-dd");
        Date leftEndDate = parseDate("2012-11-18", "yyyy-MM-dd");
        Date rightStartDate = parseDate("2012-11-15", "yyyy-MM-dd");
        Date rightEndDate = parseDate("2012-11-18", "yyyy-MM-dd");
        System.out.println(isOverlap(leftStartDate, leftEndDate, rightStartDate, rightEndDate));

        System.out.println(truncate(new Date(), Calendar.DATE));
    }
}
