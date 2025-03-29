package nl.rio282.slm;

import java.util.*;
import java.util.stream.Collectors;

public class Predictor {

    // should be between 0.0-1.0
    public static final float TEMPERATURE = 0.673483F;


    private final Tokenizer tokenizer;

    private final Map<String, Integer> frequencies = new HashMap<>();
    private final Map<String, List<String>> pairs = new HashMap<>();

    public Predictor(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void performAnalysis() {
        List<String> tokens = tokenizer.getTokens();
        for (int i = 0; i < tokens.size() - 1; ++i) {
            String word = tokens.get(i);
            frequencies.put(word, frequencies.getOrDefault(word, 0) + 1);
            String nextWord = tokens.get(i + 1);
            pairs.computeIfAbsent(word, k -> new ArrayList<>()).add(nextWord);
        }
    }

    public String predictNextToken(String token) {
        if (!pairs.containsKey(token)) {
            return "."; // no predictions available, just end sentence. LMAO
        }

        Map<String, Integer> nextPossibleTokens = new HashMap<>();
        for (String possibleToken : pairs.get(token)) {
            nextPossibleTokens.put(possibleToken, nextPossibleTokens.getOrDefault(possibleToken, 0) + 1);
        }

        // list of tokens sorted by frequency
        List<Map.Entry<String, Integer>> sortedTokens = nextPossibleTokens
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                .toList();

        // apply temperature to adjust probabilities
        double totalWeight = 0;
        List<Double> probabilities = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedTokens) {
            double adjustedFrequency = Math.pow(entry.getValue(), 1.0 / TEMPERATURE);  // temperature scaling
            totalWeight += adjustedFrequency;
            probabilities.add(adjustedFrequency);
        }

        // normalize the probabilities
        for (int i = 0; i < probabilities.size(); ++i) {
            probabilities.set(i, probabilities.get(i) / totalWeight);
        }

        // select token based on the weighted probabilities
        double rand = Math.random();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < sortedTokens.size(); ++i) {
            cumulativeProbability += probabilities.get(i);
            if (rand < cumulativeProbability) return sortedTokens.get(i).getKey();
        }

        return "."; // fallback
    }

}
