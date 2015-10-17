package com.constructor.example

import com.constructor.client.ConstructorIO;

class ConstructorExample {
	public main(String[] args) {
		ConstructorIO constructor = new ConstructorIO("some code", "some code", true, null);
		for (String s: args) {
			constructor.query(s);
		}
		// just do standard english autocomplete
	}
}
