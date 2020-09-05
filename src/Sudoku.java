import java.util.Scanner;

public class Sudoku {

    private int sudoku[][];
    private int sudoCopy[][];
    private int r;
    private int c;
    private int k;


    private Sudoku() {
        int x = -1;
        String s;
        Scanner input = new Scanner(System.in);
        this.sudoku = new int[9][9];
        this.sudoCopy = new int[9][9]; /*Copy board used to differentiate between
                                        user-entered and computer-entered numbers in cells*/

        for (int i=0;i<9;i++) {
            for (int j=0;j<9;j++) {

                    do {
                        System.out.println("Entering value for " + i + "," + j);
                        s = input.next();
                        if (s.charAt(0) == '.') {
                            x = 0;
                        } else {
                            x = Integer.parseInt(s);
                        }
                    }
                    while (x > 9 || x < 0);

                sudoku[i][j] = x;
                sudoCopy[i][j] = x;

            }
            }
    }

    @Override
    public String toString() {
        String s = "";
        for (int i=0;i<9;i++) {
            s += "\r\n";
            for (int j=0;j<9;j++) {
                if(sudoku[i][j] == 0){
                    s+= ".";
                }
                else {
                    s += (sudoku[i][j]);
                }
                s += " ";
            }
        }
        return "Sudoku Board:"
                + s;
    }




    private boolean findEmpty() {
        /*Finds the next empty cell, if not currently backtracking*/
        k=1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (sudoku[i][j] == 0) {
                    r = i;
                    c = j;
                    System.out.println("empty spot at:"+r+","+c);
                    return true;
                }

            }
        }
        return false;
    }





    private int findBox() {
        /*Finds and returns which 3x3 box the current cell is located in.*/

        if (r < 3 && c < 3) {
            return 0;
        }
        else if (r < 3 && c > 2 && c < 6){
            return 1;
        }
        else if (r < 3 && c > 5){
            return 2;
        }
        else if (r > 2 && r<6 && c < 3 ){
            return 3;
        }
        else if (r > 2 && r<6 && c > 2 && c<6){
            return 4;
        }
        else if (r > 2 && r<6 && c > 5 ){
            return 5;
        }
        else if (r > 5 && c < 3){
            return 6;
        }
        else if (r > 5 && c > 2 && c<6){
            return 7;
        }
        else if (r > 5 && c > 5){
            return 8;
        }
        return 1000;
    }


    private boolean checkRow(){
        for (int j=0;j<9;j++) {
            if (sudoku[r][j] == k) {
                System.out.println("bad row at:" + r + "," + j);
                return false;
            }
        }

        return true;
    }

    private boolean checkCol(){
        for (int i=0;i<9;i++) {
            if (sudoku[i][c] == k) {
                System.out.println("bad col at:" + i + "," + c);
                return false;
            }
        }
        return true;
    }

    private boolean checkBox(int box){
        System.out.println("Box #:" + box + " For location " + r +","+c);


        if (box<3){
            for (int i = 0; i < 3; i++) {
                for (int j=box*3;j<(box*3)+3;j++) {
                    if (sudoku[i][j] == k) {
                        System.out.println("bad box at:" + i + "," + j);
                        return false;
                    }
                }
            }
        }

        else if (box>2 && box<6){
            for (int i = 3; i < 6; i++) {
                for (int j=(box-3)*3;j<((box-3)*3)+3;j++) {
                    if (sudoku[i][j] == k) {
                        System.out.println("bad box at:" + i + "," + j);
                        return false;
                    }
                }
            }
        }

        if (box>5){
            for (int i = 6; i < 9; i++) {
                for (int j=(box-6)*3;j<((box-6)*3)+3;j++) {
                    if (sudoku[i][j] == k) {
                        System.out.println("bad box at:" + i + "," + j);
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private boolean validate() {
        /*Checks row, column and box for cell's possible valid inputs*/

        for(k=k;k<10;k++) {

            if (checkRow() && checkCol() && checkBox(findBox()) == true) {
                sudoku[r][c] = k;
                System.out.println("Added value "+k+ " to slot "+r+","+c);
                return true;
            }
        }
        System.out.println("bad validation");
        sudoku[r][c] = 0;
        return false;


    }


    private void backtrack(){
        /* Moves to previous cell when current cell
        has no valid solutions */

        sudoku[r][c] = 0;


        try {
            if (c == 0) {
                r = r - 1;
                c = 8;
            } else if (c > 0) {
                c = c - 1;

            }

            while (sudoCopy[r][c] != 0) {
                if (c == 0) {
                    r = r - 1;
                    c = 8;
                } else if (c > 0) {
                    c = c - 1;

                }
            }
        }
        catch (Exception e){
            System.out.println("Board is unsolveable.");
            System.exit(0);
        }



        k = sudoku[r][c] + 1;
        sudoku[r][c] = 0;
        System.out.println("counter reset to "+k);
        System.out.println("Backtracked to "+r+","+c);
    }


    private void solve() {

        while (true){

            if (findEmpty() == true){
            while(validate() == false) {
                backtrack();
            }
            }
            else {
                return;
            }
        }
    }










    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Sudoku sudoku = new Sudoku();
        sudoku.solve();
        System.out.println(sudoku);



    }
}
