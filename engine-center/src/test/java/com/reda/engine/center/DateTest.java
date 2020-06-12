package com.reda.engine.center;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能概述：
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-06-12 11-03
 * <p>Copyright: Copyright(c)2019 RedaFlight.com All Rights Reserved
 * @since JDK 1.7
 */
public class DateTest extends Thread {

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String name;
    private String dateStr;

    public DateTest(String name, String dateStr) {
        this.name = name;
        this.dateStr = dateStr;
    }

    @Override
    public void run() {

        try {
            Date date = sdf.parse(dateStr);
//            Date date = df.get().parse(dateStr);
            System.out.println(name + ": date:" + date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new DateTest("A", "2017-06-10"));
        executorService.execute(new DateTest("B", "2016-06-06"));
        executorService.shutdown();
    }
}
