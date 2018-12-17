package hamburg.kaischmidt.functionalcoredemo.core;

import java.util.*;

public final class PlayerList {

    private final Set<Player> players;
    private final String playerCreatedMessage;

    private PlayerList(Set<Player> players, String playerCreatedMessage) {
        this.players = players;
        this.playerCreatedMessage = playerCreatedMessage;
    }

    public static PlayerList initializePlayerList() {
        return new PlayerList(Set.of(), "Keine Spieler vorhanden");
    }

    public PlayerList addNewPlayer(PlayerList currentList, String newPlayerName) {
        if (playerExists(newPlayerName)) {
            return new PlayerList(currentList.getPlayers(), String.format("Spieler %s existiert bereits", newPlayerName));
        }
        Set<Player> newPlayers = addToCurrentList(newPlayerName);
        return new PlayerList(newPlayers, String.format("Spieler %s erstellt", newPlayerName));
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public List<Player> getSortedPlayerNames() {
        List<Player> players = new ArrayList<>(getPlayers());
        Collections.sort(players);
        return players;
    }

    public String getPlayerCreatedMessage() {
        return playerCreatedMessage;
    }

    private boolean playerExists(String name) {
        return players.contains(new Player(name));
    }

    private Set<Player> addToCurrentList(String newPlayerName) {
        Set<Player> modifiableSet = new HashSet<>(players);
        modifiableSet.add(new Player(newPlayerName));
        return Collections.unmodifiableSet(modifiableSet);
    }
}
