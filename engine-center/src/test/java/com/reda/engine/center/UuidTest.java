package com.reda.engine.center;

import java.util.UUID;

/**
 * 功能概述：
 *
 * @author Lidu
 * @version 1.0
 * <p>Date: 2020-06-13 20-13
 * <p>Copyright: Copyright(c)2019 RedaFlight.com All Rights Reserved
 * @since JDK 1.7
 */
public class UuidTest {
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().trim().replace("-", ""));
    }
}
