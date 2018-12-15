package hamburg.kaischmidt.functionalcoredemo.shell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WelcomeController.class)
public class WelcomeController_When_called {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Then_welcome_page_is_shown() throws Exception {
        mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hallo, ")));
    }

    @Test
    public void If_player_and_create_message_are_passed_Then_they_are_shown() throws Exception {
        final String createMessage = "erstellt";
        final String testUser = "TestUser";

        mockMvc.perform(get("/?playerCreateMessage=" + createMessage + "&newPlayer=" + testUser)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(createMessage)))
                .andExpect(content().string(containsString(testUser)));
    }

}