package hamburg.kaischmidt.functionalcoredemo.shell;

import hamburg.kaischmidt.functionalcoredemo.core.Player;
import hamburg.kaischmidt.functionalcoredemo.core.PlayerList;
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

    @GetMapping("/player-list")
    public String showPlayerList(Model model) {
        List<Player> players = getPlayerList().getSortedPlayerNames();
        model.addAttribute("players", players);
        return "player-list";
    }

    @PostMapping("/player")
    public RedirectView createPlayer(@RequestParam(value = "Spielername") String playerName, RedirectAttributes attributes) {
        addNewPlayer(playerName);
        attributes.addAttribute("playerCreateMessage", getPlayerList().getLastOperationMessage());
        return new RedirectView("/");
    }

    @PostMapping("player/addKudos")
    public RedirectView addKudos(@RequestParam(value = "Spielername") String playerName, RedirectAttributes attributes) {
        addKudosToPlayer(playerName);
        return new RedirectView("/player-list");
    }

    private void addKudosToPlayer(String playerName) {
        ApplicationState.getInstance().setPlayerList(getPlayerList().addKudosToPlayer(getPlayerList(), playerName));
    }

    private synchronized void addNewPlayer(String playerName) {
        ApplicationState.getInstance().setPlayerList(getPlayerList().addNewPlayer(getPlayerList(), playerName));
    }

    private PlayerList getPlayerList() {
        return ApplicationState.getInstance().getPlayerList();
    }
}
