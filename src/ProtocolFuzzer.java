import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProtocolFuzzer implements IFuzzer {
    private final List<String> generated;

    private static final String[] CMDS = {
            "ls",
            "pwd",
            "cat /etc/passwd",
            "rm -rf /"
    };

    public ProtocolFuzzer() {
        generated = new ArrayList<>();
    }

    @Override
    public void fuzz(int numInputs) {
        generated.clear();
        for(int i = 0; i < numInputs; i++) {
            generated.add(generate());
        }
    }

    private String generate() {
        Random rand = new Random(System.nanoTime());
        return CMDS[rand.nextInt(CMDS.length)] + randString(rand.nextInt(5));
    }

    public List<String> getGenerated() {
        return generated;
    }
}
