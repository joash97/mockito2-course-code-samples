package com.craftofprogramming;

import com.craftofprogramming.LibraryService.DAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static com.craftofprogramming.LibraryService.DEFAULT_BOOK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class SpyTest {
    @Spy
    private DAO dao;

    @Mock(name = "outLogger")
    private LoggerService logger1;

    @Mock(name = "errLogger")
    private LoggerService logger2;

    @InjectMocks
    private GenericService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testMethod() {
        final DAO real = new DAO();
        final DAO spy = spy(real);

        doReturn(null).when(spy).fetchBookById(42);

        final Book book = spy.fetchBookById(42);

        assertThat(book, is(nullValue()));
    }

    @Test
    void testSpy() {

//        when(dao.fetchBookById(anyInt())).thenReturn(null);

        doReturn(null).when(dao).fetchBookById(anyInt());

        final Book book = service.fetchBook(42, "Effective Java");

        assertThat(book, is(equalTo(DEFAULT_BOOK)));

        verify(dao).fetchBookByTitle(anyString());
    }

    @Test
    void testDoReturn() {

        final GenericService spy = spy(service);

//        when(spy.fetchBookById(42)).thenReturn(LibraryService.DEFAULT_BOOK);

        doReturn(DEFAULT_BOOK).when(spy).fetchBookById(42);
    }

}
