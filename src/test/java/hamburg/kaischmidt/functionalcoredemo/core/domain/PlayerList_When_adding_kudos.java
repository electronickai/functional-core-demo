package hamburg.kaischmidt.functionalcoredemo.core.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerList_When_adding_kudos {

    private static final String FIRST_USER = "firstUser";
    private static final String SECOND_USER = "secondUser";

    @Test
    public void If_player_exist_Then_kudos_are_added() {
        //Arrange
        PlayerList players = PlayerList.initializePlayerList();
        players = players
                .addNewPlayer(FIRST_USER).addNewPlayer(SECOND_USER)
                .togglePremium(FIRST_USER).togglePremium(SECOND_USER);

        //Act
        players = players
                .addKudosToPlayer(FIRST_USER)
                .addKudosToPlayer(SECOND_USER).addKudosToPlayer(SECOND_USER);

        //Assert
        assertThat(players.getPlayers().size()).isEqualTo(2);
        assertThat(players.getPlayers().stream().filter(p -> p.getName().equals(FIRST_USER)).map(Player::getKudos)).containsExactly(1);
        assertThat(players.getPlayers().stream().filter(p -> p.getName().equals(SECOND_USER)).map(Player::getKudos)).containsExactly(2);
    }

    @Test
    public void If_player_is_not_a_premium_member_Then_playerlist_stays_the_same() {
        //Arrange
        PlayerList players = PlayerList.initializePlayerList();
        players = players.addNewPlayer(FIRST_USER);

        //Act
        players = players.addKudosToPlayer(FIRST_USER);

        //Assert
        assertThat(players.getPlayers().size()).isEqualTo(1);
        assertThat(players.getPlayers().iterator().next().getName()).isEqualTo("firstUser");
        assertThat(players.getPlayers().iterator().next().getKudos()).isEqualTo(0);
    }

    @Test
    public void If_player_does_not_exist_Then_playerlist_stays_the_same() {
        //Arrange
        PlayerList players = PlayerList.initializePlayerList();
        players = players.addNewPlayer(FIRST_USER);

        //Act
        players = players.addKudosToPlayer(SECOND_USER);

        //Assert
        assertThat(players.getPlayers().size()).isEqualTo(1);
        assertThat(players.getPlayers().iterator().next().getName()).isEqualTo("firstUser");
        assertThat(players.getPlayers().iterator().next().getKudos()).isEqualTo(0);
        assertThat(players.getLastOperationMessage()).isEqualTo("Keine Kudos hinzugefügt. Spieler " + SECOND_USER + " ist nicht vorhanden");
    }
}
