import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerationFuzzer implements IFuzzer {

    private final List<String> generated;

    public GenerationFuzzer() {
        this.generated = new ArrayList<>();
    }

    @Override
    public void fuzz(int numInputs) {
        generated.clear();
        Random rand = new Random(System.nanoTime());
        for(int i = 0; i < numInputs; i++) {
            StringBuilder s = new StringBuilder(generate());
            for(int j = 0; j < rand.nextInt(10); j++) {
                s.append(" | ").append(generate());
            }
            generated.add(s.toString());
        }
    }

    private String generate() {
        Random rand = new Random(System.nanoTime());
        int i = rand.nextInt(17);
        String result = "";
        switch (i) {
            case 0:
                result += "cat " + randString(rand.nextInt(5));
                break;
            case 1:
                result += "touch " + randString(rand.nextInt(5));
                break;
            case 2:
                result += "ls";
                if (rand.nextBoolean())
                    result += " -l";
                break;
            case 3:
                result += "more";
                break;
            case 4:
                result += "less";
                break;
            case 5:
                result += "head " + randString(rand.nextInt(5));
                break;
            case 6:
                result += "tail " + randString(rand.nextInt(5));
                break;
            case 7:
                result += "mkdir " + randString(rand.nextInt(10));
                break;
            case 8:
                result += "cp " + randString(rand.nextInt(5)) + " " + randString(rand.nextInt(5));
                break;
            case 9:
                result += "mv " + randString(rand.nextInt(5)) + " " + randString(rand.nextInt(5));
                break;
            case 10:
                result += "rm";
                if (rand.nextBoolean())
                    result += " -f";
                result += " " + randString(rand.nextInt(10));
                break;
            case 11:
                result += "grep " + '\"' + randString(rand.nextInt(10)) + '\"';
                break;
            case 12:
                result += "sort";
                if (rand.nextBoolean()) {
                    result += " -n";
                    if (rand.nextBoolean())
                        result += "r";
                }
                result += " " + randString(rand.nextInt(5));
                break;
            case 13:
                result += "wc " + randString(rand.nextInt(5));
                break;
            case 14:
                result += "cut " + randString(rand.nextInt(5));
                break;
            case 15:
                result += "cd " + randString(rand.nextInt(5));
                break;
            case 16:
                if (rand.nextBoolean())
                    result += "history " + rand.nextInt(100);
                else result += "history -c";
                break;

        }
        return result;
    }

    public List<String> getGenerated() {
        return generated;
    }
}
