package nsu.fit.krasnyanskii;

import java.util.*;

public class Tree<T> implements Collection<T> {

    private final Node<T> root;
    private int count;
    private int countModified;

    private static class Node<T> {
        private T value;
        private ArrayList<Node<T>> children;
        private Node<T> parent;

        private Node() {
            value = null;
            children = new ArrayList<>();
            parent = null;
        }
    }

    public Tree(){
        root = new Node<>();
        count = 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean contains(Object obj) {
        return stream().anyMatch(elem -> elem.equals(obj));
    }

    /**
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    public Iterator<T> iteratorDFS() {
        return new DFS();
    }

    public Iterator<T> iteratorBFS() {
        return new BFS();
    }

    @Override
    public Object[] toArray() {
        return toArray(new Object[count]);
    }

    @Override
    @SuppressWarnings("Not checked")
    public <T1> T1[] toArray(T1[] a) {
        List<T> list = new ArrayList<>();
        for (T value : this) {
            list.add(value);
        }
        return (T1[]) list.toArray();
    }

    @Override
    public boolean add(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        Node<T> node = new Node<>();
        node.value = t;
        node.parent = root;
        root.children.add(node);
        count++;
        countModified++;
        return true;
    }

    @Override
    public boolean remove(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        var iterator = iterator();
        while (iterator.hasNext()) {
            T value = iterator.next();
            if (value.equals(obj)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object value : c) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        c.forEach(this::remove);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        boolean flag = false;
        ArrayList<T> toRemove = new ArrayList<>();
        for (T elem : this) {
            if (!c.contains(elem)) {
                toRemove.add(elem);
                flag = true;
            }
        }
        for (T elem : toRemove) {
            remove(elem);
        }
        return flag;
    }


    @Override
    public void clear() {
        count = 0;
        countModified = 0;
        root.value = null;
        root.children.clear();
    }

    public class DFS implements Iterator<T> {
        private final Stack<Integer> stackOfIDs = new Stack<>();
        private int expectCountModified = countModified;
        private Node<T> node = root;
        private int id = -1;

        @Override
        public boolean hasNext() {
            if (countModified != expectCountModified) {
                throw new ConcurrentModificationException();
            }
            while (id + 1 == node.children.size()) {
                if (node.parent == null) {
                    return false;
                }
                node = node.parent;
                id = stackOfIDs.pop();
            }
            return true;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            while (id + 1 == node.children.size()) {
                id = stackOfIDs.pop();
                node = node.parent;
            }
            stackOfIDs.push(id + 1);
            node = node.children.get(id + 1);
            id = -1;
            return node.value;
        }

        @Override
        public void remove() {
            if (node.parent == null) {
                clear();
                return;
            }
            node.parent.children.remove(node);
            for (Node<T> child : node.children) {
                node.parent.children.add(child);
                child.parent = node.parent;
            }
            count--;
            countModified++;
            expectCountModified++;
        }
    }

    public class BFS implements Iterator<T> {
        private final Queue<Node<T>> queue = new LinkedList<>();
        private int expectedCntChanges = countModified;
        private Node<T> node = root;


        private BFS() {
            queue.addAll(root.children);
        }

        @Override
        public boolean hasNext() {
            if (countModified != expectedCntChanges) {
                throw new ConcurrentModificationException();
            }
            return (!queue.isEmpty());
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            node = queue.remove();
            queue.addAll(node.children);
            return node.value;
        }

        @Override
        public void remove() {
            if (node.parent == null) {
                clear();
                return;
            }
            node.parent.children.remove(node);
            for (Node<T> child : node.children) {
                node.parent.children.add(child);
                child.parent = node.parent;
            }
            count--;
            countModified++;
            expectedCntChanges++;
        }
    }
}