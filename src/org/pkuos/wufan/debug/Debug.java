package org.pkuos.wufan.debug;

/**
 * Created by marchon on 15/6/2.
 */
public class Debug {
    // Output Key Value.
    public static void o(String tag, Object value)
    {
        System.out.println(tag+" : "+value.toString());
    }
    // Overtake o function. Just output the value.
    public static void o(Object value)
    {
        System.out.println(value.toString());
    }
    // Test Memory.
    public static void m()
    {
        Debug.o("Current Total Memory", Runtime.getRuntime().totalMemory()/1024/1024);
        Debug.o("Current Max Memory",Runtime.getRuntime().maxMemory()/1024/1024);
    }
}
