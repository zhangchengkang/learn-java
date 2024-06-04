package com.ksf.coding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {

    }

    public static List<Integer[]> twoSum(int[] arr, int target) {
        List<Integer[]> result = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

        for (int num : arr) {
            int temp = target - num;
            if (set.contains(temp)) {
                result.add(new Integer[]{num, temp});
            }
            set.add(num);
        }

        return result;
    }

    /**
     * 4.现有一个IP库文件，格式如下(以逗号分隔)：
     * 起始ip,结束ip,国家名
     * 其中起始ip和结束ip定义了一个ip段，这个ip段中的ip都属于后面的国家名
     * IP库文件示例如下：
     * 0.0.0.0,1.5.7.8,CN
     * 1.5.7.9,2.255.1.39,US
     * 2.255.1.40,5.2.255.255,CN
     * …
     * 4.1 为了方便处理，请实现一个函数，将ip转化为整数(已知ip格式为.分隔的4个整数，每个整数的取值范围是0-255)，并且需要保持ip之间的大小关系
     */
    public static Long transferIp(String ip) {
        String[] ipList = ip.split("\\.");

        Long result = 0L;
        for (int i=0; i<ipList.length;i++) {
            result+= ipList[i] * Math.pow(10D,Double.parseDouble((3-i).toString()));
        }

        return result;
    }
}

