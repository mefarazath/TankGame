/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Map;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Farazath Ahamed
 */
public class SortedPathList {

                public ArrayList<Path> list = new ArrayList<>();

                public Path getFirst() {
                        return list.get(0);
                }

                public void clear() {
                        list.clear();
                }

                public void add(Path path) {
                        list.add(path);
                        Collections.sort(list);
                }
                
                public Path getPathAt(int index){
                        return list.get(index);
                }
                
                public Path getPathWithEndPoint(Node n){
                       for(Path p: list){
                            if(p.getLastWayPoint().equals(n)){
                                return p;
                            }
                       }
                       return null;
                }
                
                public boolean remove(Path p) {
                    return list.remove(p);
                        
                }

                public int size() {
                        return list.size();
                }

                public boolean contains(Path p) {
                        return list.contains(p);
                }
        }

