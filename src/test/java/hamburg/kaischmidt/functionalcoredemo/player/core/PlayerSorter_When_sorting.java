package hamburg.kaischmidt.functionalcoredemo.player.core;

import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerSorter_When_sorting {

    @Test
    public void Then_the_list_is_sorted_alphabetically() {

        //Arrange
        PlayerSorter sorter = new PlayerSorter();

        //Act
        List<String> sortedPlayers = sorter.sort(Set.of("Kurt", "Heinz", "Dieter"));

        //Assert
        for (int i = 1; i < sortedPlayers.size(); i++) {
            assertThat(sortedPlayers.get(i)).isGreaterThan(sortedPlayers.get(i - 1));
        }
    }
}
