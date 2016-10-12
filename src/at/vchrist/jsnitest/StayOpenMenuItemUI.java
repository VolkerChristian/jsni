package jsnitest;

import javax.swing.JComponent;
import javax.swing.MenuSelectionManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuItemUI;

public class StayOpenMenuItemUI extends BasicMenuItemUI {
	   @Override
	   protected void doClick(MenuSelectionManager msm) {
		   System.out.println("Dododododo");
	      menuItem.doClick(0);
	   }

	   public static ComponentUI createUI(JComponent c) {
	      return new StayOpenMenuItemUI();
	   }
}
