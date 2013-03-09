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

import java.util.List;
import org.cejug.yougi.AllTests;
import org.cejug.yougi.entity.UpdateHistory;
import org.cejug.yougi.entity.UpdateHistoryPK;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class UpdateHistoryBsnTest.
 * @author helio frota
 */
public class UpdateHistoryBsnTest {

    private static UpdateHistoryBean updateHistoryBean;

    /**
     * Lookup for the UpdateHistoryBean from embedded server.
     * @throws Exception exception
     */
    @Before
    public void setUp() throws Exception {
        updateHistoryBean = (UpdateHistoryBean) AllTests.ejbContainer.getContext().lookup("java:global/classes/UpdateHistoryBean");
    }

    @Test
    public void findUpdateHistory() {
        updateHistoryBean.findUpdateHistory(null);
        updateHistoryBean.findUpdateHistory(new UpdateHistoryPK());
    }

    @Test
    public void findLastUpdate() {
        List < UpdateHistory > list = updateHistoryBean.findLastUpdate();
        Assert.assertNotNull(list);
    }

}
