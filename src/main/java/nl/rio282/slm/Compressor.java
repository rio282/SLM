package nl.rio282.slm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compressor {

    private final Map<String, Integer> pairs = new HashMap<>();
    private final List<String> tokens;


    public Compressor(List<String> tokens) {
        this.tokens = tokens;
    }

    private Map<String, Integer> getPairs(List<String> tokens) {
        Map<String, Integer> pairs = new HashMap<>();

        tokens.forEach(token -> {
            String pair;
            for (int i = 0; i < token.length() - 1; ++i) {
                pair = token.substring(i, i + 2);
                pairs.put(pair, pairs.getOrDefault(pair, 0) + 1);
            }
        });

        return pairs;
    }

    public List<String> useBytePairEncoding() {
        Map<String, Integer> pairs = getPairs(tokens);

        String bestPair = pairs.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new RuntimeException("No pairs found"));

        List<String> encodedTokens = new ArrayList<>();
        tokens.forEach(token -> {
            encodedTokens.add(token.replace(bestPair, bestPair.charAt(0) + bestPair.charAt(1) + "")); // + "" to convert to string;
        });

        return encodedTokens;
    }

}
