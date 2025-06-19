package practices.builderpractice;

public class TestBench {
    public static void main(String[] args) {
        Person person = new Person.PersonBuilder()
                .firstName("Roberto")
                .lastName("Leite")
                .username("robertomalateslale")
                .email("robertol@teste.com")
                .build();
        System.out.println(person.getEmail());
        System.out.println(person);
        Character character = Character.CharacterBuilder
                .aCharacter()
                .name("Bojack Horseman")
                .tvShow("Bojack Horseman")
                .age(55)
                .build();
        System.out.println(character);
        ReportDTO.ReportDTOBuilder.builder()
                .characterTvShow(character.getTvShow())
                .personEmail(person.getEmail())
                .build();
    }
}
