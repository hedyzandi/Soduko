class udoku

{
static int Calls = 0;
int candidates [][] = new int [getEmptyCells()][2]; // initializes array for amount of empty cells there are
int sortedCandidates [] = new int [getEmptyCells()]; // initializes array that will hold locations of empty cells sorted by cells with least possible candidates, in ascending order.
int row = cell / SIZE; // the cell your checking / 9 gives you which row your checking
int col = cell % SIZE; // the cell your checking % 9 gives you column your checking
return (checkRow(row, value) && checkColumn(col, value)) && checkBox(row, col, value);
} // end noConflicts

boolean checkRow(int row, int value) 
{
for (int i = 0; i < grid[row].length; i++)
if (value == grid[row][i])
return false;
return true; //if the value being looked for isn't in that row, return true
} //end checkRow

boolean checkColumn(int col, int value) 
{
for (int i = 0; i < grid.length; i++) 
if (value == grid[i][col])
return false;

return true; // if the value isn't in that column, return true
}// end check column

boolean checkBox(int row, int col, int value)
{
int boxRow = row / SQRT;
int boxCol = col / SQRT;
for (int i = 0; i < SQRT; i++) 
for (int j = 0; j < SQRT; j++)
if (value == grid[SQRT * boxRow + i][SQRT * boxCol + j])
return false;
return true; // if val isn't in that box, return true
} //end checkBox
 
int index = 0; // global variable. will be incremented if we move on to the next cell, or decmented if we have to go back. refers to index of sortedCandidates array, which holds the cell locations of all empty cells
boolean  solvePuzzle( int [][] g) 
{
Calls++;    
if (index == sortedCandidates.length) 
return true;
for (int val = 1; val <= 9; val++) //consider values 1-9
{
int currentCell = sortedCandidates[index]; // current empty cell we are working on 
if (noConflicts(currentCell, val)) // if there are no conflicts with row, col, or box
{
place (currentCell, val); // put it there
index++; //move on the the next cell
if (solvePuzzle(g)) // if there's nothing left we're done
return true;
else
{
remove (currentCell); // failed, clear it.. reassign current cell 0
index--; //go back to the previous cell
}
}
}
return false; //trigger backtracking
} // end solve

int getEmptyCells() // determines number of empty cells there are
{
int emptyCells = 0;
for (int i = 0; i < grid.length; i++) 
for (int j = 0; j < grid[i].length; j++) 
{
if (grid[i][j] == 0)
emptyCells++;
} 
return emptyCells;
}
void sortCells() // sorts empty cell locations in ascending order starting with cell that has the least amount of possible values
{ 
int x = 0;

   for (int i = 0; i < grid.length; i++) // puts all empty cells in first column of 2d array candidates[][]
       	for (int j = 0; j < grid[i].length; j++) 
       	if (grid[i][j] == 0)
       	{
       	candidates[x][0] = i * SIZE + j;
       	x++;
       	}

  for (int i = 0; i < candidates.length; i++) //checks each candidate for their possible values and puts that number in the corresponding row and second column. ( in english: Cell 1 has 4 possible values. Cell 2 has 2 possible values. Etc, the first column is cells and the second is it's number of possible values
    {  
  int possibilities = 0;
  for (int val = 1; val <=9; val++)
  if (noConflicts (candidates[i][0], val))
  possibilities ++;
  candidates [i][1] = possibilities;
    }
  
 //bubble sort
 for (int i =1; i < candidates.length; i++) //bubble sort to sort the array in ascending order, starting from cell with lest amount of possible values.
 
 for (int j = 0; j < candidates.length - i; j++) 
  
 if (candidates[j][1] > candidates[j+1][1])
     {
    int tempPos = candidates[j][1];
    int tempCell = candidates [j][0];
    candidates [j] [1]= candidates[j+1][1];
    candidates [j] [0]= candidates[j+1][0];
    candidates[j+1][1] = tempPos;  
    candidates[j+1][0] = tempCell;
 	 }
 
 for (int i =0; i < sortedCandidates.length; i ++) //creates a new single dimensional array that holds a list of all cells sorted by ascending value of their possible values. 
     sortedCandidates[i] = candidates [i][0];

   } // end sortCandidates
public static void main(String[] args)  
{
   	 udoku X = new udoku ();
   	 X.sortCells(); // determines the number of possible candidates for each empty cell, sorts them in ascending or from most candidates to least candidates, and puts them into a new array 
   	 
   	 if (X.solvePuzzle(grid) == true)
   	    { 
   	 	X.printBoard();
   	 	System.out.println ("Recursive calls = " + Calls);
   	 	}
   	 else
   	 System.out.println("puzzle failed");
} // end main
  
} // end udoku