/*
 * Unitex
 *
 * Copyright (C) 2001-2016 Université Paris-Est Marne-la-Vallée <unitex@univ-mlv.fr>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 *
 */
package org.gramlab.plugins.treecloud;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.xml.transform.TransformerException;

import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.batik.transcoder.TranscoderException;
import org.gramlab.core.gramlab.icons.Icons;
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
	
	public JMenu AddMenu() throws IOException, InterruptedException, TransformerException, SVGConverterException, TranscoderException{
		
		JMenu m = new JMenu("TreeCloud");
		
		JMenuItem tr = new JMenuItem(new AbstractAction("Build TreeCloud"){
		/*
		 * Get parameters from Gramlab
		 */
			public void actionPerformed(ActionEvent e){
				String c = GlobalProjectManager.getAs(GramlabProjectManager.class).getCurrentProject().getCorpusDirectory().getAbsolutePath();
				String lang = GlobalProjectManager.getAs(GramlabProjectManager.class).getCurrentProject().getLanguage();
						
				Tree t = new Tree();
				t.setCorpusPath(c);
				t.findFiles(t.corpuspath);
				if(t.concordhtml==null){
					String path = JOptionPane.showInputDialog("Cannot find concord.html file. Please enter absolute path to concord.html:");
					t.concordhtml = path;
					};
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
		m.add(tr);
		return m;

}
}
