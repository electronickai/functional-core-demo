package hamburg.kaischmidt.functionalcoredemo.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerList_When_adding_new_player {

    @Test
    public void If_player_does_not_exist_Then_it_is_created_And_message_means_created() {

        //Arrange
        final String userName = "NewUser";

        PlayerList players = new PlayerList();
        assertThat(players.getPlayers().size()).isEqualTo(0);

        //Act
        String createdMassage = players.addNewPlayer(userName);

        //Assert
        assertThat(createdMassage).isEqualTo("erstellt");
        assertThat(players.getPlayers().size()).isEqualTo(1);
        assertThat(players.getPlayers().iterator().next().getName()).isEqualTo(userName);

    }

    @Test
    public void If_player_exists_it_Then_is_not_created_And_message_means_not_created() {

        //Arrange
        final String userName = "TestUser";

        PlayerList players = new PlayerList();
        players.addNewPlayer(userName);
        assertThat(players.getPlayers().size()).isEqualTo(1);

        //Act
        String createdMassage = players.addNewPlayer(userName);

        //Assert
        assertThat(createdMassage).isEqualTo("existiert bereits");
        assertThat(players.getPlayers().size()).isEqualTo(1);
        assertThat(players.getPlayers().iterator().next().getName()).isEqualTo(userName);

    }

}