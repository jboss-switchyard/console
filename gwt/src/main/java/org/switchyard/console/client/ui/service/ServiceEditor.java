/*
 * 2012 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.switchyard.console.client.ui.service;

import org.jboss.ballroom.client.widgets.ContentGroupLabel;
import org.jboss.ballroom.client.widgets.forms.Form;
import org.jboss.ballroom.client.widgets.forms.TextItem;
import org.switchyard.console.client.NameTokens;
import org.switchyard.console.client.model.Service;
import org.switchyard.console.client.ui.widgets.ClickableTextItem;
import org.switchyard.console.client.ui.widgets.ClickableTextItem.ValueAdapter;
import org.switchyard.console.client.ui.widgets.LocalNameFormItem;
import org.switchyard.console.client.ui.widgets.NamespaceFormItem;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * ServiceEditor
 * 
 * Editor for SwitchYard service configuration.
 * 
 * @author Rob Cernich
 */
public class ServiceEditor {

    private ServicePresenter _presenter;

    private Form<Service> _implementationDetailsForm;
    private GatewaysList _gatewaysList;

    private Service _service;

    /**
     * Create a new ServiceEditor.
     */
    public ServiceEditor() {
        _gatewaysList = new GatewaysList();
    }

    /**
     * @param presenter the current presenter.
     */
    public void setPresenter(ServicePresenter presenter) {
        _presenter = presenter;
        _gatewaysList.setPresenter(_presenter);
    }

    /**
     * @return this editor as a Widget.
     */
    public Widget asWidget() {
        VerticalPanel layout = new VerticalPanel();
        layout.setStyleName("fill-layout-width");

        layout.add(createImplementationDetailsPanel());
        layout.add(createGatewayDetailsPanel());

        return layout;
    }

    /**
     * @param service the service to be edited.
     */
    public void setService(Service service) {
        _service = service;

        if (service.getInterface() == null) {
            // XXX: workaround to ensure interface field in the form gets set.
            service.setInterface("");
        }

        _implementationDetailsForm.clearValues();
        _implementationDetailsForm.edit(service);
        _gatewaysList.setData(service.getGateways());
    }

    private Widget createImplementationDetailsPanel() {
        TextItem nameItem = new LocalNameFormItem("name_1", "Name");
        TextItem namespaceItem = new NamespaceFormItem("name_2", "Namespace");
        ClickableTextItem<String> applicationItem = new ClickableTextItem<String>("application", "Application",
                new ValueAdapter<String>() {
                    @Override
                    public String getText(String value) {
                        return NameTokens.parseQName(value)[1];
                    }

                    @Override
                    public String getTargetHistoryToken(String value) {
                        return createApplicationLink(value);
                    }
                });
        TextItem interfaceItem = new TextItem("interface", "Interface") {
            @Override
            public void setValue(String value) {
                if (value == null || value.length() == 0) {
                    value = "<inherited>";
                }
                super.setValue(value);
            }
        };
        ClickableTextItem<String> implementationItem = new ClickableTextItem<String>("promotedService",
                "Promoted Service", new ValueAdapter<String>() {
                    @Override
                    public String getText(String value) {
                        return NameTokens.parseQName(value)[1];
                    }

                    @Override
                    public String getTargetHistoryToken(String value) {
                        if (_service == null || _service.getApplication() == null) {
                            return createApplicationLink(null);
                        }
                        return createApplicationLink(_service.getApplication());
                    }
                });

        _implementationDetailsForm = new Form<Service>(Service.class);
        _implementationDetailsForm.setNumColumns(2);
        _implementationDetailsForm.setFields(nameItem, applicationItem, namespaceItem);
        _implementationDetailsForm.setFieldsInGroup("Implementation Details", implementationItem, interfaceItem);

        VerticalPanel implementationDetailsLayout = new VerticalPanel();
        implementationDetailsLayout.setStyleName("fill-layout-width");
        implementationDetailsLayout.add(new ContentGroupLabel("Service Details"));
        implementationDetailsLayout.add(_implementationDetailsForm.asWidget());

        return implementationDetailsLayout;
    }

    private String createApplicationLink(String applicationName) {
        PlaceRequest request = new PlaceRequest(NameTokens.APPLICATIONS_PRESENTER);
        if (applicationName != null) {
            request = request.with(NameTokens.APPLICATION_NAME_PARAM, URL.encode(applicationName));
        }
        return _presenter.getPlaceManager().buildRelativeHistoryToken(request, -1);
    }

    private Widget createGatewayDetailsPanel() {
        _gatewaysList.setPresenter(_presenter);
        return _gatewaysList.asWidget();
    }

}
