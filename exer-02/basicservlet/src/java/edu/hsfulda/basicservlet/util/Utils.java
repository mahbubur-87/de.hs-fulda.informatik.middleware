/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsfulda.basicservlet.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author mahbuburrahman
 */
public class Utils {
    public static StringBuffer readFormFile (String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        StringBuffer sb = new StringBuffer();

        while(br.ready()) {
            sb.append(br.readLine());
        }
        
        br.close();
        
        return sb;
    }
    
    public static void writeToFile (String filePath, String result) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
        bw.append(result + "|");
        bw.close();
    }
} 
