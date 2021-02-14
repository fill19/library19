package com.cursor.library.controllers;

import com.cursor.library.daos.BookDao;
import com.cursor.library.models.Book;
import com.cursor.library.models.CreateBookDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

public class BookControllerTest extends BaseControllerTest {

    private BookDao bookDao;

    @BeforeAll
    void setUp() {
        bookDao = new BookDao();
    }

    @Test
    public void createBookTest() throws Exception {
        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setName("Cool createBookDto");
        createBookDto.setDescription("Cool description");
        createBookDto.setNumberOfWords(100500);
        createBookDto.setRating(10);
        createBookDto.setYearOfPublication(2020);
        createBookDto.setAuthors(Arrays.asList("author1", "author2"));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(createBookDto));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Book book = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                Book.class
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + book.getBookId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAllTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books");

        MvcResult result = this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<Book> books;
        books = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        List<Book> theMainList = bookDao.getAll();

        Assert.assertEquals(theMainList, books);
    }

    @Test
    public void TheProperIdTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/books/some id"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteBooksFromDB() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/books/" + "random_id_value_2"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void TheNotFoundIdTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/books/some id"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}
