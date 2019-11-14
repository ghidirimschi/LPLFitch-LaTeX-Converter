package formula;

import proof.Operator;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FunctionSymbol implements Argument {
    private String funcName;
    private ArrayList<Argument> arguments;

    public FunctionSymbol(String funcName, ArrayList<Argument> arguments) {
        this.funcName = funcName;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return funcName + "(" + arguments.stream().limit(arguments.size() - 1).map(s -> s + ", ").collect(Collectors.joining())
                + arguments.get(arguments.size() - 1) + ")";
    }
}
