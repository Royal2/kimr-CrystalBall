package android.kimr.crystalball;

import java.util.Random;

public class Predictions {
    private static Predictions predictions;
    private String[] answers;


    //Random answers = new Random();----------------

    //CrystalBallApp predictions - text
    private Predictions() {
        answers = new String[]
        {
            "10% chance.",
            "20% chance.",
            "20% chance.",
            "30% chance.",
            "30% chance.",
            "30% chance.",
            "40% chance.",
            "40% chance.",
            "40% chance.",
            "40% chance.",
            "50% chance.",
            "50% chance.",
            "50% chance.",
            "50% chance.",
            "50% chance.",
            "50% chance.",
            "50% chance.",
            "50% chance.",
            "50% chance.",
            "50% chance."

        };
        //return answers;-------------
    }

    public static Predictions get() {
        if(predictions == null)
        {
            predictions = new Predictions();
        }
        return predictions;
    }

    public String getPredictions() {
        Random predictions = new Random();
        return answers[predictions.nextInt(answers.length)];
    }
}
