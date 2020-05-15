package ru.ancndz.libraryBase.libraryEnvironment;

import ru.ancndz.libraryBase.entity.Client;
import ru.ancndz.libraryBase.operations.Penalty;
import ru.ancndz.libraryBase.operations.Rent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс читательской карты клиента, привязана к клиенту и все аренды и штрафы назначаются на нее
 */
public class Card {
    /**
     * айди карточки клиента
     */
    private final int id;
    /**
     * дата активации
     */
    private final Date dateActivation;
    /**
     * секретное слово
     */
    private final String pin_code;
    /**
     * клиент, владелец карты
     */
    private final Client cardHolder;
    /**
     * аренды клиента
     */
    private final Map<Integer, Rent> bookRents;
    /**
     * штрафы клиента
     */
    private final Map<Integer, Penalty> clientPenalties;

    /**
     * класс читательской карты клиента
     * @param id айди карты
     * @param dateActivation дата активации
     * @param pin_code секретное слово
     * @param cardHolder клиент (владелец карты)
     */
    public Card(int id, Date dateActivation, String pin_code, Client cardHolder) {
        this.id = id;
        this.dateActivation = dateActivation;
        this.pin_code = pin_code;
        this.cardHolder = cardHolder;
        this.bookRents = new HashMap<>();
        this.clientPenalties = new HashMap<>();
    }

    public void addRent(Map<Integer, Rent> rents) {
        this.bookRents.putAll(rents);
    }

    public void addPenalty(Map<Integer, Penalty> penalties) {
        this.clientPenalties.putAll(penalties);
    }


    public int getId() {
        return id;
    }

    public Date getDateActivation() {
        return dateActivation;
    }

    public String getPin_code() {
        return pin_code;
    }

    public Client getCardHolder() {
        return cardHolder;
    }

    public Map<Integer, Rent> getBookRents() {
        return bookRents;
    }

    public Map<Integer, Penalty> getClientPenalties() {
        return clientPenalties;
    }
}
