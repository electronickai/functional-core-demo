package hamburg.kaischmidt.functionalcoredemo.core;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Repository
public class PlayerList {

    private Set<Player> players = Set.of();

    public Set<Player> getPlayers() {
        return players;
    }

    public String addNewPlayer(String playerName) {
        if (playerExists(playerName)) {
            return "existiert bereits";
        }
        Set<Player> modifiableSet = new HashSet<>(players);
        modifiableSet.add(new Player(playerName));
        players = Collections.unmodifiableSet(modifiableSet);
        return "erstellt";
    }

    private boolean playerExists(String name) {
        return players.contains(new Player(name));
    }
}
