package hamburg.kaischmidt.functionalcoredemo.shell;

import hamburg.kaischmidt.functionalcoredemo.core.PlayerList;

class ApplicationState {

    private static final class ApplicationStateHolder {
        static final ApplicationState INSTANCE = new ApplicationState();
    }

    private ApplicationState() {
        playerList = PlayerList.initializePlayerList();
    }

    static ApplicationState getInstance() {
        return ApplicationStateHolder.INSTANCE;
    }

    private PlayerList playerList;

    PlayerList getPlayerList() {
        return playerList;
    }

    void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }
}
