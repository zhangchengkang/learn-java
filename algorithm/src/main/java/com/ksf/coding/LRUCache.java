package com.ksf.coding;

import java.util.HashMap;
import java.util.Map;

/**
 * 请你设计并实现一个满足LRU(最近最少使用)的缓存。
 * <p>
 * 实现 LRUCache 类：
 * LRUCache(int capacity) 以正整数作为容量 capacity 初始化LRU缓存
 * int get(int key) 如果关键字key存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value)
 * 如果关键字 key 已经存在，则变更其数据值 value ；如果不存在，则向缓存中插入该组 key-value 。
 * 如果插入操作导致关键字数量超过 capacity ，则应该逐出最久未使用的关键字。
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 * <p>
 * public class LRUCache {
 * <p>
 * class DLinkedNode {
 * int key;
 * int value;
 * DLinkedNode prev;
 * DLinkedNode next;
 * public DLinkedNode() {}
 * public DLinkedNode(int _key, int _value) {key = _key; value = _value;}
 * }
 * <p>
 * public LRUCache(int capacity) {
 * <p>
 * }
 * <p>
 * public int get(int key) {
 * <p>
 * }
 * <p>
 * public void put(int key, int value) {
 * <p>
 * }
 * }
 */
public class LRUCache {

    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;

        public DLinkedNode() {
        }

        public DLinkedNode(int _key, int _value) {
            key = _key;
            value = _value;
        }
    }

    int capacity;
    int size;
    HashMap<Integer, DLinkedNode> map;
    DLinkedNode head;
    DLinkedNode tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.map = new HashMap<>();
        this.head = new DLinkedNode();
        this.tail = new DLinkedNode();

        this.head.next = this.tail;
        this.tail.prev = this.head;
    }


    public int get(int key) {
        DLinkedNode dLinkedNode = map.get(key);
        if (dLinkedNode == null) return -1;

        moveToHead(dLinkedNode);
        return dLinkedNode.value;
    }

    public void put(int key, int value) {
        DLinkedNode dLinkedNode = map.get(key);

        if (dLinkedNode == null) {
            DLinkedNode newNode = new DLinkedNode(key, value);
            addToHead(newNode);
            map.put(key, newNode);
            size++;

            if (size > capacity) {
                int removedKey = removeTail();
                map.remove(removedKey);
            }
        } else {
            dLinkedNode.value = value;

            map.put(key, dLinkedNode);
            moveToHead(dLinkedNode);
        }

    }

    private int removeTail() {
        DLinkedNode prev = tail.prev;
        DLinkedNode prePrev = tail.prev.prev;

        prePrev.next = tail;
        tail.prev = prePrev;

        size--;
        return prev.key;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private void addToHead(DLinkedNode node) {
        DLinkedNode next = head.next;

        head.next = node;
        node.prev = head;

        node.next = next;
        next.prev = node;
    }

    private void removeNode(DLinkedNode node) {
        DLinkedNode prev = node.prev;
        DLinkedNode next = node.next;

        prev.next = next;
        next.prev = prev;
    }
}


