package hamburg.kaischmidt.functionalcoredemo.shell;

import hamburg.kaischmidt.functionalcoredemo.core.domain.Agenda;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TalkController_When_status_is_toggled {

    private static final String TOGGLE_ONCE_TALK = "ToogleOnceTalk";
    private static final String TOGGLE_TWICE_TALK = "ToogleTwiceTalk";
    private static final String TALK_STRING_TOOGLE_ONCE = "title=\"Name:\" value=\"" + TOGGLE_ONCE_TALK + "\"";
    private static final String TALK_STRING_TOOGLE_TWICE = "title=\"Name:\" value=\"" + TOGGLE_TWICE_TALK + "\"";

    private static final String RATABLE = "title=\"Status:\" value=\"Ja\"";
    private static final String NON_RATABLE = "title=\"Status:\" value=\"Nein\"";
    private static final String ADD_RATING_DISABLED = "formaction=\"/talk/addFeedback/FLOP\" formmethod=\"post\" class=\"btn btn-danger mt-3\" disabled=\"disabled\"";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationState applicationState;

    @Test
    public void If_toggled_once_Then_talk_is_displayed_as_ratable_talk() throws Exception {

        //Arrange
        applicationState.setAgenda(Agenda.initializeAgenda());
        mockMvc.perform(post("/talk").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Vortragsthema=" + TOGGLE_ONCE_TALK).accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Act
        mockMvc.perform(post("/talk/toggleStatus").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Vortragsthema=" + TOGGLE_ONCE_TALK).accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Assert
        String responseBody = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).contains(TALK_STRING_TOOGLE_ONCE);
        assertThat(responseBody).contains(RATABLE);
        assertThat(responseBody).doesNotContain("disabled");
    }

    @Test
    public void If_toggled_twice_Then_talk_is_displayed_as_non_ratable_talk() throws Exception {

        //Arrange
        mockMvc.perform(post("/talk").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Vortragsthema=" + TOGGLE_TWICE_TALK).accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Act
        mockMvc.perform(post("/talk/toggleStatus").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Vortragsthema=" + TOGGLE_TWICE_TALK).accept(APPLICATION_FORM_URLENCODED_VALUE));
        mockMvc.perform(post("/talk/toggleStatus").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Vortragsthema=" + TOGGLE_TWICE_TALK).accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Assert
        String responseBody = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).contains(TALK_STRING_TOOGLE_TWICE);
        assertThat(responseBody).contains(NON_RATABLE);
        assertThat(responseBody).contains(ADD_RATING_DISABLED);
    }

}
