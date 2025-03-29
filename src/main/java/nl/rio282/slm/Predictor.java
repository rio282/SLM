package nl.rio282.slm;

import java.util.*;

public class Predictor {

    private final Tokenizer tokenizer;

    private final Map<String, Integer> frequencies = new HashMap<>();
    private final Map<String, List<String>> pairs = new HashMap<>();

    public Predictor(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void performAnalysis() {
        List<String> tokens = tokenizer.getTokens();
        for (int i = 0; i < tokens.size() - 1; i++) {
            String word = tokens.get(i);
            frequencies.put(word, frequencies.getOrDefault(word, 0) + 1);
            String nextWord = tokens.get(i + 1);
            pairs.computeIfAbsent(word, k -> new ArrayList<>()).add(nextWord);
        }
    }

    public String predictNextToken(String token) {
        // check if the token exists in the pairs map
        if (!pairs.containsKey(token)) {
            return "."; // no predictions available, just end sentence. LMAO
        }

        Map<String, Integer> nextPossibleTokens = new HashMap<>();
        for (String possibleToken : pairs.get(token)) {
            nextPossibleTokens.put(possibleToken, nextPossibleTokens.getOrDefault(possibleToken, 0) + 1);
        }

        return nextPossibleTokens.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(".");
    }

}
