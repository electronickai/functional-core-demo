package hamburg.kaischmidt.functionalcoredemo.shell;

import hamburg.kaischmidt.functionalcoredemo.core.domain.Player;
import hamburg.kaischmidt.functionalcoredemo.core.domain.PlayerList;
import hamburg.kaischmidt.functionalcoredemo.core.presentation.PlayerRepresentation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class PlayerController {

    private ApplicationState applicationState;

    PlayerController(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    @GetMapping("/player-list")
    public String showPlayerList(Model model) {
        List<Player> players = getPlayerList().getPlayersSortedByName();
        model.addAttribute("players", PlayerRepresentation.listFrom(players));
        return "player-list";
    }

    @PostMapping("/player")
    public RedirectView createPlayer(@RequestParam(value = "Spielername") String playerName, RedirectAttributes attributes) {
        createNewPlayer(playerName);
        attributes.addAttribute("playerCreateMessage", getPlayerList().getLastOperationMessage());
        return new RedirectView("/");
    }

    @PostMapping("/player/addKudos")
    public RedirectView addKudos(@RequestParam(value = "Spielername") String playerName) {
        addKudosToPlayer(playerName);
        return new RedirectView("/player-list");
    }

    @PostMapping("/player/togglePremium")
    public RedirectView togglePremium(@RequestParam(value = "Spielername") String playerName) {
        togglePremiumOfPlayer(playerName);
        return new RedirectView("/player-list");
    }

    private void addKudosToPlayer(String playerName) {
        setPlayerList(getPlayerList().addKudosToPlayer(playerName));
    }

    private void togglePremiumOfPlayer(String playerName) {
        setPlayerList(getPlayerList().togglePremium(playerName));
    }

    private synchronized void createNewPlayer(String playerName) {
        setPlayerList(getPlayerList().addNewPlayer(playerName));
    }

    private PlayerList getPlayerList() {
        return applicationState.getPlayerList();
    }

    private void setPlayerList(PlayerList playerList) {
        applicationState.setPlayerList(playerList);
    }

}
