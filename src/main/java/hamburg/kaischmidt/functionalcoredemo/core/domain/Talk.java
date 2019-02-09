package hamburg.kaischmidt.functionalcoredemo.core.domain;

import java.util.Objects;

public final class Talk implements Comparable<Talk> {

    private final String name;
    private final int top;
    private final int okay;
    private final int flop;
    private final boolean canBeRated;

    private Talk(String name, int top, int okay, int flop, boolean canBeRated) {
        this.name = name;
        this.top = top;
        this.okay = okay;
        this.flop = flop;
        this.canBeRated = canBeRated;
    }

    public static Talk createNewTalk(String name) {
        return new Talk(name, 0, 0, 0, false);
    }

    public String getName() {
        return name;
    }

    public boolean canBeRated() {
        return canBeRated;
    }

    public int getTop() {
        return top;
    }

    public int getOkay() {
        return okay;
    }

    public int getFlop() {
        return flop;
    }

    public Talk addTop() {
        if (!canBeRated) {
            return this;
        }
        return new Talk(name, top + 1, okay, flop, canBeRated);
    }

    public Talk addOkay() {
        if (!canBeRated) {
            return this;
        }
        return new Talk(name, top, okay + 1, flop, canBeRated);
    }

    public Talk addFlop() {
        if (!canBeRated) {
            return this;
        }
        return new Talk(name, top, okay, flop + 1, canBeRated);
    }

    public Talk toggleStatus() {
        return new Talk(name, top, okay, flop, !canBeRated);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Talk talk = (Talk) o;
        return name.equals(talk.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Talk other) {
        return this.name.compareTo(other.name);
    }
}
