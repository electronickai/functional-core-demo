package hamburg.kaischmidt.functionalcoredemo.shell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
public class PlayerController_When_new_player_is_created {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Then_call_is_redirected_And_player_is_shown_in_list() throws Exception {

        //Arrange
        String newPlayer = "TestUser";
        ApplicationState state = ApplicationState.getInstance();
        assertThat(state.getPlayerList().getPlayers().size()).isEqualTo(0);

        MvcResult result = mockMvc.perform(get("/player-list"))
                .andDo(print())
                .andReturn();

        assertThat(result.getResponse().getContentAsString().contains(newPlayer)).isFalse();

        //Act
        mockMvc.perform(post("/player").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=" + newPlayer).accept(APPLICATION_FORM_URLENCODED_VALUE))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        //Assert
        mockMvc.perform(get("/player-list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(newPlayer)));
    }

}
