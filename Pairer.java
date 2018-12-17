package ssp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/** Pairs people.
 *  @author Eunice Chan
 */
class Pairer {
    /** Pairs secret Santas from the list of participants. */
    Pairer(List<String> participants, HashMap<String, ArrayList<String>> rules) {
        _santas = participants;
        _rules = rules;
        _decided = new ArrayList<>();
    }

    /** Pairs up secret Santas.
     * @return String array of "[Gifter] is Secret Santa to [giftee]."
     */
    protected String[] pair() {
        Random randInd = new Random();
        String[] pairs = new String[_santas.size()];
        String statement;
        int ind = 0;
        int pInd  = 0;
        String giftee = _santas.get(ind);
        Boolean reRoll = false;
        for (String gifter : _santas) {
            while (giftee.equals(gifter) || _decided.contains(giftee) || reRoll) {
                ind = randInd.nextInt(_santas.size());
                giftee = _santas.get(ind);
                if (_rules.containsKey(giftee) && _rules.get(giftee).contains(gifter)) {
                    if (_santas.size() == _rules.get(gifter).size()) {
                        System.out.println(String.format("Could not find recipient for %s.", gifter));
                        giftee = gifter;
                    }
                        reRoll = true;
                } else {
                    reRoll = false;
                }
            }
            statement = String.format("%s is Secret Santa to %s.", gifter, giftee);
            pairs[pInd] = statement;
            _decided.add(giftee);
            pInd += 1;
        }
        return pairs;
    }

    /** List of participants. */
    private List<String> _santas;
    /** List of people with a Santa. */
    private ArrayList<String> _decided;
    /** List of people who cannot be paired. */
    private HashMap<String, ArrayList<String>> _rules;
}