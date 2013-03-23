package com.example.aroundthecorner;

import java.util.ArrayList;

public class DealsDates {
	private String title;
	private ArrayList<String> arrayChildren;
	
	/**
	 * Public Getter - gets the title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Public Setter - sets the title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Public Getter - gets the array list of children
	 * @return array list of children
	 */
	public ArrayList<String> getArrayChildren() {
		return arrayChildren;
	}
	
	/**
	 * Public Setter - sets the array list of children
	 * @param array list of children
	 */
	public void setArrayChildren(ArrayList<String> arrayChildren) {
		this.arrayChildren = arrayChildren;
	}

}
