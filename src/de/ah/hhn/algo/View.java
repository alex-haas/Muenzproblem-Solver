package de.ah.hhn.algo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class View extends JFrame implements Observer{
	private static final long serialVersionUID = 2699995864389764712L;
	
	private DrawingPanel drawingPanel;
	private MuenzDelegate delegate;
	private Solver solver;
	private int cellSize = 20;

	public View(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildLayout();
	}

	private void buildLayout() {
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(createManagementPanel(),	BorderLayout.NORTH);
		pane.add(new JScrollPane(createDrawingPanel()),		BorderLayout.CENTER);
		pane.add(createControlPanel(),		BorderLayout.SOUTH);
		
		this.setContentPane(pane);
		this.pack();
	}

	private Component createManagementPanel() {
		final JLabel 		coinInputLabel = new JLabel("Münzen:");
		final JTextField 	coinInputField = new JTextField("2 3 10 77",20);
		final JLabel		moneyInputLabel = new JLabel("Betrag:");
		final JTextField 	moneyInputField = new JTextField("5",8);
		final JButton 		startButton = new JButton("Starten");
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String coinString = coinInputField.getText().trim();
				String[] coinStrings = coinString.split("\\s+");
				int[] coins = new int[coinStrings.length];
				for(int i=0; i<coinStrings.length; i++){
					coins[i] = Integer.parseInt(coinStrings[i]);
				}
				int money = Integer.parseInt(moneyInputField.getText().trim());
				delegate.startGame(coins, money);
			}
		});
		
		JPanel managementPanel = new JPanel();
		managementPanel.setLayout(new FlowLayout());
		managementPanel.add(coinInputLabel);
		managementPanel.add(coinInputField);
		managementPanel.add(moneyInputLabel);
		managementPanel.add(moneyInputField);
		managementPanel.add(startButton);
		return managementPanel;
	}
	
	private Component createDrawingPanel() {
		this.drawingPanel = new DrawingPanel();
		return drawingPanel;
	}
	
	private Component createControlPanel() {
		final JButton nextButton = new JButton("next");
		final JButton backButton = new JButton("back");
		
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(solver == null){
					showStartFirstMessage();
					return;
				}
				delegate.next();
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(solver == null){
					showStartFirstMessage();
					return;
				}
				delegate.back();
			}
		});
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(backButton);
		controlPanel.add(nextButton);
		return controlPanel;
	}
	
	private void showStartFirstMessage(){
		JOptionPane.showMessageDialog(View.this, "Starten Sie erst die Runde.", "Start benötigt", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void setDelegate(MuenzDelegate delegate){
		this.delegate = delegate;
	}
	
	@Override
	public void update(Observable model, Object nothing) {
		Solver solver = (Solver) model;
		if(!solver.equals(this.solver)){
			this.solver = solver;
			this.drawingPanel.invalidate();
			this.revalidate();
		}
		repaint();
	}
	
	class DrawingPanel extends JPanel{
		private static final long serialVersionUID = 6881615689153866000L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// Clear the old drawing
			g.setColor(Color.WHITE);
			Dimension preferredSize = this.getPreferredSize();
			g.fillRect(0, 0, preferredSize.width, preferredSize.height);
			
			if(solver!=null){
				// Draw the bars
				int[][] matrix = solver.getMatrix();
				int[] coins = solver.getCoins();
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(0, 0, (matrix.length+1)*cellSize, cellSize);
				g.fillRect(0, 0, cellSize, (matrix[0].length+1)*cellSize);
				
				g.setColor(Color.BLACK);
				for(int i=0; i<coins.length; i++) g.drawString(""+coins[i], 4, (i+1)*cellSize+cellSize-4);
				for(int i=0; i<matrix.length; i++) g.drawString(""+i, (i+1)*cellSize+4, cellSize-4);
				
				g.setColor(Color.BLACK);
				g.drawLine(0, cellSize-1, (matrix.length+1)*cellSize, cellSize-1);
				g.drawLine(cellSize-1, 0, cellSize-1, (matrix[0].length+1)*cellSize);
				
				// Translate and draw the Matrix
				g.translate(cellSize, cellSize);
				drawMatrix(g);
			}
		}
		
		private void drawMatrix(Graphics g) {
			// Draw the Numbers and eventually their Backgrounds
			int[][] matrix = solver.getMatrix();
			Point currentPos = solver.getCurrentPosition();
			Point source1Pos = solver.getSource1Position();
			Point sourc2Pos = solver.getSource2Position();
			for(int y=0; y<matrix[0].length; y++){
				for(int x=0; x<matrix.length; x++){
					if(x==currentPos.x && y==currentPos.y){
						g.setColor(Color.ORANGE);
						g.fillRect(x*cellSize, y*cellSize, cellSize, cellSize);
					}
					if(source1Pos!=null && x==source1Pos.x && y==source1Pos.y){
						g.setColor(Color.YELLOW);
						g.fillRect(x*cellSize, y*cellSize, cellSize, cellSize);
					}
					if(sourc2Pos!=null && x==sourc2Pos.x && y==sourc2Pos.y){
						g.setColor(Color.YELLOW);
						g.fillRect(x*cellSize, y*cellSize, cellSize, cellSize);
					}
					g.setColor(Color.BLACK);
					g.drawString(""+matrix[x][y], x*cellSize+5, y*cellSize+cellSize-4);
				}
			}
			
			// Draw the Lines
			g.setColor(Color.BLACK);
			for(int x=0; x<matrix.length; x++) g.drawLine(x*cellSize+cellSize, 0, x*cellSize+cellSize, matrix[0].length*cellSize);
			for(int y=0; y<matrix[0].length; y++) g.drawLine(0, y*cellSize+cellSize, matrix.length*cellSize, y*cellSize+cellSize);
		}

		@Override
		public Dimension getPreferredSize() {
			if(solver == null) return new Dimension(500,200);
			else return new Dimension(solver.getMatrix().length*cellSize+cellSize,solver.getMatrix()[0].length*cellSize+cellSize);
		}
	}
	
	static interface MuenzDelegate{
		public void next();
		public void back();
		public void startGame(int[] coins, int money);
	}
}
