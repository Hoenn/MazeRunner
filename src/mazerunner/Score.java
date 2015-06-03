package mazerunner;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Score  implements Serializable 
{
    private long score;
    private String name;
    private String dateTime;

    public long getScore() 
    {
        return score;
    }

    public String getName() 
    {
        return name;
    }
    
    public String getDate()
    {
		return dateTime;
    }

    public Score(String n, long s) 
    {
        score = s;
        name = n;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		dateTime = dateFormat.format(date);
    }
}