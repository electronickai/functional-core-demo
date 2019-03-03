package hamburg.kaischmidt.functionalcoredemo.core.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TalkList_When_toggle_status {

    private static final String TALK_1 = "Talk1";
    private static final String TALK_2 = "Talk2";

    @Test
    public void Then_status_is_switched() {

        //Arrange
        Agenda agenda = Agenda.initializeAgenda();
        agenda = agenda
                .addNewTalk(TALK_1).toggleStatus(TALK_1)
                .addNewTalk(TALK_2);

        //Act
        agenda = agenda.toggleStatus(TALK_1).toggleStatus(TALK_2);

        //Assert
        assertThat(agenda.getTalks().stream().filter(p -> p.getTopic().equals(TALK_1)).noneMatch(Talk::canBeRated)).isTrue();
        assertThat(agenda.getTalks().stream().filter(p -> p.getTopic().equals(TALK_2)).allMatch(Talk::canBeRated)).isTrue();
    }
}


