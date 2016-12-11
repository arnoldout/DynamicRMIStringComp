package ie.gmit.sw;

/**
 * @author Oliver Arnold
 * Singleton service
 * used to convert drop down string to enum
 * and convert enum to stringComparable instance
 */
public class AlgoService {
	private static AlgoService as;
	
	private AlgoService()
	{
		super();
	}
	/**
	 * Get the single instance of AlgoService
	 * @return AlgoService
	 */
	public static AlgoService getInstance()
	{
		if(as==null)
		{
			as = new AlgoService();
		}
		return as;
	}
	
	/**
	 * Convert string to stringComparer
	 * @param String algo
	 * @return Enum StringComparer
	 */
	public ComparerType getStringComparerType(String algo){
		switch(algo)
		{
		case "Levenshtein Distance":
			return ComparerType.Levenshtein;
		case "Hamming Distance":
			return ComparerType.HammingDistance;
		case "Damerau-Levenshtein Distance":
			return ComparerType.DamerauLevenshtein;
		case "Jaro–Winkler Distance":
			return ComparerType.JaroWinkler;
		}
		return null;
	}
	/**
	 * Convert StringConverter to instance of StringComparable
	 * @param Enum eComparer
	 * @return Instance of StringComparable
	 */
	public StringComparable getComparable(ComparerType eComparer){
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
