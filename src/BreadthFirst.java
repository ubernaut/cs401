import java.util.LinkedList;

public class BreadthFirst {
	private slideBoard sState;
	private slideBoard gState;
	private slideBoard lState;
	private LinkedList <slideBoard> currStates= new LinkedList<slideBoard>();
	private int nodesVisited;
	private boolean parityFlag=false;
	boolean goalFound = false;

	public BreadthFirst() {
		
		 sState = new slideBoard();
		 gState = new slideBoard();
		 lState = new slideBoard();
		 
		 currStates= new LinkedList<slideBoard>();
		 nodesVisited=0;
	}
	public BreadthFirst(slideBoard astartState, slideBoard agoalState) {
		
		sState=astartState;
		gState=agoalState;
		lState=astartState;
		
		nodesVisited=0;
		currStates = new LinkedList <slideBoard>();
		System.out.println("This is the startstate");
		sState.printBoard();
		System.out.println("This is the goalstate");
		gState.printBoard();
		if(!sState.checkParity(gState)){System.out.println("incompatable parity"); parityFlag=true;}
	}
	
	public void run(){
		if(parityFlag==true)return;
		currStates.addFirst(sState);
		nodesVisited++;
		lState.setBoard(sState.board);
		
		if(sState.equals(gState)){System.out.println("START state was GOAL state"); return;}
		else {
			System.out.println("parity matches up");

			while(true){
				if(goalFound==true){System.out.println("goal reached with: "+nodesVisited+" nodes expanded.");return;}
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
	}
	
	
	
	
	private void expandNodes(slideBoard aState){
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
			currStates.addLast(up);}
		aState.setBoard(lastState.board);//revert back to original state

		aState.slideDown();
		if(aState.checkEqual(lastState));
		else{
			if(gState.checkEqual(aState))goalFound=true;
			down.setBoard(aState.board);
			currStates.addLast(down);}
		aState.setBoard(lastState.board);

		aState.slideLeft();
		if(aState.checkEqual(lastState));
		else{
			if(gState.checkEqual(aState))goalFound=true;
			left.setBoard(aState.board);
			currStates.addLast(left);}
		aState.setBoard(lastState.board);
		
		aState.slideRight();
		if(aState.checkEqual(lastState));
		else{		
			if(gState.checkEqual(aState))goalFound=true;
			right.setBoard(aState.board);
			currStates.addLast(right);}

	
	}
	
}