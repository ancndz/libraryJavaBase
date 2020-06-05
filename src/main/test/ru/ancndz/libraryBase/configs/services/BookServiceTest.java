package ru.ancndz.libraryBase.configs.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ancndz.libraryBase.content.libraryEnvironment.Book;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookServiceTest {

    private final int ROWS_COUNT = 1000;

    @Autowired
    private BookService bookService;

    private static final List<Book> bookList = new ArrayList<>();

    @BeforeAll
    static void readData() {
        System.out.println("Test init...");
        String dir = "src\\main\\test\\resources\\";

        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(dir + "1000books.json"))
        {
            Object obj = parser.parse(reader);
            JSONArray books = (JSONArray) obj;
            books.forEach(bookJson -> parseBook((JSONObject) bookJson));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseBook(JSONObject bookJsonRaw) {

        JSONObject bookJson = (JSONObject) bookJsonRaw.get("book");

        Book book = new Book();
        book.setId( ((Long) bookJson.get("id")).intValue() );
        book.setBookType((String)bookJson.get("book_type"));
        book.setName((String)bookJson.get("name"));
        book.setAuthor((String)bookJson.get("author"));
        book.setPubYear(((Long) bookJson.get("pub_year")).intValue());
        book.setEditionNum((String)bookJson.get("edition_num"));
        book.setGenre((String)bookJson.get("genre"));
        book.setCount(((Long) bookJson.get("count")).intValue());
        book.setExtra((String)bookJson.get("extra"));
        book.setLibraryId(((Long) bookJson.get("library_id")).intValue());

        BookServiceTest.bookList.add(book);
    }


    /*@Test
    void readFromJsonTest() {
        Assertions.assertNotNull(bookList.get(0));
        Assertions.assertEquals(1000, bookList.size());
    }*/

    @Test
    void saveTest() {
        bookList.stream()
                .filter(book -> book.getId() <= ROWS_COUNT)
                .forEach(book -> this.bookService.save(book));
    }

    @Test
    void getAllTest() {
        List<Book> allBooks = this.bookService.booksList();
        Assertions.assertEquals(ROWS_COUNT, allBooks.size());
    }

    @Test
    void getOneTest() {
        Book book = this.bookService.get(1);
        Assertions.assertEquals(1, book.getId());
    }

    @Test
    void findByCriteriaTest() {
        int yearToFind = 2005;
        List<Book> foundBooks = this.bookService
                .findByCriteria("", "", 2005, "", "", 0);
        System.out.println(foundBooks.size());
        foundBooks.forEach(book -> Assertions.assertEquals(2005, book.getPubYear()));
    }
}