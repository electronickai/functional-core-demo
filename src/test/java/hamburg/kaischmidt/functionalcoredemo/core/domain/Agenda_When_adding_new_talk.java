package hamburg.kaischmidt.functionalcoredemo.core.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Agenda_When_adding_new_talk {

    @Test
    public void If_talk_does_not_exist_Then_it_is_created_And_message_means_created() {

        //Arrange
        final String userName = "NewUser";

        Agenda agenda = Agenda.initializeAgenda();
        assertThat(agenda.getTalks().size()).isEqualTo(0);

        //Act
        agenda = agenda.addNewTalk(userName);

        //Assert
        assertThat(agenda.getLastOperationMessage()).isEqualTo(String.format("Vortrag %s erstellt", userName));
        assertThat(agenda.getTalks().size()).isEqualTo(1);
        assertThat(agenda.getTalks().iterator().next().getTopic()).isEqualTo(userName);

    }

    @Test
    public void If_talk_exists_it_Then_is_not_created_And_message_means_not_created() {

        //Arrange
        final String userName = "TestUser";

        Agenda agenda = Agenda.initializeAgenda();
        agenda = agenda.addNewTalk(userName);
        assertThat(agenda.getTalks().size()).isEqualTo(1);

        //Act
        agenda = agenda.addNewTalk(userName);

        //Assert
        assertThat(agenda.getLastOperationMessage()).isEqualTo(String.format("Vortrag %s existiert bereits", userName));
        assertThat(agenda.getTalks().size()).isEqualTo(1);
        assertThat(agenda.getTalks().iterator().next().getTopic()).isEqualTo(userName);

    }

}