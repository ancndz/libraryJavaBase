package ru.ancndz.libraryBase.operations;

import java.util.Date;

/**
 * Класс штрафа, имеет айди аренды, к которой привязан и айди карты
 */
public class Penalty {
    /**
     * айди штрафа
     */
    private final int id;
    /**
     * причина штрафа
     */
    private final String reason;
    /**
     * дата штрафа
     */
    private final Date datePenalty;
    /**
     * сумма штрафа
     */
    private final int amount;
    /**
     * выплаченная сумма штрафа
     */
    private final int completeAmount;
    /**
     * аренда
     */
    private final Rent rent;


    /**
     * конструктор
     * @param id айди штрафа
     * @param reason причина штрафа
     * @param datePenalty дата штрафа
     * @param amount сумма штрафа
     * @param completeAmount выплаченная сумма штрафа
     * @param rent аренда
     */
    public Penalty(int id, String reason, Date datePenalty, int amount, int completeAmount, Rent rent) {
        this.id = id;
        this.reason = reason;
        this.datePenalty = datePenalty;
        this.amount = amount;
        this.completeAmount = completeAmount;
        this.rent = rent;
    }

    public int getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public Date getDatePenalty() {
        return datePenalty;
    }

    public int getAmount() {
        return amount;
    }

    public int getCompleteAmount() {
        return completeAmount;
    }

    public Rent getRent() {
        return rent;
    }
}
