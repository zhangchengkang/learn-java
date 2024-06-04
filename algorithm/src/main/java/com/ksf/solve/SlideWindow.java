package com.ksf.solve;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlideWindow {

    public static void main(String[] args) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("id","f4b24d58-0e58-4665-b0cf-bc5a49714275");
        map.put("unread_count" , 99);
        map.put("last_activity_time" , "2022-09-14T02:20:36.234028");
        map.put("last_activity_content" , "hello, can i help you. ");
        map.put("direction" , "inbound");
        map.put("message_type" , "TEXT");
        map.put("attachment_type" , "img/jpeg");


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(map);
//        System.out.println(jsonString);


    }

    //3 无重复字符串最大子串
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> windows = new HashMap<>();
        int left = 0;
        int right = 0;
        int len = 0;

        while (right < s.length()) {
            char next = s.charAt(right);
            right++;

            int temp = windows.getOrDefault(next, 0) + 1;
            windows.put(next, temp);
            if (temp == 1) {
                len = Math.max(len, right - left);
            }

            while (temp != 1 && left < right) {
                char pre = s.charAt(left);
                Integer integer = windows.get(pre) - 1;
                windows.put(pre, integer);
                if (pre == next) {
                    temp--;
                }

                left++;

            }
        }

        return len;

    }


    // 76 最小覆盖子串
    public String minWindow(String s, String t) {
        HashMap<Character, Integer> need = new HashMap<>();
        HashMap<Character, Integer> window = new HashMap<>();

        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int left = 0;
        int right = 0;
        int valid = 0;
        int start = 0;
        int len = Integer.MAX_VALUE;
        while (right < s.length()) {
            char cur = s.charAt(right);
            right++;

            if (need.containsKey(cur)) {
                window.put(cur, window.getOrDefault(cur, 0) + 1);
                if (window.get(cur).equals(need.get(cur))) {
                    valid++;
                }
            }

            while (valid == need.size()) {
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }

                char curLeft = s.charAt(left);
                left++;

                if (need.containsKey(curLeft)) {
                    if (window.get(curLeft).equals(need.get(curLeft))) {
                        valid--;
                    }
                    window.put(curLeft, window.get(curLeft) - 1);
                }
            }

        }

        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    // 438 找出字符串所以子串
    public static List<Integer> findAnagrams(String s, String p) {
        HashMap<Character, Integer> need = new HashMap<>();
        HashMap<Character, Integer> window = new HashMap<>();
        List<Integer> indexList = new ArrayList<>();

        for (Character c : p.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int left = 0;
        int right = 0;
        int valid = 0;

        while (right < s.length()) {
            char next = s.charAt(right);
            right++;

            if (need.containsKey(next)) {
                window.put(next, window.getOrDefault(next, 0) + 1);
                if (window.get(next).equals(need.get(next))) {
                    valid++;
                }
            }

            while (right - left == p.length()) {
                if (need.size() == valid) {
                    indexList.add(left);
                }

                char pre = s.charAt(left);
                left++;

                if (need.containsKey(pre)) {

                    if (window.get(pre).equals(need.get(pre))) {
                        valid--;
                    }
                    window.put(pre, window.get(pre) - 1);
                }
            }
        }

        return indexList;
    }


    // 567 字符串的排序
    public boolean checkInclusion(String s1, String s2) {
        HashMap<Character, Integer> need = new HashMap<>();
        HashMap<Character, Integer> window = new HashMap<>();
        for (char c : s1.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int left = 0;
        int right = 0;
        int valid = 0;

        while (right < s2.length()) {
            char cur = s2.charAt(right);
            right++;

            if (need.containsKey(cur)) {
                window.put(cur, window.getOrDefault(cur, 0) + 1);
                if (window.get(cur).equals(need.get(cur))) {
                    valid++;
                }
            }


            while (right - left == s1.length()) {
                if (valid == need.size()) return true;

                char curLeft = s2.charAt(left);
                left++;

                if (need.containsKey(curLeft)) {
                    if (window.get(curLeft).equals(need.get(curLeft))) {
                        valid--;
                    }
                    window.put(curLeft, window.get(curLeft) - 1);
                }
            }

        }

        return false;
    }


}
