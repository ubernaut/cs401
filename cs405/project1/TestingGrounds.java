package project1;
import java.util.TreeSet;
import java.util.Scanner;

public class TestingGrounds {
	
	

	public TestingGrounds() {
		int[][] input = new int[3][3];
		Scanner read = new Scanner(System.in);
		slideBoard startState = new slideBoard(),
        goalState = new slideBoard();
		itDeepSearch iterdeeper = new itDeepSearch();
 		System.out.println("which algorithm would you like to use?");
		System.out.println(" Enter 1 for Breadth First\n Enter 2 for iterative Deepening \n Enter 3 for misplaced Tiles\n Enter 4 for MannHattan Distance");
		int algo = read.nextInt();
    	System.out.println("Enter the 8-puzzle START state 3 chars at a time separated by spaces: \n");
		int startval=0;
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				input[i][j] = read.nextInt();}
	    startState.setBoard(input);
		System.out.println("Enter the 8-puzzle GOAL state 3 chars at a time separated by spaces: \n");
		startval=0;
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				input[i][j] = read.nextInt();}//startval;startval++;}
		
	    goalState.setBoard(input);
	    try{
	    	if(algo==1){	
	    		BreadthFirst bFirst = new BreadthFirst(startState, goalState);
	    		bFirst.run();
	    	}
	    	if(algo==2){	
	    		iterdeeper=  new itDeepSearch(startState, goalState);
	    		iterdeeper.run();
		    	}
	    	if(algo==3){	
		    	aStar(0, startState, goalState);
		       	}
	    	if(algo==4){	
	    		aStar(1,startState, goalState);
	    		}
	    	
	    	}
	    	
	    	catch(Exception e){System.out.println(e);}
		
		
	}

	public void aStar(int type, slideBoard startState, slideBoard goalState )
	{

		slideBoard goalBoard= new slideBoard(goalState);
		slideBoard stBoard = new slideBoard(startState);
		slideBoard gBoard = new slideBoard(goalState);
		TreeSet allStates = new TreeSet<slideBoard>();
		
		stBoard.searchSteez =0;
		stBoard.getSumMHDist(gBoard);
		stBoard.countMisPlacedTiles(gBoard);	
		
		slideBoard rootBoard = new slideBoard(stBoard, "root", gBoard);	
		 rootBoard.searchSteez=type;
		System.out.println("inserting root node");
		
		System.out.println();
		System.out.println("################################");
		System.out.println("#      BEGIN TREE SECTION      #");
		System.out.println("################################");
		System.out.println();

		allStates.add(rootBoard);

		while(! ((slideBoard)allStates.first()).checkEqual(gBoard) ){
	
			System.out.println("\n");
			System.out.println("###################");
			System.out.println("StateTree Size: "+allStates.size());
			System.out.println("###################");
			System.out.println("\n");
	
			((slideBoard) allStates.first()).printBoard();
	
			slideBoard topStack = new slideBoard( ((slideBoard) allStates.first()), "root", gBoard);
				
			allStates.add(new slideBoard(topStack, "up", goalBoard));
			allStates.add(new slideBoard(topStack, "down", goalBoard));
			allStates.add(new slideBoard(topStack, "left", goalBoard));
			allStates.add(new slideBoard(topStack, "right", goalBoard));
	
			if(! ((slideBoard)allStates.first()).checkEqual(gBoard) );
			else break;
	
		}

			return;

		
	}
	public static void main(String[] args) {
		TestingGrounds blarg = new TestingGrounds();
		
	}


}


