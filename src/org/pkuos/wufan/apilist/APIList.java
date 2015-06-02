package org.pkuos.wufan.apilist;

import com.sun.javafx.binding.StringFormatter;
import org.pkuos.wufan.config.StaticConfig;
import org.pkuos.wufan.debug.Debug;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Marchon on 2015/6/2.
 */
public class APIList {
    public String [] redundant_apis = new String[StaticConfig.API_NUM_MAX];
    public String [] apis = new String[StaticConfig.API_NUM];
    /**
        把jellybean_allmapping.txt 文件的内容，放入到String[] apis 中。并且index就作为编号。
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
            reader = new BufferedReader(new FileReader(allmappings));
            String tempString = null;
            //Debug.m();
            int line = 0;
            while((tempString = reader.readLine()) != null)
            {
                if (tempString.charAt(0) == '<')
                {
                    String class_string = tempString.split(": ")[0];
                    String method_string = tempString.substring(tempString.indexOf(":")+2 ,tempString.lastIndexOf(">"));
                    //Debug.o("class_string", class_string);
                    //Debug.o("m_string", method_string);
                    String Lapi = class_string.replace('<', 'L').replace('.', '/');
                    String Lmethod = method_string.substring(method_string.indexOf(' ')+1, method_string.indexOf('('));
                    //Debug.o("Lapi", Lapi);
                    //Debug.o("Lmethod", Lmethod);
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
                    redundant_apis[cnt++] = Combined_API;
                }

            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        //Debug.m();
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
