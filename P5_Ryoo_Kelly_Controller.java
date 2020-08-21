
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
/*
 * Kelly Ryoo. P5. 4/1/19. The gui part of the lab took me about 5 hours.
 * After I completed the console version of minesweeper, it was a lot easier to 
 * do the gui lab. I just had to translate everything that the console version
 * had into the gui version, which went pretty smoothly because we had 
 * already done this for the life lab. I struggled a little bit for some
 * of the new things I had to add in, like having three different levels of easy,
 * medium, and hard, but I eventually figured it out. I struggled most on 
 * displaying the board onto the actual screen. First, it was because I had been
 * putting the wrong image file name, so java could not find the tile image. Then,
 * I wasn't sure if I was going to use a gridpane or another method to organize my
 * tiles. I decided to use gridpane, which was confusing at first because I had
 * never used it before, but I managed to figure it out after reading
 * the API carefully. Overall, this lab was very time-consuming, but rewarding
 * to see it finally working after hours of coding and debugging.
 */
/*
 * Kelly Ryoo. P5. 3/30/19. This lab took me about 5 hours.
 * I was at first very confused of what I was doing and what to do because it was
 * the first time we did not have anything like a starter code to go off of.
 * But after referring to my life gui labs and the slides, I followed the basic
 * instructions to create my interface, my model class, and this driver/controller class.
 * from then, I added the methods that we decided were necessary in class and added them
 * to my interface. in my model class, i implemented that interface and did the actual
 * coding. After tha, I just followed what Mr. Ferrante quickly demo-ed for us
 * in class, which was creating the controller and driver class. Everything
 * went pretty smoothly after I was able to get started and understand what
 * I needed to do. My recurisve reveal() method took me a few tries to get right, but
 * I eventually made it work after debugging for about half an hour. Overall,
 * this lab was a good "test" of how much I had learned from the life gui lab and it
 * was fun to try make my own minesweeper.
 */
public class P5_Ryoo_Kelly_Controller extends Application {

	P5_Ryoo_Kelly_MineSweeperModel model;
	int mineNum;
	int rowNums;
	int colNums;
	int[][] grid;
	boolean[][] flags;
	boolean[][] reveals;
	GridPane gridpane;

	long startTime;

	boolean doMore;

	MenuBar menubar;
	Menu game;
	Menu options;
	Menu help;

	BorderPane border;
	MenuItem beginner;
	MenuItem medium;
	MenuItem hard;
	MenuItem exit;
	MenuItem setMines;
	MenuItem about;

	Label minesLeft;
	Label timeSpent;

	Image cellPic;
	Image flagPic;
	Image bombRevealedPic;
	Image bombWrongPic;
	Image num0;
	Image num1;
	Image num2;
	Image num3;
	Image num4;
	Image num5;
	Image num6;
	Image num7;
	Image num8;
	ImageView[][] imageList;
	
	boolean isM;
	boolean isH;

