package hamburg.kaischmidt.functionalcoredemo.player.shell;

import hamburg.kaischmidt.functionalcoredemo.player.core.PlayerSorter;
import hamburg.kaischmidt.functionalcoredemo.shell.persistence.PlayerList;
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

    private PlayerList playerList;
    private PlayerSorter sorter;

    public PlayerController(PlayerList playerList, PlayerSorter sorter) {
        this.playerList = playerList;
        this.sorter = sorter;
    }

    @GetMapping("/player-list")
    public String showPlayerList(Model model) {
        List<String> sortedPlayers = sorter.sort(playerList.getPlayers());
        model.addAttribute("players", sortedPlayers);
        return "player-list";
    }

    @PostMapping("/player")
    public RedirectView createPlayer(@RequestParam(value = "Spielername") String playerName, RedirectAttributes attributes) {

        playerList.addNewPlayer(playerName);

        attributes.addAttribute("newPlayer", playerName);
        return new RedirectView("/");
    }
}
