package hamburg.kaischmidt.functionalcoredemo.shell;

import hamburg.kaischmidt.functionalcoredemo.core.domain.PlayerList;
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
public class PlayerController_When_userlist_is_shown {

    private static final String NAME_FIELD = "input title=\"Name:\"";
    private static final String PLAYER1 = "Player";
    private static final String PLAYER2 = "Player2";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationState applicationState;

    @Test
    public void Then_welcome_page_is_shown() throws Exception {
        mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hallo, ")));
    }

    @Test
    public void If_create_message_is_passed_Then_it_is_shown() throws Exception {
        final String createMessage = "Spieler Testuser erstellt";

        mockMvc.perform(get("/?playerCreateMessage=" + createMessage)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(createMessage)));
    }

    @Test
    public void If_no_user_exist_Then_no_user_is_displayed() throws Exception {

        //Arrange
        applicationState.setPlayerList(PlayerList.initializePlayerList());

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
        applicationState.setPlayerList(PlayerList.initializePlayerList());
        mockMvc.perform(post("/player").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=" + PLAYER1).accept(APPLICATION_FORM_URLENCODED_VALUE));
        mockMvc.perform(post("/player").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=" + PLAYER2).accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Act
        String responseBody = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //Assert
        assertThat(responseBody.contains(PLAYER1)).isTrue();
        assertThat(responseBody.contains(PLAYER2)).isTrue();
        assertThat(Arrays.stream(responseBody.split(NAME_FIELD)).count()).isEqualTo(2 + 1);
    }
}
