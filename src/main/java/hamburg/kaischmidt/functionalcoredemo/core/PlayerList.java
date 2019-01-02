package hamburg.kaischmidt.functionalcoredemo.core;

import java.util.*;
import java.util.function.Function;

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

    public Set<Player> getPlayers() {
        return players;
    }

    public List<Player> getSortedPlayerNames() {
        List<Player> players = new ArrayList<>(getPlayers());
        Collections.sort(players);
        return players;
    }

    public String getLastOperationMessage() {
        return lastOperationMessage;
    }

    public PlayerList addNewPlayer(PlayerList currentList, String newPlayerName) {
        if (playerExists(newPlayerName)) {
            return new PlayerList(currentList.getPlayers(), String.format("Spieler %s existiert bereits", newPlayerName));
        }
        Set<Player> newPlayers = addToCurrentList(Player.createNewPlayer(newPlayerName));
        return new PlayerList(newPlayers, String.format("Spieler %s erstellt", newPlayerName));
    }

    public PlayerList addKudosToPlayer(PlayerList currentList, String playerName) {
        if (!playerExists(playerName)) {
            return new PlayerList(currentList.getPlayers(), String.format("Keine Kudos hinzugefügt. Spieler %s ist nicht vorhanden", playerName));
        }
        Set<Player> newPlayers = addKudosToPlayer(playerName);
        return new PlayerList(newPlayers, String.format("Kudos zu Spieler %s hinzugefügt", playerName));
    }

    public PlayerList togglePremium(PlayerList currentList, String playerName) {
        if (!playerExists(playerName)) {
            return new PlayerList(currentList.getPlayers(), String.format("Premiumstatus unverändert. Spieler %s ist nicht vorhanden", playerName));
        }
        Set<Player> newPlayers = togglePremium(playerName);
        return new PlayerList(newPlayers, String.format("Premiumstatus für Spieler %s geändert", playerName));
    }

    private boolean playerExists(String name) {
        return players.stream().anyMatch(p -> p.getName().equals(name));
    }

    private Set<Player> addKudosToPlayer(String playerName) {
        return applyFunctionToPlayer(playerName, Player::addKudos);
    }

    private Set<Player> togglePremium(String playerName) {
        return applyFunctionToPlayer(playerName, Player::togglePremium);
    }

    private Set<Player> applyFunctionToPlayer(String playerName, Function<Player, Player> function) {
        Player newPlayer = null;
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                newPlayer = function.apply(player);
            }
        }
        return replaceInCurrentList(newPlayer);
    }

    private Set<Player> addToCurrentList(Player newPlayer) {
        Set<Player> modifiableSet = new HashSet<>(players);
        modifiableSet.add(newPlayer);
        return Collections.unmodifiableSet(modifiableSet);
    }

    private Set<Player> replaceInCurrentList(Player player) {
        Set<Player> modifiableSet = new HashSet<>(players);
        modifiableSet.removeIf(p -> p.getName().equals(player.getName()));
        modifiableSet.add(player);
        return Collections.unmodifiableSet(modifiableSet);
    }

}
