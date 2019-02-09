package hamburg.kaischmidt.functionalcoredemo.shell;

import hamburg.kaischmidt.functionalcoredemo.core.domain.Agenda;
import org.springframework.stereotype.Component;

@Component
class ApplicationState {

    private Agenda agenda = Agenda.initializeAgenda();

    Agenda getAgenda() {
        return agenda;
    }

    void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
}
