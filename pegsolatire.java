import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Pegsolatire {
  class Move implements Comparable<Move>{
    int from;   // from where the marble is being moved
    int hole; // to which hole its being moved
    int to;    // to which hole its being moved
    Move(int from, int hole, int to){
      // constructor
      this.from = from;
      this.hole = hole;
      this.to = to;
    }
    // to print it we can use toString method
    public String toString() {
      return ("("+ this.from + "," + this.hole + "," + this.to + ")");
    }
    @Override
    public int compareTo(Move m) {
      return Integer.valueOf(this.from).compareTo(Integer.valueOf(m.from));   // comparing the values and sorting in ascedning order
    }
  }
  ArrayList<ArrayList<Integer>> grid;   // we store the array of the arrays for the board in the form of 1's and -1's
  ArrayList<Move> movesList;         // movesList stores from one location to the another location. 
  ArrayList<ArrayList<ArrayList<Integer>>> unsuccessfullGrid;   // we are gonna store the grids which has arraylist of arraylist inside an another arraylist
  
  
  
  Pegsolatire(ArrayList<ArrayList<Integer>> grid){
    // constructor function for the initiliase the pegSolataire function
    this.grid = grid;            // it initialises the grid given by the hardcoded
    movesList = new ArrayList<>();           // initilaises the new array list for moveslist
    unsuccessfullGrid = new ArrayList<>();
  }
  
  // we will write the method to print all the moves it has been performed when called. 
  
  private void printOutput() {
    for (Move move: movesList) {
      System.out.println(move.toString());     // we had already created the move toString to print output
    }
  }
  
  private void displayGrid() {
    //to print the entire grid
    for (ArrayList<Integer> line : grid) {
      for (int i: line) {
        if (i == -1 ) {
          System.out.print("- ");      // whenever there is no grid value print the  - 
        }
        else {
          System.out.print(Integer.toString(i)+ " ");
        }
      }
      System.out.println();
    }
  }
  private void makeMove(Move move) {
    grid.get(move.from/7).set(move.from % 7, 0);    // we will get the number from the row number using the /7 and the column number using the %
    grid.get(move.hole/7).set(move.hole % 7, 0);
    grid.get(move.to/7).set(move.to % 7, 1);
    movesList.add(move);  // we add it to the arraylist of the moveList
  }
  
  private void UndoMove(Move move) {
    grid.get(move.from/7).set(move.from % 7, 1);    // we will undo the marbel jumped on
    grid.get(move.hole/7).set(move.hole % 7, 1);
    grid.get(move.to/7).set(move.to % 7, 0);
    movesList.remove(movesList.size()-1);  // we will remove the last number from the grid. similar to pop 
  }
  private ArrayList<Move> computePossibilities() {
    // return the possibilities the list of moves
    ArrayList<Move> possibilites = new ArrayList<>();
    // we need to check the marbles that are two lengths difference or available to left right or top or bottom
    for(int i = 0 ; i<grid.size(); i++) {
      for (int j = 0; j<grid.get(i).size(); j++) {
        if (grid.get(i).get(j) == 0) {
          if ((i-2) >= 0) {
            if ((grid.get(i-2).get(j) == 1) && (grid.get(i-1).get(j) == 1)) {
              possibilites.add(new Move((i-2)*7+j,((i-1)*7+j), (i*7+j)));
            }
          }
          if ((j-2) >= 0) {
            if ((grid.get(i).get(j-2) == 1) && (grid.get(i).get(j-1) ==1))
            {
              possibilites.add(new Move(i*7 + j-2 , i*7 + j-1, i*7 +j));
            }
          }
          if (i+2 <= 6){
            if ((grid.get(i+2).get(j) == 1) && (grid.get(i+1).get(j) == 1)) {
              possibilites.add(new Move((i+2)*7+j,(i+1)*7+j, i*7+j));
            }
          }
          if (j+2 <= 6) {
            if((grid.get(i).get(j+2) == 1) && grid.get(i).get(j+1) == 1) {
              possibilites.add(new Move(i*7+j+2, i*7+j+1, i*7+j));
            }
          }
        }
      }
    }
    return possibilites;
  }
  
  private ArrayList<ArrayList<Integer>> deepCopy(ArrayList<ArrayList<Integer>> input){
	  ArrayList<ArrayList<Integer>> newGrid=new ArrayList<>();
	  for (ArrayList<Integer> line:input)
	  {
		  ArrayList<Integer> cpLine=new ArrayList<>();
		  for(Integer i:line) {
			  cpLine.add(Integer.valueOf(i));
		  }
		  newGrid.add(cpLine);
	  }
	  return newGrid;
  }
  private int getCount() {
    int count = 0;
    for (int i =0 ; i<grid.size();i++) {
      for(int j =0 ; j<grid.get(i).size(); j++) {
        if (grid.get(i).get(j) == 1) {
          count ++;
        }
      }
    }
    return count;
  }
  
  public boolean solve() {
    if (unsuccessfullGrid.contains(grid)) {
      return false;  
    }
    if (getCount() == 1) {
      displayGrid();
      printOutput();
      return true;
    }
    else {
      ArrayList<Move> moves  = computePossibilities();
      Collections.sort(moves);
      
      for (Move move: moves) {
        makeMove(move);
        if (solve())  {
          return true;
        }
        else {
          UndoMove(move);
          
        }
      }
    }
    if (!unsuccessfullGrid.contains(grid)){
      unsuccessfullGrid.add(deepCopy(grid));
    }
    return false;
  }
  
  public static void main(String args[]) {
    ArrayList<ArrayList<Integer>> grid = new ArrayList<>();
    Integer[] line1 = {-1,-1,1,1,1,-1,-1};
    Integer[] line2 = {-1,-1,1,1,1,-1,-1};
    Integer[] line3 = {1,1,1,1,1,1,1};
    Integer[] line4 = {1,1,1,0,1,1,1};
    Integer[] line5 = {1,1,1,1,1,1,1};
    Integer[] line6 = {-1,-1,1,1,1,-1,-1};
    Integer[] line7 = {-1,-1,1,1,1,-1,-1};
    
    
    
    grid.add(new ArrayList<Integer>(Arrays.asList(line1)));
    grid.add(new ArrayList<Integer>(Arrays.asList(line2)));
    grid.add(new ArrayList<Integer>(Arrays.asList(line3)));
    grid.add(new ArrayList<Integer>(Arrays.asList(line4)));
    grid.add(new ArrayList<Integer>(Arrays.asList(line5)));
    grid.add(new ArrayList<Integer>(Arrays.asList(line6)));
    grid.add(new ArrayList<Integer>(Arrays.asList(line7)));
    
    
    new Pegsolatire(grid).solve();
    
  }
  
  
}
