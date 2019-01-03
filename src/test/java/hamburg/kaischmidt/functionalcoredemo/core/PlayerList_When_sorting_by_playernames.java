package hamburg.kaischmidt.functionalcoredemo.core;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerList_When_sorting_by_playernames {

    @Test
    public void Then_players_are_returned_in_alphabetical_order() {

        //Arrange
        PlayerList players = PlayerList.initializePlayerList();
        players = players
                .addNewPlayer("Cäsar")
                .addNewPlayer("Bertram")
                .addNewPlayer("Anton")
                .addNewPlayer("Xaver")
                .addNewPlayer("Benedikt");

        //Act
        List<Player> sortedPlayers = players.getSortedPlayerNames();

        //Assert
        for (int i = 1; i < sortedPlayers.size(); i++) {
            assertThat(sortedPlayers.get(i).getName()).isGreaterThan(sortedPlayers.get(i - 1).getName());
        }
    }
}
