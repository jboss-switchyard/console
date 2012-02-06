/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package org.switchyard.console.client.gin;

import org.jboss.as.console.client.shared.SubsystemExtension;
import org.jboss.as.console.client.shared.SubsystemExtension.SubsystemGroupDefinition;
import org.jboss.as.console.client.shared.SubsystemExtension.SubsystemItemDefinition;
import org.switchyard.console.client.NameTokens;
import org.switchyard.console.client.ui.application.ApplicationPresenter;
import org.switchyard.console.client.ui.config.ConfigPresenter;
import org.switchyard.console.client.ui.service.ServicePresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

/**
 * Overall module configuration.
 * 
 * @see SwitchYardClientModule
 * 
 * @author Rob Cernich
 */
@SubsystemExtension(@SubsystemGroupDefinition(name = "SwitchYard", subsystem = "switchyard", items = {
        @SubsystemItemDefinition(presenter = NameTokens.SYSTEM_CONFIG_PRESENTER, name = NameTokens.SYSTEM_CONFIG_TEXT),
        @SubsystemItemDefinition(presenter = NameTokens.APPLICATIONS_PRESENTER, name = NameTokens.APPLICATIONS_TEXT),
        @SubsystemItemDefinition(presenter = NameTokens.SERVICES_PRESENTER, name = NameTokens.SERVICES_TEXT) }))
@GinModules(SwitchYardClientModule.class)
public interface SwitchYardGinjector extends Ginjector {

    /**
     * @return the ConfigPresenter configured for the module.
     */
    AsyncProvider<ConfigPresenter> getConfigPresenter();

    /**
     * @return the ApplicationPresenter configured for the module.
     */
    AsyncProvider<ApplicationPresenter> getApplicationPresenter();

    /**
     * @return the ServicePresenter configured for the module.
     */
    AsyncProvider<ServicePresenter> getServicePresenter();

}
