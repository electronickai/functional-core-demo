package hamburg.kaischmidt.functionalcoredemo.shell;

import hamburg.kaischmidt.functionalcoredemo.core.domain.Agenda;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TalkController_When_userlist_is_shown {

    private static final String NAME_FIELD = "input title=\"Name:\"";
    private static final String TALK1 = "Talk1";
    private static final String TALK2 = "Talk2";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationState applicationState;

    @Test
    public void Then_welcome_page_is_shown() throws Exception {
        mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Vortrag erstellen")));
    }

    @Test
    public void If_create_message_is_passed_Then_it_is_shown() throws Exception {
        final String createMessage = "Spieler Testuser erstellt";

        mockMvc.perform(get("/?topicCreateMessage=" + createMessage)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(createMessage)));
    }

    @Test
    public void If_no_user_exist_Then_no_user_is_displayed() throws Exception {

        //Arrange
        applicationState.setAgenda(Agenda.initializeAgenda());

        //Act
        String responseBody = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //Assert
        assertThat(responseBody.contains(NAME_FIELD)).isFalse();
    }

    @Test
    public void Then_multiple_Users_can_be_displayed() throws Exception {

        //Arrange
        applicationState.setAgenda(Agenda.initializeAgenda());
        mockMvc.perform(post("/talk").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Vortragsthema=" + TALK1).accept(APPLICATION_FORM_URLENCODED_VALUE));
        mockMvc.perform(post("/talk").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Vortragsthema=" + TALK2).accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Act
        String responseBody = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //Assert
        assertThat(responseBody.contains(TALK1)).isTrue();
        assertThat(responseBody.contains(TALK2)).isTrue();
        assertThat(Arrays.stream(responseBody.split(NAME_FIELD)).count()).isEqualTo(2 + 1);
    }
}
