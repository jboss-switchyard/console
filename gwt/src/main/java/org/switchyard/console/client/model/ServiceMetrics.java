/* 
 * JBoss, Home of Professional Open Source 
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
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
package org.switchyard.console.client.model;

import java.util.List;

/**
 * ServiceMetrics
 * 
 * <p/>
 * Message metrics specific to a service/reference.
 * 
 * @author Rob Cernich
 */
public interface ServiceMetrics extends MessageMetrics, HasQName {

    /**
     * @return metrics for operations
     */
    List<OperationMetrics> getOperations();

    /**
     * @param value metrics for operations
     */
    void setOperations(List<OperationMetrics> value);

    /**
     * @return metrics for referenced services
     */
    List<ServiceMetrics> getReferences();

    /**
     * @param value metrics for referenced services
     */
    void setReferences(List<ServiceMetrics> value);

    /**
     * @return metrics for associated gateways
     */
    List<GatewayMetrics> getGateways();

    /**
     * @param value metrics for associated gateways
     */
    void setGateways(List<GatewayMetrics> value);

    /**
     * @return the containing application
     */
    String getApplication();

    /**
     * @param application the containing application
     */
    void setApplication(String application);

}
