package org.pkuos.wufan.apilist;

import com.sun.javafx.binding.StringFormatter;
import org.pkuos.wufan.config.StaticConfig;
import org.pkuos.wufan.debug.Debug;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Marchon on 2015/6/2.
 * 这是一个简单的辅助。
 * redundant_apis是直接从jellybean_allmappings.txt中读取的内容
 * 但是实际上，这个内容有很多的重复。因为这个txt是按照权限来分配的。比如有一个api有对应好几个权限，
 * 那么该api就会在这个txt中出现很多遍。
 * 去重复之后就变成了apis。去重复的方法是，先排序，然后用一个游标进行读取，放入新的列表apis中。
 */
public class APIList {
    public String [] redundant_apis = new String[StaticConfig.API_NUM_MAX];
    public String [] apis = new String[StaticConfig.API_NUM];

    /**
        在初始化时，把jellybean_allmapping.txt 文件的内容，放入到String[] redundant_apis 中。并且index就作为编号。
        然后要进行去重复，放入apis。
     */
    public APIList(){
        load_api_list();
    }

    private void load_api_list()
    {
        File allmappings = new File("jellybean_allmappings.txt");
        BufferedReader reader = null;
        int cnt = 0;
        try{
            // 按照行读取。
            reader = new BufferedReader(new FileReader(allmappings));
            String tempString = null;
            // Debug.m() 测试一下内存，发现并没有太大影响。
            // Debug.m();
            int line = 0;
            while((tempString = reader.readLine()) != null)
            {
                if (tempString.charAt(0) == '<')
                {
                    String class_string = tempString.split(": ")[0];    // 类那边的原始string
                    //  方法的原始string
                    String method_string = tempString.substring(tempString.indexOf(":")+2 ,tempString.lastIndexOf(">"));
                    //  类，格式转化，把<android.**.**.**转化为Landroid/**/**/**
                    String Lapi = class_string.replace('<', 'L').replace('.', '/');
                    //  方法，格式转化。切割。
                    String Lmethod = method_string.substring(method_string.indexOf(' ')+1, method_string.indexOf('('));
                    //  合并为smali中的模样。
                    String Combined_API = StringFormatter.format("%s;->%s",Lapi, Lmethod).getValue();
                    //Debug.o("Combined_API", Combined_API);
                    /*
                    if(Lapi.equals("Landroid/accounts/AccountManager$1"))
                    {
                        Debug.o("class_string", class_string);
                        Debug.o("m_string", method_string);
                        Debug.o("LAPI",Lapi);
                        Debug.o("LM",Lmethod);
                        Debug.o("","");

                    }*/
                    // Put it into redundant_api Array。
                    redundant_apis[cnt++] = Combined_API;
                }

            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        //Debug.m();
        //  去重方法，先排序，然后用游标遍历一遍。
        Arrays.sort(redundant_apis);
        int cursor = 0;
        for(int i = 0; i < StaticConfig.API_NUM_MAX; i++, cursor++)
        {
            apis[cursor] = redundant_apis[i];
            while(i+1 < StaticConfig.API_NUM_MAX && redundant_apis[i+1].equals(apis[cursor]))
            {
                i++;
            }
        }

        // 下面被注释掉的内容是，把apis数据输出到 apis.txt
        /*

        File fout_apis = new File("apis.txt");
        FileWriter fw = null;
        BufferedWriter writer = null;
        try{
            fw = new FileWriter(fout_apis);
            writer = new BufferedWriter(fw);
            for(int i = 0; i < StaticConfig.API_NUM; i++)
            {
                writer.write(String.valueOf(i));
                writer.write('\t');
                writer.write(apis[i]);
                writer.newLine();
            }

        }catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try{
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(int u= 0; u < cursor; u++)
        {
            System.out.println(apis[u]);

        }
        Debug.o("Cursor", cursor);*/
    }

}
