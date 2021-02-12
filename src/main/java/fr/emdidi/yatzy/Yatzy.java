package fr.emdidi.yatzy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Yatzy {

    private static final int DICES_NUMBER = 5;

    private static final int DICE_MAX_NUMBER = 6;

    public static int chance(int d1, int d2, int d3, int d4, int d5)
    {

        return IntStream.of(d1, d2, d3, d4, d5).sum();
    }

    public static int yatzy(int... dice)
    {
        if(dice.length != DICES_NUMBER) {
            // FIXME : a nicer exception would be better.
            throw new IllegalArgumentException("Incorrect number of dices.");
        }

        return IntStream.of(dice).allMatch(number -> number == dice[0]) ? 50 : 0;
    }

    private static int accumulator(int value, int ... dice) {
        if (dice == null || dice.length == 0 ) {
            throw new IllegalArgumentException("The array is missing.");
        }
        return IntStream.of(dice).filter(number -> number == value).reduce(0, (a,b) -> a + value);
    }

    public static int ones(int d1, int d2, int d3, int d4, int d5) {
        return accumulator(1, d1, d2, d3, d4, d5);
    }

    public static int twos(int d1, int d2, int d3, int d4, int d5) {
        return accumulator(2, d1, d2, d3, d4, d5);
    }

    public static int threes(int d1, int d2, int d3, int d4, int d5) {
        return accumulator(3, d1, d2, d3, d4, d5);
    }

    protected int[] dice;
    public Yatzy(int d1, int d2, int d3, int d4, int d5)
    {
        dice = new int[DICES_NUMBER];
        dice[0] = d1;
        dice[1] = d2;
        dice[2] = d3;
        dice[3] = d4;
        dice[4] = d5;
    }

    public int fours()
    {
        return accumulator(4, dice);
    }

    public int fives()
    {
        return accumulator(5, dice);
    }

    public int sixes()
    {
        return accumulator(6, dice);
    }

    private static int highestOfAKind(int numberOfKind, Integer... dice)
    {
        if (dice == null || dice.length == 0 ) {
            throw new IllegalArgumentException("The array is missing.");
        }

        List<Integer> dices = Arrays.asList(dice);
        Optional<Integer> pairFound = dices.stream().sorted(Collections.reverseOrder())
                .filter(number -> Collections.frequency(dices, number) >= numberOfKind).findFirst();

        return pairFound.isPresent() ? pairFound.get() : 0;
    }

    public static int score_pair(int d1, int d2, int d3, int d4, int d5)
    {
        return highestOfAKind(2, d1, d2, d3, d4, d5)  * 2;
    }

    public static int two_pair(int d1, int d2, int d3, int d4, int d5)
    {
        List<Integer> dices = Arrays.asList(d1, d2, d3, d4, d5);
        List<Integer> pairsFound = dices.stream().sorted(Collections.reverseOrder())
                .filter(number -> Collections.frequency(dices, number) >= 2)
                .distinct()
                .collect(Collectors.toList());
        Collections.sort(pairsFound, Collections.reverseOrder());
        return IntStream.range(0, 2).reduce(0, (a, b) -> a + pairsFound.get(b) * 2);
    }

    public static int four_of_a_kind(int d1, int d2, int d3, int d4, int d5)
    {
        return highestOfAKind(4, d1, d2, d3, d4, d5)  * 4;
    }

    public static int three_of_a_kind(int d1, int d2, int d3, int d4, int d5)
    {
        return highestOfAKind(3, d1, d2, d3, d4, d5)  * 3;
    }

    public static int smallStraight(int d1, int d2, int d3, int d4, int d5)
    {
        List<Integer> dices = Arrays.asList(d1, d2, d3, d4, d5).stream().sorted().collect(Collectors.toList());
        boolean result =  Arrays.asList(1, 2, 3, 4, 5).equals(dices);
        return result ? 15 : 0;
    }

    public static int largeStraight(int d1, int d2, int d3, int d4, int d5)
    {
        int[] tallies;
        tallies = new int[6];
        tallies[d1-1] += 1;
        tallies[d2-1] += 1;
        tallies[d3-1] += 1;
        tallies[d4-1] += 1;
        tallies[d5-1] += 1;
        if (tallies[1] == 1 &&
            tallies[2] == 1 &&
            tallies[3] == 1 &&
            tallies[4] == 1
            && tallies[5] == 1)
            return 20;
        return 0;
    }

    public static int fullHouse(int d1, int d2, int d3, int d4, int d5)
    {
        int[] tallies;
        boolean _2 = false;
        int i;
        int _2_at = 0;
        boolean _3 = false;
        int _3_at = 0;




        tallies = new int[6];
        tallies[d1-1] += 1;
        tallies[d2-1] += 1;
        tallies[d3-1] += 1;
        tallies[d4-1] += 1;
        tallies[d5-1] += 1;

        for (i = 0; i != 6; i += 1)
            if (tallies[i] == 2) {
                _2 = true;
                _2_at = i+1;
            }

        for (i = 0; i != 6; i += 1)
            if (tallies[i] == 3) {
                _3 = true;
                _3_at = i+1;
            }

        if (_2 && _3)
            return _2_at * 2 + _3_at * 3;
        else
            return 0;
    }
}



