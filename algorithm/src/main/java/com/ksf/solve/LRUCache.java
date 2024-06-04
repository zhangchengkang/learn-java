package com.ksf.solve;

import java.util.HashMap;

public class LRUCache {
    class Node {
        public int key;
        public int value;
        public Node next;
        public Node pre;
    }

    private int size;
    private int capacity;
    private Node head;
    private Node tail;
    private HashMap<Integer, Node> map;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.head = new Node();
        this.tail = new Node();
        head.next = tail;
        tail.pre = head;
        this.map = new HashMap<>(capacity);
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }

        moveToHead(node);

        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node == null) {
            Node newNode = new Node();
            newNode.key = key;
            newNode.value = value;

            map.put(key, newNode);
            addToHead(newNode);

            size++;
            if (size > capacity) {
                Node last = removeLast();
                map.remove(last.key);
                size--;
            }
        } else {
            node.value = value;
            moveToHead(node);
        }
    }

    private Node removeLast() {
        Node pre = tail.pre;
        Node prePre = pre.pre;

        prePre.next = tail;
        tail.pre = prePre;

        return pre;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }

    private void addToHead(Node node) {
        Node next = head.next;

        head.next = node;
        node.next = next;

        node.pre = head;
        next.pre = node;
    }

    private void removeNode(Node node) {
        Node next = node.next;
        Node pre = node.pre;

        pre.next = next;
        next.pre = pre;
    }

}
