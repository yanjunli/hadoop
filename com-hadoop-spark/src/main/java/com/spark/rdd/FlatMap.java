package com.spark.rdd;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 将所有List里面的数据按照规则如“，”切分，返回一个另一个rdd，详细代码如下
 */
public class FlatMap
{
//    public static void main(String[] args)
//    {
//        init();
//    }

    private static void init()
    {
        SparkConf conf = new SparkConf();
        conf.setAppName("binend flatMap");
        conf.setMaster("local");

        JavaSparkContext jsc = new JavaSparkContext(conf);
        //jsc.addJar("D:\\work\\intellij_20151110\\spark\\com-hadoop-spark\\target\\com-hadoop-spark-1.0-SNAPSHOT.jar");

        List<String> list = new ArrayList<String>();

        for (int x = 0; x < 10; x++)
        {
            String s = "a" + x + "," + System.currentTimeMillis();
            System.out.println(s);
            list.add(s);
        }

        JavaRDD<String> rdd = jsc.parallelize(list);
        JavaRDD<String> flatMap = rdd.flatMap(new FlatMapFunction<String, String>()
        {
            public Iterator<String> call(String s) throws Exception
            {
                System.out.println(">>>>>>>>" + s);
                return Arrays.asList(s.split(",")).iterator();
            }
        });

        List<String> rddList = flatMap.top(3);
        for (String s : rddList)
        {
            System.out.println(s);
        }


    }
}
