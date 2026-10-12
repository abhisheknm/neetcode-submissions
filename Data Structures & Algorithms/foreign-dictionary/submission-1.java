class Solution {
    public String foreignDictionary(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<>();
        Map<Character, Integer> inDegree = new HashMap<>();
        for(int i = 1; i < words.length; i++) {
            String prev = words[i - 1], cur = words[i];
            if(prev.contains(cur) && !prev.equals(cur)) {
                return "";
            }
            for(int j = 0; j < Math.min(prev.length(), cur.length()); j++) {
                if(prev.charAt(j) != cur.charAt(j)) {
                    graph.computeIfAbsent(prev.charAt(j), k -> new HashSet<>()).add(cur.charAt(j));
                    break;
                }
            }
        }
        for(int i = 0; i < words.length; i++) {
            for(int j = 0; j < words[i].length(); j++) {
                inDegree.put(words[i].charAt(j), 0);
            }
        }
        for(var set: graph.values()) {
            for(var child: set) {
                inDegree.merge(child, 1, Integer::sum);
            }
        }
        StringBuilder res = new StringBuilder();
        Queue<Character> sources = new ArrayDeque<>();
        for(var entry: inDegree.entrySet()) {
            if(entry.getValue() == 0) {
                sources.add(entry.getKey());
            }
        }
        while(!sources.isEmpty()) {
            char cur = sources.poll();
            res.append(cur);
            for(var child: graph.getOrDefault(cur, new HashSet<>())) {
                inDegree.merge(child, -1, Integer::sum);
                if(inDegree.get(child) == 0) {
                    sources.add(child);
                }
            }
        }
        return res.length() == inDegree.size()? res.toString(): "";
    }
}


/*

     h -> e -> r



*/