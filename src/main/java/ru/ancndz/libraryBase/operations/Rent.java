package ru.ancndz.libraryBase.operations;

import ru.ancndz.libraryBase.libraryEnvironment.Book;
import ru.ancndz.libraryBase.libraryEnvironment.Staff;

import java.util.Date;

/**
 * Класс аренды
 */
public class Rent {
    /**
     * айди аренды
     */
    private final int id;
    /**
     * дата начала аренды
     */
    private final Date dateStart;
    /**
     * дата конца аренды
     */
    private final Date dateEnd;
    /**
     * фактическое окончание аренды (книга возвращена)
     */
    private final Date dateFactEnd;
    /**
     * сотрудник, выдавшего книгу
     */
    private final Staff staff;
    /**
     * книга
     */
    private final Book book;

    /**
     * конструктор
     * @param id айди аренды
     * @param dateStart дата начала аренды
     * @param dateEnd дата завершения аренды
     * @param dateFactEnd дата фактического завершения аренда (книга возвращена в библиотеку)
     * @param worker сотрудник
     * @param book книга
     */
    public Rent(int id, Date dateStart, Date dateEnd, Date dateFactEnd,
                Staff worker, Book book) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateFactEnd = dateFactEnd;
        this.staff = worker;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public Date getDateFactEnd() {
        return dateFactEnd;
    }

    public Staff getStaff() {
        return staff;
    }

    public Book getBook() {
        return book;
    }
}
