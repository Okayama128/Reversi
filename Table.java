package reversi2;
import java.util.ArrayList;

public class Table{
	private Stone[][] tables;
	private int length;
	private ArrayList<PositionDirection> possibleList;

	public Table() {
		possibleList = new ArrayList<>();
	}

	// public Table(int length) {
	// 	this.length = length;
	// 	tables = new Stone[length][length];
	// 	for (int i=0; i < length; i++) {
	// 		for (int j=0; j < length; j++) {
	// 			tables[i][j] = new Stone();
	// 		}
	// 	}
	// }

	public void setLength() {
		length = 8;
		tables = new Stone[length+2][length+2];
	}

	public void setLengthArrange() {
		int localLength = 1;
		System.out.println("ボードの長さを入力してください（4の倍数かつ最大12）：");
		localLength = StandardInput.readInt();
		if((localLength % 4) != 0) {
			this.setLength();
		}
		else {
			length = localLength;
			System.out.println("length:" + length);
			tables = new Stone[length + 2][length + 2];
		}
	}

	public void test_setup() {
		for (int i=0; i < length + 2; i++) {
			for(int j=0; j < length + 2; j++) {
				tables[i][j] = new Stone();
				if((i == 0)||(i == length + 1)||(j == 0)||(j == length + 1)) {
					tables[i][j].setStatus(Cons.FULL);
				}
				else {
					tables[i][j].setStatus(Cons.ZERO);
				}
			}
		}
		int mid = (length+2) / 2;
		//		System.out.println("length:" + mid);
		tables[mid][mid].setStatus(Cons.WHITE);
		tables[mid-1][mid-1].setStatus(Cons.WHITE);
		tables[mid-1][mid].setStatus(Cons.BLACK);
		tables[mid][mid-1].setStatus(Cons.BLACK);
	}
	public void putStone(int selectNumber, int playerColor) {
		// プレイヤーには1番から表示するため，配列のインデックス用に変換
		int index = selectNumber - 1, arrayListIndex = 0;
		int row, clmn, cnt = 0;
		for (int i=1; i<length+1; i++) {
			for (int j = 1; j<length+1; j++) {
				if(tables[i][j].getStatus() == Cons.POSSIBLE) {
					if(index == cnt) {
						System.out.println("cnt: " + cnt);
						int position = i*100 + j;
						for (PositionDirection possible:possibleList) {
							if (possible.getPosition() == position) {
								break;
							}
							arrayListIndex++;
						}
						PositionDirection possible = possibleList.get(arrayListIndex);
						row = possible.getPosition() / 100;
						clmn = possible.getPosition() % 100;
						// リバースストーン発動（今から実装）
						int directionCnt = Cons.UP;
//						// ForDbug
//						System.out.println("row: " + row + ", clmn: " + clmn);
//						for(int direction:possible.getDirection()) {
//							System.out.print(direction + ", ");
//						}
//						System.out.println();
						for(int direction:possible.getDirection()) {
							if(direction == 1) {
								switch(directionCnt) {
									case Cons.UP:
										for (int l=1; tables[i-l][j].getStatus()!=playerColor;l++) {
											tables[i-l][j].reverse();
										}
										break;
									case Cons.DW:
										for (int l=1; tables[i+l][j].getStatus()!=playerColor;l++) {
											tables[i+l][j].reverse();
										}
										break;
									case Cons.RT:
										for (int l=1; tables[i][j+l].getStatus()!=playerColor;l++) {
											tables[i][j+l].reverse();
										}
										break;
									case Cons.LT:
										for (int l=1; tables[i][j-l].getStatus()!=playerColor;l++) {
											tables[i][j-l].reverse();
										}
										break;
									case Cons.RU:
										for (int l=1; tables[i-l][j+l].getStatus()!=playerColor;l++) {
											tables[i-l][j+l].reverse();
										}
										break;
									case Cons.RD:
										for (int l=1; tables[i+l][j+l].getStatus()!=playerColor;l++) {
											tables[i+l][j+l].reverse();
										}
										break;
									case Cons.LU:
										for (int l=1; tables[i-l][j-l].getStatus()!=playerColor;l++) {
											tables[i-l][j-l].reverse();
										}
										break;
									case Cons.LD:
										for (int l=1; tables[i+l][j-l].getStatus()!=playerColor;l++) {
											tables[i+l][j-l].reverse();
										}
										break;
									default:
										System.out.println("PutStone&Reverse Error");
										break;
								}
							}
							directionCnt++;
						}
						tables[row][clmn].setStatus(playerColor);
						return;
					}
					cnt++;
				}
			}
		}
	}

