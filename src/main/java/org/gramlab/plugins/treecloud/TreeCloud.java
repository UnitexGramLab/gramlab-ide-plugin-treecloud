package org.gramlab.plugins.treecloud;

import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.batik.transcoder.TranscoderException;
import org.gramlab.core.GramlabConfigManager;
import org.gramlab.core.gramlab.project.GramlabProject;

import ro.fortsoft.pf4j.Extension;


/**
 * Main class of the TreeCloud plugin
 * (currently works only on trees from TestData)
 * @author Aleksandra Chashchina
 *
 */
@Extension
public class TreeCloud implements GramlabMenu {
	
	public void drawTreeCloud() throws IOException, InterruptedException, TransformerException, SVGConverterException, TranscoderException{
		
		/*
		 * Get parameters from Gramlab project manager
		 */
		GramlabProject p = GramlabConfigManager.getCurrentProject();
		String corpus = p.getCorpusDirectory().getAbsolutePath();
		String lang = p.getLanguage();
		
		Tree t = new Tree();
		t.setCorpusPath(corpus);
		t.findFiles(t.corpuspath);
		t.setStatsOutput(t.concordhtml);
		t.removestopwords = true;
		t.setLanguage(lang);
		//t.setAlphabetPath(TestData.getTestAlphabetPath());
		//t.setMinNbOccur(1);
		t.setNumberOfTaxa(30);
		t.colormode = "Red & blue";
		
		t.setDistanceMatrix();
		t.performNJ();
		t.performEqualAngle();
		
		t.drawTree();
		//TreeExport.exportAsJpeg(t.getSvgDoc(), "C:/mytestoutput.jpg");
		//TreeExport.exportAsSvg(t.getSvgDoc(), "C:/mytestout.svg");
		//TreeExport.exportAsNewick(t.getNewickTree(), "C:/newicktestoutput.newick");
	}

}
