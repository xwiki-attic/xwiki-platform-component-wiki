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
package org.xwiki.component.wiki.internal;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.xwiki.bridge.DocumentModelBridge;
import org.xwiki.bridge.event.DocumentCreatedEvent;
import org.xwiki.bridge.event.DocumentDeletedEvent;
import org.xwiki.bridge.event.DocumentUpdatedEvent;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.wiki.InvalidComponentDefinitionException;
import org.xwiki.component.wiki.WikiComponent;
import org.xwiki.component.wiki.WikiComponentBuilder;
import org.xwiki.component.wiki.WikiComponentException;
import org.xwiki.component.wiki.WikiComponentManager;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.observation.EventListener;
import org.xwiki.observation.event.Event;

/**
 * An {@link EventListener} responsible for dynamically registering / unregistering / updating xwiki wiki components,
 * based on wiki component create / delete / update actions.
 * 
 * @version $Id$
 * @since 4.1M1
 */
@Component
@Named(WikiComponentEventListener.NAME)
@Singleton
public class WikiComponentEventListener implements EventListener
{
    /**
     * This event listener name. Also used as role hint for this component implementation.
     */
    public static final String NAME = "wikiComponentListener";

    /**
     * The logger to log.
     */
    @Inject
    private Logger logger;

    /**
     * Wiki Component manager. Used to register/unregister wiki components.
     */
    @Inject
    private WikiComponentManager wikiComponentManager;

    /**
     * Wiki Component builder. Used to create {@link WikiComponent} form document references.
     */
    @Inject
    private WikiComponentBuilder wikiComponentBuilder;

    @Override
    public List<Event> getEvents()
    {
        return Arrays.<Event> asList(
            new DocumentCreatedEvent(),
            new DocumentUpdatedEvent(),
            new DocumentDeletedEvent());
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public void onEvent(Event event, Object source, Object data)
    {
        DocumentModelBridge document = (DocumentModelBridge) source;
        DocumentReference documentReference = document.getDocumentReference();

        if (event instanceof DocumentCreatedEvent || event instanceof DocumentUpdatedEvent) {
            
            // Unregister any existing component registered under this document.
            if (unregisterComponentInternal(documentReference)) {

                // Check whether the given document has a wiki component defined in it.
                if (this.wikiComponentBuilder.containsWikiComponent(documentReference)) {

                    // Attempt to create a wiki component.
                    WikiComponent wikiComponent = null;
                    try {
                        wikiComponent = this.wikiComponentBuilder.build(documentReference);
                    } catch (InvalidComponentDefinitionException e) {
                        // An invalid component exception here can be an expected error, so we only log at debug level.
                        this.logger.warn("Invalid component definition for document [{}]", documentReference);
                        return;
                    } catch (WikiComponentException e) {
                        this.logger.error("Failed to create wiki component for document [{}]", documentReference, e);
                        return;
                    }

                    // Register the component.
                    registerComponentInternal(wikiComponent);
                }
            }
        } else if (event instanceof DocumentDeletedEvent) {
            unregisterComponentInternal(documentReference);
        }

    }

    /**
     * Helper method to register a wiki component.
     * 
     * @param wikiComponent the wikiComponent to register.
     */
    private void registerComponentInternal(WikiComponent wikiComponent)
    {
        try {
            this.wikiComponentManager.registerWikiComponent(wikiComponent);
        } catch (WikiComponentException e) {
            this.logger.warn("Unable to register component in document [{}]", wikiComponent.getDocumentReference(),
                e);
        }
    }

    /**
     * Helper method to unregister a wiki component.
     * 
     * @param documentReference the reference to the document for which to unregister the held wiki component.
     * @return true if successful, false otherwise
     */
    private boolean unregisterComponentInternal(DocumentReference documentReference)
    {
        boolean result = false;
        if (this.wikiComponentBuilder.containsWikiComponent(documentReference)) {
            try {
                this.wikiComponentManager.unregisterWikiComponent(documentReference);
                result = true;
            } catch (WikiComponentException e) {
                this.logger.warn("Unable to unregister component in document [{}]", documentReference, e);
            }
        }
        return result;
    }
}
