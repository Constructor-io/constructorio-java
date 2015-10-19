package com.constructor.client

class ConstructorExample {
	public main(String[] args) {
		switch (args[0]) {
			case "query":
				// assertions?
				ConstructorIO constructor = new ConstructorIO("-", "P03bVBcmyYjSG1ZQyD4V", true, null);
				for (int i = 1; i < args.length; i++) {
					JsonNode res = constructor.query(s);
					System.out.println(res.toString());
				}
			default:
				System.out.println("Usage: constructor (query | add | remove | modify | trackSearch | trackConversion | trackClickThrough) args...");
		}
	}
