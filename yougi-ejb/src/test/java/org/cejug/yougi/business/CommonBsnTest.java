/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cejug.yougi.business;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import org.cejug.yougi.entity.AccessGroup;
import org.cejug.yougi.entity.Country;
import org.cejug.yougi.entity.Language;
import org.cejug.yougi.entity.MessageTemplate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test class CommonBsnTest.
 * @author helio frota
 */
public class CommonBsnTest {
    
    private static EJBContainer ejbContainer;
    private static CommonBsn commonBsn;

    /**
     * Creates the ejbContainer and gets the CommonBsn from embedded glassfish.
     * @throws Exception exception
     */
    @BeforeClass
    public static void setUp() throws Exception {
        Map<Object, Object> properties = new HashMap<Object, Object>();
        properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "./src/test/resources/glassfish");
        ejbContainer = javax.ejb.embeddable.EJBContainer.createEJBContainer(properties);
        commonBsn = (CommonBsn) ejbContainer.getContext().lookup("java:global/classes/CommonBsn");
    }
    
    /**
     * Closes the ejbContainer.
     */
    @AfterClass
    public static void shutdown() {
        ejbContainer.close();
    }

    @Test
    public void findAllOrderedBy() {
        
        // The method findAccessGroups() can be removed.
        System.out.println(commonBsn.findAllOrderedBy(AccessGroup.class, "name", true));
        
        // The method findLanguages() can be removed.
        System.out.println(commonBsn.findAllOrderedBy(Language.class, "name", true));
        
        // The method findCountries() can be removed.
        System.out.println(commonBsn.findAllOrderedBy(Country.class, "name", true));
        
        // The method findMessageTemplates() can be removed.
        System.out.println(commonBsn.findAllOrderedBy(MessageTemplate.class, "title", true));
        
    }
    
}
