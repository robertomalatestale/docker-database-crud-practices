package practices.singletonpractice;

//Singleton is a project pattern to make a class have one unique instance
public class TestBench {
    public static void main(String[] args) {
        bookSeat("1A");
        bookSeat("1A");
        bookSeatSingletonEager("1A");
        bookSeatSingletonEager("1A");
        bookSeatSingletonLazy("1A");
        bookSeatSingletonLazy("1A");
    }

    private static void bookSeat(String seat){
        Aircraft aircraft = new Aircraft("752-145");
        System.out.println(aircraft.bookSeat(seat));
    }

    private static void bookSeatSingletonEager(String seat){
        AircraftSingletonEager aircraftSingletonEager = AircraftSingletonEager.getINSTANCE();
        System.out.println(aircraftSingletonEager.bookSeat(seat));
    }

    private static void bookSeatSingletonLazy(String seat){
        AircraftSingletonLazy aircraftSingletonLazy = AircraftSingletonLazy.getINSTANCE();
        System.out.println(aircraftSingletonLazy.bookSeat(seat));
    }
}
