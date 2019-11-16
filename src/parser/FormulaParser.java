package parser;

import formula.*;
import proof.Operator;
import tokenizer.FormulaTokenizer;

import java.util.ArrayList;

public final class FormulaParser {
    public static Formula parse(String formulaString) throws FormulaParsingException {
        FormulaTokenizer tokenizer = new FormulaTokenizer(formulaString);
        return parseFormula(tokenizer.nextToken(), tokenizer);
    }

    private static Formula parseFormula(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        ArrayList<Quantifier> quantifiers = new ArrayList<>(1);
        while (tkn.equals(Operator.LEXST.getUTFCode()) || tkn.equals(Operator.LALL.getUTFCode())) {
            String freeVar = parseFreeVariable(tokenizer.nextToken());
            quantifiers.add(tkn.equals(Operator.LEXST.getUTFCode()) ? new Exists(freeVar) : new Forall(freeVar));
            tkn = tokenizer.nextToken();
        }
        return new Formula(quantifiers, parseWff(tkn, tokenizer));
    }

    private static Wff parseWff(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        Implication antecedent = parseImplication(tkn, tokenizer);
        return new Wff(antecedent, tokenizer.isNextEqualTo(Operator.LIFF.getUTFCode())
                ? parseImplication(tokenizer.nextToken(), tokenizer) : null);
    }

    private static Implication parseImplication(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {

        Conjunction antecedent = parseConjunction(tkn, tokenizer);
        return new Implication(antecedent, tokenizer.isNextEqualTo(Operator.LIF.getUTFCode())
                ? parseConjunction(tokenizer.nextToken(), tokenizer) : null);
    }

    private static Conjunction parseConjunction(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        ArrayList<Disjunction> disjunctions = new ArrayList<>(2);
        disjunctions.add(parseDisjunction(tkn, tokenizer));
        while (tokenizer.hasMoreTokens() && tokenizer.isNextEqualTo(Operator.LAND.getUTFCode())) {
            disjunctions.add(parseDisjunction(tokenizer.nextToken(), tokenizer));
        }
        return new Conjunction(disjunctions);
    }

    private static Disjunction parseDisjunction(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        ArrayList<Atom> atoms = new ArrayList<>(2);
        atoms.add(parseAtom(tkn, tokenizer));
        while (tokenizer.hasMoreTokens() && tokenizer.isNextEqualTo(Operator.LOR.getUTFCode())) {
            atoms.add(parseAtom(tokenizer.nextToken(), tokenizer));
        }
        return new Disjunction(atoms);

    }

    private static Atom parseAtom(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        int nrNeg = 0;
        while (tkn.equals(Operator.LNOT.getUTFCode())) {
            ++nrNeg;
            tkn = tokenizer.nextToken();
        }
        return new Atom(nrNeg, parsePredicate(tkn, tokenizer));
    }

    private static Predicate parsePredicate(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        if (Character.isUpperCase(tkn.charAt(0))) {
            return parsePredicateSymbol(tkn, tokenizer);
        }
        if (Character.isLowerCase(tkn.charAt(0))) {
            return parseEquality(tkn, tokenizer);
        }
        if (tkn.equals("(")) {
            return parseNestedFormula(tokenizer.nextToken(), tokenizer);
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



    private static NestedFormula parseNestedFormula(String tkn, FormulaTokenizer tokenizer) throws FormulaParsingException {
        Formula formula = parseFormula(tkn, tokenizer);
        if (!tokenizer.isNextEqualTo(")")) {
            throw new FormulaParsingException("No closing parenthesis! #3");
        }
        return new NestedFormula(formula);
    }

    private static String parseFreeVariable(String tkn) throws FormulaParsingException {
        if (tkn.length() != 1 || tkn.charAt(0) < 't' || tkn.charAt(0) > 'z') {
            throw new FormulaParsingException("A free variable may only be a letter from t to z.");
        }
        return tkn;
    }

}
