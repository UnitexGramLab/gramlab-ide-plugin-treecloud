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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;


/**
 * Class used to compute tree from Concordance file
 *
 * @author Aleksandra Chashchina
 *
 */

public class Tree {
	
	public String corpuspath;
	int numberoftaxa;
	public String language;
	public ArrayList<String> words = new ArrayList<String>();
	public ArrayList<ArrayList<Double>> distancematrix = new ArrayList<ArrayList<Double>>();
	public String newick;
	public ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
	private ArrayList<TreeNode> leaves = new ArrayList<TreeNode>();
	public String concordind;
	public String concordhtml;
	public String alphabet;
	public String statsoutput;
	public String [] statscommand;
	public String unitextool;
	public boolean removestopwords;
	public String locatetarget = "nation";
	int minNmbOfOccur;
	String colormode;
	String edgecolor;
	Document svgDoc; 
	
	public void setCorpusPath(String p){
		this.corpuspath = p;
	}
	
	public void setSvgDoc(Document svg){
		this.svgDoc = svg;
	}
	
	public void setAlphabetPath(String a){
		this.alphabet = a;
	}
	
	public void setLanguage(String lang){
		this.language = lang;
	}
	
	public void setColorMode(String color){
		this.colormode = color;
	}
	
	public void setMinNbOccur(int n){
		this.minNmbOfOccur = n;
	}
	
	public void setNumberOfTaxa(int n){
		this.numberoftaxa = n;
	}
	
	public void setLocateTarget(String l){
		this.locatetarget = l;
	}
	
	public void setUnitexToolLoggerPath(String u){
		this.unitextool = u;
	}
	
	public ArrayList<TreeNode> getNodeList(){
		return this.nodes;
	}
	
	public String getNewickTree(){
		return this.newick;
	}
	
	public ArrayList<String> getTreeWords(){
		return this.words;
	}
	
	public int getNumberOfTaxa(){
		return this.numberoftaxa;
	}
	
	public Document getSvgDoc(){
		return this.svgDoc;
	}
	
	
	public String getStopWordsFile(String workinglanguage){
		
		if(workinglanguage.equals("en") | workinglanguage.equals("fr")){
			return "resources/StoplistEnglishFrench.txt";
			
		}else if(workinglanguage.equals("de")){
			return "resources/StoplistGerman.txt";
		
		}else if(workinglanguage.equals("pt")){
			return "resources/StoplistPortuguese.txt";
		
		}else if(workinglanguage.equals("it")){
			return "resources/StoplistItalian.txt";
			
		}else if(workinglanguage.equals("es")){
			return "resources/StoplistSpanish.txt";
		}else{
			JOptionPane.showMessageDialog(null, "Stopwords list is not available for this language. Stopwords will not be removed");
			return null;
		}
		
	}
	
	public void findFiles(String dir){   
		File f = new File(dir);
		for (File item : f.listFiles()){
			if (item.isFile()){
				if (item.getName().equals("concord.html")){
					 concordhtml = item.getAbsolutePath();
				}else if(item.getName().equals("concord.ind")){
					concordind = item.getAbsolutePath();
			    }
			}else{
				findFiles(item.getAbsolutePath());
				}
			}
		}
	

	public void setStatsOutput(String concordhtmlpath){
		
		Pattern p = Pattern.compile("(.*?)(concord\\.html)");
		Matcher m = p.matcher(concordhtmlpath);
		if(m.find()){
			String res = m.group(1) + "statistics.txt";
			statsoutput = res;
		}else{
			System.out.println("Error in concord.html filepath");
		}
	}
	
	public void setStatsCommand(){
		
		String [] cmd = new String[10];
		cmd[1] = "\"Stats\"";
		cmd[4] = "\"-l10\"";
		cmd[5] = "\"-r10\"";
		cmd[7] = "\"-c1\"";
		cmd[8] = "\"-m1\"";
		cmd[9] = "\"-qutf8-no-bom\"";
		cmd[0] = "\"" + unitextool + "\"";
		cmd[2] = "\"" + concordind +  "\"";
		cmd[3] = "\"-a" + alphabet + "\"";
		cmd[6] = "\"-o" + statsoutput + "\"";
		
		statscommand = cmd;
	}

	public void setDistanceMatrix() throws IOException, InterruptedException{
		System.out.println("Computing distance matrix...");
        ConcordanceText txt = new ConcordanceText();
        if(removestopwords){
        	leaves = txt.computeMatrixNoStats(concordhtml, getStopWordsFile(language), numberoftaxa, colormode);
        }else{
        	leaves = txt.computeMatrixNoStats(concordhtml, null, numberoftaxa, colormode);
        }
		distancematrix = txt.getMatrix();
		edgecolor = txt.getEdgeColor();
		words = txt.getLabelList();
	}
	
	public void performNJ(){
		System.out.println("Performing NeighborJoining algorithm...");
		NeighborJoining nj = new NeighborJoining(leaves);
		newick = nj.computeNJTree(distancematrix, words);
		nodes = nj.allnodes;
	}
	
	public void performEqualAngle(){
		System.out.println("Performing EqualAngle algorithm...");
		EqualAngle ea = new EqualAngle();
		ea.doEqualAngle(nodes, numberoftaxa);
	}
	
	public void drawTree(){
		System.out.println("Drawing the tree...");
		setSvgDoc(TreeSVG.drawTreeCloud(nodes, edgecolor));
	}
}