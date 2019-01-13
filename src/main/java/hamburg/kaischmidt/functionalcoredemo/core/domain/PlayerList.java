package hamburg.kaischmidt.functionalcoredemo.core.domain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PlayerList {

    private final Set<Player> players;
    private final String lastOperationMessage;

    private PlayerList(Set<Player> players, String lastOperationMessage) {
        this.players = players;
        this.lastOperationMessage = lastOperationMessage;
    }

    public static PlayerList initializePlayerList() {
        return new PlayerList(Set.of(), "Keine Spieler vorhanden");
    }

    Set<Player> getPlayers() {
        return players;
    }

    public List<Player> getPlayersSortedByName() {
        List<Player> players = new ArrayList<>(getPlayers());
        Collections.sort(players);
        return players;
    }

    public String getLastOperationMessage() {
        return lastOperationMessage;
    }

    public PlayerList addNewPlayer(String newPlayerName) {
        if (playerExists(newPlayerName)) {
            return new PlayerList(players, String.format("Spieler %s existiert bereits", newPlayerName));
        }
        Set<Player> newPlayers = addToCurrentList(Player.createNewPlayer(newPlayerName));
        return new PlayerList(newPlayers, String.format("Spieler %s erstellt", newPlayerName));
    }

    public PlayerList addKudosToPlayer(String playerName) {
        if (!playerExists(playerName)) {
            return new PlayerList(players, String.format("Keine Kudos hinzugefügt. Spieler %s ist nicht vorhanden", playerName));
        }
        Set<Player> newPlayers = applyFunctionToPlayer(playerName, Player::addKudos);
        return new PlayerList(newPlayers, String.format("Kudos zu Spieler %s hinzugefügt", playerName));
    }

    public PlayerList togglePremium(String playerName) {
        if (!playerExists(playerName)) {
            return new PlayerList(players, String.format("Premiumstatus unverändert. Spieler %s ist nicht vorhanden", playerName));
        }
        Set<Player> newPlayers = applyFunctionToPlayer(playerName, Player::togglePremium);
        return new PlayerList(newPlayers, String.format("Premiumstatus für Spieler %s geändert", playerName));
    }

    private boolean playerExists(String name) {
        return players.stream().anyMatch(p -> p.getName().equals(name));
    }

    private Set<Player> applyFunctionToPlayer(String playerName, Function<Player, Player> function) {
        return players.stream()
                .map(player -> player.getName().equals(playerName) ? function.apply(player) : player)
                .collect(Collectors.toSet());
    }

    private Set<Player> addToCurrentList(Player newPlayer) {
        Set<Player> modifiableSet = new HashSet<>(players);
        modifiableSet.add(newPlayer);
        return Collections.unmodifiableSet(modifiableSet);
    }
}
