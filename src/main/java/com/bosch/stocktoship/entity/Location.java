package com.bosch.stocktoship.entity;



/**

* Represents a location in the storage with a specific rack and shelf.

* AUTHOR: AYUSH ARYA(YYA2COB)

*/

public class Location {

	// Rack and shelf identifiers for the location

	private int rack;

	private int shelf;

	// Constructor to initialize rack and shelf

	public Location(int rack, int shelf) {

		this.rack = rack;

		this.shelf = shelf;

	}

	// Getter for the shelf number

	public int getShelf() {

		return shelf;

	}

	// Getter for the rack number

	public int getRack() {

		return rack;

	}

}
 