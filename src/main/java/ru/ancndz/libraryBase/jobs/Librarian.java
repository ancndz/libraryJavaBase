package ru.ancndz.libraryBase.jobs;

/**
 * Класс сотрудника, имеющего доступ к базе данных
 */
public class Librarian extends Worker {
    /**
     * конструктор
     * @param id айди работника
     * @param name название должности
     * @param pay зар. плата, тыс. руб.
     */
    public Librarian(int id, String name, int pay) {
        super(id, name, pay);
    }
}
