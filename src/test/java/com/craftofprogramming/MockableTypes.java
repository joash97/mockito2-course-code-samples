package com.craftofprogramming;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.exceptions.base.MockitoException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Mockito 2 limitations:
 *
 * 1) Can't mock static methods
 * 2) Can't mock constructors
 * 3) Can't mock equals(), hashCode()
 * 4) Can't mock final methods/type
 * 5) Can't mock private methods
 *
 */
class MockableTypes {


    @Test
    void testFinalClass() {
        // final class
        final MockitoException exception = assertThrows(MockitoException.class, () -> {
            mock(FinalClass.class);
        });
        assertThat(exception.getClass(), is(equalTo(MockitoException.class)));
    }

    @Test
    void testInterface() {
        // interface
        final MyInterface myInterface = mock(MyInterface.class);
        myInterface.defaultMethod();
        myInterface.abstractMethod();
        verify(myInterface).defaultMethod();
        verify(myInterface).abstractMethod();
    }

    @Test
    void testConcreteClass() {
        final ConcreteClass concreteClass = mock(ConcreteClass.class);
        concreteClass.instanceMethod();
        verify(concreteClass).instanceMethod();
        verify(concreteClass).finalMethod();
        verify(concreteClass).privateMethod();
    }

    @Test
    void testAbstractClass() {
        final AbstractClass abstractClass = mock(AbstractClass.class);
        abstractClass.concreteMethod();
        verify(abstractClass).concreteMethod();

        when(abstractClass.abstractMethod()).thenReturn(42);
    }

    /**
     * Inner types declarations
     */
    static final class FinalClass {
        private void foo() {
            System.out.println("MyFinalClass.foo");
        }
    }

    abstract class AbstractClass {
        void concreteMethod() {
            System.out.println("AbstractClass.concreteMethod");
        }
        abstract int abstractMethod();
    }

    static class ConcreteClass {
        void instanceMethod() {
            System.out.println("ConcreteClass.instanceMethod");
        }
        final void finalMethod() {
            System.out.println("ConcreteClass.finalMethod");
        }
        static void staticMethod() {
            System.out.println("ConcreteClass.staticMethod");
        }
        static void staticMethod2() {
            System.out.println("ConcreteClass.staticMethod2");
        }
        private void privateMethod() {
            System.out.println("ConcreteClass.privateMethod");
        }
    }

    interface MyInterface {
        default void defaultMethod() {
            System.out.println("myInterface.defaultMethod");
        }
        void abstractMethod();
        static void staticMethod() {
            System.out.println("myInterface.staticMethod");
        }
    }

}
