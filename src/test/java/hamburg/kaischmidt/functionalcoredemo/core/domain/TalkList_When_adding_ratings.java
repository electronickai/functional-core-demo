package hamburg.kaischmidt.functionalcoredemo.core.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TalkList_When_adding_ratings {

    private static final String FIRST_TALK = "firstTalk";
    private static final String SECOND_TALK = "secondTalk";
    private static final String THIRD_TALK = "thirdTalk";

    @Test
    public void If_talk_exists_Then_feedback_is_added() {
        //Arrange
        Agenda agenda = Agenda.initializeAgenda();
        agenda = agenda
                .addNewTalk(FIRST_TALK).addNewTalk(SECOND_TALK).addNewTalk(THIRD_TALK)
                .toggleStatus(FIRST_TALK).toggleStatus(SECOND_TALK).toggleStatus(THIRD_TALK);

        //Act
        agenda = agenda
                .addRatingToTalk(FIRST_TALK, Rating.TOP)
                .addRatingToTalk(SECOND_TALK, Rating.OKAY).addRatingToTalk(SECOND_TALK, Rating.OKAY)
                .addRatingToTalk(THIRD_TALK, Rating.FLOP).addRatingToTalk(THIRD_TALK, Rating.FLOP).addRatingToTalk(THIRD_TALK, Rating.FLOP);

        //Assert
        assertThat(agenda.getTalks().size()).isEqualTo(3);
        assertThat(agenda.getTalks().stream().filter(p -> p.getName().equals(FIRST_TALK)).map(Talk::getTop)).containsExactly(1);
        assertThat(agenda.getTalks().stream().filter(p -> p.getName().equals(SECOND_TALK)).map(Talk::getOkay)).containsExactly(2);
        assertThat(agenda.getTalks().stream().filter(p -> p.getName().equals(THIRD_TALK)).map(Talk::getFlop)).containsExactly(3);
    }

    @Test
    public void If_talk_cannot_be_rated_Then_agenda_stays_the_same() {
        //Arrange
        Agenda agenda = Agenda.initializeAgenda();
        agenda = agenda.addNewTalk(FIRST_TALK);

        //Act
        agenda = agenda.addRatingToTalk(FIRST_TALK, Rating.TOP);

        //Assert
        assertThat(agenda.getTalks().size()).isEqualTo(1);
        assertThat(agenda.getTalks().iterator().next().getName()).isEqualTo("firstTalk");
        assertThat(agenda.getTalks().iterator().next().getTop()).isEqualTo(0);
    }

    @Test
    public void If_talk_does_not_exist_Then_agenda_stays_the_same() {
        //Arrange
        Agenda agenda = Agenda.initializeAgenda();
        agenda = agenda.addNewTalk(FIRST_TALK);

        //Act
        agenda = agenda.addRatingToTalk(SECOND_TALK, Rating.TOP);

        //Assert
        assertThat(agenda.getTalks().size()).isEqualTo(1);
        assertThat(agenda.getTalks().iterator().next().getName()).isEqualTo("firstTalk");
        assertThat(agenda.getTalks().iterator().next().getTop()).isEqualTo(0);
        assertThat(agenda.getLastOperationMessage()).isEqualTo("Keine Bewertung hinzugefügt. Vortrag " + SECOND_TALK + " ist nicht vorhanden");
    }
}
