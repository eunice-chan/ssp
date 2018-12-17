package ssp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import ssp.Pairer;

/** Secret Santa pairer.
 *  @author Eunice Chan
 */
public final class Main {

    public static void main(String[] args) {
        //input: System.in or File
        String file = "Test.txt";
        try {
            Scanner scanner = new Scanner(new File(file));
            System.out.println("In a text document, please enter the participants' names, separated by a single comma. When done, type \"DONE.\".");
            System.out.println("If certain people cannot be paired, on a new line, write * NAME, NAME for each pair.");
            System.out.println();
            String name = "";
            List<String> participants = new ArrayList<>();
            String[] pairs;
            Pairer pairer;
            Boolean ppl = true;
            while (scanner.hasNext() && ppl) {
                name += scanner.next();
                if (name.toUpperCase().equals("DONE.")) {
                    ppl = false;
                } else if (name.endsWith(",")) {
                    participants.add(name.substring(0, name.length() - 1));
                    name = "";
                } else {
                    name += " ";
                }
            }
            Boolean rules = true;
            String banned;
            String[] bannedppl;
            HashMap<String, ArrayList<String>> rule = new HashMap<>();
            while (scanner.hasNext() && rules) {
                if (! scanner.next().equals("*")) {
                    break;
                }
                banned = scanner.nextLine().trim();
                bannedppl = banned.split(",");
                bannedppl[1] = bannedppl[1].trim();
                if (!rule.containsKey(bannedppl[0])) {
                    rule.put(bannedppl[0], new ArrayList<>());
                }
                if (!rule.containsKey(bannedppl[1])) {
                    rule.put(bannedppl[1], new ArrayList<>());
                }
                rule.get(bannedppl[0]).add(bannedppl[1]);
                rule.get(bannedppl[1]).add(bannedppl[0]);
            }
            pairer = new Pairer(participants, rule);
            pairs = pairer.pair();
            for (String pair : pairs) {
                System.out.println(pair);
            }
        } catch (FileNotFoundException e) {
            System.err.printf("Could not open %s for writing.",
                    file);
            System.exit(1);
        }
    }
}