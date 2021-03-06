package com.titanic.hive;

import org.apache.hadoop.hive.ql.CommandNeedRetryException;

import java.io.IOException;
import java.util.Map;

/**
 * 代码调用示例
 */
public class HqlExecutable2
{
    private static String esIndex = "test_bin_es";
    private static String esType = "test_bin_es_type";
    private static String hiveToEsTable = "test_bin_es";

    public static void main(String[] args)
    {



    }

    public static void InsertHiveToEs()
    {
        HiveClient hiveClient = new HiveClient("/home/titanic/soft/hadoop/hive-2.0.0/conf/hive-site.xml");
        String hql = "INSERT OVERWRITE TABLE test_bin_es  SELECT id, name,age,tel FROM test_bin ";
        try
        {
            hiveClient.executeCmdHQL(hql);
        } catch (CommandNeedRetryException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static void createHiveForEs(Map<String, String> map,String hiveToEsTable,String mappingEsIndexTYpe)
    {

        String fields = createHive2EsTableHql(map);

        String hql = "CREATE EXTERNAL TABLE "+hiveToEsTable+"  ( " + fields + " )     \n" +
                "STORED BY 'org.elasticsearch.hadoop.hive.EsStorageHandler'   \n" +
                "TBLPROPERTIES('es.resource' = '"+mappingEsIndexTYpe+"','es.nodes'='192.9.7.4')";

        HiveClient hiveClient = new HiveClient("/home/titanic/soft/hadoop/hive-2.0.0/conf/hive-site.xml");

        try
        {
            hiveClient.executeCmdHQL(hql);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    public static String createHive2EsTableHql(Map<String, String> map)
    {
        StringBuffer sb = new StringBuffer();

        for (Map.Entry<String, String> entry : map.entrySet())
        {
            sb.append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
        }
        String str = sb.toString();
        str = str.substring(0, str.toString().length() - 1);
        System.out.println(str);
        return str;

    }

}
