package com.rev.aoc.util.graph;

import lombok.Getter;

import java.util.Objects;

@SuppressWarnings("checkstyle:VisibilityModifier")
@Getter
public final class Vertex {
    final String name;

    public Vertex(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Vertex vertex = (Vertex) o;
        return Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
