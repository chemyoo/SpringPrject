package com.chemyoo.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class TreeSerach {

	static Stack<Map<String, Object>> nodeStack = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		toTree();
		  while (!nodeStack.isEmpty()) {
			  Map<String, Object> popnode = nodeStack.pop();
			  System.err.print(popnode.get("key")+(nodeStack.isEmpty()?"":"-"));
			  List<Map<String, Object>> children = getChildren(popnode);
		        if (children != null && !children.isEmpty()) {
		            for (Map child : children) {
		            	nodeStack.remove(child);
		            	System.err.print(child.get("key")+(nodeStack.isEmpty()?"":"-"));
		            }
		        }
		  }

	}
	
	private static List<Map<String, Object>> getChildren(Map<String, Object> popnode) {
		List<Map<String, Object>> nodelist = new ArrayList<Map<String, Object>>();
		Object [] keys = (Object[]) popnode.get("childrens");
		if(keys!=null && keys.length!=0)
		{
			for(Object key :keys)
			{
				int index=0;
				while (index<nodeStack.size()) {
					Map<String, Object> node = nodeStack.get(index);
					index++;
					if(key.equals(node.get("key")))
					{
						nodelist.add(node);
					}
				}
			}
		}
		return nodelist;
	}

	public static void toTree()
	{
		nodeStack = new Stack<Map<String, Object>>();
		String [] nodes ={"A","B","C","D","E","I","F","G","H"}; 
		Map<String,Object[]>childrens = new LinkedHashMap<String, Object[]>();
		childrens.put("A", new Object[]{"B","C"});
		childrens.put("B", new Object[]{"D","E"});
		childrens.put("C", new Object[]{"F","G","H"});
		childrens.put("E", new Object[]{"I"});
		 Map<String, Object> node = null;
		 String value = null;
		 for(int i=0,size=nodes.length;i<size/2;i++)
		 {
			 value = nodes[i];
			 nodes[i] = nodes[size-1-i];
			 nodes[size-1-i] = value;
		 }
		 for(String nodename : nodes)
		 {
			 node = new HashMap<String, Object>();
			 node.put("key", nodename);
			 node.put("childrens", childrens.get(nodename));
			 nodeStack.add(node);
		 }
	}

}
