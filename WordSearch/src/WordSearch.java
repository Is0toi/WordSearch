
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Random;

public class WordSearch{
    public static void main (String[] args){
        // JFrame frame = new JFrame();
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(300,200);
        // frame.setVisible(true);


        // JOptionPane.showMessageDialog(null,"Welcome to to Word Hunt Showdown! \n Before we get started, would you like to play single player or two player mode?");
        String playerOptions[] = {"1 Player", "2 Players"};
        int choice = JOptionPane.showOptionDialog(null, "Welcome to to Word Hunt Showdown! \n Before we get started, would you like to play single player or two player mode?", "Welcome To WordSearch!", JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,null,new String[] {"1 Player", "2 Players"}, "1 Player");

        if(choice == 0){
            JOptionPane.showMessageDialog(null, "In single player, the program will generate a wordsaerch for you to solve. \n You will need to find 5 words with this distribution: \n -(2) 4 letter words \n -(3) 5 letter words \n Good luck!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Get ready for a two-player competition! \n" + 
            "In this game mode, both players will compete in a battle where they each create custom word searches for the other to solve. \n" +
            "Player 1 starts by choosing a theme and providing 5 words related to it. \n" +
            "Player 2 earns 5 points for each theme word they find and 2 bonus points for discovering words valid on the grid. \n" +
            "One Player 2 uncovers all the words, or decides to give up, the roles switch!\n"+
            "At the end, the player with the most points wins the title of WORD HUNT CHAMPIONNNNNN" +
            " Good luck and let the battle begin >:D");

            String player1 = JOptionPane.showInputDialog(null, "Enter Player 1's name:");
            String player2 = JOptionPane.showInputDialog(null,"Enter Player 2's name:");

            String player1Theme = JOptionPane.showInputDialog(null,"Okay " + player1 + ", are you ready? Enter a theme for you word search (Please be specific):");
            JOptionPane.showMessageDialog(null, "Excellent choice! Now, enter 5 words (between 3-15 characters) you'd like for the program to scramble.", "Okay!", JOptionPane.INFORMATION_MESSAGE);
        
            String player1word1 = JOptionPane.showInputDialog(null, "Word 1:");
            
            while(isWord(player1word1) == false || player1word1.length() > 15 ||  player1word1.length() < 3){
                player1word1 = JOptionPane.showInputDialog(null, "Please enter a valid word, less than 15 characters and greater than 3:");
            }
            
            player1word1 = player1word1.toUpperCase();

            String player1word2 = JOptionPane.showInputDialog(null, "Word 2:");
            
            while(isWord(player1word2) == false || player1word2.length() > 15 ||  player1word2.length() < 3){
                player1word2 = JOptionPane.showInputDialog(null, "Please enter a valid word, less than 15 characters and greater than 3:");
            }
            
            player1word2 = player1word2.toUpperCase();
        
            String player1word3 = JOptionPane.showInputDialog(null, "Word 3:");
            
            while(isWord(player1word3) == false || player1word3.length() > 15 ||  player1word3.length() < 3){
                player1word3 = JOptionPane.showInputDialog(null, "Please enter a valid word, less than 15 characters and greater than 3:");
            }
            
            player1word3 = player1word3.toUpperCase();
        

            String player1word4 = JOptionPane.showInputDialog(null, "Word 4:");
            
            while(isWord(player1word4) == false || player1word4.length() > 15 ||  player1word4.length() < 3){
                player1word4 = JOptionPane.showInputDialog(null, "Please enter a valid word, less than 15 characters and greater than 3:");
            }
            
            player1word4 = player1word4.toUpperCase();
        

            String player1word5 = JOptionPane.showInputDialog(null, "Word 5:");
            
            while(isWord(player1word5) == false || player1word5.length() > 15 ||  player1word5.length() < 3){
                player1word5 = JOptionPane.showInputDialog(null, "Please enter a valid word, less than 15 characters and greater than 3:");
            }
            
            player1word5 = player1word5.toUpperCase();
        
            JOptionPane.showMessageDialog(null, "Thank you " + player1 + "! We'll get to work...", "Okay!", JOptionPane.INFORMATION_MESSAGE);
        
            JOptionPane.showMessageDialog(null,"Aaaaand we're back! " + player2 + ", are you ready to find the words with the theme: " + player1Theme +". Good luck!", "Okay!", JOptionPane.INFORMATION_MESSAGE);
            
            // Making board
            Board board1 = new Board();
            board1.makeWordSearch(player1word1,player1word2,player1word3,player1word4,player1word5);
            board1.fillRandomLetters();
            board1.setVisible(true);
            
        }




    }



    public static boolean isWord(String word){
        //Checks on the dictionary if it is a word
        try {
            String dictionaryPage = "https://www.merriam-webster.com/dictionary/" + word;
            //This works because whatever word it is will be at the end of the website URL
            URL dictionary = new URI(dictionaryPage).toURL();
            URLConnection connection = dictionary.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));            
            String inputLine;
            String pageContent = "";
            while((inputLine = in.readLine()) != null){
                pageContent += inputLine;
            }
            in.close();

            if(pageContent.contains("Sorry, the word you're looking for can't be found in the dictionary")|| pageContent.contains("The word you've entered isn't in the dictionary.")){
                return false;
            } else{
                return true;
            }
        } catch (FileNotFoundException ex) {
             return false;
         } catch (MalformedURLException e) {
             return false;
         } catch (URISyntaxException e) {
             return false;
         } catch (IOException e) {
             return false;
         }
    }
}