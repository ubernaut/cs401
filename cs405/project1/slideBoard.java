package project1;
import java.math.*;
import java.util.TreeSet.*;

public class slideBoard implements Comparable {
	public int piecesOutOfPlace=0; //CoS h(n)
	public int mhDist= 0;//CoS  h(n)
	public int Depth=0; //CoS  aka g(n)
	public int toopSumGH=0;
	public int MHDSumGH=0;
	public int heuristic=0; 
	public int searchSteez=0; //zero represents tiles out of place
	public int[][] board =	   {{9,9,9},
								{9,9,9},
								{9,9,9}};
	

	public slideBoard()
	{
		piecesOutOfPlace=0;
		mhDist=0;
		Depth=0; 
		toopSumGH=0;
		MHDSumGH=0;
		heuristic=0; 
		searchSteez=0;
		int count=0;
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){board[i][j]=10;}
				//board[i][j] = daBoard.board[i][j];}
				
			//for(int i=0;i<3;i++)for(int j=0;j<3;j++)board[i][j]=10; 
	}
	public slideBoard(slideBoard daBoard)
	{
		piecesOutOfPlace=daBoard.piecesOutOfPlace;
		mhDist=daBoard.mhDist;
		Depth=daBoard.Depth; 
		toopSumGH=daBoard.toopSumGH;
		MHDSumGH=daBoard.MHDSumGH;
		heuristic=daBoard.heuristic; 
		searchSteez=daBoard.searchSteez;
		
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				board[i][j] = daBoard.board[i][j];}
				
			//for(int i=0;i<3;i++)for(int j=0;j<3;j++)board[i][j]=10; 
	}

	public slideBoard(slideBoard daBoard, String direction, slideBoard goalState)
	{
		piecesOutOfPlace=daBoard.piecesOutOfPlace;
		mhDist=daBoard.mhDist;
		Depth=daBoard.Depth; 
		toopSumGH=daBoard.toopSumGH;
		MHDSumGH=daBoard.MHDSumGH;
		heuristic=daBoard.heuristic; 
		searchSteez=daBoard.searchSteez;
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				board[i][j] = daBoard.board[i][j];}
		
		Depth++;
		if(direction=="up")slideUp();
		if(direction=="down")slideDown();
		if(direction=="left")slideLeft();
		if(direction=="right")slideRight();
		if(direction=="root");//Depth+=10;
		//else{System.out.println("shift direction could not be read");}
					
		getSumMHDist(goalState);
		countMisPlacedTiles(goalState);
		
		if(searchSteez==1)heuristic=MHDSumGH;//ManhattanDistance
		else heuristic=toopSumGH;//TilePlacmentSearch
		
		System.out.println(" ");
		System.out.println("created "+direction);
		printBoard();
		
	}
	
	public slideBoard(int[][] daBoard)
	{	
		new slideBoard();
		setBoard(daBoard);
	}
	public void slideUp(){
		int i, 
		    j = 0,
		    temp;
		
		boolean notFound = true;
		
		while(notFound)	{
			for(i = 0; i < 3; i++){
				if((board[i][j] == 0) && notFound){ // using 0 to represent the blank space
					notFound = false;
					if(i != 0){ // if blank isn't in first row, move up
						temp = board[i][j];
						board[i][j] = board[i - 1][j];
						board[i - 1][j] = temp;						
					}
				}
			}
			j++;
	}}
	
	public void slideDown(){
		int i, 
	    j = 0,
	    temp;	
		boolean notFound = true;
		while(notFound)	{
			for(i = 0; i < 3; i++){
				if((board[i][j] == 0) && notFound){ // using 0 to represent the blank space
					notFound = false;
					if(i != 2){ // if blank isn't in last row, move down
						temp = board[i][j];
						board[i][j] = board[i + 1][j];
						board[i + 1][j] = temp;						
					}				}			}		j++;}	}
	
	public void slideLeft()
	{
		int i, 
	    j = 0,
	    temp;
			boolean notFound = true;
			while(notFound)		{
			for(i = 0; i < 3; i++)			{
				if((board[i][j] == 0) && notFound) // using 0 to represent the blank space
				{
					notFound = false;
					if(j != 0) // if blank isn't in first column, move left
					{
						temp = board[i][j];
						board[i][j] = board[i][j - 1];
						board[i][j - 1] = temp;				
						}				}			}			j++;}}
	
	public void slideRight()
	{
		int i, 
	    j = 0,
	    temp;
	
		boolean notFound = true;
	
		while(notFound)
		{
			for(i = 0; i < 3; i++)
			{
				if((board[i][j] == 0) && notFound) // using 0 to represent the blank space
				{
					notFound = false;
					if(j != 2) // if blank isn't in last column, move right
					{
						temp = board[i][j];
						board[i][j] = board[i][j + 1];
						board[i][j + 1] = temp;						
					}
				}
			}
			j++; 
		}	
	}
	
	public void printBoard()
	{				
		
		System.out.println("################################");
		System.out.println("##  Manhattan Distance: "+mhDist);
		System.out.println("##  Tiles Out Of place: "+piecesOutOfPlace);
		System.out.println("##  Depth:              "+Depth);
		System.out.println("##  Heuristic:          "+heuristic);
			for(int i = 0; i < 3; i++)
		{
			System.out.print("##              ");
			for(int j = 0; j < 3; j++)
				System.out.print(board[i][j]+" ");
			System.out.print("\n");
		}		
	}

	public void setBoard(int[][] values)
	{
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				board[i][j] = values[i][j];
	}

	public int[][] getBoard(){
		return board;
	}
	
	
	//below here all CoS
	public void regenBoard(slideBoard gState){
		//Depth++;
		countMisPlacedTiles(gState);
		getSumMHDist(gState);
		toopSumGH=Depth+piecesOutOfPlace;
		MHDSumGH=Depth+mhDist;
	}
	public int[] findTilePlace(int myX, int myY, slideBoard theGoalState){		//Cos
		
		for(int allY =0; allY<3; allY++){
			for(int allX=0;allX<3; allX++){
				if(board[myX][myY]==theGoalState.board[allX][allY]){
					int[] location = {allX, allY};
					return location;
				}
			}}
		int[] notFound={100,100};
		return notFound;
	}
	public void countMisPlacedTiles(slideBoard theGoalState){		//CoS
		piecesOutOfPlace=0;
		for(int allY =0; allY<3; allY++){
			for(int allX=0;allX<3; allX++){
				if(board[allX][allY]!=theGoalState.board[allX][allY]){
					piecesOutOfPlace++;
					
				}
			}
		}
		toopSumGH=Depth+piecesOutOfPlace;
		//heuristic=toopSumGH;
		//return piecesOutOfPlace;
	}
	public void getSumMHDist(slideBoard theGoalState){//CoS
		mhDist=0;
		for(int allY =0; allY<3; allY++){
			for(int allX=0;allX<3; allX++){
				if(board[allX][allY]!=theGoalState.board[allX][allY])mhDist+=getOneMHDist(allX, allY, theGoalState);
				
		}}
		//heuristic=mhDist;	
		//return mhDist;	
	}
	public int getOneMHDist(int myX, int myY, slideBoard theGoalState){
	  	int tileMHDist=0;
		int[] goalPosition = findTilePlace(myX, myY, theGoalState);
	  	tileMHDist = Math.abs(myY-goalPosition[0])+Math.abs(myX-goalPosition[1]);
		
		return tileMHDist;
	}
	public boolean checkParity(slideBoard aState){  //CoS
		int myParity = 0;
		int thatParity =0;
		int meRemapped[]={this.board[0][0],this.board[0][1],this.board[0][2],this.board[1][0],this.board[1][1],this.board[1][2],this.board[2][0],this.board[2][1],this.board[2][2]};
		int thatRemapped[]={aState.board[0][0],aState.board[0][1],aState.board[0][2],aState.board[1][0],aState.board[1][1],aState.board[1][2],aState.board[2][0],aState.board[2][1],aState.board[2][2]};
		int beforeHim =0;
		int beforeMe =0;
		
		for(int i = 0; i < 9; i++){
			for(int j = 0; j <= i; j++){
				if(meRemapped[j]>meRemapped[i])if(meRemapped[i]!=0)beforeMe+=1;
				if(thatRemapped[j]>thatRemapped[i])if(thatRemapped[i]!=0)beforeHim+=1;
				else;
			}}
		if(beforeMe%2==beforeHim%2)return true;
		else return false; 
	}
	public boolean checkEqual(slideBoard aState){  //CoS
		int someFlag=0;
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				if(aState.board[i][j] == board[i][j]);
				else someFlag=10;}
		if (someFlag==0)return true;
		else return false;		
	}
	public boolean equals(Object obj){
		slideBoard aState = (slideBoard)obj;
		int someFlag=0;
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++){
				if(aState.board[i][j] == board[i][j]);
				else someFlag=10;}
		if (someFlag==0)return true;
		else return false;	
	}
	public int compareTo(Object obj){ //CoS
		slideBoard aState= new slideBoard((slideBoard) obj);
		if(searchSteez==1){heuristic=MHDSumGH; System.out.println("MHD compare");}//ManhattanDistance
		else{heuristic=toopSumGH;System.out.println("TilePlace compare");}//TilePlacmentSearch
				
		if(aState.heuristic>heuristic)return -1;
		if(aState.heuristic<heuristic)return 1;
		/*if(aState.heuristic==heuristic){
			if(checkEqual(aState)){System.out.println("boom Data Collision");return 0;}
			else return -1;	}
		*/
		else {
			System.out.println("equal Heuristic"); 
			if(checkEqual(aState)){
				System.out.println("equal state");
				((slideBoard) obj).Depth= Depth;
				return 0;}
			else return 1;}//return 0;
	
	}

}

