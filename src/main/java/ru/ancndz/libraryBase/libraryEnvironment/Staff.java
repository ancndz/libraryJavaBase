package ru.ancndz.libraryBase.libraryEnvironment;

import ru.ancndz.libraryBase.jobs.Worker;

/**
 * Класс сотрудника
 */
public class Staff {
    /**
     * айди работника
     */
    private final int id;
    /**
     * имя работника
     */
    private final String firstName;
    /**
     * фамилия работника
     */
    private final String secondName;
    /**
     * адрес проживания
     */
    private final String address;
    /**
     * тел. номер (без +)
     */
    private final int number;
    /**
     * должность
     */
    private final Worker job;

    /**
     * конструктор
     * @param id айди работника
     * @param firstName имя работника
     * @param secondName фамилия работника
     * @param address адрес проживания
     * @param number тел. номер (без +)
     * @param job должность
     */
    public Staff(int id, String firstName, String secondName, String address, int number, Worker job) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
        this.number = number;
        this.job = job;
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

    public String getAddress() {
        return address;
    }

    public int getNumber() {
        return number;
    }

    public Worker getJob() {
        return job;
    }
}
