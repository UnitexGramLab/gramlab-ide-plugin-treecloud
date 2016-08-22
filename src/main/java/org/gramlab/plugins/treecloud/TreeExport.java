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

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterException;

/**
 * Class contains methods for exporting the TreeCloud into several formats: PDF, SVG, Newick
 * @author SONY
 *
 */

public class TreeExport {
	
	
	public static void  exportAsPdf(Document svgDoc, String outputpath) throws TransformerException, IOException, SVGConverterException{

		File svgFile = File.createTempFile("treecloud-" , ".svg");
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		DOMSource source2 = new DOMSource(svgDoc);
		FileOutputStream fOut = new FileOutputStream(svgFile);
		try{ 
			transformer.transform(source2, new StreamResult(fOut)); 
			}finally{
				fOut.close(); 
				}

		File outputFile = new File(outputpath);
		SVGConverter converter = new SVGConverter();
		converter.setDestinationType(DestinationType.PDF);
		converter.setSources(new String[] { svgFile.toString() });
		converter.setDst(outputFile);
		converter.execute();
	}
	
	public static void exportAsSvg(Document svgDoc, String outputpath) throws IOException, TransformerFactoryConfigurationError, TransformerException{
		File svgFile = new File(outputpath);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		DOMSource source2 = new DOMSource(svgDoc);
		FileOutputStream fOut = new FileOutputStream(svgFile);
		try{
			transformer.transform(source2, new StreamResult(fOut)); 
			}finally{
				fOut.close(); 
				}
	}
	
	public static void exportAsJpeg(Document svgDoc, String outputpath) throws IOException, TransformerFactoryConfigurationError, TransformerException, SVGConverterException{
		File svgFile = File.createTempFile("treecloud-" , ".svg");
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		DOMSource source2 = new DOMSource(svgDoc);
		FileOutputStream fOut = new FileOutputStream(svgFile);
		try{ 
			transformer.transform(source2, new StreamResult(fOut)); 
			}finally{
				fOut.close(); 
				}

		File outputFile = new File(outputpath);
		SVGConverter converter = new SVGConverter();
		converter.setDestinationType(DestinationType.JPEG);
		converter.setQuality(new Float(0.99));
		converter.setSources(new String[] { svgFile.toString() });
		converter.setDst(outputFile);
		converter.execute();
	}
	
	/**
	 * Save tree in Newick format
	 * @param newick String
	 * @param path Absolute path to the newick file
	 * @throws IOException
	 */
	
	public static void exportAsNewick(String newick, String path) throws IOException{
		FileWriter fw = new FileWriter(path);
		fw.write(newick);
		fw.close();
	}

}
