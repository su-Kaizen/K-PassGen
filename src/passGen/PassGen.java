package passGen;
import java.util.*;
import java.io.*;

public class PassGen {
	/*Characters like ^ are excludes from being generated*/
	private static Set<Integer> excluded = new HashSet<>(List.of(34,39,40,41,44,58,59,60,62,91,92,93,94,95,96));
	public static List<String> words;
	/* Indicates the length of the password and generates it with random characters*/
	public static String gen(int len) {
		String pass = "";
	
		while(len>0) {
			pass += (char)randomChar(33,122);
			len --;
		}
	
		return pass;
	}
	
	
	 // Generates numbers from n to a, both included
	private static int random(int n, int a) {
		a = a+1;
		int number = a-n;
		int random = (int) (Math.random()*number+n);
		
		return random;
	}
	
	// Genererates a number from n to a both included but avoiding the exluded ansi codes.
	public static int randomChar(int n, int a) { 
		int random = 0;
		do {
			random = random(n,a);
		}
		while(excluded.contains(random));
	
		return random;
	}
		

	public static void loadWords(boolean plain) {
		if(plain) {
			try (BufferedReader b = new BufferedReader(new FileReader("words2.txt"))){
				words = new ArrayList<>();
				String word = b.readLine();
				while(word != null) {
					/* The first letter of every word is uppercase by default*/
					word = word.substring(0,1).toUpperCase()+word.substring(1);
					words.add(word);
					word = b.readLine();
					
				}
			}
			catch(IOException ex) {
				System.out.println(ex);
			}
			
		}
		else {
			// Hace que cargue el fichero como recurso del jar y no como fichero del sistema de archivos
			try (ObjectInputStream o = new ObjectInputStream(PassGen.class.getResourceAsStream("/passGen/resources/words.bin"))){
				
				words = new ArrayList<>((List<String>) o.readObject());
				
			}
			catch(IOException | ClassNotFoundException ex) {
				System.out.println(ex);
			}
		}
	}
	
	
	public static void saveWordsBin() {
		try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("words.bin"))){
			if(words != null) {
				o.writeObject(words);
			}
			else {
				System.out.println("Not saved, empty list");
			}
			
		}
		catch(IOException ex) {
			System.out.println(ex);
		}
	}
	
	public static void saveWordsPlain() {
		try (BufferedWriter b = new BufferedWriter(new FileWriter("words2.txt"))){
			
			for(String i: words) {
				b.write(i);
				b.newLine();
			}
			
			
		}
		catch(IOException ex) {
			
		}
	}
	
//	public static void showLen() {
//		System.out.println(words.size());
//	}
//	
	
	
//	public static void removeDuplicates() {
//		HashSet<String> s = new HashSet<>(words);
//		words = new ArrayList<>(s);
//		
//		
//	}
	
	public static String gen(int nwords, String separator, boolean lower, int numbers) {
		String pass = "";
		int size = words.size()-1;
	
		while(nwords > 0) {
			String w = words.get(random(0,size));
			w = lower ? w : w.toUpperCase();
			pass += nwords == 1 ? w : w+separator;
			nwords --;
		}
		
		while(numbers > 0) {
			pass += random(0,9)+"";
			numbers --;
		}
		return pass;
	}
	
}