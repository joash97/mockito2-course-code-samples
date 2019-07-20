package com.craftofprogramming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LibraryServiceTest {

    @Mock
    private LibraryService service;

    @Mock
    private LibraryService.DAO dao;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testBasicMethodVerification() {
        final LibraryService service = new LibraryService(dao);

        service.hasBookWithId(42);

        verify(dao).fetchBookById(anyInt());
    }

    @Test
    void testMethodVerificationUsingLambdas() {
        final LibraryService service = new LibraryService(dao);

        service.hasBookWithId(42);

        verify(dao).fetchBookById(argThat(argument ->argument.equals(42)));
    }

    @Test
    void testMethodVerificationUsingLogicalOp() {
        final LibraryService service = new LibraryService(dao);

        service.hasBookWithId(42);

        verify(dao).fetchBookById(eq(42));
    }

    @Test
    void testArgMatcherWithNull() {
        when(service.hasBookWithId(isNull())).thenThrow(IllegalArgumentException.class);

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.hasBookWithId(null);
        });
        assertThat(exception.getClass(), is(equalTo(IllegalArgumentException.class)));
    }

    @Test
    void testArgMatcherAllArgs() {
        final String author = "Joshua Block";

        when(service.hasBookWithTopicAndAuthor(any(Topic.class), eq(author))).thenReturn(false);

        assertFalse( service.hasBookWithTopicAndAuthor(Topic.COMPUTING, author) );
    }

    @Test
    void testCustomArgMatcherWithLambda() {

        when(service.hasBookWithId(argThat(argument -> argument!=null && argument>0))).thenReturn(true);

        assertTrue(service.hasBookWithId(42));
        assertFalse(service.hasBookWithId(-42));
    }

    @Test
    void testCustomArgMatcher() {
        when(service.hasBookWithId(argThat(isValid()))).thenReturn(true);

        assertTrue(service.hasBookWithId(42));
        assertFalse(service.hasBookWithId(-42));
    }

    private MyArgMatcher isValid() {
        return new MyArgMatcher();
    }

    public static class MyArgMatcher implements ArgumentMatcher<Integer> {

        Integer argument;

        @Override
        public boolean matches(Integer argument) {
            this.argument = argument;
            return argument!=null && argument>0;
        }

        @Override
        public String toString() {
            return String.format("%d must be a positive integer", argument);
        }
    }

}