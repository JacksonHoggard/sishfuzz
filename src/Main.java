import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Mutation Fuzzer
        File mFuzzFile = new File("mFuzz");
        mFuzzFile.createNewFile();
        FileWriter mWriter = new FileWriter("mFuzz");
        MutationFuzzer mFuzzer = new MutationFuzzer(1, 10);
        mFuzzer.fuzz(20);
        StringBuilder mFuzzString = new StringBuilder();
        for(int i = 0; i < mFuzzer.getMutations().size(); i++)
            mFuzzString.append(mFuzzer.getMutations().get(i)).append("\n");
        mWriter.write(mFuzzString.toString());
        mWriter.close();
        System.out.println("Mutation Fuzzer Test Cases Generated in File : mFuzz");
        System.out.print("Running mutation fuzzer test cases....");
        boolean mFuzzerResult = mFuzzer.runTests("mFuzz");
        System.out.println("Done.");
        System.out.println("Mutation Fuzzer Tests Passed? : " + mFuzzerResult + "\n");

        // Generation Fuzzer
        File gFuzzFile = new File("gFuzz");
        gFuzzFile.createNewFile();
        FileWriter gWriter = new FileWriter("gFuzz");
        GenerationFuzzer gFuzzer = new GenerationFuzzer();
        gFuzzer.fuzz(20);
        StringBuilder gFuzzString = new StringBuilder();
        for(int i = 0; i < gFuzzer.getGenerated().size(); i++)
            gFuzzString.append(gFuzzer.getGenerated().get(i)).append("\n");
        gWriter.write(gFuzzString.toString());
        gWriter.close();
        System.out.println("Generation fuzzer test cases generated in file : gFuzz");
        System.out.print("Running generation fuzzer test cases....");
        boolean gFuzzerResult = gFuzzer.runTests("gFuzz");
        System.out.println("Done.");
        System.out.println("Generation Fuzzer Tests Passed? : " + gFuzzerResult + "\n");

        // Protocol Fuzzer
        File pFuzzFile = new File("pFuzz");
        pFuzzFile.createNewFile();
        FileWriter pWriter = new FileWriter("pFuzz");
        ProtocolFuzzer pFuzzer = new ProtocolFuzzer();
        pFuzzer.fuzz(5);
        StringBuilder pFuzzString = new StringBuilder();
        for(int i = 0; i < pFuzzer.getGenerated().size(); i++)
            pFuzzString.append(pFuzzer.getGenerated().get(i)).append("\n");
        pWriter.write(pFuzzString.toString());
        pWriter.close();
        System.out.println("Protocol fuzzer test cases generated in file : pFuzz");
        System.out.print("Running generation fuzzer test cases....");
        boolean pFuzzerResult = gFuzzer.runTests("pFuzz");
        System.out.println("Done.");
        System.out.println("Protocol Fuzzer Tests Passed? : " + pFuzzerResult + "\n");
    }
}