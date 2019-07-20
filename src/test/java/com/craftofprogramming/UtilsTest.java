package com.craftofprogramming;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UtilsTest {

    @Test
    void testMethod() {
        final Utils utils = mock(Utils.class);

        List<Book> expect = Arrays.asList(new Book[]{new Book("Effective Java", 280, Topic.COMPUTING, Year.of(2000), "Joshua Block")});
        when(utils.parseLibraryFrom(any(Path.class))).thenReturn(expect);

        System.out.println(utils.parseLibraryFrom(BookDAO.DEFAULT_PATH));

        assertThat(utils.parseLibraryFrom(BookDAO.DEFAULT_PATH), is(equalTo(expect)));
    }

    @Test
    void testStubbingOfException() {
        final Utils utils = mock(Utils.class);

        when(utils.getBook(anyString())).thenThrow(RuntimeException.class);

        final RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            utils.getBook("");
        });
        assertThat(exception.getClass(), is(equalTo(RuntimeException.class)));
    }

    @Test
    void testMethodFoo() {
        final Utils utils = mock(Utils.class);

        utils.getBook("Effective Java");
        utils.parseLibraryFrom(BookDAO.DEFAULT_PATH);

        verify(utils).parseLibraryFrom(BookDAO.DEFAULT_PATH);
        verify(utils).getBook("Effective Java");
    }

}