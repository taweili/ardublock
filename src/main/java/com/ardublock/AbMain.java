package com.ardublock;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import edu.mit.blocks.controller.WorkspaceController;

public class AbMain {
   
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WorkspaceController wc = new WorkspaceController();
		wc.resetLanguage();
		wc.setLangDefDtd(AbMain.class.getResourceAsStream("/com/ardublock/block/lang_def.dtd"));
		wc.setLangDefStream(AbMain.class.getResourceAsStream("/com/ardublock/block/ardublock_def.xml"));
		wc.loadFreshWorkspace();
        JFrame frame = new JFrame("WorkspaceDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 500);
        frame.add(wc.getWorkspacePanel(), BorderLayout.CENTER);
        frame.setVisible(true);
        
        wc.loadFreshWorkspace();
        wc.loadProjectFromPath("src/test/resources/ab001.xml");
        System.out.println(wc.getSaveString());
	}

}
