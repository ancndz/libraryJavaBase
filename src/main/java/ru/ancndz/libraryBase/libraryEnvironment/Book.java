package ru.ancndz.libraryBase.libraryEnvironment;

/**
 * Класс книги, журнала, и т.п.
 */
public class Book {
    /**
     * айди книги
     */
    private final int id;
    /**
     * тип книги (журнал, книга, статья и т.п.)
     */
    private final String bookType;
    /**
     * название книги
     */
    private final String name;
    /**
     * автор книги
     */
    private final String author;
    /**
     * издание книги
     */
    private final String edition;
    /**
     * год публикации
     */
    private final int pubYear;
    /**
     * жанр книги
     */
    private final String genre;
    /**
     * кол-во книг в библиотеке
     */
    private final int count;
    /**
     * доп информация о книге
     */
    private final String extra;


    /**
     * конструктор
     * @param id айди книги
     * @param bookType тип книги
     * @param name название книги
     * @param author автор книги
     * @param edition издание книги
     * @param pubYear жанр книги
     * @param genre кол-во книг в библиотеке
     * @param count доп информация о книге
     * @param extra доп информация о книге
     */
    public Book(int id, String bookType, String name, String author, String edition,
                int pubYear, String genre, int count, String extra) {
        this.id = id;
        this.bookType = bookType;
        this.name = name;
        this.author = author;
        this.edition = edition;
        this.pubYear = pubYear;
        this.genre = genre;
        this.count = count;
        this.extra = extra;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getEdition() {
        return edition;
    }

    public int getPubYear() {
        return pubYear;
    }

    public String getGenre() {
        return genre;
    }

    public int getCount() {
        return count;
    }

    public String getExtra() {
        return extra;
    }

}
