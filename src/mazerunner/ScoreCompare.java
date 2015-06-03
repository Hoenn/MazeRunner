package mazerunner;

import java.util.Comparator;
import mazerunner.Score;

public class ScoreCompare implements Comparator<Score> 
{
    public int compare(Score score1, Score score2) 
    {

        long sc1 = score1.getScore();
        long sc2 = score2.getScore();

        if (sc1 > sc2)
        {
            return -1;
        }else if (sc1 < sc2)
        {
            return +1;
        }else
        {
            return 0;
        }
    }
}
