package formula;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.stream.Collectors;


public class PredicateSymbol implements Predicate {
    private String name;
    private ArrayList<Argument> arguments;

    public PredicateSymbol(String name, ArrayList<Argument> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return name + ((arguments != null) ? ("(" + arguments.stream().limit(arguments.size() - 1).map(s -> s + ", ").collect(Collectors.joining())
                + arguments.get(arguments.size() - 1) + ")") : "");
    }
}
