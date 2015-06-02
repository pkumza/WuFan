package org.pkuos.wufan;

import org.pkuos.wufan.signature.KeyExtractor;

/**
 * Created by Marchon on 2015/6/1.
 */
public class testMain {
    public static void main(String[] argv)
    {
        System.out.println("我是主函数");

        System.out.println(KeyExtractor.get_MD5("/Users/marchon/Downloads/lanzi.apk"));//bza

    }
}