	@Override
	public void start(Stage stage) throws Exception {

		doMore = true;
		startTime = System.currentTimeMillis();
		rowNums = 8;
		colNums = 8;
		mineNum = 10;

		if(isM) {
			mineNum = 40;
		}else if(isH) {
			mineNum = 99;
		}

		// instantaniate
		grid = new int[rowNums][colNums];
		flags = new boolean[rowNums][colNums];
		reveals = new boolean[rowNums][colNums];
		model = new P5_Ryoo_Kelly_MineSweeperModel(rowNums, colNums, mineNum);
		model.setRandomMines(mineNum);

		// basic set up
		stage.setTitle("MINESWEEPER");
		border = new BorderPane();
		Scene scene = new Scene(border, 600, 600);
		stage.setScene(scene);
		stage.setMinWidth(400);
		stage.setMinHeight(400);
		stage.setMaxWidth(1200);
		stage.setMaxHeight(1500);

		// set up
		HBox bottom = new HBox();
		border.setBottom(bottom);
		minesLeft = new Label(Integer.toString(mineNum));
		timeSpent = new Label("0");
		Label remaining = new Label("Mines Remaining: ");
		Label time = new Label("Time Elapsed in seconds: ");
		bottom.getChildren().addAll(remaining, minesLeft, time, timeSpent);
		bottom.setPadding(new Insets(10));
		bottom.setSpacing(10);

		// menu
		menubar = new MenuBar();
		game = new Menu("Game");
		options = new Menu("Options");
		help = new Menu("Help");
		menubar.getMenus().addAll(game, options, help);
		border.setTop(menubar);

		// game menu
		beginner = new MenuItem("Beginner");
		medium = new MenuItem("Medium");
		hard = new MenuItem("Expert");

		exit = new MenuItem("Exit");
		game.getItems().addAll(beginner, medium, hard, exit);

		// options menu
		setMines = new MenuItem("Set Number of Mines");
		options.getItems().addAll(setMines);

		// help menu
		about = new MenuItem("About");
		help.getItems().addAll(about);

		// set eventhandlers
		beginner.setOnAction(new MenuHandler());
		medium.setOnAction(new MenuHandler());
		hard.setOnAction(new MenuHandler());
		exit.setOnAction(new MenuHandler());
		setMines.setOnAction(new MenuHandler());
		about.setOnAction(new MenuHandler());

		// images
		cellPic = new Image("file:minesweeper_images/blank.gif");
		flagPic = new Image("file:minesweeper_images/bomb_flagged.gif");
		bombRevealedPic = new Image("file:minesweeper_images/bomb_revealed.gif");
		bombWrongPic = new Image("file:minesweeper_images/bomb_wrong.gif");
		num0 = new Image("file:minesweeper_images/num_0.gif");
		num1 = new Image("file:minesweeper_images/num_1.gif");
		num2 = new Image("file:minesweeper_images/num_2.gif");
		num3 = new Image("file:minesweeper_images/num_3.gif");
		num4 = new Image("file:minesweeper_images/num_4.gif");
		num5 = new Image("file:minesweeper_images/num_5.gif");
		num6 = new Image("file:minesweeper_images/num_6.gif");
		num7 = new Image("file:minesweeper_images/num_7.gif");
		num8 = new Image("file:minesweeper_images/num_8.gif");

		// fill arraylist with imageviews
		imageList = new ImageView[rowNums][colNums];
		for (int r = 0; r < rowNums; r++) {
			for (int c = 0; c < colNums; c++) {
				imageList[r][c] = new ImageView(cellPic);
				imageList[r][c].setOnMouseClicked(new MyMouseEventHandler());
				imageList[r][c].setOnMousePressed(new MyMouseEventHandler());

			}
		}

		// fill board with imageviews
		gridpane = new GridPane();
		border.setCenter(gridpane);
		gridpane.setAlignment(Pos.CENTER);

		for (int r = 0; r < rowNums; r++) {
			for (int c = 0; c < colNums; c++) {
				// gridpane.setConstraints(imageList[r][c], r, c); // column=2 row=0
				gridpane.add(imageList[r][c], c, r);
			}
		}

		updateView(imageList);
		updateTime();
		stage.show();

	}

	void updateView(ImageView[][] images) {
		for (int r = 0; r < model.getRowNum(); r++) {
			for (int c = 0; c < model.getColNum(); c++) {
				if (!doMore) {
					if (model.isMine(r, c) && model.isRevealed(r, c)) {
						images[r][c].setImage(bombWrongPic);
					}else if (model.isMine(r, c)) {
						images[r][c].setImage(bombRevealedPic);
					}
				} else if (model.isFlagged(r, c)) {
					images[r][c].setImage(flagPic);
				} else if (model.isRevealed(r, c)) {

					int i = model.getNumMineNeighbors(r, c);

					switch (i) {

					case 0:
						images[r][c].setImage(num0);
						break;

					case 1:
						images[r][c].setImage(num1);
						break;

					case 2:
						images[r][c].setImage(num2);
						break;

					case 3:
						images[r][c].setImage(num3);
						break;

					case 4:
						images[r][c].setImage(num4);
						break;

					case 5:
						images[r][c].setImage(num5);
						break;

					case 6:
						images[r][c].setImage(num6);
						break;

					case 7:
						images[r][c].setImage(num7);
						break;

					case 8:
						images[r][c].setImage(num8);
						break;

					}
				} else if (!model.isRevealed(r, c)) {
					images[r][c].setImage(cellPic);
				}
			}
		}
		updateTime();
	}

	public void minesLeft(int newVal) {
		minesLeft.setText(Integer.toString(newVal));
	}

