package hamburg.kaischmidt.functionalcoredemo.shell;

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
public class PlayerController_When_kudos_are_added {

    private static final String PLAYER_STRING = "title=\"Name:\" value=\"Player\"";
    private static final String KUDOS_STRING = "title=\"Kudos:\" value=\"2\"";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Then_kudos_are_displayed() throws Exception {

        //Arrange
        mockMvc.perform(post("/player").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=Player").accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Act
        mockMvc.perform(post("/player/togglePremium").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=Player").accept(APPLICATION_FORM_URLENCODED_VALUE));
        mockMvc.perform(post("/player/addKudos").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=Player").accept(APPLICATION_FORM_URLENCODED_VALUE));
        mockMvc.perform(post("/player/addKudos").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=Player").accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Assert
        String result = mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(result).contains(PLAYER_STRING);
        assertThat(result).contains(KUDOS_STRING);
    }
}
