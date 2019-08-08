package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import proof.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public final class Parser {

    public static Proof parse(String fileName) throws IOException {
        File input = new File(fileName);
        Document doc = Jsoup.parse(input, "UTF-8");
        Element mainDiv = doc.selectFirst("div.proof-inner-outline");
        if(mainDiv == null || !mainDiv.child(0).attr("class").equals("proof")) {
            throw new IOException("Invalid proof html document. Code #0001.");
        }
        return parseMainProof(mainDiv.child(0));
    }


    private static Proof parseMainProof(Element proofWrapper) throws IOException {
        Elements children = proofWrapper.children();
        Element curr;
        Proof proof = new Proof();
        Iterator iter = children.iterator();
        label:
        while (iter.hasNext()) {
            curr = (Element) iter.next();
            if (!curr.tagName().equals("div")) {
                throw new IOException("Invalid proof html document. Code #0002.");
            }
            switch (curr.attr("class")) {
                case "step":
                    proof.addPremise(parsePremise(curr.child(0)));
                    break;
                case "fitchbar":
                    break label;
                default:
                    throw new IOException("Invalid proof html document. Code #0003.");
            }
        }

        while(iter.hasNext()) {
            curr = (Element) iter.next();
            if (!curr.tagName().equals("div")) {
                throw new IOException("Invalid proof html document. Code #0004.");
            }
            switch (curr.attr("class")) {
                case "step":
                    proof.addStep(parseInference(curr.child(0)));
                    break;
                case "proof":
                    proof.addStep(parseSubProof(curr));
                    break;
                default:
                    throw new IOException("Invalid proof html document. Code #0005.");

            }

        }
        return proof;
    }

    private static Premise parsePremise(Element premiseWrapper) throws IOException {
        Element table = premiseWrapper.selectFirst("table");
        if(table == null) {
            throw new IOException("Invalid proof html document. Code #0006.");
        }
        Element wffWrapper = table.selectFirst("span[class=stepFormula]");
        if(wffWrapper == null) {
            throw new IOException("Invalid proof html document. Code #0007.");
        }
        return new Premise(wffWrapper.text());
    }

    private static Inference parseInference(Element inferenceWrapper) throws IOException {
        Element table = inferenceWrapper.selectFirst("table");
        if(table == null) {
            throw new IOException("Invalid proof html document. Code #0008.");
        }
        Element wffWrapper = table.selectFirst("span[class=stepFormula]"),
                ruleNameWrapper = table.selectFirst("span[class=rulename]"),
                ruleSupportWrapper = table.selectFirst("span[class=support]");
        if(wffWrapper == null || ruleNameWrapper == null || ruleSupportWrapper == null) {
            throw new IOException("Invalid proof html document. Code #0009.");
        }
        InferenceRule inferenceRule = InferenceRule.parseInferenceRule(ruleNameWrapper.text().trim());
        if(inferenceRule == null) {
            throw new IOException("Invalid proof html document. Code #0010.");
        }
        return new Inference(wffWrapper.text(), inferenceRule, ruleSupportWrapper.text().substring(3));
    }

    private static SubProof parseSubProof(Element subProofWrapper) throws IOException {
        Elements children = subProofWrapper.children();
        Element premiseWrapper = children.first();
        Element fitchBar = children.get(1);
        if(premiseWrapper == null || !premiseWrapper.tagName().equals("div") || !premiseWrapper.attr("class").equals("step")) {
            throw new IOException("Invalid proof html document. Code #0011.");
        }
        if(fitchBar == null || !fitchBar.tagName().equals("div") || !fitchBar.attr("class").equals("fitchbar")) {
            throw new IOException("Invalid proof html document. Code #0012.");
        }
        SubProof subProof = new SubProof(parsePremise(premiseWrapper));
        List<Element> subProofStepsWrapper = children.subList(2, children.size());
        for(Element curr : subProofStepsWrapper) {
            if (!curr.tagName().equals("div")) {
                throw new IOException("Invalid proof html document. Code #0013.");
            }
            switch (curr.attr("class")) {
                case "step":
                    subProof.addStep(parseInference(curr.child(0)));
                    break;
                case "proof":
                    subProof.addStep(parseSubProof(curr));
                    break;
                default:
                    throw new IOException("Invalid proof html document. Code #0014.");
            }
        }
        return null;
    }

}
