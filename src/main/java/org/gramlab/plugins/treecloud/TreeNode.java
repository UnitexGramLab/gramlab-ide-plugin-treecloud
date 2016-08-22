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

import java.util.ArrayList;

/**
 * Data structure for a node of a tree
 * @author Aleksandra Chashchina
 *
 */

public class TreeNode {
	
	public String name;
	public int id;
	public double length;
	public ArrayList<Integer> children = new ArrayList<Integer>();
	public ArrayList<TreeNode> childnodes = new ArrayList<TreeNode>();
	TreeNode parent;
	public boolean hasSisterLeaf = false;
	public double angle;
	public int descendants;
	public boolean isLeaf = false;
	public double startX;
	public double startY;
	public double endX;
	public double endY;
	public String pathattr;
	public boolean isRoot = false;
	public String fontcolor;
	public String fontsize;
	
	public void setName(String n){
		this.name = n;
	}
	
	public void setParent(TreeNode p){
		this.parent = p;
	}
	
	public void addChild(int ch){
		this.children.add(ch);
	}
	
	public void setLength(double l){
		this.length = l;
	}
	
	public void setID(int i){
		this.id = i;
	}
	
	public void setAngle(double a){
		this.angle = a;
	}
	
	public void setAsLeaf(){
		this.isLeaf = true;
	}
	
	public void setColor(String c){
		this.fontcolor = c;
	}
	
	public void setSize(String s){
		this.fontsize = s;
	}

}
