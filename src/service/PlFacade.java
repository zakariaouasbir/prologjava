/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import helper.Util;
import java.io.File;
import java.util.Map;
import org.jpl7.Query;
import org.jpl7.Term;

/**
 * All rights reserved The source code is protected to its owner
 *
 * @author Abed
 */
public class PlFacade {

    Util util = new Util();
    File file = new File("texte.pl");

    public String makeRequest(String query) {
        util.checkdb("texte.pl");
        Query q = new Query(query);
        q.open();

        while (q.hasMoreSolutions()) {
            Map<String, Term> solution = q.nextSolution();
            return solution.toString();
        }
        return "";
    }

    public void save(String text) {
        System.out.println("save facade");
        util.rewrite(file, text);
    }

    public String readFile() {
        String content = util.readFile("texte.pl");
        return content;

    }

    public void saveFile(String content) {
        
        util.save(file, content);
        
        
        
    }

}
