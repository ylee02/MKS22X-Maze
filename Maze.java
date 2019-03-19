import java.util.*;
import java.io.*;
public class Maze{


    private char[][]maze;
    private boolean animate;//false by default
	private int startx;
	private int starty;
	private int[] moves = {1,0,-1,0,0,-1,0,1};
  private int ans = 1;

    /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)

      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!


      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:

         throw a FileNotFoundException or IllegalStateException

    */

    public Maze(String filename) throws FileNotFoundException{
      try {
        File fil = new File(filename);
        Scanner in = new Scanner(fil);
		ArrayList<String> temp = new ArrayList<String>();
        while (in.hasNextLine()){
			temp.add(in.nextLine());
        }
		maze = new char[temp.size()][temp.get(0).length()];
		for (int i =0; i < maze.length; i++) {
			for (int y= 0; y < maze[0].length; y++) {
				maze[i][y] = temp.get(i).charAt(y);
			}
		}
		test();
      }catch (FileNotFoundException e){
        System.out.println("Error: File " + filename + " not found");
        e.printStackTrace();
        System.exit(1);
      }
    }

	private void test() {
		int a = 0, b = 0;
		for (int i =0; i < maze.length; i++) {
			for (int y= 0; y < maze[0].length; y++) {
				if (maze[i][y] == 'E') {
					a += 1;
				}
				if (maze[i][y] == 'S') {
					b +=1;
					startx = y;
					starty = i;
				}
			}

		}
    if (a > 1 || b > 1 || b == 0 || a ==0 ){
      throw new IllegalArgumentException("There has to be only one E and one S");
    }
	}


    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }


    public void setAnimate(boolean b){

        animate = b;

    }


    public void clearTerminal(){

        //erase terminal, go to top left of screen.

        System.out.println("\033[2J\033[1;1H");

    }



    /*Wrapper Solve Function returns the helper function

      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.

    */
    public int solve(){

            //find the location of the S.


            //erase the S


            //and start solving at the location of the s.

            //return solve(???,???);
			return solve(starty, startx);

    }
	public String toString() {
		String ans = "";
		for (int i =0; i < maze.length; i++) {
			for (int y = 0; y <maze[0].length; y++) {
				ans += maze[i][y] + " ";
			}
			ans += "\n";
		}
		return ans;
	}

    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.


      Postcondition:

        The S is replaced with '@' but the 'E' is not.

        All visited spots that were not part of the solution are changed to '.'

        All visited spots that are part of the solution are changed to '@'
    */
	private boolean addAt(int row, int col) {
		char temp = maze[row][col];
		if(temp == '.' || temp == '#' || temp == '@') return false;
    maze[row][col] = '@';
		return true;
	}


    private int solve(int row, int col){ //you can add more parameters since this is private
  		if (maze[row][col] == 'E') {
        maze[row][col] = '@';
  			return ans;
  		}
  		if (addAt(row, col)) {
  			for (int i = 0; i < 8; i+=2) {
          ans += 1;
  				if (solve(row + moves[i], col + moves[i + 1]) != -1) {
  					return ans;
  				}
  			}
        maze[row][col] = '.';
  		}
  		ans -= 1;
      return -1; //so it compiles
    }


}
