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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class GenericServiceTest {

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
    void testInvocationOrder() {
        when(dao.fetchBookById(anyInt())).thenReturn(LibraryService.DEFAULT_BOOK);

        service.fetchBookById(1);
        service.fetchBookById(2);

        final InOrder singleInOrder = inOrder(dao);
        singleInOrder.verify(dao).fetchBookById(1);
        singleInOrder.verify(dao).fetchBookById(2);

        final InOrder multipleInOrder = inOrder(logger1, logger2);
        multipleInOrder.verify(logger1).logToOut(anyString());
        multipleInOrder.verify(logger2).logToErr(anyString());

        final InOrder order = inOrder(dao, logger1, logger2);
        order.verify(dao).fetchBookById(1);
        order.verify(dao).fetchBookById(2);
        order.verify(logger1).logToOut(anyString());
        order.verify(logger2).logToErr(anyString());
    }

    @Test
    void testNumberOfInvocations() {
        when(dao.fetchBookById(42)).thenReturn(LibraryService.DEFAULT_BOOK);

        service.fetchBookById(42);

        verify(dao).fetchBookById(42);
        verify(dao, times(1)).fetchBookById(42);
        verify(dao, atLeast(1)).fetchBookById(42);
        verify(dao, atLeastOnce()).fetchBookById(42);
        verify(dao, atMost(1)).fetchBookById(42);
        verify(dao, atMostOnce()).fetchBookById(42);

        verify(logger1, never()).logToErr(anyString());
        verify(logger1, times(1)).logToOut(anyString());
        verify(logger1, atLeast(1)).logToOut(anyString());
        verify(logger1, atMost(10)).logToOut(anyString());

        verify(logger2, never()).logToOut(anyString());
        verify(logger2, times(1)).logToErr(anyString());

        verifyNoMoreInteractions(dao);
        verifyNoMoreInteractions(logger1);
        verifyNoMoreInteractions(logger2);

        final LoggerService unusedMock = mock(LoggerService.class);
        verifyZeroInteractions(unusedMock);
    }

    @Test
    void testMethod() {
        when(dao.fetchBookById(42)).thenReturn(LibraryService.DEFAULT_BOOK);

        final Book book = service.fetchBookById(42);

        assertThat(book, is(equalTo(LibraryService.DEFAULT_BOOK)));

        verify(logger1).logToOut(anyString());

        verify(logger2).logToErr(anyString());
    }

}