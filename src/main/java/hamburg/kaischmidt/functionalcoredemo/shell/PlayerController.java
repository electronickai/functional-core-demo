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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class PlayerController {

    private final PlayerList playerList;

    public PlayerController(PlayerList playerList) {
        this.playerList = playerList;
    }

    @GetMapping("/player-list")
    public String showPlayerList(Model model) {
        List<Player> players = new ArrayList<>(playerList.getPlayers());
        Collections.sort(players);
        model.addAttribute("players", players);
        return "player-list";
    }

    @PostMapping("/player")
    public RedirectView createPlayer(@RequestParam(value = "Spielername") String playerName, RedirectAttributes attributes) {
        String playerCreateMessage = playerList.addNewPlayer(playerName);
        attributes.addAttribute("playerCreateMessage", playerCreateMessage);
        attributes.addAttribute("newPlayer", playerName);
        return new RedirectView("/");
    }
}
