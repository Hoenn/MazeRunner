package mazerunner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public  class HighScoreManager 
{
    private ArrayList<Score> scores;
    private final int max = 10; // Number of scores that will be displayed

    private static final String FileNameLoc = "res/Scores";

    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    public HighScoreManager() 
    {
        scores = new ArrayList<Score>();
    }
    
    public ArrayList<Score> getScores() 
    {
        loadScoreFile();
        sort();
        return scores;
    }
    
    private void sort() 
    {
        ScoreCompare comparator = new ScoreCompare();
        Collections.sort(scores, comparator);
    }
    
    public void addScore(String name, long score) 
    {
        loadScoreFile();
        System.out.println(score);
        scores.add(new Score(name, score));
        updateScoreFile();
    }
    
    @SuppressWarnings("unchecked")
	public void loadScoreFile() 
    {
        try 
        {
            inputStream = new ObjectInputStream(new FileInputStream(FileNameLoc));
            scores = (ArrayList<Score>) inputStream.readObject();
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("File Not Found: " + e.getMessage());
        } 
        catch (IOException e) 
        {
    		System.out.println("IO Error: " + e.getMessage());
        } 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Class Not Found: " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (outputStream != null) 
                {
                    outputStream.flush();
                    outputStream.close();
                }
            } 
            catch (IOException e) 
            {
                System.out.println("IO Error: " + e.getMessage());
            }
        }
    }
    
    public void updateScoreFile() 
    {
        try 
        {
            outputStream = new ObjectOutputStream(new FileOutputStream(FileNameLoc));
            outputStream.writeObject(scores);
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
        } 
        catch (IOException e) 
        {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (outputStream != null) 
                {
                    outputStream.flush();
                    outputStream.close();
                }
            } 
            catch (IOException e)
            {
                System.out.println("[Update] Error: " + e.getMessage());
            }
        }
    }
    
    public int getMax()
    {
    	return max;
    }
    
    public  String getHighscoreString() 
    {
        String highscoreString = "";

        ArrayList<Score> scores;
        scores = getScores();

        int i = 0;
        int x = scores.size();
        if (x > max) 
        {
            x = max;
        }
        while (i < x) 
        {
        	if (i < 9)
        	{
    			highscoreString += (i + 1) + ".  " + scores.get(i).getName() + ": " + scores.get(i).getScore() + "\n";
        	}
        	else
        	{
        		highscoreString += (i + 1) + ". " + scores.get(i).getName() + ": " + scores.get(i).getScore() + "\n";
        	}
            i++;
        }
        return highscoreString;
    }
    
    public String getDateString()
    {
    	 String date = "";

         ArrayList<Score> scores;
         scores = getScores();

         int i = 0;
         int x = scores.size();
         if (x > max) 
         {
             x = max;
         }
         while (i < x) 
         {
         	date += scores.get(i).getDate() + "\n";
         	i++;
         }
         return date;
    }
}
