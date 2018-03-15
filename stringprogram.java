import java.util.Scanner;
public class stringprogram {

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Please write a sentence: ");
		String sentences = reader.nextLine();
		String[] sentenceArray = sentences.split("\\. ");
		//separates the inputed string for each period and space
		String finalOutput = "";
		//The individual sentence components will be added to this string in the subsequent for loop
		for (int x = 0; x < sentenceArray.length; x++) {
			finalOutput += sentenceArray[x].substring(0, 1).toUpperCase() + sentenceArray[x].substring(1).toLowerCase();
			//each section has the first letter capitalized, and the rest lower-cased.
			if (x != sentenceArray.length-1) 
				finalOutput += ". ";
			//since the period and space are removed, they must be added in (except for the last sentence, since it is not split)
		}
		String newString = sentences.substring(0, 1).toUpperCase() + sentences.substring(1);
		System.out.println(finalOutput);
	}

}


