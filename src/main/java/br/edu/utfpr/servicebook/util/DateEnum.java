package br.edu.utfpr.servicebook.util;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public enum DateEnum {
    TODAY("Para hoje"),
    TOMORROW("Para amanhã"),
    THIS_WEEK("Para esta semana"),
    NEXT_WEEK("Para a próxima semana"),
    THIS_MONTH("Para este mês"),
    NEXT_MONTH("Para o próximo mês"),
    FUTURE("Para os próximos meses");

    private String textualDate;

    private DateEnum(String textualDate){
        this.textualDate = textualDate;
    }

    public String getTextualDate() {
        return textualDate;
    }
}
