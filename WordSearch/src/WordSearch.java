
import javax.swing.JOptionPane;




class WordSearch{
    public static void main (String args[]) throws IOException{
        Jframe frame = new JFrame()
        JOptionPane.showMessageDialog("Welcome to to Word Hunt Showdown! \n Before we get started, would you like to play single player or two player mode?");
        int playerOptions[] = {1, 2};
        int choice = JOptionPane.showOptionDialog(null, "Welcome to to Word Hunt Showdown! \n Before we get started, would you like to play single player or two player mode?", "Welcome To WordSearch!", JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.DEFAULT_OPTION,null,playerOption,playerOption[1]);

        if(option == 1){
            JoptionPane.showMessageDialog(null, "In single player, the program will generate a wordsaerch for you to solve. \n You will need to find 5 words with this distribution: \n -(2) 4 letter words \n -(3) 5 letter words \n Good luck!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Get ready for a two-player competition! \n" + 
            "In this game mode, both players will compete in a battle where they each create custom word searches for the other to solve. \n" +
            "Player 1 starts by choosing a theme and providing 5 words related to it. \n" +
            "Player 2 earns 5 points for each theme word they find and 2 bonus points for discovering words valid on the grid. \n" +
            "One Player 2 uncovers all the words, or decides to give up, the roles switch!\n"+
            "At the end, the player with the most points wins the title of WORD HUNT CHAMPIONNNNNN" +
            "Good luck and let the battle begin >:D");

            String player1 = JOptionPane.showInputDialog(null, "Enter Player 1's name:");
            String player2 = JOptionPane.showInputDialog(null,"Enter Player 2's name:");
        }




    }
}