import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;

import java.io.*;
import java.util.Scanner;
import java.util.*;

public class NFA<S,A> {

	/**
	 * Für die Datenstruktur nehmen wir eine verkettete HashMap. Dies ermöglicht schnelle Zugriffszeiten auf den gewünschten Zustand. Die zweite HashMap stellt die Transitionen von diesem Übergang dar.
	 * Map<S,Map<A,Set<S>>>;
	 */
	private HashMap<S,HashMap<A,HashSet<S>>> data;

	public NFA(Set states) {
		if(states != null) initializeData(states);
	}

	/**
	 * Initialisiert die Datenstruktur.
	 */
	private void initializeData(Set<S> states) {
		data = new HashMap<S,HashMap<A,HashSet<S>>>();
		for(S state : states) {
			data.put(state, new HashMap<A, HashSet<S>>());		// Zu jedem Zustand wird eine HashMap erstellt. Hier werden später die Transitionen gespeichert.
		}
	}

	/**
	 * Fügt eine Transitionen von Zustand q über Buchstabe a zu Zustand p hinzu.
	 * @return true, falls beide Zustände existieren
	 */
	public boolean addTransition(S q, A a, S p) {
		if(!data.containsKey(q) || !data.containsKey(p)) return false;
		if(!data.get(q).containsKey(a)) {
			data.get(q).put(a, new HashSet<S>());
		}
		data.get(q).get(a).add(p);
		return true;
	}

	/**
	 * Simuliert den NFA für eine gegebene Eingabe w ab einem gegebenen Zustand q
	 * @return eine Menge aller erreichbaren Zustände
	 */
	 public Set<S> simulate(S q, List<A> w) {
		 long startTime = System.nanoTime();

		 Set<S> states = new HashSet<S>();
		 states.add(q);

		 for(A a : w) {
			 Set<S> newStates = new HashSet<S>();
			 for(S state : states) {
				 if(data.containsKey(state) && data.get(state).containsKey(a)) {
					 newStates.addAll(data.get(state).get(a));
				 }
			 }
			 states = newStates;
		 }

		 long estimatedTime = System.nanoTime() - startTime;
		 System.out.println("nanoseconds: " + estimatedTime);

		 return states;
	 }


	 /**
	  * Used for parsing the files. Makes it a lot simpler and doesn't affect the NFA itself.
	  */
	 public void setData(HashMap<S,HashMap<A,HashSet<S>>> data) {
		 this.data = data;
	 }

	 public static void main(String[] args) {
		 // Important for files
		 int num_words = 4;

		 // Beispiel auf dem Blatt
		 if(args.length == 0) {
			 Set<Integer> states = new HashSet<Integer>();
			 states.add(1); states.add(2); states.add(3); states.add(4);
			 NFA<Integer, String> M = new NFA<Integer, String>(states);
			 M.addTransition(1, "rechts", 2); M.addTransition(1, "runter", 3);
			 M.addTransition(2, "links", 1); M.addTransition(2, "runter", 4);
			 M.addTransition(3, "rechts", 4); M.addTransition(3, "hoch", 1);
			 M.addTransition(4, "links", 3); M.addTransition(4, "hoch", 2);
			 List<String> eingabe = new LinkedList<String>();
			 eingabe.add("rechts"); eingabe.add("runter"); eingabe.add("links");
			 Set<Integer> reachable = M.simulate(1, eingabe);
			 System.out.println(reachable);
		 }

		 // Langer NFA
		 else {
			 NFA nfa = parseNFA();
			 if(args[0].equals("sheet")) {
				 System.out.println(nfa.simulate(7, intoList("abababbaa")));
			 }else if(args[0].equals("file")) {
				 String[] words = loadWords(num_words);
				 for(int i = 0; i < num_words; i++) {
					 System.out.println(nfa.simulate(7, intoList(words[i])));
				 }
			 }
		 }
	 }

	 public static List<Character> intoList(String s) {
		 List<Character> list = new ArrayList<>();
		 for(char c : s.toCharArray()) {
			 list.add(c);
		 }
		 return list;
	 }

	 public static NFA parseNFA() {
		 NFA<Integer, Character> nfa = new NFA<>(null);
		 HashMap<Integer ,HashMap<Character ,HashSet<Integer>>> data = new HashMap<>();

		 //Reading from file
		 try {
			 FileInputStream fis = new FileInputStream("resources\\2020_H09.trans");
			 Scanner sc = new Scanner(fis);
			 String line = "";
			 while(sc.hasNextLine()) {
				 line = sc.nextLine();
				 if(line == "") break;
				 String[] splits = line.split(" ");

				 int q = Integer.parseInt(splits[0]), p = Integer.parseInt(splits[2]);
				 char a = splits[1].charAt(0);

				 // Adding the q State
				 if(data.containsKey(q)) {
					 if(!data.get(q).containsKey(a)) {
					 	data.get(q).put(a, new HashSet<Integer>());
					}
				}else {
					data.put(q, new HashMap<Character, HashSet<Integer>>());
					data.get(q).put(a, new HashSet<Integer>());
				}

				// Adding Transition
				data.get(q).get(a).add(p);
			 }

			 nfa.setData(data);
		 }catch(FileNotFoundException e) {
			 e.printStackTrace();
		 }
		 return nfa;
	 }

	 public static String[] loadWords(int num_words) {
		 String[] words = new String[num_words];
		 try {
		 	FileInputStream fis = new FileInputStream("resources\\2020_H09_input");
   		 	Scanner sc = new Scanner(fis);
   		 	for(int i = 0; i < num_words; i++) {
   			 	words[i] = sc.nextLine();
   		 	}
		 }catch(FileNotFoundException e) {
			 e.printStackTrace();
		 }
		 return words;
	 }
}
