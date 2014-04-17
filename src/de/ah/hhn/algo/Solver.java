package de.ah.hhn.algo;

public class Solver {

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
		if(this.currY != this.coins.length-1){
			this.matrix[this.currX][this.currY] = matrix[this.currX][this.currY+1];
		}
		if(this.currX <= money-coins[this.currY]){
			this.matrix[this.currX][this.currY] += matrix[this.currX+this.coins[this.currY]][this.currY];
		}
		return true;
	}
	
	public boolean back(){
		// Calculate the new position
		if(this.currX == money && this.currY == coins.length-1) return false;
		this.matrix[this.currX][this.currY] = 0;	// Set the current position as not calculated and calculate next position
		if(this.currX == money){
			this.currX = 0;
			this.currY++;
		} else {
			this.currX++;
		}

		return true;
	}
	
	public int getCurrentX(){
		return this.currX;
	}
	
	public int getCurrentY(){
		return this.currY;
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
