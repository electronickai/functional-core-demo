package hamburg.kaischmidt.functionalcoredemo.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerList_When_adding_new_player {

    @Test
    public void If_player_does_not_exist_Then_it_is_created_And_message_means_created() {

        //Arrange
        final String userName = "NewUser";

        PlayerList players = PlayerList.initializePlayerList();
        assertThat(players.getPlayers().size()).isEqualTo(0);

        //Act
        players = players.addNewPlayer(players, userName);

        //Assert
        assertThat(players.getPlayerCreatedMessage()).isEqualTo(String.format("Spieler %s erstellt", userName));
        assertThat(players.getPlayers().size()).isEqualTo(1);
        assertThat(players.getPlayers().iterator().next().getName()).isEqualTo(userName);

    }

    @Test
    public void If_player_exists_it_Then_is_not_created_And_message_means_not_created() {

        //Arrange
        final String userName = "TestUser";

        PlayerList players = PlayerList.initializePlayerList();
        players = players.addNewPlayer(players, userName);
        assertThat(players.getPlayers().size()).isEqualTo(1);

        //Act
        players = players.addNewPlayer(players, userName);

        //Assert
        assertThat(players.getPlayerCreatedMessage()).isEqualTo(String.format("Spieler %s existiert bereits", userName));
        assertThat(players.getPlayers().size()).isEqualTo(1);
        assertThat(players.getPlayers().iterator().next().getName()).isEqualTo(userName);

    }

}