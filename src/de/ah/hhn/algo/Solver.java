package de.ah.hhn.algo;

import java.awt.Point;
import java.util.Observable;

public class Solver extends Observable{

	private int[] coins;
	private int money;
	
	private int currX, currY;
	
	private int[][] matrix;	// [money][coin]
	
	public Solver(int[] coins, int money){
		this.coins = coins;
		this.money = money;
		
		this.matrix = new int[money+1][coins.length];
		this.currX = money;
		this.currY = coins.length-1;
		this.matrix[this.currX][this.currY] = 1;
	}
	
	public boolean next(){
		// Calculate the new position
		if(this.currX == 0){
			if(this.currY == 0) return false;
			this.currX = money;
			this.currY--;
		} else {
			this.currX--;
		}
		
		// Calculate the Value and set it to the matrix
		Point source1Pos = this.getSource1Position();
		Point source2Pos = this.getSource2Position();
		if(source1Pos != null) this.matrix[this.currX][this.currY] += matrix[source1Pos.x][source1Pos.y];
		if(source2Pos != null) this.matrix[this.currX][this.currY] += matrix[source2Pos.x][source2Pos.y];

		this.setChanged();
		this.notifyObservers();
		return true;
	}
	
	public boolean back(){
		// Calculate the new position
		if(this.currX == money && this.currY == coins.length-1) return false;
		// Set the current position as not calculated and calculate next position
		this.matrix[this.currX][this.currY] = 0;	
		if(this.currX == money){
			this.currX = 0;
			this.currY++;
		} else {
			this.currX++;
		}

		this.setChanged();
		this.notifyObservers();
		return true;
	}
	
	public Point getCurrentPosition(){
		return new Point(this.currX, this.currY);
	}
	
	public Point getSource1Position(){
		if(this.currY == this.coins.length-1) return null;
		return new Point(this.currX, this.currY+1);
	}
	
	public Point getSource2Position(){
		if(this.currX > money-coins[this.currY]) return null;
		return new Point(this.currX+this.coins[this.currY], this.currY);
	}
	
	public int[][] getMatrix(){
		return this.matrix;
	}
	
	public int[] getCoins() {
		return this.coins;
	}
	
	public void printMatrix(){
		System.out.println("### Printing Matrix!");
		for(int y=0; y<matrix[0].length; y++){
			for(int x=0; x<matrix.length; x++){
				System.out.print(matrix[x][y]+"\t");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		// Test 1: Simple Test where 1 should be the result
		System.out.println("Test 1: Result should be '1'");
		Solver solver = new Solver(new int[]{2,3,10,77},5);
		solver.printMatrix();
		for(int i=0; i<4*5-1; i++) solver.next();
		solver.printMatrix();
		
		// Test 2: Simple Test where 1 should be the result
		System.out.println("Test 1: Result should be '1'");
		Solver solver2 = new Solver(new int[]{2,3,10,77},55);
		solver2.printMatrix();
		for(int i=0; i<999; i++) solver2.next();
		solver2.printMatrix();
		for(int i=0; i<999; i++) solver2.back();
		solver2.printMatrix();
		for(int i=0; i<999; i++) solver2.next();
		solver2.printMatrix();
	}
}
