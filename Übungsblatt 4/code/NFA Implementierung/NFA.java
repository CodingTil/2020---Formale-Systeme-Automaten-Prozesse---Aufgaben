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

		 return states;
	 }


	 /**
	  * Used for parsing the files. Makes it a lot simpler and doesn't affect the NFA itself.
	  */
	 public void setData(HashMap<S,HashMap<A,HashSet<S>>> data) {
		 this.data = data;
	 }

	 public static void main(String[] args) {
		NFA nfa = createRegexNFA();
		
        boolean[] results = new boolean[20];
        long[] time = new long[20];

		for(int i = 1; i <= 20; i++) {
		   String text = "1,2,3,4,5,6,7,8,9,10,11,12";
		   for(int j = 0; j < i; j++) {
			   text += ",1";
		   }
		   List<Character> list = intoList(text);
		   long start = System.nanoTime();
		   Set s = nfa.simulate(0, list);
		   long end = System.nanoTime();
		   long elapsed = (end - start);
		   boolean result = false;
		   if(s.size() == 1 && s.contains(14)) result = true;
		   results[i-1] = result;
		   time[i-1] = elapsed;
	   }

		long avg = 0;
		System.out.println("Index | Result | Duration (ns)");
		for(int i = 01; i <= 20; i++) {
			String text = i + "    ";
			if(i < 10) text += " ";
			text += "| " + results[i-1] + "  ";
			if(results[i-1] == true) text += " ";
			text += "| " + time[i-1];
			System.out.println(text);
			avg += time[i-1];
		}

		avg /= 20;

		System.out.println("Average Time (ns): " + avg);
	 }

	 public static List<Character> intoList(String s) {
		 List<Character> list = new ArrayList<>();
		 for(char c : s.toCharArray()) {
			 list.add(c);
		 }
		 return list;
	 }

	public static NFA<Integer, Character> createRegexNFA() {
		Set states = new HashSet<>(Arrays.asList(new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14}));
		NFA<Integer, Character> nfa = new NFA<Integer,Character>(states);

		int q;
		for(q = 0; q < 13; q++) {
			for(int i = 0; i <= 9; i++) {
				nfa.addTransition(q, (char)(i+'0'), q);
			}
			nfa.addTransition(q, ',', q+1);
		}
		nfa.addTransition(13, 'z', 14);

		return nfa;
	}
}
