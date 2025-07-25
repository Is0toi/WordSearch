import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

public class Board extends JFrame{
    private String[][] board = new String[15][15];
    private JButton[][] button = new JButton[15][15];
    private JButton hintButton;
    private JButton submitButton;
    private JButton quitButton;
    private List<Point> selectedCells = new ArrayList<>();
    private Color defaultButtonColor;
    private List<String> foundWords = new ArrayList<>();
    private List<String> hiddenWords = new ArrayList<>();
    private List<WordLocation> wordLocations = new ArrayList<>();

    public Board(String theme){
        setTitle("WordSearch Board");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JLabel themeLabel = new JLabel("Theme: " + theme, SwingConstants.CENTER);
        add(themeLabel, BorderLayout.NORTH);

        for (int r = 0; r < 15; r++) {
            for (int c = 0; c < 15; c++) {
                board[r][c] = "-";
            }
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(15, 15));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        hintButton = new JButton("Hint");
        submitButton = new JButton("Submit");
        quitButton = new JButton("Quit");
        buttonPanel.add(hintButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.EAST); 

        for (int r = 0; r < 15; r++) {
            for (int c = 0; c < 15; c++) {
                button[r][c] = new JButton(board[r][c]);
                button[r][c].setOpaque(true);
                button[r][c].setContentAreaFilled(true);
                button[r][c].setBorderPainted(false);

                panel.add(button[r][c]);
                final int row = r;
                final int col = c;
                button[r][c].addActionListener(e -> {
                    if (!button[row][col].getBackground().equals(Color.BLUE)) {
                        toggleSelection(row, col);
                    }
                });
            }
        }

        add(panel, BorderLayout.CENTER);
        defaultButtonColor = button[0][0].getBackground();
        setVisible(true);

        quitButton.addActionListener(e -> quitGame());
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


    // PLACE WORD LOGIC------------------------------------------------

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


    public void placeHorizontal(String word, int row, int column) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            board[row][column + i] = String.valueOf(word.charAt(i));
            button[row][column + i].setText(String.valueOf(word.charAt(i)));
            points.add(new Point(row, column + i));
        }
        hiddenWords.add(word);
        wordLocations.add(new WordLocation(word, points));
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

    public void placeVertical(String word, int row, int column) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            board[row + i][column] = String.valueOf(word.charAt(i));
            button[row + i][column].setText(String.valueOf(word.charAt(i)));
            points.add(new Point(row + i, column));
        }
        hiddenWords.add(word);
        wordLocations.add(new WordLocation(word, points));
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

    public void placeDiagonal(String word, int row, int column) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            board[row + i][column + i] = String.valueOf(word.charAt(i));
            button[row + i][column + i].setText(String.valueOf(word.charAt(i)));
            points.add(new Point(row + i, column + i));
        }
        hiddenWords.add(word);
        wordLocations.add(new WordLocation(word, points));
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
    //------------------------------------------------
    public JButton getHintButton() {
        return hintButton;
    }
    public JButton getSubmitButton(){
        return submitButton;
    }
    
    public boolean checkSubmission(){
        //Checks if word is correct
        if(selectedCells.isEmpty()){
            return false;
        }

        StringBuilder selectedWord = new StringBuilder();
        for(Point p: selectedCells){
            selectedWord.append(button[p.x][p.y].getText());
        }
        String word = selectedWord.toString();
        String reverseWord = new StringBuilder(word).reverse().toString();

        if (isWordFound(word)){
            JOptionPane.showMessageDialog(this, "You already found this word!", "Word Already Found", JOptionPane.INFORMATION_MESSAGE);
            clearSelection();
            return false;
        }
        for(String hidden: hiddenWords){
            if(!isWordFound(hidden)&& (word.equals(hidden) || reverseWord.equals(hidden))){
                foundWords.add(hidden);
                highlightFoundWord(hidden); // Should highlight the actuall word location
                clearSelection();
                return true;
            }
        }
        clearSelection();
        return false;
    }
    public void toggleSelection(int r, int c) {
            Point p = new Point(r, c);
            
            if (selectedCells.isEmpty()) {
                selectedCells.add(p);
                button[r][c].setBackground(Color.BLUE);
            } else {
                Point last = selectedCells.get(selectedCells.size() - 1);
                int dr = r - last.x;
                int dc = c - last.y;
                
                if ((Math.abs(dr) <= 1 && Math.abs(dc) <= 1) && 
                    (dr == 0 || dc == 0 || Math.abs(dr) == Math.abs(dc))) {
                    
                    if (selectedCells.size() == 1 || 
                        (r - last.x == last.x - selectedCells.get(selectedCells.size() - 2).x &&
                        c - last.y == last.y - selectedCells.get(selectedCells.size() - 2).y)) {
                        
                        selectedCells.add(p);
                        button[r][c].setBackground(Color.YELLOW);
                    }
                } else {
                    clearSelection();
                    selectedCells.add(p);
                    button[r][c].setBackground(Color.YELLOW);
                }
            }
        }

        public void clearSelection() {
            for (Point p : selectedCells) {
                button[p.x][p.y].setBackground(defaultButtonColor);
            }
            selectedCells.clear();
        }

        public void refresh(){
            for(int r = 0; r < 15; r++){
                for(int c = 0; c < 15; c++){
                    button[r][c].setText(board[r][c]);
                }
            }
        }

        private boolean isWordFound(String word) {
            for (String found : foundWords) {
                if (found.equals(word)) {
                    return true;
                }
            }
            return false;
        }

        private void highlightFoundWord(String word) {
            for (WordLocation wl : wordLocations) {
                if (wl.word.equals(word)) {
                    for (Point p : wl.locations) {
                        JButton highlightButton = button[p.x][p.y];
                        highlightButton.setBackground(Color.GREEN);
                        highlightButton.setOpaque(true);
                        highlightButton.repaint();
                    }
                    break;
                }
            }
            repaint();
        }

        public class WordLocation{
            String word;
            List<Point> locations;

            public WordLocation(String word, List<Point> locations){
                this.word = word;
                this.locations = locations;
            }
        }

        private void quitGame(){
            int response = JOptionPane.showConfirmDialog(
                this, "Are you sure you want to quit?", "Confirm Quit", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

                if(response == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
        }

        public JButton getQuitButton(){
            return quitButton;
        }

}