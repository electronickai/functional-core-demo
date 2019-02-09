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
public class TalkController_When_ratings_are_added {

    private static final String TALK_STRING = "title=\"Name:\" value=\"Talk\"";
    private static final String RATING_STRING = "title=\"RateTop:\" value=\"2\"";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Then_ratings_are_displayed() throws Exception {

        //Arrange
        mockMvc.perform(post("/talk").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Vortragsthema=Talk").accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Act
        mockMvc.perform(post("/talk/toggleStatus").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Vortragsthema=Talk").accept(APPLICATION_FORM_URLENCODED_VALUE));
        mockMvc.perform(post("/talk/addFeedback/TOP").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Vortragsthema=Talk").accept(APPLICATION_FORM_URLENCODED_VALUE));
        mockMvc.perform(post("/talk/addFeedback/TOP").contentType(APPLICATION_FORM_URLENCODED_VALUE).content("Vortragsthema=Talk").accept(APPLICATION_FORM_URLENCODED_VALUE));

        //Assert
        String result = mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(result).contains(TALK_STRING);
        assertThat(result).contains(RATING_STRING);
    }
}
