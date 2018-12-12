package hamburg.kaischmidt.functionalcoredemo.shell;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class PlayerList {

    private Set<String> players = new HashSet<>();

    Set<String> getPlayers() {
        return players;
    }

    void addNewPlayer(String playerName) {
        players.add(playerName);
    }
}