	public void test_showTable() {
		int counter = 0;
		System.out.println();
		for (int i=0; i < length + 2; i++) {
			for (int j=0; j < length + 2; j++) {
				switch(tables[i][j].getStatus()) {
				case Cons.WHITE:
					System.out.print("○");
					break;
				case Cons.BLACK:
					System.out.print("●");
					break;
				case Cons.FULL:
					System.out.print("×");
					break;
				case Cons.POSSIBLE:
					System.out.print(++counter);
					break;
				case Cons.ZERO:
					System.out.print(" ");
					break;
				default:
					System.out.print("?");
					break;
				}
				System.out.print("|");
			}
			System.out.println();
			for(int j=0; j < 2 * (length + 2); j++) {
				System.out.print("-");
			}
			System.out.println();
		}
	}
	public void clearPossible() {
		for(int i=1; i < length + 1; i++) {
			for (int j=1; j < length + 1; j++) {
				if(tables[i][j].getStatus() == Cons.POSSIBLE) {
					tables[i][j].setStatus(Cons.ZERO);
				}
			}
		}
		possibleList.clear();
	}
	public boolean test_setPossible(int color) {
		int playerColor = color, counterColor = 0;
		switch(playerColor) {
		case Cons.BLACK:
			counterColor = Cons.WHITE;
			break;
		case Cons.WHITE:
			counterColor = Cons.BLACK;
			break;
		default:
			System.out.println("Error");
			break;
		}
		System.out.println("playerColor: " + playerColor);
		System.out.println("counterColor: " + counterColor);
		// ForDebug
		//		System.out.println("playerColor:" + playerColor);
		//		System.out.println("counterColor:" + counterColor);
		for(int i=1; i < length + 1; i++) {
			for(int j=1; j < length + 1; j++) {
				// 自分の石を探索（＊＊ここでまとめてもいいかもね．自分の石がどこにあるのかを．）
				if(tables[i][j].getStatus() == playerColor) {
					System.out.println("playerColor: " + i +", "+ j);
					// 各方向を探すメソッドを後で分離
					// RightSearch
					if(tables[i][j+1].getStatus() == counterColor) {
						for(int k=j+1; k < length + 1;k++) {
							// ForDebug
							System.out.println("i:" + i + ", k:" + k);
							// 二次元配列の位置情報をintに格納
							int position = i*100 + (k+1);
							// 右隣の状況で場合分け

							if((tables[i][k+1].getStatus() == Cons.FULL) || (tables[i][k+1].getStatus() == playerColor)) {
								// 置けないor自色
								break;
							}
							else if(tables[i][k+1].getStatus() == counterColor) {
								// 相手の色があってさらに奥を探索
								continue;
							}
							else if ((tables[i][k+1].getStatus() == Cons.ZERO)||(tables[i][k+1].getStatus() == Cons.POSSIBLE)) {
								// 置ける
								if(possibleList == null) {
									// まだリストがない
									possibleList.add(new PositionDirection(position, Cons.LT));
								}
								else {
									int cnt = 0;
									for (PositionDirection possible:possibleList) {
										if(possible.getPosition() == position) {
											break;
										}
										cnt++;
									}
									if (cnt == possibleList.size()) {
										// まだリストにないとき
										possibleList.add(new PositionDirection(position, Cons.LT));
									}
									else {
										// すでに同じポジションにデータがあったとき(左方向にひっくり返すので，LT)
										possibleList.get(cnt).setDirection(Cons.LT);
									}
								}
								break;
							}
							else {
								System.out.println("Error:RightSearch");
							}
						}
					}
					// 左方向LeftSearch
					if(tables[i][j-1].getStatus() == counterColor) {
						for(int k=j-1; k > 1;k--) {
							// ForDebug
							System.out.println("i:" + i + ", k:" + k);
							// 二次元配列の位置情報をintに格納
							int position = i*100 + (k-1);
							// 隣の状況で場合分け

							if((tables[i][k-1].getStatus() == Cons.FULL) || (tables[i][k-1].getStatus() == playerColor)) {
								// 置けないor自色
								break;
							}
							else if(tables[i][k-1].getStatus() == counterColor) {
								// 相手の色があってさらに奥を探索
								continue;
							}
							else if ((tables[i][k-1].getStatus() == Cons.ZERO)||(tables[i][k+1].getStatus() == Cons.POSSIBLE)) {
								// 置ける
								if(possibleList == null) {
									// まだリストがない
									possibleList.add(new PositionDirection(position, Cons.RT));
								}
								else {
									int cnt = 0;
									for (PositionDirection possible:possibleList) {
										if(possible.getPosition() == position) {
											break;
										}
										cnt++;
									}
									if (cnt == possibleList.size()) {
										// まだリストにないとき
										possibleList.add(new PositionDirection(position, Cons.RT));
									}
									else {
										// すでに同じポジションにデータがあったとき(左方向にひっくり返すので，LT)
										possibleList.get(cnt).setDirection(Cons.RT);
									}
								}
								break;
							}
							else {
								System.out.println("Error:LeftSearch");
							}
						}
					}
					// 下方向DownSearch
					if(tables[i+1][j].getStatus() == counterColor) {
						for(int k=i+1; k < length + 1;k++) {
							// ForDebug
							System.out.println("k:" + k + ", j:" + j);
							// 二次元配列の位置情報をintに格納
							int position = (k+1)*100 + j;
							// 隣の状況で場合分け
							if((tables[k+1][j].getStatus() == Cons.FULL) || (tables[k+1][j].getStatus() == playerColor)) {
								// 置けないor自色
								break;
							}
							else if(tables[k+1][j].getStatus() == counterColor) {
								// 相手の色があってさらに奥を探索
								continue;
							}
							else if ((tables[k+1][j].getStatus() == Cons.ZERO)||(tables[i][k+1].getStatus() == Cons.POSSIBLE)) {
								// 置ける
								if(possibleList == null) {
									// まだリストがない
									possibleList.add(new PositionDirection(position, Cons.UP));
								}
								else {
									int cnt = 0;
									for (PositionDirection possible:possibleList) {
										if(possible.getPosition() == position) {
											break;
										}
										cnt++;
									}
									if (cnt == possibleList.size()) {
										// まだリストにないとき
										possibleList.add(new PositionDirection(position, Cons.UP));
									}
									else {
										// すでに同じポジションにデータがあったとき(左方向にひっくり返すので，LT)
										possibleList.get(cnt).setDirection(Cons.UP);
									}
								}
								break;
							}
							else {
								System.out.println("Error:DownSearch");
							}
						}
					}
					// 上方向UpSearch
					if(tables[i-1][j].getStatus() == counterColor) {
						for(int k=i-1; k > 1;k--) {
							// ForDebug
							System.out.println("k:" + k + ", j:" + j);
							// 二次元配列の位置情報をintに格納
							int position = (k-1)*100 + j;
							// 隣の状況で場合分け

							if((tables[k-1][j].getStatus() == Cons.FULL) || (tables[k-1][j].getStatus() == playerColor)) {
								// 置けないor自色
								break;
							}
							else if(tables[k-1][j].getStatus() == counterColor) {
								// 相手の色があってさらに奥を探索
								continue;
							}
							else if ((tables[k-1][j].getStatus() == Cons.ZERO)||(tables[i][k+1].getStatus() == Cons.POSSIBLE)) {
								// 置ける
								if(possibleList == null) {
									// まだリストがない
									possibleList.add(new PositionDirection(position, Cons.DW));
								}
								else {
									int cnt = 0;
									for (PositionDirection possible:possibleList) {
										if(possible.getPosition() == position) {
											break;
										}
										cnt++;
									}
									if (cnt == possibleList.size()) {
										// まだリストにないとき
										possibleList.add(new PositionDirection(position, Cons.DW));
									}
									else {
										// すでに同じポジションにデータがあったとき(左方向にひっくり返すので，LT)
										possibleList.get(cnt).setDirection(Cons.DW);
									}
								}
								break;
							}
							else {
								System.out.println("Error:LeftSearch");
							}
						}
					}
					// 右下方向RightDownSearch
					if(tables[i+1][j+1].getStatus() == counterColor) {
						// [1]～[length]までが，石の置ける範囲
						for(int k=1; (((length+1 - (i+k))>0)||(length+1 - (j+k)) > 0);k++) {
							// ForDebug
							System.out.println("i+k:" + (i+k) + ", j+k:" + (j+k));
							// 二次元配列の位置情報をintに格納
							int position = (i+k)*100 + j+k;
							// 隣の状況で場合分け
							if((tables[i+k][j+k].getStatus() == Cons.FULL) || (tables[i+k][j+k].getStatus() == playerColor)) {
								// 置けないor自色
								break;
							}
							else if(tables[i+k][j+k].getStatus() == counterColor) {
								// 相手の色があってさらに奥を探索
								continue;
							}
							else if ((tables[i+k][j+k].getStatus() == Cons.ZERO)||(tables[i][k+1].getStatus() == Cons.POSSIBLE)) {
								// 置ける
								if(possibleList == null) {
									// まだリストがない
									possibleList.add(new PositionDirection(position, Cons.LU));
								}
								else {
									int cnt = 0;
									for (PositionDirection possible:possibleList) {
										if(possible.getPosition() == position) {
											break;
										}
										cnt++;
									}
									if (cnt == possibleList.size()) {
										// まだリストにないとき
										possibleList.add(new PositionDirection(position, Cons.LU));
									}
									else {
										// すでに同じポジションにデータがあったとき(左上方向にひっくり返すので，LU)
										possibleList.get(cnt).setDirection(Cons.LU);
									}
								}
								break;
							}
							else {
								System.out.println("Error:RightDownSearch");
							}
						}
					}
					// 右上方向RightUpSearch
					if(tables[i-1][j+1].getStatus() == counterColor) {
						// [1]～[length]までが，石の置ける範囲
						for(int k=1; ((i-k)>0)||((length+1 - (j+k)) > 0);k++) {
							// ForDebug
							System.out.println("i-k:" + (i-k) + ", j+k:" + (j+k));
							// 二次元配列の位置情報をintに格納
							int position = (i-k)*100 + j+k;
							// 隣の状況で場合分け
							if((tables[i-k][j+k].getStatus() == Cons.FULL) || (tables[i-k][j+k].getStatus() == playerColor)) {
								// 置けないor自色
								break;
							}
							else if(tables[i-k][j+k].getStatus() == counterColor) {
								// 相手の色があってさらに奥を探索
								continue;
							}
							else if ((tables[i-k][j+k].getStatus() == Cons.ZERO)||(tables[i][k+1].getStatus() == Cons.POSSIBLE)) {
								// 置ける
								if(possibleList == null) {
									// まだリストがない
									possibleList.add(new PositionDirection(position, Cons.LD));
								}
								else {
									int cnt = 0;
									for (PositionDirection possible:possibleList) {
										if(possible.getPosition() == position) {
											break;
										}
										cnt++;
									}
									if (cnt == possibleList.size()) {
										// まだリストにないとき
										possibleList.add(new PositionDirection(position, Cons.LD));
									}
									else {
										// すでに同じポジションにデータがあったとき(左下方向にひっくり返すので，LD)
										possibleList.get(cnt).setDirection(Cons.LD);
									}
								}
								break;
							}
							else {
								System.out.println("Error:RightUpSearch");
							}
						}
					}
					// 左上方向LeftUpSearch
					if(tables[i-1][j-1].getStatus() == counterColor) {
						// [1]～[length]までが，石の置ける範囲
						// Start:[i][j] -(-k)-> Dest: i-k -> 1 or j-k -> 1
						for(int k=1; ((i-k)>0)||((j-k) > 0);k++) {
							// ForDebug
							System.out.println("i-k:" + (i-k) + ", j-k:" + (j-k));
							// 二次元配列の位置情報をintに格納
							int position = (i-k)*100 + j-k;
							// 隣の状況で場合分け
							if((tables[i-k][j-k].getStatus() == Cons.FULL) || (tables[i-k][j-k].getStatus() == playerColor)) {
								// 置けないor自色
								break;
							}
							else if(tables[i-k][j-k].getStatus() == counterColor) {
								// 相手の色があってさらに奥を探索
								continue;
							}
							else if ((tables[i-k][j-k].getStatus() == Cons.ZERO)||(tables[i][k+1].getStatus() == Cons.POSSIBLE)) {
								// 置ける
								if(possibleList == null) {
									// まだリストがない
									possibleList.add(new PositionDirection(position, Cons.RD));
								}
								else {
									int cnt = 0;
									for (PositionDirection possible:possibleList) {
										if(possible.getPosition() == position) {
											break;
										}
										cnt++;
									}
									if (cnt == possibleList.size()) {
										// まだリストにないとき
										possibleList.add(new PositionDirection(position, Cons.RD));
									}
									else {
										// すでに同じポジションにデータがあったとき(右下方向にひっくり返すので，RD)
										possibleList.get(cnt).setDirection(Cons.RD);
									}
								}
								break;
							}
							else {
								System.out.println("Error:LeftUpSearch");
							}
						}
					}
					// 左下方向LeftDownSearch
					if(tables[i+1][j-1].getStatus() == counterColor) {
						// [1]～[length]までが，石の置ける範囲
						// Start:[i][j] -(-k)-> Dest: i-k -> 1 or j-k -> 1
						for(int k=1; (length+1 - (i+k)>0)||((j-k) > 0);k++) {
							// ForDebug
							System.out.println("i+k:" + (i+k) + ", j-k:" + (j-k));
							// 二次元配列の位置情報をintに格納
							int position = (i+k)*100 + j-k;
							// 隣の状況で場合分け
							if((tables[i+k][j-k].getStatus() == Cons.FULL) || (tables[i+k][j-k].getStatus() == playerColor)) {
								// 置けないor自色
								break;
							}
							else if(tables[i+k][j-k].getStatus() == counterColor) {
								// 相手の色があってさらに奥を探索
								continue;
							}
							else if ((tables[i+k][j-k].getStatus() == Cons.ZERO)||(tables[i][k+1].getStatus() == Cons.POSSIBLE)) {
								// 置ける
								if(possibleList == null) {
									// まだリストがない
									possibleList.add(new PositionDirection(position, Cons.RU));
								}
								else {
									int cnt = 0;
									for (PositionDirection possible:possibleList) {
										if(possible.getPosition() == position) {
											break;
										}
										cnt++;
									}
									if (cnt == possibleList.size()) {
										// まだリストにないとき
										possibleList.add(new PositionDirection(position, Cons.RU));
									}
									else {
										// すでに同じポジションにデータがあったとき(右上方向にひっくり返すので，RU)
										possibleList.get(cnt).setDirection(Cons.RU);
									}
								}
								break;
							}
							else {
								System.out.println("Error:RightUpSearch");
							}
						}
					}
				}
			}
		}

		if (possibleList.size() != 0) {
			for (PositionDirection possible:possibleList) {
				int row = possible.getPosition() / 100;
				int clmn = possible.getPosition() % 100;
				tables[row][clmn].setStatus(Cons.POSSIBLE);
				// ForDebug
				System.out.println("Set Possible!");
			}
			return true;
		}
		else {
			System.out.println("Naiyoooo");
			return false;
		}
	}
	public boolean setPossible() {
		for(int i=0; i < length;i++) {
			for(int j = 0; j < length; j++) {
				// 相手の意思を探索？
			}
		}
		return true;
	}
	////////////////////////////////////////////////////////////////////
	public void showTable() {
		int counter = 0;
		System.out.println();
		for (int i=0; i < length + 2; i++) {
			for (int j=0; j < length + 2; j++) {
				switch(tables[i][j].getStatus()) {
				case Cons.WHITE:
					System.out.print("○");
					break;
				case Cons.BLACK:
					System.out.print("●");
					break;
				case Cons.FULL:
					System.out.print("×");
					break;
				case Cons.POSSIBLE:
					System.out.print(++counter);
					break;
				case Cons.ZERO:
					System.out.print(" ");
					break;
				default:
					System.out.print("?");
					break;
				}
				System.out.print("|");
			}
			System.out.println();
			for(int j=0; j < 2 * (length + 2); j++) {
				System.out.print("-");
			}
			System.out.println();
		}
	}
	public void setup() {
		int mid = length / 2;
		//		System.out.println("length:" + mid);
		tables[mid][mid].setStatus(Cons.WHITE);
		tables[mid-1][mid-1].setStatus(Cons.WHITE);
		tables[mid-1][mid].setStatus(Cons.BLACK);
		tables[mid][mid-1].setStatus(Cons.BLACK);
	}
}
