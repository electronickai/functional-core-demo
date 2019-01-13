package hamburg.kaischmidt.functionalcoredemo.shell;

import hamburg.kaischmidt.functionalcoredemo.core.domain.PlayerList;
import org.springframework.stereotype.Component;

@Component
class ApplicationState {

    private PlayerList playerList = PlayerList.initializePlayerList();

    PlayerList getPlayerList() {
        return playerList;
    }

    void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }
}
