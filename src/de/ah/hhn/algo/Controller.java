package de.ah.hhn.algo;

public class Controller {
	private View view;
	private Solver solver;
	
	private View.MuenzDelegate muenzDelegate = new View.MuenzDelegate(){

		@Override
		public void next() {
			solver.next();
		}

		@Override
		public void back() {
			solver.back();
		}

		@Override
		public void startGame(int[] coins, int money) {
			solver = new Solver(coins, money);
			solver.addObserver(view);
			view.update(solver, null);
		}
		
	};
	
	public Controller(){
		this.view = new View();
		this.view.setDelegate(this.muenzDelegate);
		this.view.setVisible(true);
	}
	
	public static void main(String[] args){
		new Controller();
	}
}
