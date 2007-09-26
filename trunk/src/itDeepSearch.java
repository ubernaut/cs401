
import java.util.LinkedList;

public class itDeepSearch {
	private slideBoard sState;
	private slideBoard gState;
	private slideBoard lState;
	private LinkedList <slideBoard> currStates= new LinkedList<slideBoard>();
	private int nodesVisited;
	private int depth;
	private int cutoff = 999999999;
	private boolean parityFlag=false;
	boolean goalFound = false;

	public itDeepSearch() {
		
		 sState = new slideBoard();
		 gState = new slideBoard();
		 lState = new slideBoard();
		 
		 currStates= new LinkedList<slideBoard>();
		 nodesVisited=0;
		 depth=0;
	}
	
	public itDeepSearch(slideBoard astartState, slideBoard agoalState) {
		
		sState=astartState;
		gState=agoalState;
		lState=astartState;
		
		nodesVisited=0;
		currStates = new LinkedList<slideBoard>();
		System.out.println("This is the startstate");
		sState.printBoard();
		System.out.println("This is the goalstate");
		gState.printBoard();
		if(!sState.checkParity(gState)){System.out.println("incompatable parity"); parityFlag=true;}
	}
	
	public void run(){
		slideBoard answer = new slideBoard();
		
		if(parityFlag==true)return;
		currStates.addFirst(sState);
		nodesVisited++;
		lState.setBoard(sState.board);
		
		if(sState.equals(gState))
		    {System.out.println("START state was GOAL state"); 
			return;}  
		else {
			System.out.println("parity matches up");
			
			for(depth=0; depth < cutoff; depth++)
			{
				recursiveDLS(currStates.getFirst());
				if (depth != cutoff)
				{
					currStates.peekFirst().printBoard();
				}
				if(goalFound==true)
					{System.out.println("goal reached with: "+nodesVisited+" nodes expanded.");
					return;}
				else {
					System.out.println("goal reached with: "+nodesVisited+" nodes expanded.");
					break;}
			}
			
			
/*
			for(depth = 1; depth < 999999999; depth++){
				while(true){
					if(goalFound==true)
						{System.out.println("goal reached with: "+nodesVisited+" nodes expanded.");
						return;}
					if(!gState.checkEqual(currStates.peekFirst())){
						System.out.println("expanding node #"+nodesVisited+"\n");
						currStates.peekFirst().printBoard();
						expandNodes(currStates.pop());	
						nodesVisited++;}
					else {
						System.out.println("goal reached with: "+nodesVisited+" nodes expanded.");
						break;
					}
				}
			}
*/
			
		}
	}
	
	public void recursiveDLS(slideBoard inState)
	{
		boolean cutoffOccured = false;
		
		cutoffOccured = false;
		if(inState.equals(gState))
			return;
		else if(depth == cutoff)
			System.out.println("Cutoff limit reached.");
		else{
			System.out.println("expanding node #"+nodesVisited+"\n");
			currStates.peekFirst().printBoard();
			System.out.println("");
			expandNodes(inState, cutoffOccured);}
		if(cutoffOccured)
			System.out.println("Cutoff limit reached.");	
		return;
	}
	
	private void expandNodes(slideBoard aState, boolean cutoffFlag){
		//expand a state in all possible ways checking for the goal state as you go
		
		slideBoard up = new slideBoard();
		slideBoard down = new slideBoard();
		slideBoard left = new slideBoard();
		slideBoard right = new slideBoard();
		if(goalFound==true)return;
		
		slideBoard lastState = new slideBoard(); 
		lastState.setBoard(aState.board);      //this is for place-holding the last state
		
		aState.slideUp();
		if(aState.checkEqual(lastState)); //if we can't slide that way don't add a redundant state to the list
		else{
			if(gState.checkEqual(aState)){goalFound=true;} //check for the goal
			up.setBoard(aState.board);//create and add the new state to the list
			currStates.addLast(up);
			if(depth == cutoff){cutoffFlag = true;}
			recursiveDLS(currStates.getLast());}
		aState.setBoard(lastState.board);//revert back to original state

		aState.slideDown();
		if(aState.checkEqual(lastState));
		else{
			if(gState.checkEqual(aState))goalFound=true;
			down.setBoard(aState.board);
			currStates.addLast(down);
			if(depth == cutoff){cutoffFlag = true;}
			recursiveDLS(currStates.getLast());}
		aState.setBoard(lastState.board);

		aState.slideLeft();
		if(aState.checkEqual(lastState));
		else{
			if(gState.checkEqual(aState))goalFound=true;
			left.setBoard(aState.board);
			currStates.addLast(left);
			if(depth == cutoff){cutoffFlag = true;}
			recursiveDLS(currStates.getLast());}
		aState.setBoard(lastState.board);
		
		aState.slideRight();
		if(aState.checkEqual(lastState));
		else{		
			if(gState.checkEqual(aState))goalFound=true;
			right.setBoard(aState.board);
			currStates.addLast(right);
			if(depth == cutoff){cutoffFlag = true;}
			recursiveDLS(currStates.getLast());}

	
	}
	
}


/*


public class itDeepSearch {

// Figure out how to count these...

	int nodesExpanded,
	  	maxNodes,
	  	solutionLength;

// put in clause for check for even/odd parity

// implement as general tree search first for test, modify to Iterative Deepening

	public itDeepSearch()
	{
		nodesExpanded = 0;
		maxNodes = 0;
		solutionLength = 0;
	}

	public static Queue run(slideBoard puzzle, slideBoard goal, Queue toBeSearched)
	{
		boolean notDone = true;
		Node boardNode = new Node(puzzle),
			 first;



		if(goalTest(puzzle, goal))
		{
			return toBeSearched;
		}
		else{
			toBeSearched.insert(boardNode);
			do{
				if(toBeSearched.isEmpty())
					return null;
				first = toBeSearched.removeFirst();
				if(goalTest(first.state, goal))
				{
					toBeSearched.insert(first);
					return(toBeSearched);
				}
				toBeSearched.insertAll(expand(first, toBeSearched));
			}while(notDone);
		}
		return toBeSearched;
	}

	public static boolean goalTest(slideBoard current, slideBoard end)
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(current.getBoard()[i][j] != end.getBoard()[i][j])
					return false;
			}
		}
		return true;
	}

	public static Queue expand(Node unExpanded, Queue toBeSearched)
	{
		Queue successors = new Queue();
		Node save = unExpanded;

		unExpanded.state.slideUp();
		Node a = new Node(unExpanded.state, unExpanded, null,
				unExpanded.pathCost + 1, unExpanded.depth + 1);
		successors.insert(a);

		unExpanded = save;
		unExpanded.state.slideDown();
		Node b = new Node(unExpanded.state, unExpanded, null,
				unExpanded.pathCost + 1, unExpanded.depth + 1);
		successors.insert(b);

		unExpanded = save;
		unExpanded.state.slideLeft();
		Node c = new Node(unExpanded.state, unExpanded, null,
				unExpanded.pathCost + 1, unExpanded.depth + 1);
		successors.insert(c);

		unExpanded = save;
		unExpanded.state.slideRight();
		Node d = new Node(unExpanded.state, unExpanded, null,
				unExpanded.pathCost + 1, unExpanded.depth + 1);
		successors.insert(d);

		return successors;
	}
}


*/