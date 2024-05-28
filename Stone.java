package reversi2;

public class Stone {
	private int status;
	
	public Stone() {}
	
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
	
	public void reverse() {
		switch(status) {
			case Cons.WHITE:
				status = Cons.BLACK;
				break;
			case Cons.BLACK:
				status = Cons.WHITE;
				break;
			default:
				System.out.println("Stone.reverse():Error");
				break;
		}
	}
}
