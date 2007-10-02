
import java.util.Random;

public class hillClimb {
	
	String threecnf; // holds the 3-CNF sentence
	boolean[] varValues = new boolean[26]; // holds the truth value for
	                                       // all 26 alphabet characters
	Random randNum = new Random();  // Generates random values for varValues
	char last; // keeps track of the alphabetically last literal character used
	
	public hillClimb(String inSentence)
	{
		threecnf = inSentence;
	}
	
	public void run()
	{
		int loopCount = 0; // # of times the entire while loop has run
		String result = null; // holds results
		String tfClauses = ""; // keeps track of true/false indicators for each clause
		boolean[] randAssign = new boolean[26];  // copy of varVaules for hill-climbing
		
		while(result == null)
		{
			int index = 0; // counter for 3-CNF sentence
			int falseLitCount = 0; // tracks # of false literals
			int varValuesLength; // # of Boolean entries actually used
			boolean literalValue = false; // used to evaluate each individual literal
			boolean threeClauseValue = true; // used to evaluate each 3-clause
			boolean not = false; // indicates a negation symbol '!' has been read in
			boolean error = false; // indicates invalid symbol detected
	
			// Scan through the 3-CNF sentence to find the last literal alphabetically
			
			last = '0';
			for(int i = 0; i < threecnf.length() - 1; i++)
			{
				if((Character.isLetter(threecnf.charAt(i))) && ((threecnf.charAt(i)) > last))
					last = threecnf.charAt(i);
			}
			
			// if this is the first time through the loop...
			
			if(loopCount <= 0)
			{
				// Randomly generate an initial Boolean assignment for all 26 alphabet 
				// characters. Index 0 will contain a's value, 1 will contain b's, etc.
				// randAssign is a copy of varVaules, to be used for hill-climbing 
				// complementing
			
				for(int i = 0; i < 26; i++)
				{
					int binDigit = randNum.nextInt(2);
					varValues[i] = (binDigit == 1?true:false); // converts int to boolean
					randAssign[i] = varValues[i];  
				}
			} else 
			{
				// for each subsequent loop iteration, get a copy of the original
				// Boolean assignments and then complement one of them
				
				for(int i = 0; i < 26; i++)
				{
					varValues[i] = randAssign[i];
				}
				
				varValues[loopCount - 1] = !varValues[loopCount - 1];
			}

			// Evaluates 3-CNF sentence so long as each 3-clause is true. If a single 
			// 3-clause is false, then the evaluation must stop since it will be 
			// impossible for the 3-CNF sentence to be completely true at that point
		
			while((index < threecnf.length() - 1) && (threeClauseValue) && (!error))
			{
				// Checks if the sentence segment at given index is an alphabetic character
			
				if(Character.isLetter(threecnf.charAt(index)))
				{
					// if so, check the character's truth value
				
					literalValue = literalEval(threecnf, index, not);
				
					// if one of the literals in the 3-clause evaluates as true, 
					// the entire 3-clause is also true and can be skipped. This is done 
					// by moving the index to the end of the clause, indicated by ")" 
				
					if(literalValue == true)
					{
						index = threecnf.indexOf(")", index);  
						falseLitCount = 0;
						tfClauses = tfClauses + "T"; // Count the clause value
					}
					else
						falseLitCount++;  
			
					// if three literals in the 3-clause are false, the entire clause is false
					// and, in turn, the entire sentence is as well
				
					if(falseLitCount >= 3)
					{
						threeClauseValue = false;
						tfClauses = tfClauses + "F"; // Count the clauses value
					}
		
					not = false;  // reset the not value
				}
				else if(threecnf.charAt(index) == '!') 
				{
					not = true;  // a negation symbol was read in
				}
				else if(threecnf.charAt(index) == '&')
				{
					// checks if && has been entered. If so, skips.
					// Otherwise, error
				
					index++;
					if(threecnf.charAt(index) != '&')
					{
						error = true;
					}
				}
				else if(threecnf.charAt(index) == '|')
				{
					// checks if || has been entered. If so, skips.
					// Otherwise, error
				
					index++;
					if(threecnf.charAt(index) != '|')
					{
						error = true;
					}
				} 
				else if (threecnf.charAt(index) == ')')
				{
					falseLitCount = 0; // reset false literal count at end of clause
				}
				else if((threecnf.charAt(index) == '(') || (threecnf.charAt(index) == ' '))
				{
					// skip valid characters that don't need to be evaulated
				}
				else
				{
					error = true;
				}
				
				index++;
			}	
	
			// calculate length of array based on number of alphabet characters used and
			// print out only that portion of the Boolean assignment
			
			result = "";
			varValuesLength = Character.toLowerCase(last) - '`'; 
			for(int i = 0; i < varValuesLength; i++)
			{
				if(varValues[i] == true)
					result = result + "1";
				else
					result = result + "0";
			}
			
			// If the search has reached the end of the sentence and the 3-clause value is true
			// a valid Boolean assignment must have been generated.
			
			if(error)
			{
				System.out.println("This sentence contains an invalid symbol.");
			}
			else if(threeClauseValue == false)
			{
				// Add the values for each clause at the end of the result for printing.
				
				result = result + " = " + tfClauses; 
				
				System.out.println("Failed Boolean assignment: " + result);
				result = null;  // reset result to null to loop for answer
				tfClauses = "";
			}
			else
			{    
				// Add the values for each clause at the end of the result for printing.
				
				result = result + " = " + tfClauses; 
				
				System.out.println("One correct answer is:     " + result);
				System.out.println("                           ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			} 
			
			// if each literal being used in varValues has been complemented
			// (as used in hill-climbing) then reset loop to 0 so an entirely 
			// new Boolean assignment is generated 
			
			if(loopCount >= varValuesLength)
			{
				loopCount = 0;
				System.out.println("Random restart...");
			}
			else
				loopCount++;
		}
	}
	
	private boolean literalEval(String sentence, int index, boolean complement)
	{
		boolean literal = true; // the truth value of the literal
		
		switch(sentence.charAt(index))
		{
			// Matches literal value with randomly generated boolean assignment 
			// for that letter
		
			case 'a':
			case 'A':
				literal = varValues[0];
				break;
			case 'b':
			case 'B':
				literal = varValues[1];
				break;
			case 'c':
			case 'C':
				literal = varValues[2];
				break;
			case 'd':
			case 'D':
				literal = varValues[3];
				break;
			case 'e':
			case 'E':
				literal = varValues[4];
				break;
			case 'f':
			case 'F':
				literal = varValues[5];
				break;
			case 'g':
			case 'G':
				literal = varValues[6];
				break;
			case 'h':
			case 'H':
				literal = varValues[7];
				break;
			case 'i':
			case 'I':
				literal = varValues[8];
				break;
			case 'j':
			case 'J':
				literal = varValues[9];
				break;
			case 'k':
			case 'K':
				literal = varValues[10];
				break;
			case 'l':
			case 'L':
				literal = varValues[11];
				break;
			case 'm':
			case 'M':					
				literal = varValues[12];
				break;
			case 'n':
			case 'N':
				literal = varValues[13];
				break;
			case 'o':
			case 'O':					
				literal = varValues[14];
				break;
			case 'p':
			case 'P':					
				literal = varValues[15];
				break;
			case 'q':
			case 'Q':
				literal = varValues[16];
				break;
			case 'r':
			case 'R':					
				literal = varValues[17];
				break;
			case 's':
			case 'S':					
				literal = varValues[18];
				break;
			case 't':
			case 'T':					
				literal = varValues[19];
				break;
			case 'u':
			case 'U':					
				literal = varValues[20];
				break;
			case 'v':
			case 'V':					
				literal = varValues[21];
				break;
			case 'w':
			case 'W':					
				literal = varValues[22];
				break;
			case 'x':
			case 'X':					
				literal = varValues[23];
				break;
			case 'y':
			case 'Y':					
				literal = varValues[24];
				break;
			case 'z':
			case 'Z':					
				literal = varValues[25];
				break;
		}	
		
		// If a '!' symbol preceeded the literal, it gets complemented. 
		
		if(complement)
			literal = !literal;

		return literal;
	}
}