	public void updateTime() {
		int time = (int) (System.currentTimeMillis() - startTime) / 1000;
		timeSpent.setText("" + time);
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void resetArrList() {
		imageList = new ImageView[model.getRowNum()][model.getColNum()];
		for (int r = 0; r < model.getRowNum(); r++) {
			for (int c = 0; c < model.getColNum(); c++) {
				imageList[r][c] = new ImageView(cellPic);
				imageList[r][c].setOnMouseClicked(new MyMouseEventHandler());
				imageList[r][c].setOnMousePressed(new MyMouseEventHandler());

			}
		}
	}

	class MyAnimationTimer extends AnimationTimer {

		@Override
		public void handle(long now) {
			if (doMore) {
				updateTime();
			}
		}
	}

	/*
	 * MenuItem beginner; MenuItem medium; MenuItem hard; MenuItem exit; MenuItem
	 * setMines; MenuItem about;
	 */
	class MenuHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if (event.getSource() == beginner) {
				model.reset();
				model.levelM(8, 8, 10);
				resetArrList();
				GridPane gridpanes = new GridPane();
				border.setCenter(gridpanes);
				gridpanes.setAlignment(Pos.CENTER);
				for (int r = 0; r < model.getRowNum(); r++) {
					for (int c = 0; c < model.getColNum(); c++) {
						// gridpane.setConstraints(imageList[r][c], r, c); // column=2 row=0
						gridpanes.add(imageList[r][c], c, r);
					}
				}
				doMore = true;
				updateView(imageList);
			} else if (event.getSource() == medium) {
				isM = true;
				isH = false;
				model.reset();
				model.levelM(16, 16, 40);
				mineNum = 40;
				resetArrList();
				GridPane gridpanes = new GridPane();
				border.setCenter(gridpanes);
				gridpanes.setAlignment(Pos.CENTER);
				for (int r = 0; r < model.getRowNum(); r++) {
					for (int c = 0; c < model.getColNum(); c++) {
						// gridpane.setConstraints(imageList[r][c], r, c); // column=2 row=0
						gridpanes.add(imageList[r][c], c, r);
					}
				}
				doMore = true;
				updateView(imageList);
			} else if (event.getSource() == hard) {
				isH = true;
				isM = false;
				model.reset();
				model.levelM(16, 31, 99);
				resetArrList();
				mineNum = 99;
				GridPane gridpanes = new GridPane();
				border.setCenter(gridpanes);
				gridpanes.setAlignment(Pos.CENTER);
				for (int r = 0; r < model.getRowNum(); r++) {
					for (int c = 0; c < model.getColNum(); c++) {
						// gridpane.setConstraints(imageList[r][c], r, c); // column=2 row=0
						gridpanes.add(imageList[r][c], c, r);
					}
				}
				doMore = true;
				updateView(imageList);
			}else if (event.getSource() == exit) {
				System.exit(0);
			} else if (event.getSource() == setMines) {
				TextInputDialog input = new TextInputDialog();
				input.setHeaderText("How many mines would you like?");
				input.showAndWait();
				String answer = input.getEditor().getText();
				int mines = Integer.parseInt(answer);
				if (mines < 0 || mines > model.getColNum() * model.getRowNum()) {
					Alert a = new Alert(AlertType.ERROR, "You can't do that!", ButtonType.OK);
					a.showAndWait();
				} else {
					model.reset();
					model.clearMines();
					mineNum = mines;
					model.setRandomMines(mines);
				}
				updateView(imageList);
			} else if (event.getSource() == about) {
				Alert alert = new Alert(AlertType.INFORMATION, "MineSweeper 4/1/19 by Kelly Ryoo.", ButtonType.OK);
				alert.showAndWait();
			}
		}

	}

	class MyMouseEventHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {

			if (doMore) {
				MyAnimationTimer timer = new MyAnimationTimer();
				timer.start();
				timer.handle(System.nanoTime());
				if (model.isGameWon()) {
					Alert a = new Alert(AlertType.INFORMATION, "You win!", ButtonType.OK);
					a.showAndWait();
					doMore = false;

				} else if (event.getSource() instanceof ImageView) {

					ImageView imagev = (ImageView) event.getSource();
					int row = gridpane.getRowIndex(imagev);
					int col = gridpane.getColumnIndex(imagev);

					if (event.isSecondaryButtonDown()) {
						if (!model.isFlagged(row, col)) {
							model.setFlag(row, col);
						} else if (model.isFlagged(row, col)) {
							model.removeFlag(row, col);
						}
						updateView(imageList);
					} else if (event.isPrimaryButtonDown()) {
						if (!model.isFlagged(row, col)) {
							if (model.isMine(row, col)) {
								doMore = false;
								model.revealOne(row, col);
								updateView(imageList);
								Alert a = new Alert(AlertType.INFORMATION, "You lose!", ButtonType.OK);
								a.showAndWait();
							} else {
								model.reveal(row, col);
							}
						}
						updateView(imageList);

					}
					minesLeft(mineNum - model.numFlags());
				}
			}
		}

	}
}