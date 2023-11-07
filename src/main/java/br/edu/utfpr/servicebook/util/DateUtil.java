package br.edu.utfpr.servicebook.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

public class DateUtil {
    private static final String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HHmm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };
    private static final int[] dayOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    public static final String DATETIME_PATTERN="yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN="yyyy-MM-dd";

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate getToday(){
        return LocalDate.now();
    }

    /**
     * Retorna o dia de amanhã
     * @return
     */
    public static LocalDate getTomorrow() {

        return LocalDate.now().plusDays(1);
    }

    /**
     * Retorna o primeiro dia da semana corrente, iniciando no domingo.
     * @return
     */
    public static LocalDate getThisWeek(){
        LocalDate today = LocalDate.now();

        LocalDate sunday = today.with(TemporalAdjusters.previousOrSame(SUNDAY));

        return sunday;
    }


    /**
     * Retorna o primeiro dia da próxima semana, iniciando no domingo.
     * @return
     */
    public static LocalDate getNextWeek(){
        LocalDate today = LocalDate.now();

        LocalDate sunday = today.with(TemporalAdjusters.next(SUNDAY));
        return sunday;
    }

    /**
     * Retorna o primeiro dia do mês corrente
     * @return
     */
    public static LocalDate getThisMonth(){
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * Retorna o primeiro dia do próximo mês
     * @return
     */
    public static LocalDate getNextMonth(){
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.firstDayOfNextMonth());
    }

    /**
     * Retorna um texto expressando a proximidade da data de expiração.
     * @param expiredDate
     * @return
     */
    public static String getTextualDate(LocalDate expiredDate){
        LocalDate now = LocalDate.now();

        if(expiredDate.isEqual(DateUtil.getToday())){
            return DateEnum.TODAY.getTextualDate();
        } else if(expiredDate.isEqual(DateUtil.getTomorrow())){
            return DateEnum.TOMORROW.getTextualDate();
        }
        else if(expiredDate.isAfter(DateUtil.getThisWeek().minusDays(1)) && expiredDate.isBefore(DateUtil.getNextWeek())){
            return DateEnum.THIS_WEEK.getTextualDate();
        }
        else if(expiredDate.isAfter(DateUtil.getNextWeek().minusDays(1)) && expiredDate.isBefore(DateUtil.getNextWeek().plusWeeks(1))){
            return DateEnum.NEXT_WEEK.getTextualDate();
        }
        else if(expiredDate.isAfter(DateUtil.getThisMonth().minusDays(1)) && expiredDate.isBefore(DateUtil.getNextMonth())){
            return DateEnum.THIS_MONTH.getTextualDate();
        }
        else if(expiredDate.isAfter(DateUtil.getNextMonth().minusDays(1)) && expiredDate.isBefore(DateUtil.getNextMonth().plusMonths(1))){
            return DateEnum.NEXT_MONTH.getTextualDate();
        }
        else{
            return DateEnum.FUTURE.getTextualDate();
        }
    }
}