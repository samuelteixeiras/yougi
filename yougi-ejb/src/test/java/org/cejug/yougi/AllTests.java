package org.cejug.yougi;

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
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import org.cejug.yougi.business.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Suite class AllTests.
 * @author helio frota
 */
@RunWith(value = Suite.class)
@SuiteClasses(value = {
    CommonBsnTest.class,
    LanguageBsnTest.class
})
public class AllTests {

    public static EJBContainer ejbContainer;

    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        Map<Object, Object> properties = new HashMap<Object, Object>(1);
        properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "./src/test/resources/glassfish");
        ejbContainer = javax.ejb.embeddable.EJBContainer.createEJBContainer(properties);
    }


    @AfterClass
    public static void shutdown() {
        ejbContainer.close();
    }

}