package hamburg.kaischmidt.functionalcoredemo.shell;

import hamburg.kaischmidt.functionalcoredemo.core.domain.PlayerList;
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
public class PlayerController_When_premium_is_toggled {

    private static final String TOGGLE_ONCE_PLAYER = "ToogleOncePlayer";
    private static final String TOGGLE_TWICE_PLAYER = "ToogleTwicePlayer";
    private static final String PLAYER_STRING_TOOGLE_ONCE = "title=\"Name:\" value=\"" + TOGGLE_ONCE_PLAYER + "\"";
    private static final String PLAYER_STRING_TOOGLE_TWICE = "title=\"Name:\" value=\"" + TOGGLE_TWICE_PLAYER + "\"";

    private static final String PREMIUM_STRING = "title=\"Premium:\" value=\"Premium\"";
    private static final String STANDARD_STRING = "title=\"Premium:\" value=\"Standard\"";
    private static final String ADD_KUDOS_DISABLED = "formaction=\"/player/addKudos\" formmethod=\"post\" class=\"btn btn-primary\" disabled=\"disabled\"";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationState applicationState;

    @Test
    public void If_toggled_once_Then_player_is_displayed_as_premium_user() throws Exception {

        //Arrange
        applicationState.setPlayerList(PlayerList.initializePlayerList());
        mockMvc.perform(post("/player").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=" + TOGGLE_ONCE_PLAYER).accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Act
        mockMvc.perform(post("/player/togglePremium").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=" + TOGGLE_ONCE_PLAYER).accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Assert
        String responseBody = mockMvc.perform(get("/player-list"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).contains(PLAYER_STRING_TOOGLE_ONCE);
        assertThat(responseBody).contains(PREMIUM_STRING);
        assertThat(responseBody).doesNotContain("disabled");
    }

    @Test
    public void If_toggled_twice_Then_player_is_displayed_as_standard_user() throws Exception {

        //Arrange
        mockMvc.perform(post("/player").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=" + TOGGLE_TWICE_PLAYER).accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Act
        mockMvc.perform(post("/player/togglePremium").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=" + TOGGLE_TWICE_PLAYER).accept(APPLICATION_FORM_URLENCODED_VALUE));
        mockMvc.perform(post("/player/togglePremium").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=" + TOGGLE_TWICE_PLAYER).accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Assert
        String responseBody = mockMvc.perform(get("/player-list"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).contains(PLAYER_STRING_TOOGLE_TWICE);
        assertThat(responseBody).contains(STANDARD_STRING);
        assertThat(responseBody).contains(ADD_KUDOS_DISABLED);
    }

}
