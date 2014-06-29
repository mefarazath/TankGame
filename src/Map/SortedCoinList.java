/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Map;


import Entity.Coin;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Farazath Ahamed
 */
public class SortedCoinList {

                public ArrayList<Coin> list = new ArrayList<>();

                public Coin getFirst() {
                        return list.get(0);
                }

                public void clear() {
                        list.clear();
                }

                public void add(Coin path) {
                        list.add(path);
                        Collections.sort(list);
                }
                
                public Coin getCoinAt(int index){
                        return list.get(index);
                }
                
                
                public boolean remove(Coin p) {
                    boolean result = list.remove(p);
                   // Collections.sort(list);
                    return result;
                        
                }

                public int size() {
                        return list.size();
                }

                public boolean contains(Coin p) {
                        return list.contains(p);
                }
        }

