package hamburg.kaischmidt.functionalcoredemo.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerList_When_adding_kudos {

    private static final String FIRST_USER = "firstUser";
    private static final String SECOND_USER = "secondUser";

    @Test
    public void If_player_exist_Then_kudos_are_added() {
        //Arrange
        final String firstUser = FIRST_USER;
        final String secondUser = SECOND_USER;
        PlayerList players = PlayerList.initializePlayerList();
        players = players.addNewPlayer(players, firstUser);
        players = players.addNewPlayer(players, secondUser);

        //Act
        players = players.addKudosToPlayer(players, firstUser);
        players = players.addKudosToPlayer(players, secondUser);
        players = players.addKudosToPlayer(players, secondUser);

        //Assert
        assertThat(players.getPlayers().size()).isEqualTo(2);
        for (Player player : players.getPlayers()) {
            switch (player.getName()) {
                case firstUser:
                    assertThat(player.getKudos()).isEqualTo(1);
                    break;
                case secondUser:
                    assertThat(player.getKudos()).isEqualTo(2);
            }
        }
    }

    @Test
    public void If_player_does_not_exist_Then_playerlist_stays_the_same() {
        //Arrange
        PlayerList players = PlayerList.initializePlayerList();
        players = players.addNewPlayer(players, FIRST_USER);

        //Act
        players = players.addKudosToPlayer(players, SECOND_USER);

        //Assert
        assertThat(players.getPlayers().size()).isEqualTo(1);
        assertThat(players.getPlayers().iterator().next().getName()).isEqualTo("firstUser");
        assertThat(players.getPlayers().iterator().next().getKudos()).isEqualTo(0);
        assertThat(players.getLastOperationMessage()).isEqualTo("Keine Kudos hinzugefügt. Spieler " + SECOND_USER + " ist nicht vorhanden");
    }
}

