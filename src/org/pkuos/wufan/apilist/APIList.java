package org.pkuos.wufan.apilist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Marchon on 2015/6/2.
 */
public class APIList {
    public String [] apis = new String[40000];
    private void load_api_list()
    {
        File allmappings = new File("jellybean_allmappings.txt");
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(allmappings));
            String tempString = null;
            int line = 0;
            while((tempString = reader.readLine()) != null)
            {
                if (tempString.charAt(0) == '<')
                {
                    String class_string = tempString.split(": ")[0];
                    String method_string = tempString.split(":")[1].split(">")[0];
                }

            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
