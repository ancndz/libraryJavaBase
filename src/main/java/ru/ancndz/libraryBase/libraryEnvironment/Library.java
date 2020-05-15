package ru.ancndz.libraryBase.libraryEnvironment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Класс библиотеки
 */
@Component
public class Library {
    /**
     * айди библиотеки
     */
    private final int id;
    /**
     * название библиотеки
     */
    private final String name;
    /**
     * адрес библиотеки
     */
    private final String address;
    /**
     * мап(айди - книга) всех книг библиотеки, каталог
     */
    private final Map<Integer, Book> catalog;
    /**
     * мап(айди - работник) всех работников библиотеки
     */
    private final Map<Integer, Staff> workGroup;

    /**
     * класс "библиотека"
     * @param id айди библиотеки
     * @param name название библиотеки
     * @param address адрес библиотеки
     */
    public Library(@Value("${libraryTest.id}") int id,
                   @Value("${libraryTest.name}") String name,
                   @Value("${libraryTest.address}") String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.catalog = new HashMap<>();
        this.workGroup = new HashMap<>();
    }

    public void addBook(Map<Integer, Book> books) {
        this.catalog.putAll(books);
    }

    public void delBook(int bookId) {
        this.catalog.remove(bookId);
    }

    public Book getBook(int bookId) {
        return this.catalog.getOrDefault(bookId, null);
    }

    public void addStaff(Map<Integer, Staff> staff) {
        this.workGroup.putAll(staff);
    }

    public void delStaff(int staffId) {
        this.workGroup.remove(staffId);
    }

    public Staff getStaff(int staffId) {
        return this.workGroup.getOrDefault(staffId, null);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
