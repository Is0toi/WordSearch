import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;

public class WordSearch{
    public static void main (String[] args){
        // INTRODUCTION & WORD COLLECTION ------------------------------------------------------------
        String playerOptions[] = {"1 Player", "2 Players"};
        int choice = JOptionPane.showOptionDialog(null, "Welcome to to Word Hunt Showdown! \n Before we get started, would you like to play single player or two player mode?", "Welcome To WordSearch!", JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,null,new String[] {"1 Player", "2 Players"}, "1 Player");

        if(choice == 0){
            String player = JOptionPane.showInputDialog(null, "Whats your name?:");
            JOptionPane.showMessageDialog(null, "In single player, the program will generate a wordsaerch for you to solve. \n You will need to find 5 words with this distribution: \n -(2) 4 letter words \n -(3) 5 letter words \n "+
            "If you use a hint that will deduct you 5 points but you are allowed 4 hints \n" +
            "Well, Good luck!");


        AtomicInteger numWordsLeft = new AtomicInteger(5);
        AtomicInteger playerPoints = new AtomicInteger(0);
        try {
            final String fourLetter1 = generateWord("4-letter-words.txt", 417).toUpperCase();
            final String fourLetter2 = generateWord("4-letter-words.txt", 417).toUpperCase();
            final String fiveLetter1 = generateWord("5-letter-words.txt", 256).toUpperCase();
            final String fiveLetter2 = generateWord("5-letter-words.txt", 256).toUpperCase();
            final String fiveLetter3 = generateWord("5-letter-words.txt", 256).toUpperCase();

            Board board = new Board(null, "Single Player!");
            board.makeWordSearch(fourLetter1, fourLetter2, fiveLetter1, fiveLetter2, fiveLetter3);
            board.fillRandomLetters();
            // board.setVisible(true);

           
            AtomicInteger hintsUsedNum = new AtomicInteger(0);

            board.getHintButton().addActionListener(e -> {
            if (hintsUsedNum.get() < 4) {
                hintsUsedNum.incrementAndGet();
                playerPoints.addAndGet(-5);
                String hints = (
                    "Hint #" + hintsUsedNum.get() + ":\n" +
                    hintSubstring(hintsUsedNum.get(), fourLetter1) + "\n" +
                    hintSubstring(hintsUsedNum.get(), fourLetter2) + "\n" +
                    hintSubstring(hintsUsedNum.get(), fiveLetter1) + "\n" +
                    hintSubstring(hintsUsedNum.get(), fiveLetter2) + "\n" +
                    hintSubstring(hintsUsedNum.get(), fiveLetter3) +
                    "\n\nPoints deducted: 5");
                    board.setMessage(hints);
            } else{
                board.setMessage("No more hints available! :p now you're too greedy and don't get hints");
            }
           
        });

            board.getSubmitButton().addActionListener(e -> {
                if (board.checkSubmission()) {
                    numWordsLeft.decrementAndGet(); 
                    playerPoints.addAndGet(5);  
                    board.setMessage("Correct!!! +5 points. Words left: " + numWordsLeft.get());

                    if (numWordsLeft.get() == 0) {
                        board.setMessage("Congrats! You have found all the words \n" + 
                            "Final Score: " + playerPoints.get());
                    }
                } else {
                    board.setMessage( "Not a hidden word. Try again!");
                }
            });

            board.setVisible(true);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading word list files.");
            e.printStackTrace();
            System.exit(1);
        }

         JOptionPane.showMessageDialog(null, 
            "Congrats " + player + "! Let's get the conclusion: \n" +
            "You got " + playerPoints.get() + "! That's really impressive!");
            

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

    
            // BOARD -----------------------------------------------------------------------------------------
            Board board1 = new Board(null, player1Theme);
            board1.makeWordSearch(words[0],words[1],words[2],words[3],words[4]);
            board1.fillRandomLetters();
            

            // LOGIC ON BOARD ---------------------------------------------------------------------

            AtomicInteger numWordsLeft = new AtomicInteger(5);
            AtomicInteger player2Points = new AtomicInteger(0);
            AtomicInteger hintsUsedNum2 = new AtomicInteger(0);

            board1.getHintButton().addActionListener(e -> {
            if (hintsUsedNum2.get() < 3) {
                System.out.println("Hint button: " + board1.getHintButton());
                hintsUsedNum2.incrementAndGet();
                player2Points.addAndGet(-3);
                String hints = (
                    "Hint #" + hintsUsedNum2.get() + ":\n" +
                    hintSubstring(hintsUsedNum2.get(), words[0]) + "\n" +
                    hintSubstring(hintsUsedNum2.get(), words[1]) + "\n" +
                    hintSubstring(hintsUsedNum2.get(), words[2]) + "\n" +
                    hintSubstring(hintsUsedNum2.get(), words[3]) + "\n" +
                    hintSubstring(hintsUsedNum2.get(), words[4]) +
                    "\n\nPoints deducted: 3");
                board1.setMessage(hints);
            } else {
                board1.setMessage("No more hints available! :p");
            }
        });

            board1.getSubmitButton().addActionListener(e -> {
             //Some atmoicInteger ngl I used chatgpt because I have never experienced this before
            if (board1.checkSubmission()) {
                numWordsLeft.decrementAndGet(); 
                player2Points.addAndGet(5);  
                board1.setMessage("Correct!!! +5 points. Words left: " + numWordsLeft.get());

                if (numWordsLeft.get() == 0) {
                    board1.setMessage( "Congrats! You have found all the words \n" + 
                        "Final Score: " + player2Points.get());
                }
            } else {
                board1.setMessage("Not a hidden word. Try again!");
            }
        });
        board1.setVisible(true);
        








        // PLAYER 2 TURN ------------------------------------------------------------------------------------------------------------------------------------------
        String player2Theme = JOptionPane.showInputDialog(null,"Okay " + player2 + ", are you ready? Enter a theme for you word search (Please be specific and make sure player 2 doesn't see):");

            final String[] words2 = new String[5];
            Set<String> usedWords2 = new HashSet<>();
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

                    if (usedWords2.contains(input)){
                        JOptionPane.showMessageDialog(null, input + " was already entered. Try again.");
                        continue;
                    }
                    
                    if (!isWord(input)) {
                        JOptionPane.showMessageDialog(null, 
                            "\"" + input + "\" is not a valid dictionary word. Try again.");
                        continue;
                    }
                    
                    words2[i] = input;
                    usedWords2.add(input);
                    break;
                }
            }

