package com.constructor.client;

import com.mashape.unirest.http.*;

class ConstructorExample {
	public void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: constructor (query | add | remove | modify | trackSearch | trackConversion | trackClickThrough) args...");
			// exit
		}
		String firstArg = args[0];
		// big if else in order to avoid dependency on java 7
		if (firstArg.equals("query")) {
			try {
				ConstructorIO constructor = new ConstructorIO("-", "P03bVBcmyYjSG1ZQyD4V", true, null);
				for (int i = 1; i < args.length; i++) {
					JsonNode res = constructor.query(args[i]);
					System.out.println(res.toString());
				}
			} catch (ConstructorException con) {
				System.out.println("fuck fuck fuck");
			}
		} else if (firstArg.equals("add")) {
			//
		} else if (firstArg.equals("remove")) {
			//
		} else if (firstArg.equals("modify")) {
			//
		} else if (firstArg.equals("trackSearch")) {
			//
		} else if (firstArg.equals("trackConversion")) {
			//
		} else if (firstArg.equals("trackClickThrough")) {
			//
		}
	}
}
