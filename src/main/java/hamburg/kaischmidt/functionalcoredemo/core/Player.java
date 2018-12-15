package hamburg.kaischmidt.functionalcoredemo.core;

import java.util.Objects;

public final class Player implements Comparable<Player> {

    private final String name;

    Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Player other) {
        return this.name.compareTo(other.name);
    }

}
