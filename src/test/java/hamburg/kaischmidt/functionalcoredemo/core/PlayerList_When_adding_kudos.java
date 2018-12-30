package hamburg.kaischmidt.functionalcoredemo.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerList_When_adding_kudos {

    @Test
    public void If_player_exists_kudos_are_added() {
        //Arrange
        final String firstUser = "FirstUser";
        final String secondUser = "SecondUser";
        PlayerList players = PlayerList.initializePlayerList();
        players = players.addNewPlayer(players, firstUser);
        players = players.addNewPlayer(players, secondUser);

        //Act
        players = players.addKudosToPlayer(players, firstUser);
        players = players.addKudosToPlayer(players, secondUser);

        //Assert
        assertThat(players.getPlayers().size()).isEqualTo(2);
        for (Player player : players.getPlayers()) {
            switch (player.getName()) {
                case firstUser:
                    assertThat(player.getKudos()).isEqualTo(1);
                case secondUser:
                    assertThat(player.getKudos()).isEqualTo(1);
            }
        }
    }
}

