/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package org.eclipse.rap.featurebrowser.ui;

import static org.eclipse.rap.featurebrowser.util.GridDataUtil.*;
import static org.eclipse.rap.featurebrowser.util.GridLayoutUtil.*;
import static org.eclipse.rap.featurebrowser.util.StyleUtil.*;
import static org.eclipse.rap.featurebrowser.util.WidgetFactory.*;

import org.eclipse.rap.featurebrowser.Feature;
import org.eclipse.rap.featurebrowser.util.ResourceUtil;
import org.eclipse.rap.featurebrowser.util.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class SnippetResourcesArea {

  public SnippetResourcesArea( Composite parent, Feature feature ) {
    Composite box = new Composite( parent, SWT.NONE );
    style( box ).as( "floatingBox" );
    applyGridLayout( box );
    EnhancedTabFolder folder = WidgetFactory.createEnhancedTabFolder( box );
    applyGridData( folder.getControl() ).fill();
    folder.addTab(
      feature.getSnippet().getSimpleName() + ".java",
      ResourceUtil.getImage( "icons/jcu_obj.gif" ),
      createBrowser( folder.getFolder(), feature.getSnippetHtmlUrl() )
    );
  }

}
