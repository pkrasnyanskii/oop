package nsu.fit.krasnyanskii;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTest {
    private final int testAmount = 1000;
    private Tree<Integer> tree;
    private ArrayList<Integer> list;

    @Test
    void testIsEmpty() {
        tree.clear();
        assertTrue(tree.isEmpty());
    }

    @BeforeEach
    void forEach() {
        tree = new Tree<>();
        list = new ArrayList<>();
        for (int i = 0; i < testAmount; i++) {
            tree.add(i);
            list.add(i);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testBFS() {
        var BFS1 = tree.iteratorBFS();
        for (int i = 0; i < testAmount; i++) {
            assertTrue(BFS1.hasNext());
            assertEquals(i, BFS1.next());
        }
        assertFalse(BFS1.hasNext());
        var BFS2 = tree.iteratorBFS();
        for (int i = 0; i < testAmount; i++) {
            BFS2.remove();
        }
        assertTrue(tree.isEmpty());
        assertThrows(ConcurrentModificationException.class, BFS2::hasNext);
    }

    @Test
    void testExceptions() {
        assertThrows(NullPointerException.class, () -> tree.remove(null));
        assertThrows(NullPointerException.class, () -> tree.retainAll(null));
        assertThrows(NullPointerException.class, () -> tree.add(null));
        assertThrows(NullPointerException.class, () -> tree.addAll(null));
    }

}
