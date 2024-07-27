package org.githubwuzupkev.tools;

import java.util.Random;
public class UsefulFunction {
    public static String generateRandomChars () {
        String candidateChars="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder ();
        Random random = new Random ();
        for (int i = 0; i < 8; i ++) {
            sb.append (candidateChars.charAt (random.nextInt (candidateChars
                    .length ())));
        }
        return sb.toString ();
    }
}
