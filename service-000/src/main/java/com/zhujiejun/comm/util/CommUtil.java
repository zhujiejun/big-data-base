package com.zhujiejun.comm.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.stream.IntStream;

public class CommUtil {
    public static int[] generate(int max) {
        return IntStream.rangeClosed(1, max)
                .map(i -> RandomUtils.nextInt(i, 1 << 15))
                .sorted()
                .toArray();
    }

    public static String convert(int[] nums) {
        return ArrayUtils.toString(nums);
    }

    public static void main(String[] args) {
        //Arrays.stream(generate(18)).forEach(System.out::println);
    }
}
