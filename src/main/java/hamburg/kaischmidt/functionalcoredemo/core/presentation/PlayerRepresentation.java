package hamburg.kaischmidt.functionalcoredemo.core.presentation;

import hamburg.kaischmidt.functionalcoredemo.core.domain.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerRepresentation {

    private String playerName;
    private int kudos;
    private String premiumStatus;
    private boolean addKudosEnabled;
    private String premiumToggleButtonText;

    static final String PREMIUM = "Premium";
    static final String STANDARD = "Standard";

    static final String PREMIUM_VERWERFEN = "Premium verwerfen";
    static final String PREMIUM_VERGEBEN = "Premium vergeben";

    private PlayerRepresentation() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getKudos() {
        return kudos;
    }

    public String getPremiumStatus() {
        return premiumStatus;
    }

    public boolean isAddKudosEnabled() {
        return addKudosEnabled;
    }

    public String getPremiumToggleButtonText() {
        return premiumToggleButtonText;
    }

    public static List<PlayerRepresentation> listFrom(List<Player> playerList) {
        return playerList.stream()
                .map(PlayerRepresentation::mapFields)
                .collect(Collectors.toList());
    }

    private static PlayerRepresentation mapFields(Player player) {
        var representation = new PlayerRepresentation();
        representation.setPlayerName(player.getName());
        representation.setKudos(player.getKudos());
        representation.setPremiumStatus(player.isPremiumMember());
        representation.setAddKudosEnabled(player.isPremiumMember());
        representation.setPremiumToggleButtonText(player.isPremiumMember() ? PREMIUM_VERWERFEN : PREMIUM_VERGEBEN);
        return representation;
    }

    private void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    private void setKudos(int kudos) {
        this.kudos = kudos;
    }

    private void setPremiumStatus(boolean premiumStatus) {
        this.premiumStatus = premiumStatus ? PREMIUM : STANDARD;
    }

    private void setAddKudosEnabled(boolean addKudosEnabled) {
        this.addKudosEnabled = addKudosEnabled;
    }

    private void setPremiumToggleButtonText(String premiumToggleButtonText) {
        this.premiumToggleButtonText = premiumToggleButtonText;
    }
}
