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
		case "Jaro–Winkler Distance":
			return StringComparer.JaroWinkler;
		}
		return null;
	}
	
	public static StringComparable getComparable(StringComparer eComparer){
		switch (eComparer) {
		case DamerauLevenshtein:
			return new DamerauLevenshtein();
			
		case HammingDistance:
			return new HammingDistance();
			
		case Levenshtein:
			return new Levenshtein();
			
		case JaroWinkler:
			return new JaroWinkler();
			default:
				return null;
		}
	}
}
