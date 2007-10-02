
import java.io.IOException;
import java.util.Scanner;

public class Project2 {

	public static void main(String[] args) throws IOException
	{
		Scanner read1 = new Scanner(System.in); // uses default whitespace delimiter
		Scanner read2 = new Scanner(System.in).useDelimiter("\n"); // newline delimiter

		//ask which search method the user wants to use
		System.out.println("Which algorithm would you like to use?");
		System.out.println("Enter 1 for Hill-Climbing\nEnter 2 for Simulated "
				            + "Annealing: ");
		int algo = read1.nextInt();

		// Get the 3-Conjunctive Normal Form sentence from the user

		System.out.println("Enter the 3-CNF sentence using the following syntax:\n"
						   + "NOT is '!', OR is '||', AND is '&&'\n"
						   + "Example: (!A || B || !C) && (A || B || F) && "
						   + "(!B || C || E) && (!B || D || E)");
		String sentence = read2.next();

		try{
		    if(algo == 1){
		    	hillClimb hClimb = new hillClimb(sentence);
		    	hClimb.run();
		    }
		    if(algo == 2){
//		    	simAnneal sAnneal = new simAnneal(sentence);
//		    	sAnneal.run();
			    }
		}
		    catch(Exception e){System.out.println(e);}
	}
}

