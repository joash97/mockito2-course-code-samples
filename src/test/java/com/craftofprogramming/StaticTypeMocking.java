package com.craftofprogramming;

import com.craftofprogramming.MockableTypes.ConcreteClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConcreteClass.class)
public class StaticTypeMocking {

    @Test
    void testMethod() {
        PowerMockito.verifyStatic(ConcreteClass.class); // 1
        ConcreteClass.staticMethod();
        ConcreteClass.staticMethod();
//        ConcreteClass.staticMethod2();
    }

}
