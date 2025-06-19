package practices.singletonpractice;

import java.util.HashSet;
import java.util.Set;

public class AircraftSingletonLazy {
        private static AircraftSingletonLazy INSTANCE;
        private final Set<String> avaibleSeats = new HashSet<>();
        private final String name;

        private AircraftSingletonLazy(String name) {
            this.name = name;
        }

        {
            avaibleSeats.add("1A");
            avaibleSeats.add("1B");
        }

        public static AircraftSingletonLazy getINSTANCE() {
            if (INSTANCE == null) {
                synchronized (AircraftSingletonLazy.class) {
                    if(INSTANCE == null){
                INSTANCE = new AircraftSingletonLazy("752-145");
                    }
            }
        }
            return INSTANCE;
        }

        public boolean bookSeat(String seat){
            return avaibleSeats.remove(seat);
        }
}
