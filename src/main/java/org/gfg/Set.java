package org.gfg;

import java.util.*;

/**
 * Represents a set abstraction - a collection of distinct items.
 * @param <T> type of elements stored in the set
 */
public interface Set<T> extends Iterable<T>{
    /**
     * Returns a number of items contained in this set.
     * @return size of this set
     */
    public int size();

    /**
     * Returns true if this set is empty.
     * @return {@code true} if this set contains no elements 
     */
    public boolean isEmpty();

    /**
     * Adds an item to this set. If the item is already present in the set, it is overwritten.
     * @param item item to be added
     */
    public void add(T item);

    /**
     * Checks for set membership.
     * @param item item to look up in the set
     * @return true if the item is present in the set
     */
    public boolean contains(T item);

    /**
     * Removes a supplied item from this set. If the item is not present in the set,
     * this operation has no effect.
     * @param item item to delete from the set
     */
    public void remove(T item);

    /**
     * Returns an iterator over elements in this set in arbitrary order.
     * @return iterator supplying elements in arbitrary order
     */
    @Override
    public Iterator<T> iterator();

    /**
     * Copies elements in this set to a list. Order of elements is determined by the iterator.
     * @return a list consisting of the elements in the set
     */
    public List<T> toList();
}