package jsni;

public class MouseDoubleClickEvent extends MouseSingleClickEvent {
    private int oldX;
    private int oldY;
    private int clicks;
    
    public MouseDoubleClickEvent(int clicks, int clickX, int clickY, int oldClickX, int oldClickY) {
    	super(clickX, clickY);
    	
        this.oldX = oldClickX;
        this.oldY = oldClickY;
        
        this.clicks = clicks;
    }

    public int oldX() {
        return oldX;
    }
    
    public int oldY() {
        return oldY;
    }
    
    public int clicks() {
    	return clicks;
    }
}
