package blockchain;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final Blockchain blockchain;
        Blockchain blockchain1;
        System.out.println("Loading");
        String[] users = { "Tim", "Mike", "Sheila", "Tina", "Doug", "Pam" } ;
        String fileName = "blockchaintest";
        try {
            blockchain1 = (Blockchain) deserialize(fileName);
            if (!blockchain1.validateBlockchain()) {
                throw new Exception("Invalid block chain in file");
            }
        } catch (Exception e) {
            blockchain1 = new Blockchain();
        }
        blockchain = blockchain1;
        Scanner sc = new Scanner(System.in);
        //System.out.print("Enter how many zeros the hash must start with: ");
        //int zeros = sc.nextInt();
        int zeros = 4;
        final int numWorkers = Runtime.getRuntime().availableProcessors() - 1;
        System.out.println(numWorkers + " processors available for miners");

        ScheduledExecutorService messageExec = Executors.newScheduledThreadPool(1);
        messageExec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String testMessage = "test message ";
                Random random = new Random();
                String user = users[random.nextInt(6)];
                blockchain.addMessage(new Message(user, testMessage));
            }
        }, 200, 200, TimeUnit.MILLISECONDS);

        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);

        long startTime = new Date().getTime();
        //mine ten blocks
        for (int i = 0; i < 10; i++) {

            CompletionService<Block> completionService =
                    new ExecutorCompletionService<>(executor);

            Future<?>[] futures = new Future<?>[numWorkers];
            for (int j = 0; j < numWorkers; j++) {
                futures[j] = completionService.submit(new MiningTask(blockchain, zeros));
            }

            Future<Block> firstToComplete = completionService.take();

            try {
                Block newBlock =  firstToComplete.get();
                newBlock.setId(blockchain.getNextId());
                blockchain.addBlock(newBlock);
                System.out.println(blockchain.getLastBlock());

                if (blockchain.getLastBlock().getTimeGenerating() > 5000) {
                    System.out.println("N was decreased to " + --zeros + "\n");
                } else if (blockchain.getLastBlock().getTimeGenerating() < 2000
                        && zeros < 6) {
                    System.out.println("N was increased to " + ++zeros + "\n");
                } else {
                    System.out.println("N remained " + zeros + "\n");
                }

            } catch (ExecutionException ignored) {
            }

            for (int j = 0; j < numWorkers; j++) {
                futures[j].cancel(true);
            }
            blockchain.clearMessages();
        }
        long totalTime = new Date().getTime() - startTime;
        System.out.println("Total time: " + totalTime / 1000 + " seconds");
        System.out.println("Shutting down threads");
        executor.shutdown();
        messageExec.shutdown();
        String a = "g";

        System.out.println("Writing to file");
        try {
            serialize(blockchain, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done");

        System.exit(0);
    }

    public static void serialize(Object obj, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
}
