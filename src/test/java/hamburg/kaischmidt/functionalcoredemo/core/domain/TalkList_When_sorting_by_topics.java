package hamburg.kaischmidt.functionalcoredemo.core.domain;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TalkList_When_sorting_by_topics {

    @Test
    public void Then_talks_are_returned_in_alphabetical_order() {

        //Arrange
        Agenda agenda = Agenda.initializeAgenda();
        agenda = agenda
                .addNewTalk("Java")
                .addNewTalk("Kotlin")
                .addNewTalk("Closure")
                .addNewTalk("Groovy")
                .addNewTalk("Frege");

        //Act
        List<Talk> sortedTalks = agenda.getTalksSortedByName();

        //Assert
        for (int i = 1; i < sortedTalks.size(); i++) {
            assertThat(sortedTalks.get(i).getTopic()).isGreaterThan(sortedTalks.get(i - 1).getTopic());
        }
    }
}
