/* Yougi is a web application conceived to manage user groups or
 * communities focused on a certain domain of knowledge, whose members are
 * constantly sharing information and participating in social and educational
 * events. Copyright (C) 2011 Hildeberto Mendonça.
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

import org.cejug.yougi.AllTests;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class LanguageBsnTest.
 *
 * @author Helio Frota - http://www.heliofrota.com
 */
public class LanguageBsnTest {

    private static LanguageBean languageBean;

    /**
     * Lookup for the LanguageBean from embedded server.
     * @throws Exception exception
     */
    @Before
    public void setUp() throws Exception {
        languageBean = (LanguageBean) AllTests.ejbContainer.getContext().lookup("java:global/classes/LanguageBean");
    }

    @Test
    public void findLanguage() {
        languageBean.findLanguage("pt_BR");
    }

    @Test
    public void findLanguages() {
        languageBean.findLanguages();
    }
}