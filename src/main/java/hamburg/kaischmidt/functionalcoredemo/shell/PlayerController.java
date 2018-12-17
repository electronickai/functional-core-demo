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
        attributes.addAttribute("playerCreateMessage", getPlayerList().getPlayerCreatedMessage());
        return new RedirectView("/");
    }

    private synchronized void addNewPlayer(@RequestParam("Spielername") String playerName) {
        ApplicationState.getInstance().setPlayerList(getPlayerList().addNewPlayer(getPlayerList(), playerName));
    }

    private PlayerList getPlayerList() {
        return ApplicationState.getInstance().getPlayerList();
    }
}
