package at.vchrist.jsni;

public class MouseSingleClickEvent implements MouseClickEvent {
    private int x;
    private int y;
    
    public MouseSingleClickEvent(int clickX, int clickY) {
        this.x = clickX;
        this.y = clickY;
    }
    
    public int x() {
        return x;
    }
    
    public int y() {
        return y;
    }
}
