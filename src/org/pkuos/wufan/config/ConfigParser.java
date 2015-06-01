package org.pkuos.wufan.config;

/**
 * Created by Marchon on 2015/6/1.
 */
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


/**
 * 这个类用于解析config.properties文件
 */
public class ConfigParser {

    private static Properties pro = new Properties();
    public ConfigParser(){}

    public static Object get_value(String Key)
    {
        ConfigParser.getProValue();
        return pro.get(Key);
    }

    public static void getProValue(){
        String filePath = "config.properties";
        InputStream in = null;
        try{
            in = new BufferedInputStream(new FileInputStream(filePath));
            pro.load(in);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}