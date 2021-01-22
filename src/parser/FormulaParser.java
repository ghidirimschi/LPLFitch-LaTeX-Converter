package parser;

import formulanew.*;
import proof.Operator;
import tokenizer.FormulaTokenizer;

import java.util.ArrayList;

public final class FormulaParser {
    public static Sentence parse(String formulaString) throws FormulaParsingException {
        FormulaTokenizer tokenizer = new FormulaTokenizer(formulaString);
        if (!tokenizer.hasMoreTokens()) {
            return null;
        }
        return parseFormula(tokenizer.nextToken(), tokenizer);
    }

    private static Sentence parseFormula(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        Sentence first = parseAtomic(tkn, tokenizer);
        if (tokenizer.isNextEqualTo(Operator.LAND.getUTFCode())) {
            Sentence second = parseFormula(tokenizer.nextToken(), tokenizer);
            while (tokenizer.isNextEqualTo(Operator.LAND.getUTFCode())) {
                second = new Conjunction(second, parseFormula(tokenizer.nextToken(), tokenizer));
            }
            return new Conjunction(first, second);
        } else if (tokenizer.isNextEqualTo(Operator.LOR.getUTFCode())) {
            Sentence second = parseFormula(tokenizer.nextToken(), tokenizer);
            while (tokenizer.isNextEqualTo(Operator.LOR.getUTFCode())) {
                second = new Disjunction(second, parseFormula(tokenizer.nextToken(), tokenizer));
            }
            return new Disjunction(first, second);
        } else if (tokenizer.isNextEqualTo(Operator.LIF.getUTFCode())) {
            return new Implication(first, parseFormula(tokenizer.nextToken(), tokenizer));
        } else if (tokenizer.isNextEqualTo(Operator.LIFF.getUTFCode())) {
            return new BiImplication(first, parseFormula(tokenizer.nextToken(), tokenizer));
        }
        return first;
    }

    private static Sentence parseAtomic(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        if (tkn == null) {
            throw new FormulaParsingException("Could not parse formula!");
        }
        if (tkn.equals(Operator.LNOT.getUTFCode())) {
            return new Negation(parseAtomic(tokenizer.nextToken(), tokenizer));
        }
        ArrayList<Quantifier> quantifiers = new ArrayList<>(1);
        while (tkn.equals(Operator.LEXST.getUTFCode()) || tkn.equals(Operator.LALL.getUTFCode())) {
            String freeVar = parseFreeVariable(tokenizer.nextToken());
            quantifiers.add(tkn.equals(Operator.LEXST.getUTFCode()) ? new Exists(freeVar) : new Forall(freeVar));
            tkn = tokenizer.nextToken();
        }
        if (!quantifiers.isEmpty()) {
            return new QuantifierFormula(quantifiers, parseAtomic(tkn, tokenizer));
        }
        if (tkn.equals("(")) {
            Sentence rtn = parseFormula(tokenizer.nextToken(), tokenizer);
            if (!tokenizer.isNextEqualTo(")")) {
                throw new FormulaParsingException("No closing parenthesis!");
            }
            return rtn;
        }
        return parseTerminal(tkn, tokenizer);
    }

    private static Sentence parseTerminal(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        if (Character.isUpperCase(tkn.charAt(0))) {
            return parsePredicateSymbol(tkn, tokenizer);
        }
        if (Character.isLowerCase(tkn.charAt(0))) {
            return parseEquality(tkn, tokenizer);
        }
        if (tkn.equals(Operator.LCONTR.getUTFCode())) {
            return Contradiction.getInstance();
        }
        throw new FormulaParsingException("Invalid predicate!");
    }



    private static PredicateSymbol parsePredicateSymbol(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        if (!tkn.matches("[A-Z][A-Za-z0-9]*")) {
            throw new FormulaParsingException("Invalid predicate symbol! Should start with a capital letter and contain alphanumeric symbols!");
        }
        ArrayList<Argument> arguments = null;
        if (tokenizer.isNextEqualTo("(")) {
            arguments = parseArguments(tokenizer.nextToken(), tokenizer);
            if (!tokenizer.isNextEqualTo(")")) {
                throw new FormulaParsingException("No closing parenthesis! #2");
            }
        }
        return new PredicateSymbol(tkn, arguments);
    }

    private static ArrayList<Argument> parseArguments(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        ArrayList<Argument> arguments = new ArrayList<>(1);
        arguments.add(parseArgument(tkn, tokenizer));
        while(tokenizer.isNextEqualTo(",")) {
            arguments.add(parseArgument(tokenizer.nextToken(), tokenizer));
        }
        return arguments;
    }

    private static Argument parseArgument(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        if (!tkn.matches("[a-z][A-Za-z0-9]*")) {
            throw new FormulaParsingException("Invalid identifier symbol! Should start with a lowercase letter and contain alphanumeric symbols!");
        }
        ArrayList<Argument> arguments = null;
        if (tokenizer.isNextEqualTo("(")) {
            arguments = parseArguments(tokenizer.nextToken(), tokenizer);
            if (!tokenizer.isNextEqualTo(")")) {
                throw new FormulaParsingException("No closing parenthesis! #2");
            }
        }
        return arguments == null ? new Name(tkn) : new FunctionSymbol(tkn, arguments);
    }

    private static Equality parseEquality(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        Argument firstOperand = parseArgument(tkn, tokenizer);
        if(!tokenizer.nextToken().equals(Operator.EQUALS.getUTFCode())) {
            throw new FormulaParsingException("Expected equality!");
        }
        return new Equality(firstOperand, parseArgument(tokenizer.nextToken(), tokenizer));
    }


    private static String parseFreeVariable(String tkn) throws FormulaParsingException {
        if (tkn.length() != 1 || tkn.charAt(0) < 't' || tkn.charAt(0) > 'z') {
            throw new FormulaParsingException("A free variable may only be a letter from t to z.");
        }
        return tkn;
    }

}
