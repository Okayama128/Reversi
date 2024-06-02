package reversi2;

public class StartUp3 {
	public static void main(String[] args) {
		// 時間があればそれぞれの名前を聞けばいいさ
		String playerBlack = "Player_1(黒)";
		String playerWhite = "Player_2(白)";		
		int limit = 0;
		int selectNumber = 0;
		
		Table board = new Table();
		board.setLength(); // 普通の大きさが嫌な人のためにある．
//		board.setLengthArrange();
		board.test_setup();
		board.test_showTable();
		System.out.println("ゲームを開始します．");

		while(limit < 50) {
		
		// 先攻は黒
		if (board.test_setPossible(Cons.BLACK)) {
//			board.test_setPossible(Cons.BLACK);
			board.test_showTable();
			System.out.println(playerBlack + "の手番");
			System.out.print("石の置く場所を指定してください：");
			selectNumber = StandardInput.readInt();
			board.putStone(selectNumber, Cons.BLACK);
			board.clearPossible();
			board.test_showTable();
		}
		else {
			board.test_showTable();
			System.out.println(playerBlack + "の手番");
			System.out.println("石を置く場所がないのでパス");
		}
		// 後攻は白
		if (board.test_setPossible(Cons.WHITE)) {
			// board.test_setPossible(Cons.WHITE);
			board.test_showTable();
			System.out.println(playerWhite + "の手番");
			System.out.print("石の置く場所を指定してください：");
			selectNumber = StandardInput.readInt();
			board.putStone(selectNumber, Cons.WHITE);
			board.clearPossible();
		}
		else {
			board.test_showTable();
			System.out.println(playerWhite + "の手番");
			System.out.print("石を置く場所がないのでパス");
		}
				
		limit++;
		}
	}
}
