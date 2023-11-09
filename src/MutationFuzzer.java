import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MutationFuzzer implements IFuzzer {

    private static final String[] POP = {
            "pwd",
            "touch a1 a2 a3 a4 a5 a6 a7 a8 a9 a10",
            "ls -l",
            "rm -f a1 a2 a3 a4 a5 a6 a7 a8 a9 a10",
            "echo helloooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo",
            "mkdir test",
            "cd test",
            "cd",
            "history 0",
            "history -c",
            "history",
            "ls | wc",
            "find . -print | more",
            "echo hi | cat | cat",
            "cat hist100 |grep -v z -i | sort |uniq|wc -l",
            "cat hist100|cat|cat|cat|cat|cat|cat|cat|cat|cat|cat|cat|cat|cat|cat|cat|cat|c|wc -l"
    };

    private final int MIN_MUTATIONS;
    private final int MAX_MUTATIONS;
    private final List<String> mutations;

    public MutationFuzzer (int minMutations, int maxMutations) {
        this.mutations = new ArrayList<>();
        this.MIN_MUTATIONS = minMutations;
        this.MAX_MUTATIONS = maxMutations;
    }

    @Override
    public void fuzz(int num) {
        mutations.clear();
        for(int i = 0; i < num; i++) {
            Random rand = new Random(System.nanoTime());
            String seed = POP[rand.nextInt(POP.length)];
            int numMuts = (int) (Math.random() * (MAX_MUTATIONS - MIN_MUTATIONS)) + MIN_MUTATIONS;
            String mutation = seed;
            for(int j = 0; j < numMuts; j++) {
                mutation = mutate(mutation);
            }
            mutations.add(mutation);
        }
    }

    private String mutate(String s) {
        Random rand = new Random(System.nanoTime());

        int choice = rand.nextInt(3);
        switch (choice) {
            case 0: return deleteRandChar(s);
            case 1: return insertRandChar(s);
        }
        return flipRandChar(s);
    }

    private String deleteRandChar(String s) {
        if(s.isEmpty())
            return s;

        Random rand = new Random(System.nanoTime());
        int i = rand.nextInt(s.length());
        return s.substring(0, i) + s.substring(i + 1);
    }

    private String insertRandChar(String s) {
        Random rand = new Random(System.nanoTime());
        if(s.isEmpty())
            return "" + (char)(rand.nextInt(26) + 'a');
        int i = rand.nextInt(s.length());
        return s.substring(0, i) + (char)(rand.nextInt(26) + 'a') + s.substring(i + 1);
    }

    private String flipRandChar(String s) {
        if(s.isEmpty())
            return s;

        Random rand = new Random(System.nanoTime());
        int i = rand.nextInt(s.length());
        char c = s.charAt(i);
        int bit = 1 << rand.nextInt(7);
        char newC = (char)((int)(c) ^ bit);
        return s.substring(0, i) + newC + s.substring(i + 1);
    }

    public List<String> getMutations() {
        return mutations;
    }

    public void clearMutations() {
        mutations.clear();
    }
}
