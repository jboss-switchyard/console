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
package org.switchyard.console.client.ui.common;

import java.util.ArrayList;

import org.jboss.as.console.client.widgets.tabs.DefaultTabLayoutPanel;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.Tab;
import com.gwtplatform.mvp.client.TabData;
import com.gwtplatform.mvp.client.TabPanel;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * GWTPTabPanel
 * 
 * GWT Platform TabPanel functionality.
 */
public class GWTPTabPanel implements TabPanel {

    private Panel _layout;
    private TabLayoutPanel _tabBar;
    private Panel _content;
    private boolean _modifyingTabs;
    private ArrayList<GWTPTab> _tabs = new ArrayList<GWTPTab>();

    /**
     * Create a new GWTPTabPanel.
     * 
     * @param placeManager the place manager to use when processing tab
     *            selections.
     */
    public GWTPTabPanel(final PlaceManager placeManager) {
        _layout = new LayoutPanel();
        _layout.addStyleName("fill-layout");

        _tabBar = new DefaultTabLayoutPanel(40, Unit.PX);
        // _tabBar = new TabBar();
        _tabBar.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                if (_modifyingTabs) {
                    // add/remove tab may change the selection
                    return;
                }
                if (event.getSelectedItem() < 0) {
                    // TODO: WHAT????
                    return;
                }
                final PlaceRequest current = placeManager.getCurrentPlaceRequest();
                final String selectedToken = _tabs.get(event.getSelectedItem()).getTargetHistoryToken();
                final String currentToken = current == null ? null : current.getNameToken();
                if (!selectedToken.equals(currentToken)) {
                    placeManager.revealPlace(new PlaceRequest(selectedToken));
                }
            }
        });

        _content = new LayoutPanel();
        _content.setStyleName("fill-layout");

        _layout.add(_tabBar);
        _layout.add(_content);

        ((LayoutPanel) _layout).setWidgetTopHeight(_tabBar, 0, Unit.PX, 40, Unit.PX);
        ((LayoutPanel) _layout).setWidgetTopHeight(_content, 40, Unit.PX, 100, Unit.PCT);
    }

    /**
     * @return the widget associated with this tab panel.
     */
    public Widget asWidget() {
        return _layout;
    }

    /**
     * @param content set view content for tab place.
     */
    public void setContent(Widget content) {
        _content.clear();
        if (content != null) {
            _content.add(content);
        }
    }

    @Override
    public Tab addTab(TabData tabData, String historyToken) {
        _modifyingTabs = true;
        try {
            final GWTPTab tab = new GWTPTab(tabData, historyToken);
            final float priority = tabData.getPriority();
            int index = 0;
            for (GWTPTab existing : _tabs) {
                if (existing.getPriority() > priority) {
                    break;
                }
                ++index;
            }
            _tabs.add(index, tab);
            _tabBar.insert(new Label(), tab.getText(), index);
            return tab;
        } finally {
            _modifyingTabs = false;
        }
    }

    @Override
    public void removeTab(Tab tab) {
        _modifyingTabs = true;
        try {
            final int index = _tabs.indexOf(tab);
            if (index < 0) {
                return;
            }
            _tabs.remove(index);
            _tabBar.remove(index);
        } finally {
            _modifyingTabs = false;
        }
    }

    @Override
    public void removeTabs() {
        _modifyingTabs = true;
        try {
            for (int index = 0, count = _tabBar.getWidgetCount(); index < count; ++index) {
                _tabs.remove(index);
                _tabBar.remove(0);
            }
        } finally {
            _modifyingTabs = false;
        }
    }

    @Override
    public void setActiveTab(Tab tab) {
        _tabBar.selectTab(_tabs.indexOf(tab), false);
    }

}
