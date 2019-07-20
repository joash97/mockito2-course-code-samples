package com.craftofprogramming;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.craftofprogramming.LibraryService.DEFAULT_BOOK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Family of methods:
 *
 * 1) doReturn()
 * 2) doThrow()
 * 3) doAnswer()
 * 4) doNothing()
 * 5) doCallRealMethod()
 */
class GenericServiceTestOne {
    @Mock
    private LibraryService.DAO dao;

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
    void testDoCallRealMethod() {

        doCallRealMethod().when(dao).fetchBookById(anyInt());
        when(dao.fetchBookByTitle(anyString())).thenReturn(DEFAULT_BOOK);

        service.fetchBookById(42);

    }

    @Test
    void testDoNothing() {
        doNothing()
        .doThrow(RuntimeException.class)
        .when(dao).foo();

        dao.foo();

        final RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            dao.foo();
        });
        assertThat(exception.getClass(), is(equalTo(RuntimeException.class)));
    }

    @Test
    void testDoAnswer() {

//        when(dao.fetchBookById(anyInt())).thenReturn(DEFAULT_BOOK);

        doAnswer(invocation -> {
            final Integer id = invocation.getArgument(0, Integer.class);
            return id == 42 ? DEFAULT_BOOK : null;
        }).when(dao).fetchBookById(anyInt());

        final Book book = service.fetchBookById(1);

        assertThat(book, is(equalTo(DEFAULT_BOOK)));
    }

    @Test
    void testDoReturn() {

        final GenericService spy = spy(service);

        doNothing().when(spy).fetchBookById(anyInt());

//        when(spy.fetchBookById(42)).thenReturn(LibraryService.DEFAULT_BOOK);

        doReturn(DEFAULT_BOOK).when(spy).fetchBookById(42);

    }

    @Test
    void testDoThrow() {

//        when(dao.fetchBookById(anyInt())).thenThrow(RuntimeException.class);

//        when(dao.foo()).thenThrow(RuntimeException.class);

        doThrow(RuntimeException.class).when(dao).foo();
//        doThrow(new RuntimeException()).when(dao).foo();

        final RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            service.fetchBookById(42);
        });
        assertThat(exception.getClass(), is(equalTo(RuntimeException.class)));
    }

}
