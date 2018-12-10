package hamburg.kaischmidt.functionalcoredemo.shell.persistence;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class PlayerList {

    private Set<String> players = new HashSet<>();

    public Set<String> getPlayers() {
        return players;
    }

    public void addNewPlayer(String playerName) {
        players.add(playerName);
    }
}
