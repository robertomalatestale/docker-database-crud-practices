package singletonpractice;

import java.util.HashSet;
import java.util.Set;

//Uses the same memory address for every instance (the same)
//Creates the instance as soon the Class get loaded, that can be good to avoid concorrency thread problems
public class AircraftSingletonEager {
    private static final AircraftSingletonEager INSTANCE = new AircraftSingletonEager("752-145");
    private final Set<String> avaibleSeats = new HashSet<>();
    private final String name;

    private AircraftSingletonEager(String name) {
        this.name = name;
    }

    {
        avaibleSeats.add("1A");
        avaibleSeats.add("1B");
    }

    public static AircraftSingletonEager getINSTANCE(){
        return INSTANCE;
    }

    public boolean bookSeat(String seat){
        return avaibleSeats.remove(seat);
    }
}
