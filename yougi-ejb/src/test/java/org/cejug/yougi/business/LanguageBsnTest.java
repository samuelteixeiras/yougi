/* Yougi is a web application conceived to manage user groups or 
 * communities focused on a certain domain of knowledge, whose members are 
 * constantly sharing information and participating in social and educational 
 * events. Copyright (C) 2011 Ceara Java User Group - CEJUG.
 * 
 * This application is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation; either version 2.1 of the License, or (at your 
 * option) any later version.
 * 
 * This application is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 * 
 * There is a full copy of the GNU Lesser General Public License along with 
 * this library. Look for the file license.txt at the root level. If you do not
 * find it, write to the Free Software Foundation, Inc., 59 Temple Place, 
 * Suite 330, Boston, MA 02111-1307 USA.
 * 
 */
package org.cejug.yougi.business;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test class LanguageBsnTest.
 * @author helio frota
 */
public class LanguageBsnTest {
    
    private static EJBContainer ejbContainer;
    private static LanguageBsn languageBsn;

    /**
     * Creates the ejbContainer and gets the LanguageBsn from embedded glassfish.
     * @throws Exception exception
     */
    @BeforeClass
    public static void setUp() throws Exception {
        Map<Object, Object> properties = new HashMap<>(1);
        properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "./src/test/resources/glassfish");
        ejbContainer = javax.ejb.embeddable.EJBContainer.createEJBContainer(properties);
        languageBsn = (LanguageBsn) ejbContainer.getContext().lookup("java:global/classes/LanguageBsn");
    }
    
    /**
     * Closes the ejbContainer.
     */
    @AfterClass
    public static void shutdown() {
        ejbContainer.close();
    }

    @Test
    public void findLanguage() {
        languageBsn.findLanguage("pt_BR");
    }
    
    @Test
    public void findLanguages() {
        languageBsn.findLanguages();
    }
}
