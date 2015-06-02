package org.pkuos.wufan.signature;

/**
 * Created by Marchon on 2015/6/1.
 */
import org.pkuos.wufan.config.ConfigParser;

import java.io.*;
import java.util.zip.*;


public class KeyExtractor {
    private String apk_path;
    private void set_apk_path(String _path){
        apk_path = _path;
    }
    private String decompress(){
        try {
            ZipInputStream Zin = new ZipInputStream(new FileInputStream(apk_path));
            BufferedInputStream Bin = new BufferedInputStream(Zin);
            File fout = null;
            ZipEntry entry;
            try{
                String name = null;
                while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()) {
                    name = entry.getName();
                    //System.out.println("Tag1:"+name.substring(name.length()-4, name.length()));
                    if (!(name.substring(name.length() - 4).equals(".DSA") ||
                            name.substring(name.length() - 4).equals(".RSA")) ||
                            !name.substring(0, 8).equals("META-INF")) {
                        continue;
                    }
                    fout = new File("keys", name.substring(9)); // Choose 9 because "META-INF".length = 8
                    if (!fout.exists()) {
                        (new File(fout.getParent())).mkdirs();
                    }
                    FileOutputStream out = new FileOutputStream(fout);
                    //System.out.println(entry.getName());
                    BufferedOutputStream Bout = new BufferedOutputStream(out);
                    int b;
                    while ((b = Bin.read()) != -1) {
                        Bout.write(b);
                    }
                    Bout.close();
                    out.close();
                    break;
                }
                Bin.close();
                Zin.close();
                return name;
                //System.out.println(ConfigParser.get_value("jdk_home"));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "Error";
    }

    private String extract_key(String RSA_path)
    {
        // 计算出命令内容
        String Command = ConfigParser.get_value("jdk_home").toString();
        Command += "/bin/keytool -printcert -file keys/" + RSA_path.substring(9);
        //System.out.println(Command);
        // 命令行运行
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try{
            Process p = Runtime.getRuntime().exec(Command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while((line = br.readLine()) != null){
                if(line.contains("MD5"))
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
        return sb.toString();
    }

    public void delete_RSA(String RSA_path)
    {
        File rsa = new File("keys\\"+RSA_path.substring(9));
        //System.out.println(rsa.getPath());
        if(rsa.exists()) {
            //System.out.println(rsa.getPath());
            rsa.delete();
        }
    }

    public static String get_MD5(String apk_path)
    {
        KeyExtractor ke = new KeyExtractor();
        ke.set_apk_path(apk_path);
        String rsa_path = ke.decompress();
        String MD5 = ke.extract_key(rsa_path).trim();
        ke.delete_RSA(rsa_path);
        return MD5;
    }

    public static void main(String[] args)
    {
        System.out.println("Main Function Starts.");
        System.out.println(get_MD5("G:\\3rsa\\Hello_Marc.Android.4.2.20140409053219.apk"));
        System.out.println("Main Function Ends.");
    }
}
