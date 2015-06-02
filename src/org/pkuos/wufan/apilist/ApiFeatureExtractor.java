package org.pkuos.wufan.apilist;

import org.pkuos.wufan.config.StaticConfig;
import org.pkuos.wufan.debug.Debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by marchon on 15/6/2.
 * 用来提取api特征。
 * 重点在于一个函数。get_api_map
 * 这个函数，接受 smali directory root
 * 返回一个list
 * list的index代表api的种类，value代表频次。
 */
public class ApiFeatureExtractor {
    private String [] libs = new String[249];
    public int[] api_list = new int[StaticConfig.API_NUM_MAX];
    private APIList apiList = new APIList();

    public ApiFeatureExtractor(){
        load_lib_list();
    }
    private void load_lib_list(){
        File TPL = new File("Third-party_library.txt");
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(TPL));
            String tempString = null;
            int line = 0;
            while((tempString = reader.readLine()) != null)
            {
                //System.out.println(line);
                String library_name = tempString.split("\t")[0];
                libs[line++] = library_name;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public int api_index(String api)
    {
        if(apiList.apis.length <= 0)
        {
            System.out.println("API 列表 存在重大问题。");
            return -2;
        }
        int begin = 0;
        int end =apiList.apis.length-1;
        while(begin <= end)
        {
            int mid = (begin+end)/2;
            if(api.equals(apiList.apis[mid]))
            {
                return mid;
            }
            else if (api.compareTo(apiList.apis[mid]) > 0)
            {
                // 往右边搜
                begin = mid + 1;
            }
            else
            {
                // 往左边搜
                end = mid - 1;
            }
        }
        return -1;
    }

    public void get_api_map(String decoded_path)
    {

        File root = new File(decoded_path);
        File[] files = root.listFiles();
        for(File file: files)
        {

            // System.out.println("files ：　"+ file.getAbsolutePath());
            if(file.isDirectory())
            {
                boolean not_lib = true;
                for(String lib: libs)
                {
                    if(file.getName().toLowerCase().contains(lib.toLowerCase()))
                    {
                        not_lib = false;
                        //System.out.println(lib);
                        break;
                    }
                }
                if(not_lib != false) // 如果不是库，进行递归查询。是库就不理了。
                {
                    get_api_map(file.getPath());
                }
            }
            else
            {
                // 如果是文件，那么就进行读取。
                BufferedReader reader = null;
                try{
                    reader = new BufferedReader(new FileReader(file));
                    String tempString = null;
                    int line = 0;
                    Pattern pattern = Pattern.compile(".*invoke-.*Landroid/.+?;->.*");
                    Pattern pattern2 = Pattern.compile(".*invoke-.*Lcom/android/.+?;->.*");
                    Pattern pattern3 = Pattern.compile(".*invoke-.*Lcom/google/.+?;->.*");
                    Pattern pattern4 = Pattern.compile(".*invoke-.*Ljava/net/.+?;->.*");
                    Pattern pattern5 = Pattern.compile(".*invoke-.*Lorg/apache/http/impl/client/DefaultHttpClient.+?;->.*");

                    while((tempString = reader.readLine()) != null)
                    {
                        //System.out.println(line);
/*
                        if(tempString.trim().contains("Landroid")||
                                tempString.trim().contains("Lcom/android") ||
                                tempString.trim().contains("Ljava")||
                                tempString.trim().contains("Lcom/google"))
                        {*/

                        Matcher m = pattern.matcher(tempString);
                        if(m.matches())
                        {
                            String api_string = tempString.substring(tempString.indexOf("Landroid"),
                                    tempString.indexOf('('));
                            int string_index = api_index(api_string);
                            if(string_index >= 0)
                            {
                                api_list[string_index]++;
                            }
                            continue;
                        }

                        m = pattern2.matcher(tempString);
                        if(m.matches())
                        {
                            String api_string = tempString.substring(tempString.indexOf("Lcom"),
                                tempString.indexOf('('));
                            int string_index = api_index(api_string);
                            if(string_index >= 0)
                            {
                                //Debug.o("DDDDDD",tempString);
                                // 暂时不太明白，可能Lcom/android 很少，至少我在Landroid.jar中并没有找到相关的类。样例程序99个api，没有一个是Lcom
                                api_list[string_index]++;
                            }
                            continue;
                        }

                        m = pattern3.matcher(tempString);
                        if(m.matches())
                        {
                            String api_string = tempString.substring(tempString.indexOf("Lcom"),
                                    tempString.indexOf('('));
                            int string_index = api_index(api_string);
                            if(string_index >= 0)
                            {
                                api_list[string_index]++;
                            }
                            continue;
                        }

                        m = pattern4.matcher(tempString);
                        if(m.matches())
                        {
                            String api_string = tempString.substring(tempString.indexOf("Ljava"),
                                    tempString.indexOf('('));
                            int string_index = api_index(api_string);
                            if(string_index >= 0)
                            {
                                api_list[string_index]++;
                            }
                            continue;
                        }

                        m = pattern5.matcher(tempString);
                        if(m.matches())
                        {
                            String api_string = tempString.substring(tempString.indexOf("Lorg"),
                                    tempString.indexOf('('));
                            int string_index = api_index(api_string);
                            if(string_index >= 0)
                            {
                                api_list[string_index]++;
                            }
                            continue;
                        }
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return ;
    }
    public static void main(String[ ] args)
    {
        ApiFeatureExtractor e = new ApiFeatureExtractor();
        e.get_api_map("decoded/lanzi.apk");
        int sum = 0;
        for(int i = 0 ; i < StaticConfig.API_NUM+10; i++){
            sum += e.api_list[i];
            //System.out.println(e.api_list[i]);
        }
        System.out.println(sum);
    }
}
