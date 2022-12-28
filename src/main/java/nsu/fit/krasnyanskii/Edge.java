package nsu.fit.krasnyanskii;

public record Edge<T>(Vertex<T> vertexFrom, Vertex<T> vertexTo, int weight) {}