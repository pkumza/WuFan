package org.pkuos.wufan;

import org.pkuos.wufan.signature.KeyExtractor;

/**
 * Created by Marchon on 2015/6/1.
 */
public class testMain {
    public static void main(String[] argv)
    {
        System.out.println("我是主函数");

        System.out.println(KeyExtractor.get_MD5("G:\\3rsa\\Hello_Marc.Android.4.2.20140409053219.apk"));
    }
}
