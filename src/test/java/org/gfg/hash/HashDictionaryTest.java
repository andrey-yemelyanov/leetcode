package org.gfg.hash;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.gfg.Dictionary;
import static org.hamcrest.Matchers.*;

public class HashDictionaryTest{
    @Test
    public void getKeys(){
        Dictionary<Integer, String> dict = new HashDictionary<>();
        dict.add(1, "One");
        dict.add(2, "Two");
        dict.add(3, "Three");
        dict.add(4, "Four");
        dict.add(5, "Five");
        assertThat(dict.keys().stream()
                              .sorted()
                              .collect(Collectors.toList()), 
                    is(Arrays.asList(1, 2, 3, 4, 5)));
    }

    @Test
    public void testAddAndContains(){
        Dictionary<Integer, String> dict = new HashDictionary<>();
        assertThat(dict.containsKey(1), is(false));
        dict.add(1, "One");
        assertThat(dict.containsKey(1), is(true));
        assertThat(dict.get(1), is("One"));
        dict.add(1, "ONE");
        assertThat(dict.containsKey(1), is(true));
        assertThat(dict.get(1), is("ONE"));
        assertThat(dict.size(), is(1));
        assertThat(dict.containsKey(2), is(false));
    }

    @Test
    public void testRemoveAndContains(){
        Dictionary<Integer, String> dict = new HashDictionary<>();
        assertThat(dict.containsKey(1), is(false));
        dict.add(1, "One");
        dict.add(2, "Two");
        dict.add(3, "Three");
        assertThat(dict.containsKey(1), is(true));
        assertThat(dict.containsKey(2), is(true));
        assertThat(dict.containsKey(3), is(true));
        assertThat(dict.size(), is(3));
        dict.delete(1);
        assertThat(dict.containsKey(1), is(false));
        assertThat(dict.size(), is(2));
        dict.delete(2);
        assertThat(dict.containsKey(2), is(false));
        assertThat(dict.size(), is(1));
    }

    @Test
    public void testBigData(){
        Dictionary<Integer, Integer> squares = new HashDictionary<>();
        final int MAX = 1000000;
        for(int i = 0; i < MAX; i++) squares.add(i, -i);

        assertThat(squares.size(), is(MAX));
        for(int i = 0; i < MAX; i++){
            assertThat(squares.get(i), is(-i));
        }

        for(int i = 1; i < MAX; i += 2) assertThat(squares.delete(i), is(-i));
        for(int i = 0; i < MAX; i++){
            if((i & 1) == 0) assertThat(squares.get(i), is(-i));
            else assertThat(squares.containsKey(i), is(false));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteExceptionIfKeyDoesNotExist(){
        Dictionary<Integer, String> dict = new HashDictionary<>();
        dict.add(1, "ONE");
        dict.delete(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetExceptionIfKeyDoesNotExist(){
        Dictionary<Integer, String> dict = new HashDictionary<>();
        dict.add(1, "ONE");
        dict.get(2);
    }
}