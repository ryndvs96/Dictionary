
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Define {
	public static void main(String[] args) throws Exception {

		//Scanner
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		//Sets up how large Array wordList is
		System.out.println("How many words?");
		int number = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter Words:");

		//Array wordList
		String[] wordList = new String[number];
		String[] defList = new String[number];

		//Puts words into Array
		for(int i = 0; i < number; i++){
			wordList[i] = scan.nextLine();
		}


		//Goes through each word getting the definition
		for(int i = 0; i<number; i++){
			String word = wordList[i];

			//Adds to "+" to complete the URL
			if (word.contains(" ") || word.contains("-")) {
				word = word.replaceAll(" " , "+");
				word = word.replaceAll("-" , "+");
			}

			//The URL
			URL dictionary = new URL("http://dictionary.reference.com/browse/" + word + "?s=t");
			BufferedReader in = new BufferedReader(new InputStreamReader(dictionary.openStream()));

			StringBuffer sb = new StringBuffer();

			//Puts HTML code from URL into String
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				sb.append(inputLine.toString());
			in.close();


			//Gets location of definition
			String str = "<meta name=\"description\" content=\"";
			int loc = (sb.indexOf(str) + str.length() + word.length() + 13);
			String locStr = sb.substring(loc);
			int endLoc = locStr.indexOf("\"/>");


			//The Definition
			String def = locStr.substring(0, (endLoc-10));

			if (def.contains(": ")) {
				def = locStr.substring(0, locStr.indexOf(": "));
			}

			//Output
			String definition = ((i+1) + ". " + wordList[i] + " - " + def);
			defList[i] = definition;
			if (def.equals("t Dictionary.com, a free online dictionary with pronunciation, synonyms and translation. Look i")) {

				System.out.println(word + " - " + "***MISSPELLED***");
				do {
					System.out.println("Correct Spelling?");
					wordList[i] = scan.nextLine();
					word = wordList[i];
					//Adds to "+" to complete the URL
					if (word.contains(" ") || word.contains("-")) {
						word = word.replaceAll(" " , "+");
						word = word.replaceAll("-" , "+");
					}

					//The URL
					dictionary = new URL("http://dictionary.reference.com/browse/" + word + "?s=t");
					in = new BufferedReader(new InputStreamReader(dictionary.openStream()));

					sb = new StringBuffer();

					//Puts HTML code from URL into String
					while ((inputLine = in.readLine()) != null)
						sb.append(inputLine.toString());
					in.close();

					//Gets location of definition
					str = "<meta name=\"description\" content=\"";
					loc = (sb.indexOf(str) + str.length() + word.length() + 13);
					locStr = sb.substring(loc);
					endLoc = locStr.indexOf("\"/>");

					//The Definition
					def = locStr.substring(0, (endLoc-10));

					if (def.contains(": ")) {
						def = locStr.substring(0, locStr.indexOf(": "));
					}

					//Output
					definition = ((i+1) + ". " + wordList[i] + " - " + def);
					defList[i] = definition;
				} while(def.equals("t Dictionary.com, a free online dictionary with pronunciation, synonyms and translation. Look i"));
				System.out.println(definition);
			}
			else {
				System.out.println(definition);
			}
		}

		System.out.println("Change?");
		String ans = scan.nextLine();
		if (ans.equals("yes")) {
			do {
				System.out.println("Number?");
				int x = scan.nextInt();

				System.out.println("New Word?");
				String word = wordList[x-1];
				String newWord = scan.nextLine();
				newWord = scan.nextLine();

				//Adds to "+" to complete the URL
				if (word.contains(" ") || word.contains("-")) {
					word = word.replaceAll(" " , "+");
					word = word.replaceAll("-" , "+");
				}

				//The URL
				URL dictionary = new URL("http://dictionary.reference.com/browse/" + newWord + "?s=t");
				BufferedReader in = new BufferedReader(new InputStreamReader(dictionary.openStream()));

				StringBuffer sb = new StringBuffer();

				//Puts HTML code from URL into String
				String inputLine;
				while ((inputLine = in.readLine()) != null)
					sb.append(inputLine.toString());
				in.close();

				//Gets location of definition
				String str = "<meta name=\"description\" content=\"";
				int loc = (sb.indexOf(str) + str.length() + newWord.length() + 13);
				String locStr = sb.substring(loc);
				int endLoc = locStr.indexOf(" See more.\"/>");

				//The Definition
				String def = locStr.substring(0, endLoc);

				if (def.contains(": ")) {
					def = locStr.substring(0, locStr.indexOf(": "));
				}

				//Output
				String definition = ((x) + ". " + wordList[x-1] + " - " + def);
				defList[x-1] = definition;
				System.out.println(definition);
				System.out.println("Change more?");
				ans = scan.nextLine();

			} while (ans.equals("yes"));
		}
		System.out.println("Specify?");
		ans = scan.nextLine();
		if (ans.equals("yes")) {
			do {
				System.out.println("Number?");
				int x = scan.nextInt();

				System.out.println("noun, verb, adj, or adv?");
				String word = wordList[x-1];
				String specific = scan.nextLine();
				specific = scan.nextLine();


				//The URL
				URL dictionary = new URL("http://dictionary.reference.com/browse/" + word + "?s=t");
				BufferedReader in = new BufferedReader(new InputStreamReader(dictionary.openStream()));

				StringBuffer sb = new StringBuffer();

				//Puts HTML code from URL into String
				String inputLine;
				while ((inputLine = in.readLine()) != null)
					sb.append(inputLine.toString());
				in.close();
				String def = null;

				if (specific.equals("noun")) {
					//Gets location of definition
					String noun = "<span class=\"pg\">noun";
					int nounLoc = (sb.indexOf(noun));
					String nounLocStr = sb.substring(nounLoc);
					String lang = "</span><div class=\"dndata\">";
					String nounSection = nounLocStr.substring(0, nounLocStr.indexOf(lang));
					int nounLength = nounSection.length() + lang.length();


					//The Definition
					def = nounLocStr.substring(nounLength, nounLocStr.indexOf(" </div></div>"));

					if (def.contains(": ")) {
						def = def.substring(0, def.indexOf(": "));
					}
					if (def.contains("</span>")) {
						def = def.substring(0, def.indexOf("</span>"));
					}
				}
				else if (specific.equals("verb")) {
					String verb = "<span class=\"pg\">verb";
					int verbLoc = (sb.indexOf(verb));
					String verbLocStr = sb.substring(verbLoc);
					String lang = "</span><div class=\"dndata\">";
					String verbSection = verbLocStr.substring(0, verbLocStr.indexOf(lang));
					int verbLength = verbSection.length() + lang.length();


					//The Definition
					def = verbLocStr.substring(verbLength, verbLocStr.indexOf(" </div></div>"));

					if (def.contains(": ")) {
						def = def.substring(0, def.indexOf(": "));
					}
					if (def.contains("</span>")) {
						def = def.substring(0, def.indexOf("</span>"));
					}
				}
				else if (specific.equals("adj")) {
					String adj = "<span class=\"pg\">adjective";
					int adjLoc = (sb.indexOf(adj));
					String adjLocStr = sb.substring(adjLoc);
					String lang = "</span><div class=\"dndata\">";
					String adjSection = adjLocStr.substring(0, adjLocStr.indexOf(lang));
					int adjLength = adjSection.length() + lang.length();


					//The Definition
					def = adjLocStr.substring(adjLength, adjLocStr.indexOf(" </div></div>"));

					if (def.contains(": ")) {
						def = def.substring(0, def.indexOf(": "));
					}
					if (def.contains("</span>")) {
						def = def.substring(0, def.indexOf("</span>"));
					}
				}
				else if (specific.equals("adv")) {
					String adv = "<span class=\"pg\">adverb";
					int advLoc = (sb.indexOf(adv));
					String advLocStr = sb.substring(advLoc);
					String lang = "</span><div class=\"dndata\">";
					String advSection = advLocStr.substring(0, advLocStr.indexOf(lang));
					int advLength = advSection.length() + lang.length();


					//The Definition
					def = advLocStr.substring(advLength, advLocStr.indexOf(" </div></div>"));

					if (def.contains(": ")) {
						def = def.substring(0, def.indexOf(": "));
					}
					if (def.contains("</span>")) {
						def = def.substring(0, def.indexOf("</span>"));
					}
				}

				//Output
				String definition = ((x) + ". " + wordList[x-1] + " - " + def);
				defList[x-1] = definition;
				System.out.println(definition);
				System.out.println("Specify more?");
				ans = scan.nextLine();

			} while (ans.equals("yes"));
		}
		for(int g = 0; g<number; g++){
			System.out.println(defList[g]);
		}
	}
}