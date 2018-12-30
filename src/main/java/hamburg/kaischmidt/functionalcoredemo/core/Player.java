package hamburg.kaischmidt.functionalcoredemo.core;

import java.util.Objects;

public final class Player implements Comparable<Player> {

    private final String name;
    private final int kudos;

    private Player(String name, int kudos) {
        this.name = name;
        this.kudos = kudos;
    }

    static Player createNewPlayer(String name) {
        return new Player(name, 0);
    }

    public String getName() {
        return name;
    }

    public int getKudos() {
        return kudos;
    }

    Player addKudos(Player player) {
        return new Player(player.name, player.kudos + 1);
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
