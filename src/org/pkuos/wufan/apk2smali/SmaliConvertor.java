package org.pkuos.wufan.apk2smali;

import org.pkuos.wufan.config.ConfigParser;

import java.io.*;

/**
 * Created by Marchon on 2015/6/1.
 */
public class SmaliConvertor {

    private String [] libs = new String[249];
    private String apk_path = null;

    public SmaliConvertor(){
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

    private void delete_lib(String file_path){
        File root = new File(file_path);
        // System.out.println("delete_lib : " + root.getAbsolutePath());

        File[] files = root.listFiles();
        for(File file: files)
        {
            // System.out.println("files ：　"+ file.getAbsolutePath());
            if(file.isDirectory())
            {
                // 如果是文件夹，那么尽管递归好了。
                delete_lib(file.getPath());
            }
            else
            {
                // 如果是文件，那么就删除。
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
                if(not_lib == false) // 如果是库
                {
                    //System.out.println(file.getAbsolutePath());
                    file.delete();
                }
            }
        }
    }

    // Return apk_name.
    public String convert(String _apk_path)
    {
        apk_path = _apk_path;
        // 计算出命令内容
        String Command = this.getClass().getResource("/").getPath();
        //System.out.println(Command);
        int path_index = apk_path.lastIndexOf('/')+1;
        if( path_index <  apk_path.lastIndexOf('\\')+1)
            path_index = apk_path.lastIndexOf('\\')+1;
        if(!(apk_path.contains("\\") || apk_path.contains("/")))
            path_index = 0;
        String original_apk_name = apk_path.substring(path_index);
        String apk_name = original_apk_name;
        int copy = 1;
        File apk_decoded_dir = new File("decoded/"+apk_name);
        while(apk_decoded_dir.exists())
        {
            copy += 1;
            apk_name = original_apk_name + String.valueOf(copy);
            apk_decoded_dir = new File("decoded/"+apk_name);
        }
        String os = ConfigParser.get_value("os").toString();
        if(os.equals("mac"))
            Command += "org/pkuos/wufan/apk2smali/apktool d "+apk_path+ " -o decoded/" + apk_name;
        else if(os.equals("windows"))
            Command += "org/pkuos/wufan/apk2smali/apktool2.bat d "+apk_path+ " -o decoded/" + apk_name;
        else if(os.equals("linux"))
            Command += "org/pkuos/wufan/apk2smali/apktool d "+apk_path+ " -o decoded/" + apk_name;
        else
        {
            System.out.println("Unrecognized Operating System.");
        }
        System.out.println(Command);
        // 命令行运行
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try{
            Process p = Runtime.getRuntime().exec(Command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = br.readLine()) != null){
                sb.append(line+'\n');
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(br != null)
            {
                try{
                    br.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(sb);
        //System.out.println(apk_name);
        delete_lib("decoded/"+apk_name+"/smali");
        return apk_name;
    }


    public static void main(String [] args)
    {
        // Main Function Just For Test.
        SmaliConvertor sc = new SmaliConvertor();
        String u = sc.convert("/Users/marchon/Downloads/lanzi.apk");
        System.out.println(u);
    }
}
