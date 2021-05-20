package br.edu.utfpr.servicebook.util;

import lombok.SneakyThrows;
import org.apache.tomcat.jni.Local;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class DateUtil {
    private static final String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HHmm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };
    private static final int[] dayOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    public static final String DATETIME_PATTERN="yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN="yyyy-MM-dd";

    public static LocalDate getToday(){
        return LocalDate.now();
    }

    public static LocalDate getTomorrow() {

        return LocalDate.now().plusDays(1);
    }

    public static LocalDate getThisWeek(){
        LocalDate today = LocalDate.now();

        LocalDate sunday = today.with(nextOrSame(SUNDAY));

        return sunday;
    }
    public static LocalDate getNextWeek(){
        LocalDate today = LocalDate.now();

        LocalDate sunday = today.with(nextOrSame(SUNDAY));
        LocalDate nextSunday = sunday.plusWeeks(1);
        return nextSunday;
    }

    public static LocalDate getThisMonth(){
        LocalDate lastDay = LocalDate.now();
        lastDay = lastDay.withDayOfMonth(lastDay.lengthOfMonth());
        return lastDay;
    }
    public static LocalDate getNextMonth(){
        LocalDate lastDay = LocalDate.now();
        lastDay = lastDay.withDayOfMonth(lastDay.lengthOfMonth());
        LocalDate nextMonth = lastDay.plusMonths(1);

        return nextMonth;
    }


    public static String getAfterTomorrow() {
        return formatDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 * 2), "yyyy-MM-dd");
    }
    public static int getMaxDayOfMonth() {
        Calendar gregorianCalendar = GregorianCalendar.getInstance();
        return getMaxDayOfMonth(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH) + 1);
    }
    public static int getMaxDayOfMonth(int year, int month) {
        if (month == 2 && ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)) {
            return 29;
        } else {
            return dayOfMonth[month - 1];
        }
    }
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }
    public static String getMonth(Date date) {
        return formatDate(date, "MM");
    }
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }
    public static String getDay(Date date) {
        return formatDate(date, "dd");
    }
    public static String getDay(Timestamp timestamp) {
        return formatDate(timestamp, "dd");
    }
    public static long pastDays(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }
    public static long pastMinute(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }
    public static Calendar getDateOfNextMonth(Calendar date) {
        Calendar lastDate = (Calendar) date.clone();
        lastDate.add(Calendar.MONTH, 1);
        return lastDate;
    }
    public static Calendar getDateOfNextMonth(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return getDateOfNextMonth(c);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format(yyyy-MM-dd): " + dateStr);
        }
    }
    public static Date getCurrentLocalTime() {
        return Calendar.getInstance().getTime();
    }
    public static String getCurrentLocalTime(String format) {
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d).toString();
    }
    public static String formatDate(long time){
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = formatter.format(date);
        return timeStr;
    }

    public static String formatDate(long time, String format){
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String timeStr = formatter.format(date);
        return timeStr;
    }
    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = formatter.format(date);
        return time;
    }
    public static String formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String time = formatter.format(date);
        return time;
    }
    public static String getDefaultCurrentLocalTime(){
        return  getCurrentLocalTime("yyyy-MM-dd HH:mm:ss");
    }
    public static Date charConvertDate(String dateStr) {
        try{
            return charConvertDate("yyyy-MM-dd HH:mm:ss",dateStr);
        }catch(RuntimeException e){
            throw e;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static Date charConvertDate(String format, String dateStr) {
        try{
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(dateStr);
        }catch(ParseException e){
            throw new RuntimeException(e);
        }
    }
    public static String getTimeFolder(String uid){
        try {
            String temp="/";
            StringBuilder floder=new StringBuilder();
            floder.append(getSomeTime("year")+temp);
            floder.append(getSomeTime("month")+temp);
            floder.append(getSomeTime("date")+temp);
            floder.append(uid);
            return floder.toString();
        } catch (Exception e) {
            return "";
        }
    }
    @SuppressWarnings("deprecation")
    public static int getSomeTime(String con) {
        try{
            Date d = new Date();
            if(con.equals("year")){
                return d.getYear()+1900;
            }else if(con.equals("month")){
                return d.getMonth()+1;
            }else if(con.equals("date")){
                return d.getDate();
            }else if(con.equals("hour")){
                return d.getHours();
            }else if(con.equals("min")){
                return d.getMinutes();
            }else if(con.equals("second")){
                return d.getSeconds();
            }else{
                return 0;
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static int getMonthLastDay(int year, int month)
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}