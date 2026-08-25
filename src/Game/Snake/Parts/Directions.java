package Game.Snake.Parts;

public enum Directions {
    UP ("up"),
    DOWN ("down"),
    LEFT ("left"),
    RIGHT ("right");
    private final String name;
    private Directions(String s) {
        name=s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
