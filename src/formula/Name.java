package formula;

public class Name implements Argument {
    private String name;
    public Name(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
