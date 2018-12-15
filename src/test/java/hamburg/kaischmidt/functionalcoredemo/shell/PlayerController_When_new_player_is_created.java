package hamburg.kaischmidt.functionalcoredemo.shell;

import hamburg.kaischmidt.functionalcoredemo.core.PlayerList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Autowired
    private PlayerList playerList;

    @Test
    public void Then_call_is_redirected_And_player_is_created() throws Exception {

        //Arrange
        String newPlayer = "TestUser";
        assertThat(playerList.getPlayers().size()).isEqualTo(0);

        //Act
        mockMvc.perform(post("/player").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Spielername=" + newPlayer).accept(APPLICATION_FORM_URLENCODED_VALUE))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        //Assert
        assertThat(playerList.getPlayers().size()).isEqualTo(1);
        assertThat(playerList.getPlayers().iterator().next().getName()).isEqualTo(newPlayer);

        mockMvc.perform(get("/player-list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(newPlayer)));
    }

}
