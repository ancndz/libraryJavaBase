package ru.ancndz.libraryBase.content.libraryEnvironment;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {
    /**
     * айди книги
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "book_type")
    private String bookType;
    @Column(name = "name")
    private String name;
    @Column(name = "author")
    private String author;
    @Column(name = "edition")
    private String edition;
    @Column(name = "edition_num")
    private String editionNum;
    @Column(name = "pub_year")
    private int pubYear;
    @Column(name = "genre")
    private String genre;
    @Column(name = "count")
    private int count;
    @Column(name = "extra")
    private String extra;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, targetEntity = Library.class)
    @JoinColumn(name = "library_id")
    private Library library;

    @Transient
    private int libraryId;

    public Book() {
    }

    public Book(int id, String bookType, String name, String author,
                String edition, String editionNum, int pubYear, String genre,
                int count, String extra, Library library) {
        this.id = id;
        this.bookType = bookType;
        this.name = name;
        this.author = author;
        this.edition = edition;
        this.editionNum = editionNum;
        this.pubYear = pubYear;
        this.genre = genre;
        this.count = count;
        this.extra = extra;
        this.library = library;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getEditionNum() {
        return editionNum;
    }

    public void setEditionNum(String editionNum) {
        this.editionNum = editionNum;
    }

    public int getPubYear() {
        return pubYear;
    }

    public void setPubYear(int pubYear) {
        this.pubYear = pubYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getPubYear() == book.getPubYear() &&
                getName().equals(book.getName()) &&
                Objects.equals(getAuthor(), book.getAuthor()) &&
                Objects.equals(getEdition(), book.getEdition()) &&
                Objects.equals(getEditionNum(), book.getEditionNum()) &&
                getLibrary().equals(book.getLibrary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAuthor(), getEdition(), getEditionNum(), getPubYear(), getLibrary());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookType='" + bookType + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", edition='" + edition + '\'' +
                ", editionNum='" + editionNum + '\'' +
                ", pubYear=" + pubYear +
                ", genre='" + genre + '\'' +
                ", count=" + count +
                ", extra='" + extra + '\'' +
                ", library=" + library +
                '}';
    }
}
