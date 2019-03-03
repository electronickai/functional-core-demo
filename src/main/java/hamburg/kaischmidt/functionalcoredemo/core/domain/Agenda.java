package hamburg.kaischmidt.functionalcoredemo.core.domain;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Agenda {

    private final Set<Talk> talks;
    private final String lastOperationMessage;

    private Agenda(Set<Talk> talks, String lastOperationMessage) {
        this.talks = talks;
        this.lastOperationMessage = lastOperationMessage;
    }

    public static Agenda initializeAgenda() {
        return new Agenda(Set.of(), "Keine Vorträge vorhanden");
    }

    Set<Talk> getTalks() {
        return talks;
    }

    public List<Talk> getTalksSortedByName() {
        return talks.stream().sorted().collect(Collectors.toUnmodifiableList());
    }

    public String getLastOperationMessage() {
        return lastOperationMessage;
    }

    public Agenda addNewTalk(String topic) {
        return talkExists(topic)
                ? new Agenda(talks, String.format("Vortrag %s existiert bereits", topic))
                : new Agenda(addToCurrentList(Talk.createNewTalk(topic)), String.format("Vortrag %s erstellt", topic));
    }

    public Agenda addRatingToTalk(String topic, Rating rating) {
        return !talkExists(topic)
                ? new Agenda(talks, String.format("Keine Bewertung hinzugefügt. Vortrag %s ist nicht vorhanden", topic))
                : applyRating(topic, rating);
    }

    public Agenda toggleStatus(String topic) {
        return !talkExists(topic)
                ? new Agenda(talks, String.format("Status unverändert. Spieler %s ist nicht vorhanden", topic))
                : new Agenda(applyFunctionToTalk(topic, Talk::toggleStatus), String.format("Status für Spieler %s geändert", topic));
    }

    private boolean talkExists(String topic) {
        return talks.stream().anyMatch(t -> t.getTopic().equals(topic));
    }

    private Set<Talk> applyFunctionToTalk(String topic, Function<Talk, Talk> function) {
        return talks.stream()
                .map(talk -> talk.getTopic().equals(topic) ? function.apply(talk) : talk)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Agenda applyRating(String topic, Rating rating) {
        switch (rating) {
            case TOP:
                return new Agenda(applyFunctionToTalk(topic, Talk::addTop), String.format("Positive Bewertung zu Vortrag %s hinzugefügt", topic));
            case OKAY:
                return new Agenda(applyFunctionToTalk(topic, Talk::addOkay), String.format("Neutrale Bewertung zu Vortrag %s hinzugefügt", topic));
            case FLOP:
                return new Agenda(applyFunctionToTalk(topic, Talk::addFlop), String.format("Negative Bewertung zu Vortrag %s hinzugefügt", topic));
        }
        return new Agenda(talks, String.format("Bewertung %s konnte nicht interpretiert werden", rating));
    }

    private Set<Talk> addToCurrentList(Talk newTalk) {
        return Stream.concat(talks.stream(), Stream.of(newTalk)).collect(Collectors.toUnmodifiableSet());
    }
}