            // BOARD --------------------------------------------------------------------------------
            Board board2 = new Board(null, player2Theme);
            board2.makeWordSearch(words2[0],words2[1],words2[2],words2[3],words2[4]);
            board2.fillRandomLetters();

            // LOGIC ON BOARD ---------------------------------------------------------------------

            AtomicInteger numWordsLeft2 = new AtomicInteger(5);
            AtomicInteger player1Points = new AtomicInteger(0);
            AtomicInteger hintsUsedNum1 = new AtomicInteger(0);

            // JButton hintButton = board1.getHintButton();
            board2.getHintButton().addActionListener(e -> {
            if (hintsUsedNum1.get() < 3) {
                hintsUsedNum1.incrementAndGet();
                player1Points.addAndGet(-3);
                String hints = (
                    "Hint #" + hintsUsedNum1.get() + ":\n" +
                    hintSubstring(hintsUsedNum1.get(), words2[0]) + "\n" +
                    hintSubstring(hintsUsedNum1.get(), words2[1]) + "\n" +
                    hintSubstring(hintsUsedNum1.get(), words2[2]) + "\n" +
                    hintSubstring(hintsUsedNum1.get(), words2[3]) + "\n" +
                    hintSubstring(hintsUsedNum1.get(), words2[4]) +
                    "\n\nPoints deducted: 3");
                    board1.setMessage(hints);
            } else {
                board2.setMessage( "No more hints available! :p");
            }
        });

            board2.getSubmitButton().addActionListener(e -> {
             //Some atmoicInteger ngl I used chatgpt because I have never experienced this before
            if (board2.checkSubmission()) {
                numWordsLeft2.decrementAndGet(); 
                player1Points.addAndGet(5);  
                board2.setMessage("Correct!!! +5 points. Words left: " + numWordsLeft2.get());

                if (numWordsLeft2.get() == 0) {
                    board2.setMessage( "Congrats! You have found all the words \n" + 
                        "Final Score: " + player2Points.get());
                }
            } else {
                board2.setMessage( "Not a hidden word. Try again!");
            }
        });

        board2.setVisible(true);


        if(player1Points.get() > player2Points.get()){
            JOptionPane.showMessageDialog(null, "Congrats " + player1 + " & " + player2 + "! You both did great. Let's get the conclusion: \n" +
            "Player 1 got " + player1Points + " points while Player 2 got " +player2Points+ " points. Therefore PLAYER 1 IS THE WINNERRRRR. \n"+
            "I hope you two come back and play again especially " + player2 + " for revenge >:)");
        }
        else if(player1Points.get() < player2Points.get()){
            JOptionPane.showMessageDialog(null, "Congrats " + player1 + " & " + player2 + "! You both did great. Let's get the conclusion: \n" +
            "Player 1 got " + player1Points + " points while Player 2 got " +player2Points+ " points. Therefore PLAYER 2 IS THE WINNERRRRR. \n"+
            "I hope you two come back and play again especially " + player1 + " for revenge >:)");
        }
        else{
            JOptionPane.showMessageDialog(null, "Congrats " + player1 + " & " + player2 + "! You both did great. Let's get the conclusion: \n" +
            "You guys both got the same amount of points of " + player1Points 
            + "\n I hope you two come back and play again to find a definite WINNERRRRR");
        }
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

    public static String generateWord(String fileName, int numLines) throws IOException{
        int currentLine = 1;
        String randomWord = null;
        
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        int randomLine = (int) (Math.random() * numLines) + 1;

        String line;

        // read random line
        while((line = reader.readLine()) != null){
            if(currentLine == randomLine){
                randomWord = line.trim(); // So that the word doesn't have any excess space
                break;
            }
            currentLine++;
        }
        reader.close();
        return randomWord;
    }




}