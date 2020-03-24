public class Tile {
    private boolean isVisited;
    private boolean hasKnight;
    private boolean isNull;

    public Tile() {
        isVisited = false;
        hasKnight = false;
    }
    
    public void visit() {
        isVisited = true;
        hasKnight = true;
    }
    
    public void leave() {
        hasKnight = false;
    }
}