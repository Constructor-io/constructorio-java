package com.constructor.client;

import java.util.ArrayList;

class ConstructorExample {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: constructor (query | add | remove | modify | trackSearch | trackConversion | trackClickThrough) args...");
			// exit
		}
		String firstArg = args[0];
		// big if else in order to avoid dependency on java 7
		try {
			if (firstArg.equals("query")) {
				ConstructorIO constructor = new ConstructorIO("-", "P03bVBcmyYjSG1ZQyD4V", true, null);
				for (int i = 1; i < args.length; i++) {
					ArrayList<String> res = constructor.query(args[i]);
					System.out.println(res.toString());
				}
			} else 
			{
				ConstructorIO constructor = new ConstructorIO(args[1], args[2], true, null);
				boolean res = false;
				if (firstArg.equals("add")) {
					String itemName = args[3];
					String autocompleteSection = args[4];
					res = constructor.add(itemName, autocompleteSection);
				} else if (firstArg.equals("remove")) {
					String itemName = args[3];
					String autocompleteSection = args[4];
					res = constructor.remove(itemName, autocompleteSection);
				} else if (firstArg.equals("modify")) {
					String itemName = args[3];
					String newItemName = args[4];
					String autocompleteSection = args[5];
					res = constructor.modify(itemName, newItemName, autocompleteSection);
				} else if (firstArg.equals("trackSearch")) {
					String termName = args[3];
					res = constructor.trackSearch(termName);
				} else if (firstArg.equals("trackConversion")) {
					String termName = args[3];
					String autocompleteSection = args[4];
					res = constructor.trackConversion(termName, autocompleteSection);
				} else if (firstArg.equals("trackClickThrough")) {
					String termName = args[3];
					String autocompleteSection = args[4];
					res = constructor.trackClickThrough(termName, autocompleteSection);
				}
				if (res) {
					System.out.println("Sucess!");
				} // throw otherwise, actually
			}
		} catch (ConstructorException con) {
			System.out.println(con.getMessage());
		}
	}
}
