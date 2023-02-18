package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Blockchain implements Serializable {
    private final List<Block> blocks;
    private Block firstBlock;
    private Block lastBlock;
    private List<Message> messages;
    private int currentMessageId;

    public Blockchain() {
        this.blocks = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.currentMessageId = 1;
    }

    public List<Block> getBlocks() {
        return this.blocks;
    }

    public Block genBlock(int zeros) {
        Block newBlock = new Block(zeros);
        if (blocks.size() == 0) {
            newBlock.setPrevHash("0");
            this.firstBlock = newBlock;
        } else {
            newBlock.setPrevHash(lastBlock.getHash());
        }
        return newBlock;
    }

    public void addMessage(Message message) {
        message.setMessageId(currentMessageId++);
        message.setMessageText(message.getMessageText() + message.getMessageId());
        this.messages.add(message);
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public void clearMessages() {
        this.messages.clear();
    }

    public void addBlock(Block newBlock) {
        newBlock.setMessages(messages);
        this.lastBlock = newBlock;
        blocks.add(newBlock);
    }

    public boolean validateBlockchain() {
        for (int i = 1; i < blocks.size(); i++) {
            if (!Objects.equals(blocks.get(i).getPrevHash(), blocks.get(i - 1).getHash())) {
                return false;
            }
        }
        return true;
    }

    public int getNextId() {
        if (this.lastBlock == null) {
            return 1;
        } else {
            return lastBlock.getId() + 1;
        }
    }

    public Block getLastBlock() {
        return this.lastBlock;
    }
}
