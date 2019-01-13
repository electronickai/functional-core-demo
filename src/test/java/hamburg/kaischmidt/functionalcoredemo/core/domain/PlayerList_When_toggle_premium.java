package hamburg.kaischmidt.functionalcoredemo.core.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerList_When_toggle_premium {

    private static final String PLAYER_1 = "Player1";
    private static final String PLAYER_2 = "Player2";

    @Test
    public void Then_premium_state_is_switched() {

        //Arrange
        PlayerList players = PlayerList.initializePlayerList();
        players = players
                .addNewPlayer(PLAYER_1).togglePremium(PLAYER_1)
                .addNewPlayer(PLAYER_2);

        //Act
        players = players.togglePremium(PLAYER_1).togglePremium(PLAYER_2);

        //Assert
        assertThat(players.getPlayers().stream().filter(p -> p.getName().equals(PLAYER_1)).noneMatch(Player::isPremiumMember)).isTrue();
        assertThat(players.getPlayers().stream().filter(p -> p.getName().equals(PLAYER_2)).allMatch(Player::isPremiumMember)).isTrue();
    }
}


