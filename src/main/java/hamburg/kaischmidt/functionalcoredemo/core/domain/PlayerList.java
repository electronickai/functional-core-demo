package hamburg.kaischmidt.functionalcoredemo.core.domain;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return players.stream().sorted().collect(Collectors.toUnmodifiableList());
    }

    public String getLastOperationMessage() {
        return lastOperationMessage;
    }

    public PlayerList addNewPlayer(String newPlayerName) {
        return playerExists(newPlayerName)
                ? new PlayerList(players, String.format("Spieler %s existiert bereits", newPlayerName))
                : new PlayerList(addToCurrentList(Player.createNewPlayer(newPlayerName)), String.format("Spieler %s erstellt", newPlayerName));
    }

    public PlayerList addKudosToPlayer(String playerName) {
        return !playerExists(playerName)
                ? new PlayerList(players, String.format("Keine Kudos hinzugefügt. Spieler %s ist nicht vorhanden", playerName))
                : new PlayerList(applyFunctionToPlayer(playerName, Player::addKudos), String.format("Kudos zu Spieler %s hinzugefügt", playerName));
    }

    public PlayerList togglePremium(String playerName) {
        return !playerExists(playerName)
                ? new PlayerList(players, String.format("Premiumstatus unverändert. Spieler %s ist nicht vorhanden", playerName))
                : new PlayerList(applyFunctionToPlayer(playerName, Player::togglePremium), String.format("Premiumstatus für Spieler %s geändert", playerName));
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
        return Stream.concat(players.stream(), Stream.of(newPlayer)).collect(Collectors.toUnmodifiableSet());
    }
}
