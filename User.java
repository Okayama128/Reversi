package reversi2;

public class User {
    // UserName
    private String name;
    // UserReversiScore
    private int score = 0;
    // User has pass user's turn:true
    // User has put user's stone:false
    private boolean hasPass = false;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setName(String name, int playerColor){
        if(playerColor == Cons.BLACK){
            this.name = name + "(黒)";
        }
        else if(playerColor == Cons.WHITE){
            this.name = name + "(白)";
        }
        else{
            this.name = name + "(？)";
        }
    }
    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return score;
    }
    public void setHasPass(boolean hasPass){
        this.hasPass = hasPass;
    }
    public boolean getHasPass(){
        return hasPass;
    }
}
