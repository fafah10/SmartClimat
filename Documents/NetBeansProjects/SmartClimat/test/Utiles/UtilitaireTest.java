/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Loulouze
 */
public class UtilitaireTest {
    
    public UtilitaireTest() {
    }
    
    
    @Test
    public void testVerfiConnect() {
        System.out.println("verfiConnect");
        boolean result = Utilitaire.verifierConection();
        assertEquals(true, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
