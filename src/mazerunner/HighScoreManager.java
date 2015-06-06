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
    private ArrayList<Score> arcade_scores;
    private ArrayList<Score> zen_scores;
    private ArrayList<Score> maze_scores;
    private final int max = 10; // Number of scores that will be displayed

    private static final String FileNameLoc_Arcade = "res/res/Scores_Arcade";
    private static final String FileNameLoc_Zen = "res/res/Scores_Zen";
    private static final String FileNameLoc_Maze = "res/res/Scores_Maze";

    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    public HighScoreManager() 
    {
        arcade_scores = new ArrayList<Score>();
        zen_scores = new ArrayList<Score>();
        maze_scores= new ArrayList<Score>();
    }
    
    public ArrayList<Score> getArcadeScores() 
    {
        loadArcadeScoreFile();
        sort(arcade_scores);
        return arcade_scores;
    }
    public ArrayList<Score> getZenScores() 
    {
        loadZenScoreFile();
        sort(zen_scores);
        return zen_scores;
    }
    public ArrayList<Score> getMazeScores() 
    {
        loadMazeScoreFile();
        sort(maze_scores);
        return maze_scores;
    }
    
    private void sort(ArrayList<Score> temp) 
    {
        ScoreCompare comparator = new ScoreCompare();
        Collections.sort(temp, comparator);
    }
    
    public void addArcadeScore(String name, long score) 
    {
        loadArcadeScoreFile();
        arcade_scores.add(new Score(name, score));
        updateArcadeScoreFile();
    }
    public void addZenScore(String name, long score) 
    {
        loadZenScoreFile();
        zen_scores.add(new Score(name, score));
        updateZenScoreFile();
    }
    public void addMazeScore(String name, long score) 
    {
        loadMazeScoreFile();
        maze_scores.add(new Score(name, score));
        updateMazeScoreFile();
    }
    
    @SuppressWarnings("unchecked")
	public void loadArcadeScoreFile() 
    {
        try 
        {
            inputStream = new ObjectInputStream(new FileInputStream(FileNameLoc_Arcade));
            arcade_scores = (ArrayList<Score>) inputStream.readObject();
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
    @SuppressWarnings("unchecked")
	public void loadZenScoreFile() 
    {
        try 
        {
            inputStream = new ObjectInputStream(new FileInputStream(FileNameLoc_Zen));
            zen_scores = (ArrayList<Score>) inputStream.readObject();
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
    @SuppressWarnings("unchecked")
	public void loadMazeScoreFile() 
    {
        try 
        {
            inputStream = new ObjectInputStream(new FileInputStream(FileNameLoc_Maze));
            maze_scores = (ArrayList<Score>) inputStream.readObject();
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
    
    public void updateArcadeScoreFile() 
    {
        try 
        {
            outputStream = new ObjectOutputStream(new FileOutputStream(FileNameLoc_Arcade));
            outputStream.writeObject(arcade_scores);
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
    public void updateZenScoreFile() 
    {
        try 
        {
            outputStream = new ObjectOutputStream(new FileOutputStream(FileNameLoc_Zen));
            outputStream.writeObject(zen_scores);
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
    public void updateMazeScoreFile() 
    {
        try 
        {
            outputStream = new ObjectOutputStream(new FileOutputStream(FileNameLoc_Maze));
            outputStream.writeObject(maze_scores);
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
    
    public  String getHighscoreString_Arcade() 
    {
        String highscoreString = "";

        ArrayList<Score> scores;
        scores = getArcadeScores();

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
    public  String getHighscoreString_Zen() 
    {
        String highscoreString = "";

        ArrayList<Score> scores;
        scores = getZenScores();

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
    public  String getHighscoreString_Maze() 
    {
        String highscoreString = "";

        ArrayList<Score> scores;
        scores = getMazeScores();

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
    
    
    public String getDateString_Arcade()
    {
    	 String date = "";

         ArrayList<Score> scores;
         scores = getArcadeScores();

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
    public String getDateString_Zen()
    {
    	 String date = "";

         ArrayList<Score> scores;
         scores = getZenScores();

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
    public String getDateString_Maze()
    {
    	 String date = "";

         ArrayList<Score> scores;
         scores = getMazeScores();

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
