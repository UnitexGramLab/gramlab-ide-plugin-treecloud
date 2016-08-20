package org.gramlab.plugins.treecloud;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.xml.transform.TransformerException;

import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.batik.transcoder.TranscoderException;
import org.gramlab.core.gramlab.project.GramlabProjectManager;
import org.gramlab.core.umlv.unitex.common.project.manager.GlobalProjectManager;
import org.gramlab.core.umlv.unitex.config.ConfigManager;

import ro.fortsoft.pf4j.Extension;


/**
 * Main class of the TreeCloud plugin
 * @author Aleksandra Chashchina
 *
 */
@Extension
public class TreeCloud implements GramlabMenu {
	
	public void AddMenu() throws IOException, InterruptedException, TransformerException, SVGConverterException, TranscoderException{
		
		JMenu m = new JMenu("TreeCloud");
		
		JMenuItem tr = new JMenuItem(new AbstractAction("Build TreeCloud"){
		/*
		 * Get parameters from Gramlab
		 */
			public void actionPerformed(ActionEvent e){
				String c = GlobalProjectManager.getAs(GramlabProjectManager.class).getCurrentProject().getCurrentSntDir().getAbsolutePath();
				String lang = GlobalProjectManager.getAs(GramlabProjectManager.class).getCurrentProject().getLanguage();
						
				Tree t = new Tree();
				t.setCorpusPath(c);
				t.findFiles(t.corpuspath);
				t.removestopwords = true;
				t.setLanguage(lang);
				t.setNumberOfTaxa(30);
				t.colormode = "Red & blue";
				
				t.setDistanceMatrix();
				t.performNJ();
				t.performEqualAngle();
				
				t.drawTree();
				TreeCloudFrame f = new TreeCloudFrame();
				f.currentTree = t;
				f.paintFrame();
			}
		
	    });

}
}
