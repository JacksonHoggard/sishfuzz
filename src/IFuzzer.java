import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public interface IFuzzer {
    void fuzz(int numInputs);

    default boolean runTests(String filename) throws IOException {
        String[] command = {"./sish"};
        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();
        OutputStream stdin = process.getOutputStream();
        InputStream stdout = process.getInputStream();
        BufferedWriter pWriter = new BufferedWriter(new OutputStreamWriter(stdin));
        BufferedReader fReader = new BufferedReader(new FileReader(filename));
        String line = fReader.readLine();
        Scanner scanner = new Scanner(stdout);
        while(line != null) {
            pWriter.write(line + "\n");
            //pWriter.flush();
            long start = System.nanoTime();
            long elapsed = 0;
            while(elapsed < 5e7)
                elapsed = System.nanoTime() - start;
            line = fReader.readLine();
        }
        pWriter.write("exit\n");
        try{process.waitFor(500, TimeUnit.MILLISECONDS);}
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        scanner.close();
        fReader.close();
        pWriter.close();
        if(process.isAlive()) {
            process.destroy();
            return false;
        }
        return true;
    }

    default String randString(int len) {
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int randomLimitedInt = leftLimit + (int)(random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

}
