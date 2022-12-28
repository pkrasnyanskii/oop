package nsu.fit.krasnyanskii;

import java.util.Objects;

public class Vertex<T> {
    private final T key;
    private boolean visited;
    private int distance;

    public Vertex(T key) {
        this.key = key;
        this.visited = false;
        this.distance = 0;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int newDistance) {
        this.distance = newDistance;
    }

    public boolean getVisited() {
        return this.visited;
    }

    public void setVisited(boolean newStatus) {
        this.visited = newStatus;
    }

    public T getKey() {
        return this.key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vertex)) {
            return false;
        }
        Vertex<?> vertex = (Vertex<?>) o;
        return visited == vertex.visited && Objects.equals(key, vertex.key)
                && Objects.equals(distance, vertex.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, distance, visited);
    }

}
