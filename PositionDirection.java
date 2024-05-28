package reversi;

public class PositionDirection {
	private int position;
	// 上，右上，右，右下，下，左下，左，左上を順に0～7のインデックス
	private int[] direction = {0,0,0,0,0,0,0,0};
	
	public PositionDirection() {}
	
	public PositionDirection(int position) {
		this.position = position;
	}
	
	public PositionDirection(int position, int index) {
		this.position = position;
		this.direction[index] = 1;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	public int getPosition() {
		return position;
	}
	public void setDirection(int index) {
		this.direction[index] = 1;
	}
	public int[] getDirection() {
		return direction;
	}
}
