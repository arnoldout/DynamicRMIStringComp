package ie.gmit.sw;

public class AlgoService {
	
	public static StringComparer getComparer(String algo){
		switch(algo)
		{
		case "Levenshtein Distance":
			return StringComparer.Levenshtein;
		case "Hamming Distance":
			return StringComparer.HammingDistance;
		case "Damerau-Levenshtein Distance":
			return StringComparer.DamerauLevenshtein;
		}
		return null;
	}
	
}
