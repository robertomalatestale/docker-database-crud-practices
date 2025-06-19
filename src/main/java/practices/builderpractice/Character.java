package practices.builderpractice;

public class Character {
    String name;
    String tvShow;
    int age;


    public static final class CharacterBuilder {
        private String name;
        private String tvShow;
        private int age;

        private CharacterBuilder() {
        }

        public static CharacterBuilder aCharacter() {
            return new CharacterBuilder();
        }

        public CharacterBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CharacterBuilder tvShow(String tvShow) {
            this.tvShow = tvShow;
            return this;
        }

        public CharacterBuilder age(int age) {
            this.age = age;
            return this;
        }

        public Character build() {
            Character character = new Character();
            character.name = this.name;
            character.tvShow = this.tvShow;
            character.age = this.age;
            return character;
        }
    }

    public String getName() {
        return name;
    }

    public String getTvShow() {
        return tvShow;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", tvShow='" + tvShow + '\'' +
                ", age=" + age +
                '}';
    }
}
