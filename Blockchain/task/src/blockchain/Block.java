package blockchain;

import java.io.Serializable;
import java.util.Date;
import java.security.MessageDigest;
import java.util.List;

public class Block implements Serializable {
    private int id;
    private final long timeStamp;
    private final String hash;
    private String prevHash;
    private long magicNum;
    private long timeGenerating;
    private long minedBy;
    private List<Message> messages;

    public Block(int zeros) {
        this.timeStamp = new Date().getTime();
        this.hash = generateHash(zeros);
        this.timeGenerating = new Date().getTime() - timeStamp;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMinedBy(long minedBy) {
            this.minedBy = minedBy;
    }

    public long getTimeGenerating() {
        return this.timeGenerating;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public String getPrevHash() {
        return this.prevHash;
    }

    public int getId() {
        return this.id;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public String getHash() {
        return this.hash;
    }

    private String generateHash(int zeros) {
        magicNum = 0;
        String hashString = "";
        String newHash = "";
        boolean validHash = true;
        do {
            hashString = this.getId() + String.valueOf(this.getTimeStamp()) +
                    String.valueOf(magicNum) + prevHash;
            validHash = true;
            newHash = applySha256(hashString);
            for (int i = 0; i < zeros; i++) {
                if (newHash.charAt(i) != '0') {
                    //System.out.println(newHash);
                    validHash = false;
                    break;
                }
            }
            magicNum++;
        } while (!validHash);
        return newHash;
    }

    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b: hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        String messageString = "";
        for (Message message : messages) {
            messageString += "\n" + message.toString();
        }
        if (messages.isEmpty()) {
            messageString = "no messages";
        }
        return "\nBlock:\nCreated by: miner" + this.minedBy +
                "\nminer" + this.minedBy + " gets 100 VC" +
                "\nId: " + this.id +
                "\nTimestamp: " + this.timeStamp +
                "\nMagic number: " + this.magicNum +
                "\nHash of the previous block:\n" + this.prevHash +
                "\nHash of the block:\n" + this.hash +
                "\nBlock data: " + messageString +
                "\nMessages in block: " + messages.size() +
                "\nBlock was generating for " + this.timeGenerating + " milliseconds";
    }
}
