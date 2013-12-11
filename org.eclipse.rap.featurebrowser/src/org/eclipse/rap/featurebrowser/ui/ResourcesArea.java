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
import org.eclipse.rap.featurebrowser.FeatureBrowser;
import org.eclipse.rap.featurebrowser.util.ResourceUtil;
import org.eclipse.rap.featurebrowser.util.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ResourcesArea {

  private Composite main;
  private EnhancedTabFolder folder;
  private FeatureBrowser browser;

  public ResourcesArea( FeatureBrowser browser ) {
    this.browser = browser;
    main = new Composite( browser.getMainComposite(), SWT.NONE );
    style( main ).as( "floatingBox" );
    applyGridLayout( main );
    folder = WidgetFactory.createEnhancedTabFolder( main );
    applyGridData( folder.getControl() ).fill();
  }

  public Control getControl() {
    return main;
  }

  public void clearContents() {
    folder.clearContents();
  }

  public boolean hasContent() {
    return folder.getItemCount() > 0;
  }

  public void setFeature( Feature feature ) {
    clearContents();
    if( feature == null ) {
      return;
    }
    if( browser.getUserShowSource() ) {
      if( feature.getSnippetHtmlUrl() != null ) {
        folder.addTab( feature.getSnippet().getSimpleName() + ".java",
                       ResourceUtil.getImage( "icons/jcu_obj.gif" ),
                       createBrowser( folder.getFolder(), feature.getSnippetHtmlUrl() ) );
      }
      if( feature.getUrl() != null ) {
        String url = feature.getUrl();
        folder.addTab( url.substring( url.lastIndexOf( '/' ) + 1 ),
                       ResourceUtil.getImage( "icons/internal_browser.gif" ),
                       createBrowser( folder.getFolder(), url ) );
      }
      browser.setResoucesVisible( hasContent() );
    }
  }

}
