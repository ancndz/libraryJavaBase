package ru.ancndz.libraryBase.jobs;

/**
 * Класс дефолтной должности, сотрудник общего назначения
 */
public class Worker {
    /**
     * id должности
     */
    protected int id;
    /**
     * название должности
     */
    protected String name;
    /**
     * зарплата, тысяч рублей
     */
    protected int pay;

    /**
     * конструктор
     * @param id айди должности
     * @param name название должности
     * @param pay зар. плата, тыс. руб.
     */
    public Worker(int id, String name, int pay) {
        this.id = id;
        this.name = name;
        this.pay = pay;
    }


    private int getId() {
        return this.id;
    }

    private String getName() {
        return this.name;
    }

    private int getPay() {
        return this.pay;
    }
}
