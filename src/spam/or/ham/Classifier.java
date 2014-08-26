package spam.or.ham;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Classifier {

	private static List<String> _inputMsgs;;
	private List<String> _spam = new ArrayList<String>();
	private List<String> _ham = new ArrayList<String>();
	private Map<String, Double> featuresProb = new HashMap<String, Double>();
	Map<String, Double> featuresProbSpam = new HashMap<String, Double>();
	Map<String, Double> featuresProbHam = new HashMap<String, Double>();

	public Classifier(List<String> inputMsgs) {
		// TODO Auto-generated constructor stub
		_inputMsgs = inputMsgs;
		separateSpamAndHam();
		calculateProb();

	}

	private void calculateProb() {
		// TODO Auto-generated method stub

		//		for (int i = 0; i < _spam.size()*0.9; i++) {
		//			String msg = _spam.get(i);
		//			String[] msgSplit = _spam.
		//		}
		//		
		//		HashMap spamFreq = new HashMap();
		//		spamFreq.put(key, value);


	}

	private void separateSpamAndHam() {
		// TODO Auto-generated method stub

		for (String s: _inputMsgs) {
			String[] array = s.split("\t");
			if (array.length == 2) {
				if (array[0].equals("ham"))  {
					_ham.add(array[1]);
				} else {
					_spam.add(array[1]);
				}
			}
		}

		featuresProbSpam = calculateProbability(_spam);
		featuresProbSpam = calculateProbability(_ham);

	}

	private boolean checkIfWord(String word) {
		// TODO Auto-generated method stub
		char[] chars = word.toCharArray();

		for (char c : chars) {
			if(!Character.isLetter(c)) {
				return false;
			}
		}
		return true;	
	}

	public boolean isUpper(String s) {

		if (s.length() < 2) {
			return false;
		}

		for(char c : s.toCharArray()) {
			if(! Character.isUpperCase(c)) {
				return false;
			}
		}

		return true;
	}

	//	private void calcAve(int size, List<Integer> array) {
	//		// TODO Auto-generated method stub
	////		int[] len = new int[size];
	//		int sum = 0;
	//		for (Integer i: array) {
	//			sum += i.intValue();
	//		}
	//		
	//		System.out.println("AVERAGE: "+sum/array.size());
	//
	//		
	//	}

	public Map<String, Double> calculateProbability(List<String> label) {

		Map<String, Integer> featuresFreq = new HashMap<String, Integer>();
		Map<String, Double> featuresProb = new HashMap<String, Double>();


		String charToDel = ":;'><^!?. \"/-_,";
		String pat = "[" + Pattern.quote(charToDel) + "]";

		for (String s: label) {

			//check if the whole msg is caps. ham tends to have msgs all caps 
			if (isUpper(s.replaceAll("[^a-zA-Z ]", "").replaceAll(" ", ""))) {
				if (!featuresFreq.containsKey("Caps msg")) {
					featuresFreq.put("Caps msg", 1);
				} else {
					featuresFreq.put("Caps msg", featuresFreq.get("Caps msg") + 1);
				}
				continue;
			}

			//check for websites
			if (s.contains("www.")) {
				if (!featuresFreq.containsKey("Contains link")) {
					featuresFreq.put("Contains link", 1);
				} else {
					featuresFreq.put("Contains link", featuresFreq.get("Contains link") + 1);
				}
			}

			//checks for currency signs
			if ((s.contains("£")) || (s.contains("$"))) {
				if (!featuresFreq.containsKey("Contains currency")) {
					featuresFreq.put("Contains currency", 1);
				} else {
					featuresFreq.put("Contains currency", featuresFreq.get("Contains currency") + 1);
				}
			}

			//categorise by length of messages

			if (s.length() <= 25) {
				if (!featuresFreq.containsKey("Length 0 and 25")) {
					featuresFreq.put("Length 0 and 25", 1);
				} else {
					featuresFreq.put("Length 0 and 25", featuresFreq.get("Length 0 and 25") + 1);
				}
			} else if ((s.length() > 25) && (s.length() <= 50)) {
				if (!featuresFreq.containsKey("Length 25 and 50")) {
					featuresFreq.put("Length 25 and 50", 1);
				} else {
					featuresFreq.put("Length 25 and 50", featuresFreq.get("Length 25 and 50") + 1);
				}
			} else if ((s.length() > 50) && (s.length() <= 75)) {
				if (!featuresFreq.containsKey("Length 50 and 75")) {
					featuresFreq.put("Length 50 and 75", 1);
				} else {
					featuresFreq.put("Length 50 and 75", featuresFreq.get("Length 50 and 75") + 1);
				}
			} else if ((s.length() > 75) && (s.length() <= 100)) {
				if (!featuresFreq.containsKey("Length 75 and 100")) {
					featuresFreq.put("Length 75 and 100", 1);
				} else {
					featuresFreq.put("Length 75 and 100", featuresFreq.get("Length 75 and 100") + 1);
				}	
			} else if ((s.length() > 100) && (s.length() <= 125)) {
				if (!featuresFreq.containsKey("Length 100 and 125")) {
					featuresFreq.put("Length 100 and 125", 1);
				} else {
					featuresFreq.put("Length 100 and 125", featuresFreq.get("Length 100 and 125") + 1);
				}	
			} else if ((s.length() > 125) && (s.length() <= 150)) {
				if (!featuresFreq.containsKey("Length 125 and 150")) {
					featuresFreq.put("Length 125 and 150", 1);
				} else {
					featuresFreq.put("Length 125 and 150", featuresFreq.get("Length 125 and 150") + 1);
				}
			} else if (s.length() > 150) {
				if (!featuresFreq.containsKey("Length 150 and more")) {
					featuresFreq.put("Length 150 and more", 1);
				} else {
					featuresFreq.put("Length 150 and more", featuresFreq.get("Length 150 and more") + 1);
				}
			}

			List<Integer> lens = new ArrayList<Integer>();

			for (String word: s.split(pat)) {
				if (word.isEmpty()) {
					continue;
				}

				String noPunc = word.replaceAll("[^a-zA-Z ]", "");

				if (checkIfWord(noPunc)) {
					//					
					if (isUpper(word)){

						//check for all caps words
						if (!featuresFreq.containsKey("Caps word")) {
							featuresFreq.put("Caps word", 1);
						} else {
							featuresFreq.put("Caps word", featuresFreq.get("Caps word") + 1);
						}

					}

					String wordLower = noPunc.toLowerCase();
					//adds words lowercased to hashmap
					if (!featuresFreq.containsKey(wordLower)) {
						featuresFreq.put(wordLower, 1);
					} else {
						featuresFreq.put(wordLower, featuresFreq.get(wordLower) + 1);
					}


				}
			}
		}

		Iterator it = featuresFreq.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry)it.next();

			String key = (String) pairs.getKey();

			if (key.equals("Caps msg")) {
				featuresProb.put("Caps msg", featuresFreq.get("Caps msg")/(double)label.size());
			} else	if (key.equals("Contains link")) {
				featuresProb.put("Contains link", featuresFreq.get("Contains link")/(double)label.size());
			} else if (key.equals("Contains currency")) {
				featuresProb.put("Contains currency", featuresFreq.get("Contains currency")/(double)label.size());
			} else if (key.equals("Length 0 and 25")) {
				featuresProb.put("Length 0 and 25", featuresFreq.get("Length 0 and 25")/(double)label.size());
			} else if (key.equals("Length 25 and 50")) {
				featuresProb.put("Length 25 and 50", featuresFreq.get("Length 25 and 50")/(double)label.size());
			} else if (key.equals("Length 50 and 75")) {
				featuresProb.put("Length 50 and 75", featuresFreq.get("Length 50 and 75")/(double)label.size());
			} else if (key.equals("Length 75 and 100")) {
				featuresProb.put("Length 75 and 100", featuresFreq.get("Length 75 and 100")/(double)label.size());
			} else if (key.equals("Length 100 and 125")) {
				featuresProb.put("Length 100 and 125", featuresFreq.get("Length 100 and 125")/(double)label.size());
			} else if (key.equals("Length 125 and 150")) {
				featuresProb.put("Length 125 and 150", featuresFreq.get("Length 125 and 150")/(double)label.size());
			} else if (key.equals("Length 150 and more")) {
				featuresProb.put("Length 150 and more", featuresFreq.get("Length 150 and more")/(double)label.size());
			} else if (key.equals("Caps word")) {
				featuresProb.put("Caps word", featuresFreq.get("Caps word")/((double)featuresFreq.size()-11));
			} else {
				featuresProb.put(key, featuresFreq.get(key)/((double)featuresFreq.size()-11));
			}

		}
		
//		Iterator it2 = featuresProb.entrySet().iterator();
//		while (it2.hasNext()) {
//			Map.Entry pairs = (Map.Entry)it2.next();
//			System.out.println(pairs.getKey() + " = " + pairs.getValue());
//			it2.remove(); // avoids a ConcurrentModificationException
//		}
		return featuresProb;

	}


}

