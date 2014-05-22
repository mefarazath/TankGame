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

                private ArrayList<Path> list = new ArrayList<>();

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
                

                public void remove(Path p) {
                        list.remove(p);
                }

                public int size() {
                        return list.size();
                }

                public boolean contains(Path p) {
                        return list.contains(p);
                }
        }

