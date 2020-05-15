package ru.ancndz.libraryBase.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Класс клиента
 */
//@Component
public class Client {
    /**
     * айди клиента
     */
    private final int id;
    /**
     * имя клиента
     */
    private final String firstName;
    /**
     * фамилия клиента
     */
    private final String secondName;
    /**
     * e-mail клиента
     */
    private final String email;
    /**
     * студент? науч работник?
     */
    private final String status;
    /**
     * дата регистрации
     */
    private final Date dateReg;

    /**
     * конструктор
     * @param id айди клиента
     * @param firstName имя клиента
     * @param secondName фамилия клиента
     * @param email e-mail клиента
     * @param status студент? науч работник?
     * @param dateReg дата регистрации
     */
    public Client(int id, String firstName, String secondName, String email, String status, Date dateReg) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.status = status;
        this.dateReg = dateReg;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public Date getDateReg() {
        return dateReg;
    }
}
