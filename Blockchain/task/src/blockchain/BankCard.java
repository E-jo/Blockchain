package blockchain;

import java.util.*;

public class BankCard {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String numbers = scn.nextLine();
        String card = numbers.replaceAll("\\s", "");
        String visaRegex = "4[0-9]{15}";
        String masterCardRegex = "(5[1-5][0-9]{14})|(2[2-7][0-9][0-9][0-9]{12})";
        String americanExpressRegex = "3[47][0-9]{13}";

        if (card.matches(visaRegex)) {
            System.out.println("Visa");
        } else if (card.matches(masterCardRegex)) {
            System.out.println("MasterCard");
        } else if (card.matches(americanExpressRegex)) {
            System.out.println("AmericanExpress");
        } else {
            System.out.println("Such card does not exist");
        }
    }
}