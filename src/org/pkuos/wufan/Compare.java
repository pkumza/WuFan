package org.pkuos.wufan;

import org.pkuos.wufan.apilist.ApiFeatureExtractor;
import org.pkuos.wufan.apk2smali.SmaliConvertor;
import org.pkuos.wufan.config.StaticConfig;
import org.pkuos.wufan.signature.KeyExtractor;

/**
 * Compare two apks.
 * Created by marchon on 15/6/2.
 */
public class Compare {

    private String apk1_path;
    private String apk2_path;
    private String apk1_decoded_name;
    private String apk2_decoded_name;

    public double get_similarity(String _path1, String _path2)
    {
        apk1_path = _path1;
        apk2_path = _path2;

        // 比较作者的签名
        String apk1_md5 = KeyExtractor.get_MD5(apk1_path);
        String apk2_md5 = KeyExtractor.get_MD5(apk2_path);
        if(apk1_md5.equals(apk2_md5))
            return -1.0;

        SmaliConvertor sc = new SmaliConvertor();
        apk1_decoded_name = sc.convert(apk1_path);
        apk2_decoded_name = sc.convert(apk2_path);

        ApiFeatureExtractor afe1 = new ApiFeatureExtractor("decoded/"+apk1_decoded_name);
        ApiFeatureExtractor afe2 = new ApiFeatureExtractor("decoded/"+apk2_decoded_name);

        int sum_1_2 = 0;
        int diff_1_2 = 0;
        for(int i = 0; i < StaticConfig.API_NUM; i++)
        {
            sum_1_2 += afe1.api_list[i];
            sum_1_2 += afe2.api_list[i];
            diff_1_2 += Math.abs(afe1.api_list[i]-afe2.api_list[i]);
        }
        return 1.0-((double)diff_1_2)/(double)sum_1_2;
    }

    public static void main(String [] args)
    {
        // For Test.
        Compare c = new Compare();
        double sim = c.get_similarity("/Users/marchon/Downloads/lanzi.apk","/Users/marchon/Downloads/lanzi.apk");
        System.out.println("Similarity : "+sim);
    }
}
