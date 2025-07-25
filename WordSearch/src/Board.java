import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Board extends JFrame{
    private String[][] board = new String[15][15];
    private JButton[][] button = new JButton[15][15];
    private JButton hintButton;
    private JButton submitButton;
    private List<JButton> selectedButtons = new ArrayList<>();


    public Board(){
        setTitle("WordSearch Board");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        for (int r = 0; r < 15; r++) {
            for (int c = 0; c < 15; c++) {
                board[r][c] = "-";
            }
        }

        // Create hint button panel on the right side
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        hintButton = new JButton("Hint");
        buttonPanel.add(hintButton);

        submitButton = new JButton("Submit Word");
        buttonPanel.add(submitButton);  
        add(buttonPanel, BorderLayout.EAST); 
        //how to make it near the hint button

        // Create grid panel for wordsearch
        JPanel panel = new JPanel(new GridLayout(15, 15));
        for (int r = 0; r < 15; r++) {
            for (int c = 0; c < 15; c++) {
                JButton b = new JButton(board[r][c]);
                button[r][c] = b;
                int finalR = r;
                int finalC = c;
                b.addActionListener(e->{
                    if(!selectedButtons.contains(b)){
                        selectedButtons.add(b);
                        b.setBackground(Color.BLUE);
                    }
                    else{
                        selectedButtons.remove(b);
                        b.setBackground(null);
                    }
                });
                panel.add(b);
            }
        }
        add(panel, BorderLayout.CENTER);  

        setVisible(true);
    }

    public void refresh(){
        // Refreshes the board after updates
        for(int r = 0; r <15; r++){
            for(int c = 0; c< 15; c++){
                button[r][c].setText(board[r][c]);
            }
        }
    }

    public void makeWordSearch(String word1, String word2, String word3, String word4, String word5){
        boolean placed1 = false;
        boolean placed2 = false;
        boolean placed3 = false;
        boolean placed4 = false;
        boolean placed5 = false;

        while(!placed1 || !placed2 || !placed3 || !placed4 || !placed5){
            if(!placed1){
                placed1 = tryToPlace(word1);
            }
            if(!placed2){
                placed2 = tryToPlace(word2);
            }
            if(!placed3){
                placed3 = tryToPlace(word3);
            }
            if(!placed4){
                placed4 = tryToPlace(word4);
            }
            if(!placed5){
                placed5 = tryToPlace(word5);
            }
        }
    }

    public boolean tryToPlace(String word){
        int randomizedDirection = (int) (Math.random() * 3);
        // Random number between 0-2: 0 horizontal, 1 vertical, 2 diagonal

        int randomRow = (int) (Math.random() * 15);
        int randomColumn = (int) (Math.random() * 15);

        if (randomizedDirection == 0){
            //horizontal
            if(tryHorizontal(word,randomRow,randomColumn)){
                placeHorizontal(word,randomRow,randomColumn);
                return true;
            }
        } else if(randomizedDirection == 1){
            if(tryVertical(word, randomRow, randomColumn)){
                placeVertical(word,randomRow,randomColumn);
                return true;
            }
        } else{
            if(tryDiagonal(word,randomRow,randomColumn)){
                placeDiagonal(word,randomRow,randomColumn);
                return true;
            }
        }
        return false;
    }


    //------------------------------------------------
    public boolean tryHorizontal(String word, int row, int column){
        if(column + word.length() > 15){
            return false;
        }
        for(int i = 0; i < word.length(); i++){
            String boardLetter = board[row][column+i];

            if(!boardLetter.equals("-") && !boardLetter.equals(String.valueOf(word.charAt(i)))){
                return false;
            }
        }
        return true;
    }


    public void placeHorizontal(String word, int row, int column){
        for (int i = 0; i < word.length(); i++){
            board[row][column + i] = String.valueOf(word.charAt(i));
            button[row][column + i].setText(String.valueOf(word.charAt(i)));
        }
    }


    //------------------------------------------------
    public boolean tryVertical(String word, int row, int column){
        if(row + word.length() > 15){
            return false;
        }
        for(int i = 0; i < word.length(); i++){
            String boardLetter = board[row+i][column]; 

            if(!boardLetter.equals("-") && !boardLetter.equals(String.valueOf(word.charAt(i)))){
                return false;
            }
        }
        return true;
    }

    public void placeVertical(String word, int row, int column){
        for(int i = 0; i < word.length(); i++){
            board[row + i][column] = String.valueOf(word.charAt(i));
            button[row +i][column].setText(String.valueOf(word.charAt(i)));
        }
    }

    //------------------------------------------------

    public boolean tryDiagonal(String word, int row, int column){
        if(row + word.length() > 15 || column + word.length() > 15){
            return false;
        }

        for(int i = 0; i < word.length(); i++){
            String current = board[row + i][column + i];
            if(!current.equals("-") && !current.equals(String.valueOf(word.charAt(i)))){
                return false;
            }
        }
        return true;
    }

    public void placeDiagonal(String word, int row, int column){
        for(int i = 0; i < word.length(); i++){
            board[row + i][column + i] = String.valueOf(word.charAt(i));
            button[row +i][column + i].setText(String.valueOf(word.charAt(i)));
        }
    }

    //------------------------------------------------

    public void fillRandomLetters(){
        for(int r = 0; r < 15; r++){
            for(int c = 0; c < 15; c++){
                if(board[r][c].equals("-")){
                    char randomChar = (char) ('A' + (int)(Math.random() * 26));
                    board[r][c] =String.valueOf(randomChar);
                    button[r][c].setText(String.valueOf(randomChar));
                }
                
            }
            
        }
        refresh();
    }
    public JButton getHintButton() {
        return hintButton;
    }

    public String getSelectedWord(){
        StringBuilder sb = new StringBuilder();
        for(JButton b : selectedButtons){
            sb.append(b.getText());
        }
        return sb.toString();
    }
    public void clearSelection() {
        for(JButton b : selectedButtons){
            b.setBackground(null);
        }
        selectedButtons.clear();
    }

    public JButton getSubmitButton(){
        return submitButton;
    }


}