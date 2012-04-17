/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.component.wiki;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.xwiki.component.wiki.internal.DefaultWikiComponent;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.test.AbstractComponentTestCase;

public class WikiComponentManagerTest extends AbstractComponentTestCase
{    
    private WikiComponentManager manager;

    @Before
    public void setUp() throws Exception
    {
        manager = getComponentManager().getInstance(WikiComponentManager.class);
    }

    @Test
    public void testRegisterWikiComponent() throws Exception
    {
        DocumentReference pseudoReference = new DocumentReference("somewiki","XWiki","MyComponent");

        DefaultWikiComponent wc = new DefaultWikiComponent(pseudoReference, WikiComponentManager.class, "test");
        
        this.manager.registerWikiComponent(wc);
        
        WikiComponentManager registered = this.getComponentManager().getInstance(WikiComponentManager.class, "test");
        
        Assert.assertNotNull(registered);
    }
}
