package hamburg.kaischmidt.functionalcoredemo.core.presentation;

import hamburg.kaischmidt.functionalcoredemo.core.domain.Talk;

import java.util.List;
import java.util.stream.Collectors;

public class TalkRepresentation {

    private String topic;
    private int top;
    private int okay;
    private int flop;
    private String canBeRated;
    private boolean rateEnabled;
    private String statusButtonText;

    static final String BEWERTBAR = "Ja";
    static final String NICHT_BEWERTBAR = "Nein";

    static final String STOPPEN = "Stoppen";
    static final String FREISCHALTEN = "Freischalten";

    private TalkRepresentation() {
    }

    public String getHeadline() {
        return topic;
    }

    public int getTop() {
        return top;
    }

    public int getOkay() {
        return okay;
    }

    public int getFlop() {
        return flop;
    }

    public String getStatusOfTalk() {
        return canBeRated;
    }

    public boolean isRateEnabled() {
        return rateEnabled;
    }

    public String getStatusButtonText() {
        return statusButtonText;
    }

    public static List<TalkRepresentation> listFrom(List<Talk> talkList) {
        return talkList.stream()
                .map(TalkRepresentation::mapFields)
                .collect(Collectors.toList());
    }

    private static TalkRepresentation mapFields(Talk talk) {
        var representation = new TalkRepresentation();
        representation.setTopic(talk.getName());
        representation.setTop(talk.getTop());
        representation.setOkay(talk.getOkay());
        representation.setFlop(talk.getFlop());
        representation.setCanBeRated(talk.canBeRated());
        representation.setRateEnabled(talk.canBeRated());
        representation.setStatusButtonText(talk.canBeRated() ? STOPPEN : FREISCHALTEN);
        return representation;
    }

    private void setTopic(String topic) {
        this.topic = topic;
    }

    private void setTop(int top) {
        this.top = top;
    }

    private void setOkay(int okay) {
        this.okay = okay;
    }

    private void setFlop(int flop) {
        this.flop = flop;
    }

    private void setCanBeRated(boolean canBeRated) {
        this.canBeRated = canBeRated ? BEWERTBAR : NICHT_BEWERTBAR;
    }

    private void setRateEnabled(boolean rateEnabled) {
        this.rateEnabled = rateEnabled;
    }

    private void setStatusButtonText(String statusButtonText) {
        this.statusButtonText = statusButtonText;
    }
}
