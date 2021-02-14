package com.cursor.library.services;

import com.cursor.library.daos.BookDao;
import com.cursor.library.exceptions.BadIdException;
import com.cursor.library.exceptions.BookNameIsNullException;
import com.cursor.library.exceptions.BookNameIsTooLongException;
import com.cursor.library.models.Book;
import com.cursor.library.models.CreateBookDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private  BookService bookService;

    @Mock
    private BookDao bookDao = new BookDao();

    public BookServiceTest() {
        bookService = new BookService(bookDao);
    }

    @BeforeAll
    void setUp() {
        bookDao = Mockito.mock(BookDao.class);

    }

    @Test
    void getBookByIdSuccessTest() {
        String bookId = "book-id";

        Mockito.when(bookDao.getById(bookId)).thenReturn(new Book(bookId));

        Book bookFromDB = bookService.getById(bookId);

        assertEquals(
                bookId,
                bookFromDB.getBookId()
        );
    }

    @Test
    void getBookByIdBadIdExceptionTest() {
        assertThrows(
                BadIdException.class,
                () -> bookService.getById("       ")
        );
    }

    @Test
    void getValidatedBookNameExpectBookNameIsNullExceptionTest() {
        assertThrows(
                BookNameIsNullException.class,
                () -> bookService.getValidatedBookName(null)
        );
    }
    @Test
    @DisplayName("The test of too long name of book ")
    void getBookValidationNameIsTooLongExceptionTest() {
        BookNameIsTooLongException theTooLongWordException = Assertions.assertThrows(
                BookNameIsTooLongException.class,
                () -> bookService.getValidatedBookName(" ".repeat(1001)));
                Assertions.assertEquals(null, theTooLongWordException.getMessage());

    }
    @Test
    @DisplayName("Testing the book by id")
    void getBookById() {
      String bookId = "random_id_value_3";
      Book test = new Book();
        test.setName("Elon Musk: Tesla, SpaceX, and the Quest for a Fantastic Future");
        test.setDescription("");
        test.setAuthors(Collections.singletonList("Ashlee Vance"));
        test.setYearOfPublication(2017);
        test.setNumberOfWords(865874);
        test.setRating(10);
        Book actual = bookService.getById(bookId);
        Assertions.assertEquals(test.getName(), actual.getName());
    }

    @Test
    @DisplayName("The test which show us a correct and property implementation a part of code")
    void getCreateBookTest() {
        CreateBookDto bookDto = new CreateBookDto();
        bookDto.setName("The catcher in the rye");
        bookDto.setDescription("This book told us the story about one young man");
        bookDto.setAuthors(Collections.singletonList("J.D.Salinger"));
        bookDto.setYearOfPublication(1951);
        bookDto.setRating(98);
        bookDto.setNumberOfWords(151532);

    }
}

