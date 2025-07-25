import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashSet;
import java.util.Set;

public class WordSearch{
    public static void main (String[] args){
        // INTRODUCTION & WORD COLLECTION ------------------------------------------------------------
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
            "Player 2 earns 5 points for each theme word they find \n" +
            "Once Player 2 uncovers all the words, or decides to give up, the roles switch!\n"+
            "Do not repeat words and select words that are 3-15 letters. \n"+
            "At the end, the player with the most points wins the title of WORD HUNT CHAMPIONNNNNN" +
            " Make sure to full screen the board and Good luck! let the battle begin >:D");

            String player1 = JOptionPane.showInputDialog(null, "Enter Player 1's name:");
            String player2 = JOptionPane.showInputDialog(null,"Enter Player 2's name:");

            String player1Theme = JOptionPane.showInputDialog(null,"Okay " + player1 + ", are you ready? Enter a theme for you word search (Please be specific and make sure player 2 doesn't see):");

            final String[] words = new String[5];
            Set<String> usedWords = new HashSet<>();
            for (int i = 0; i < 5; i++) {
                while (true) {
                    String input = JOptionPane.showInputDialog(null, "Word " + (i+1) + ":");
                    if (input == null) System.exit(0); 
                    
                    input = input.trim().toUpperCase();
                    
                    if (input.length() < 3 || input.length() > 15) {
                        JOptionPane.showMessageDialog(null, 
                            "Word must be between 3 - 15 letters. Try again.");
                        continue;
                    }

                    if (usedWords.contains(input)){
                        JOptionPane.showMessageDialog(null, input + " was already entered. Try again.");
                        continue;
                    }
                    
                    if (!isWord(input)) {
                        JOptionPane.showMessageDialog(null, 
                            "\"" + input + "\" is not a valid dictionary word. Try again.");
                        continue;
                    }
                    
                    words[i] = input;
                    usedWords.add(input);
                    break;
                }
            }

            // JOptionPane.showMessageDialog(null, "Excellent choice! Now, enter 5 words (between 3-15 characters) you'd like for the program to scramble.", "Okay!", JOptionPane.INFORMATION_MESSAGE);

            // String player1word1 = JOptionPane.showInputDialog(null, "Word 1:");

            // while(isWord(player1word1) == false || player1word1.length() > 15 ||  player1word1.length() < 3){
            //     player1word1 = JOptionPane.showInputDialog(null, "Please enter a valid word, less than 15 characters and greater than 3:");
            // }

            // String player1word2 = JOptionPane.showInputDialog(null, "Word 2:");

            // while(isWord(player1word2) == false || player1word2.length() > 15 ||  player1word2.length() < 3){
            //     player1word2 = JOptionPane.showInputDialog(null, "Please enter a valid word, less than 15 characters and greater than 3:");
            // }

            // String player1word3 = JOptionPane.showInputDialog(null, "Word 3:");

            // while(isWord(player1word3) == false || player1word3.length() > 15 ||  player1word3.length() < 3){
            //     player1word3 = JOptionPane.showInputDialog(null, "Please enter a valid word, less than 15 characters and greater than 3:");
            // }

            // String player1word4 = JOptionPane.showInputDialog(null, "Word 4:");

            // while(isWord(player1word4) == false || player1word4.length() > 15 ||  player1word4.length() < 3){
            //     player1word4 = JOptionPane.showInputDialog(null, "Please enter a valid word, less than 15 characters and greater than 3:");
            // }

            // String player1word5 = JOptionPane.showInputDialog(null, "Word 5:");

            // while(isWord(player1word5) == false || player1word5.length() > 15 ||  player1word5.length() < 3){
            //     player1word5 = JOptionPane.showInputDialog(null, "Please enter a valid word, less than 15 characters and greater than 3:");
            // }


            // JOptionPane.showMessageDialog(null, "Thank you " + player1 + "! We'll get to work! Turn the computer to " + player2, "Okay!", JOptionPane.INFORMATION_MESSAGE);

            // JOptionPane.showMessageDialog(null,"Aaaaand we're back! " + player2 + ", are you ready to find the words with the theme: " + player1Theme +". Good luck!", "Okay!", JOptionPane.INFORMATION_MESSAGE);

            // BOARD --------------------------------------------------------------------------------
            Board board1 = new Board(player1Theme);
            board1.makeWordSearch(words[0],words[1],words[2],words[3],words[4]);
            board1.fillRandomLetters();
            board1.setVisible(true);

            // LOGIC ON BOARD ---------------------------------------------------------------------

            // int numWordsLeft = 5;
            // int player2Points = 0;
            // int hintsUsedNum2 = 0;
            //Somehow lambda likes this better I've never encountered this situation

            AtomicInteger numWordsLeft = new AtomicInteger(5);
            AtomicInteger player2Points = new AtomicInteger(0);
            AtomicInteger hintsUsedNum2 = new AtomicInteger(0);

            // JButton hintButton = board1.getHintButton();
            board1.getHintButton().addActionListener(e -> {
            if (hintsUsedNum2.get() < 3) {
                hintsUsedNum2.incrementAndGet();
                player2Points.addAndGet(-3);
                JOptionPane.showMessageDialog(null, 
                    "Hint #" + hintsUsedNum2.get() + ":\n" +
                    hintSubstring(hintsUsedNum2.get(), words[0]) + "\n" +
                    hintSubstring(hintsUsedNum2.get(), words[1]) + "\n" +
                    hintSubstring(hintsUsedNum2.get(), words[2]) + "\n" +
                    hintSubstring(hintsUsedNum2.get(), words[3]) + "\n" +
                    hintSubstring(hintsUsedNum2.get(), words[4]) +
                    "\n\nPoints deducted: 3");
            } else {
                JOptionPane.showMessageDialog(null, "No more hints available! :p");
            }
        });

            board1.getSubmitButton().addActionListener(e -> {
                //Some atmoicInteger ngl I used chatgpt because I have never experienced this before
            if (board1.checkSubmission()) {
                numWordsLeft.decrementAndGet(); 
                player2Points.addAndGet(5);    // Fixed addition
                JOptionPane.showMessageDialog(null, "Correct!!! +5 points. Words left: " + numWordsLeft.get());

                if (numWordsLeft.get() == 0) { // Fixed comparison
                    JOptionPane.showMessageDialog(null, "Congrats! You have found all the words \n" + 
                        "Final Score: " + player2Points.get());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Not a hidden word. Try again!");
            }
        });
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
    public static String hintSubstring(int numTimes, String word) {
         return word.substring(0, numTimes);
     }




}