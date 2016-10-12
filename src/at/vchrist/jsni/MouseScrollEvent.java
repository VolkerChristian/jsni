package at.vchrist.jsni;

import java.awt.event.ActionEvent;

public class MouseScrollEvent extends ActionEvent {
    private static final long serialVersionUID = 9138737718425720920L;
    
    private int delta = 0;
    private String orientation = "";
    
    public MouseScrollEvent(Object source, int delta, String orientation) {
        super(source, ActionEvent.ACTION_PERFORMED, "scrolled");
        
        this.delta = delta;
        this.orientation = orientation;
    }
    
    public int delta() {
        return delta;
    }
    
    public String orientation() {
        return orientation;
    }
}
