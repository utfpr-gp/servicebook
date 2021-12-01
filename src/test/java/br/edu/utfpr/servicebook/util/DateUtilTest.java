package br.edu.utfpr.servicebook.util;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DateUtilTest {

    @Test
    void getTextualDate_Today() {
        LocalDate expiredDate = LocalDate.now();
        String textualDate = DateUtil.getTextualDate(expiredDate);
        Assertions.assertEquals(textualDate, DateEnum.TODAY.getTextualDate());
    }

    @Test
    void getTextualDate_Tomorrow() {
        LocalDate expiredDate = DateUtil.getTomorrow();
        String textualDate = DateUtil.getTextualDate(expiredDate);
        Assertions.assertEquals(textualDate, DateEnum.TOMORROW.getTextualDate());
    }

    @Test
    void getTextualDate_ThisWeek() {
        LocalDate expiredDate = DateUtil.getTomorrow();
        String textualDate = DateUtil.getTextualDate(expiredDate);
        Assertions.assertEquals(textualDate, DateEnum.TOMORROW.getTextualDate());
    }

    @Test
    void getTextualDate_NextWeek() {
        LocalDate expiredDate = DateUtil.getNextWeek();
        String textualDate = DateUtil.getTextualDate(expiredDate);
        Assertions.assertEquals(textualDate, DateEnum.NEXT_WEEK.getTextualDate());
    }

//    @Test
//    void getTextualDate_ThisMonth() {
//        LocalDate expiredDate = DateUtil.getThisMonth();
//        String textualDate = DateUtil.getTextualDate(expiredDate);
//        Assertions.assertEquals(textualDate, DateEnum.THIS_MONTH.getTextualDate());
//    }

    @Test
    void getTextualDate_NextMonth() {
        LocalDate expiredDate = DateUtil.getNextMonth().plusWeeks(1);
        String textualDate = DateUtil.getTextualDate(expiredDate);
        Assertions.assertEquals(textualDate, DateEnum.NEXT_MONTH.getTextualDate());
    }

    @Test
    void getTextualDate_Future() {
        LocalDate expiredDate = DateUtil.getNextMonth().plusMonths(1);
        String textualDate = DateUtil.getTextualDate(expiredDate);
        Assertions.assertEquals(textualDate, DateEnum.FUTURE.getTextualDate());
    }

}