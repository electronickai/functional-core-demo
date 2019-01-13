package hamburg.kaischmidt.functionalcoredemo.core.presentation;

import hamburg.kaischmidt.functionalcoredemo.core.domain.Player;
import org.junit.Test;

import java.util.List;

import static hamburg.kaischmidt.functionalcoredemo.core.presentation.PlayerRepresentation.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayerRepresentation_When_representing {

    private static final String PLAYER_1 = "Player1";
    private static final String PLAYER_2 = "Player2";
    private static final String PLAYER_3 = "Player3";

    @Test
    public void Then_display_information_is_filled_correctly() {
        //Arrange
        Player player1 = Player.createNewPlayer(PLAYER_1);
        Player player2 = Player.createNewPlayer(PLAYER_2).togglePremium().addKudos().addKudos();
        Player player3 = Player.createNewPlayer(PLAYER_3).togglePremium().addKudos().addKudos().addKudos().togglePremium();

        //Act
        List<PlayerRepresentation> representations = PlayerRepresentation.listFrom(List.of(player1, player2, player3));

        //Assert
        assertThat(representations.size()).isEqualTo(3);

        assertThat(representations.get(0).getPlayerName()).isEqualTo(PLAYER_1);
        assertThat(representations.get(0).getKudos()).isEqualTo(0);
        assertThat(representations.get(0).getPremiumStatus()).isEqualTo(STANDARD);
        assertThat(representations.get(0).isAddKudosEnabled()).isEqualTo(false);
        assertThat(representations.get(0).getPremiumToggleButtonText()).isEqualTo(PREMIUM_VERGEBEN);

        assertThat(representations.get(1).getPlayerName()).isEqualTo(PLAYER_2);
        assertThat(representations.get(1).getKudos()).isEqualTo(2);
        assertThat(representations.get(1).getPremiumStatus()).isEqualTo(PREMIUM);
        assertThat(representations.get(1).isAddKudosEnabled()).isEqualTo(true);
        assertThat(representations.get(1).getPremiumToggleButtonText()).isEqualTo(PREMIUM_VERWERFEN);

        assertThat(representations.get(2).getPlayerName()).isEqualTo(PLAYER_3);
        assertThat(representations.get(2).getKudos()).isEqualTo(3);
        assertThat(representations.get(2).getPremiumStatus()).isEqualTo(STANDARD);
        assertThat(representations.get(2).isAddKudosEnabled()).isEqualTo(false);
        assertThat(representations.get(2).getPremiumToggleButtonText()).isEqualTo(PREMIUM_VERGEBEN);
    }

    @Test
    public void If_playerlist_is_empty_Then_representation_remains_empty() {
        //Arrange + Act + Assert
        assertThat(PlayerRepresentation.listFrom(List.of())).hasSize(0);
    }
}
