package passGen;
import java.util.*;
public class Setup {

	public static void main(String[] args) {
		PassGen.loadWords(true);
		System.out.println(PassGen.words.size());
		PassGen.saveWordsBin();
	}

}

