package hamburg.kaischmidt.functionalcoredemo.core;

import java.util.Objects;

public final class Player implements Comparable<Player> {

    private final String name;
    private final int kudos;
    private final boolean premiumMember;

    private Player(String name, int kudos, boolean premiumMember) {
        this.name = name;
        this.kudos = kudos;
        this.premiumMember = premiumMember;
    }

    static Player createNewPlayer(String name) {
        return new Player(name, 0, false);
    }

    String getName() {
        return name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public int getKudos() {
        return kudos;
    }

    static Player addKudos(Player player) {
        if (!player.premiumMember) {
            return player;
        }
        return new Player(player.name, player.kudos + 1, true);
    }

    static Player togglePremium(Player player) {
        return new Player(player.name, player.kudos, !player.premiumMember);
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
