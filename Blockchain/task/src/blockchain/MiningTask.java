package blockchain;

import java.util.concurrent.Callable;

public class MiningTask implements Callable<Block> {
    private final Blockchain blockchain;
    private int zeros;

    public MiningTask(Blockchain blockchain, int zeros) {
        this.blockchain = blockchain;
        this.zeros = zeros;
    }

    @Override
    public Block call() throws Exception {
        Block newBlock = blockchain.genBlock(zeros);
        newBlock.setMinedBy(Thread.currentThread().getId());
        return newBlock;
    }
}