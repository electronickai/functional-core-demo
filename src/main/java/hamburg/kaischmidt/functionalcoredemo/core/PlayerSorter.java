package hamburg.kaischmidt.functionalcoredemo.core;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class PlayerSorter {

    public List<String> sort(Set<String> players) {
        List<String> playerList = new ArrayList<>(players);
        Collections.sort(playerList);
        return playerList;
    }
}
