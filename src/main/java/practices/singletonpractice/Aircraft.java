package practices.singletonpractice;

import java.util.HashSet;
import java.util.Set;

public final class Aircraft {
    private final Set<String> avaibleSeats = new HashSet<>();
    private final String name;

    public Aircraft(String name) {
        this.name = name;
    }

    {
        avaibleSeats.add("1A");
        avaibleSeats.add("1B");
    }

    public boolean bookSeat(String seat){
        return avaibleSeats.remove(seat);
    }
}
