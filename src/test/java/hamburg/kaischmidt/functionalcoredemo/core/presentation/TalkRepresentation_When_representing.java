package hamburg.kaischmidt.functionalcoredemo.core.presentation;

import hamburg.kaischmidt.functionalcoredemo.core.domain.Talk;
import org.junit.Test;

import java.util.List;

import static hamburg.kaischmidt.functionalcoredemo.core.presentation.TalkRepresentation.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TalkRepresentation_When_representing {

    private static final String TALK_1 = "Talk1";
    private static final String TALK_2 = "Talk2";
    private static final String TALK_3 = "Talk3";

    @Test
    public void Then_display_information_is_filled_correctly() {
        //Arrange
        Talk talk1 = Talk.createNewTalk(TALK_1);
        Talk talk2 = Talk.createNewTalk(TALK_2).toggleStatus().addTop().addTop();
        Talk talk3 = Talk.createNewTalk(TALK_3).toggleStatus().addTop().addTop().addTop().toggleStatus();

        //Act
        List<TalkRepresentation> representations = TalkRepresentation.listFrom(List.of(talk1, talk2, talk3));

        //Assert
        assertThat(representations.size()).isEqualTo(3);

        assertThat(representations.get(0).getHeadline()).isEqualTo(TALK_1);
        assertThat(representations.get(0).getTop()).isEqualTo(0);
        assertThat(representations.get(0).getStatusOfTalk()).isEqualTo(NICHT_BEWERTBAR);
        assertThat(representations.get(0).isRateEnabled()).isEqualTo(false);
        assertThat(representations.get(0).getStatusButtonText()).isEqualTo(FREISCHALTEN);

        assertThat(representations.get(1).getHeadline()).isEqualTo(TALK_2);
        assertThat(representations.get(1).getTop()).isEqualTo(2);
        assertThat(representations.get(1).getStatusOfTalk()).isEqualTo(BEWERTBAR);
        assertThat(representations.get(1).isRateEnabled()).isEqualTo(true);
        assertThat(representations.get(1).getStatusButtonText()).isEqualTo(STOPPEN);

        assertThat(representations.get(2).getHeadline()).isEqualTo(TALK_3);
        assertThat(representations.get(2).getTop()).isEqualTo(3);
        assertThat(representations.get(2).getStatusOfTalk()).isEqualTo(NICHT_BEWERTBAR);
        assertThat(representations.get(2).isRateEnabled()).isEqualTo(false);
        assertThat(representations.get(2).getStatusButtonText()).isEqualTo(FREISCHALTEN);
    }

    @Test
    public void If_agenda_is_empty_Then_representation_remains_empty() {
        //Arrange + Act + Assert
        assertThat(TalkRepresentation.listFrom(List.of())).hasSize(0);
    }
}
