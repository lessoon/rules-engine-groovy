package com.reda.engine.center;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能概述：
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-06-12 18-24
 * <p>Copyright: Copyright(c)2019 RedaFlight.com All Rights Reserved
 * @since JDK 1.7
 */
public class GroovyClassLoaderTest {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "a");
        map.put(1, "b");
        map.put(2, "c");

        ClassLoader cl = new GroovyClassLoaderTest().getClass().getClassLoader();
        GroovyClassLoader grvyCl = new GroovyClassLoader(cl);
        //将上面新建的Calculaotr.groovy类的代码放到grvyCalculatorFile的本地文件中
        String grvyClassConcent = readFileContent("/Users/leedu/Du-space/work/reda/rules-engine-groovy/engine-center/src/test/java/com/reda/engine/center/FindPolitDriveTime.groovy");
        Class groovyClass = grvyCl.parseClass(grvyClassConcent);
        try {
            GroovyObject grvyObj = (GroovyObject) groovyClass.newInstance();

            System.out.println("addStr=" + grvyObj.invokeMethod("addStr", map));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static String readFileContent(String path) {
        StringBuffer stringBuffer = new StringBuffer();
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        try (FileReader reader = new FileReader(path);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            //网友推荐更加简洁的写法
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
                // 一次读入一行数据
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }


}

