package org.gfg.bst;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.gfg.Dictionary;

/**
 * Implements {@link Dictionary} abstraction using an AVL tree.
 * 
 * @param <K> type of keys
 * @param <V> type of values
 */
public class TreeDictionary<K extends Comparable<K>, V> implements Dictionary<K, V> {

    private class KeyValuePair implements Comparable<KeyValuePair> {
        @Override
        public int compareTo(TreeDictionary<K, V>.KeyValuePair pair) {
            return this.key.compareTo(pair.key);
        }

        public K key;
        public V value;

        public KeyValuePair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private AvlTree<KeyValuePair> tree = new AvlTree<>();

    @Override
    public boolean containsKey(K key) {
        return tree.contains(new KeyValuePair(key, null));
    }

    private void ensureKeyExists(K key) {
        if (!containsKey(key)) {
            throw new IllegalArgumentException(String.format("Key '%s' does not exist in the dictionary.", key));
        }
    }

    @Override
    public V delete(K key) {
        ensureKeyExists(key);
        KeyValuePair pair = new KeyValuePair(key, null);
        V value = tree.ceil(pair).value;
        tree.remove(pair);
        return value;
    }

    @Override
    public void add(K key, V value) {
        tree.add(new KeyValuePair(key, value));
    }

    @Override
    public V get(K key) {
        ensureKeyExists(key);
        return tree.ceil(new KeyValuePair(key, null)).value;
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public List<K> keys() {
        return tree.toList().stream()
                            .map(p -> p.key)
                            .collect(Collectors.toList());
    }
}