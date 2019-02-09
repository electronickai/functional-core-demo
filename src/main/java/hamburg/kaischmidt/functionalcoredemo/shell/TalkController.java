package hamburg.kaischmidt.functionalcoredemo.shell;

import hamburg.kaischmidt.functionalcoredemo.core.domain.Agenda;
import hamburg.kaischmidt.functionalcoredemo.core.domain.Rating;
import hamburg.kaischmidt.functionalcoredemo.core.domain.Talk;
import hamburg.kaischmidt.functionalcoredemo.core.presentation.TalkRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class TalkController {

    @Value("${demo.customer}")
    private String CUSTOMER = "";

    private ApplicationState applicationState;

    TalkController(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    @GetMapping("/")
    public String showAgenda(@ModelAttribute("topicCreateMessage") String topicCreatedMessage, Model model) {
        List<Talk> talks = getAgenda().getTalksSortedByName();
        model.addAttribute("customer", CUSTOMER);
        model.addAttribute("talks", TalkRepresentation.listFrom(talks));
        return "talk-list";
    }

    @PostMapping("/talk")
    public RedirectView createTalk(@RequestParam(value = "Vortragsthema") String topic, RedirectAttributes attributes) {
        createNewTalk(topic);
        attributes.addAttribute("topicCreateMessage", getAgenda().getLastOperationMessage());
        return new RedirectView("/");
    }

    @PostMapping("/talk/addFeedback/{rating}")
    public RedirectView addFeedback(@RequestParam(value = "Vortragsthema") String topic, @PathVariable Rating rating) {
        addRatingToTalk(topic, rating);
        return new RedirectView("/");
    }

    @PostMapping("/talk/toggleStatus")
    public RedirectView toggleStatus(@RequestParam(value = "Vortragsthema") String topic) {
        toggleStatusOfTalk(topic);
        return new RedirectView("/");
    }

    private void addRatingToTalk(String topic, Rating rating) {
        setAgenda(getAgenda().addRatingToTalk(topic, rating));
    }

    private void toggleStatusOfTalk(String topic) {
        setAgenda(getAgenda().toggleStatus(topic));
    }

    private synchronized void createNewTalk(String topic) {
        setAgenda(getAgenda().addNewTalk(topic));
    }

    private Agenda getAgenda() {
        return applicationState.getAgenda();
    }

    private void setAgenda(Agenda agenda) {
        applicationState.setAgenda(agenda);
    }

}
