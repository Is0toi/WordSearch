public class Board{
    private String[][] board = new String[15][15];

    public Board(){
        for(int i = 0; i<15; i++){
            for(int x = 0; x <15; x++){
                board[x][i] = "-";
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
                placed2 = tryToPlace(word3);
            }
            if(!placed4){
                placed2 = tryToPlace(word4);
            }
            if(!placed5){
                placed2 = tryToPlace(word5);
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
        }
    }

}