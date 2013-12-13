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
package org.eclipse.rap.demo.samples.ui;

import static org.eclipse.rap.demo.samples.util.GridDataUtil.*;
import static org.eclipse.rap.demo.samples.util.GridLayoutUtil.*;
import static org.eclipse.rap.demo.samples.util.StyleUtil.*;
import static org.eclipse.rap.demo.samples.util.WidgetFactory.*;

import org.eclipse.rap.demo.samples.Feature;
import org.eclipse.rap.demo.samples.FeatureBrowser;
import org.eclipse.rap.demo.samples.util.ResourceUtil;
import org.eclipse.rap.demo.samples.util.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ResourcesArea {

  private Composite main;
  private EnhancedTabFolder folder;
  private FeatureBrowser browser;
  private boolean hasContent = false;

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
    hasContent = false;
  }

  public boolean hasContent() {
    return hasContent;
  }

  public void setFeature( Feature feature ) {
    clearContents();
    if( feature == null ) {
      return;
    }
    if( feature.getSnippetHtmlUrl() != null ) {
      hasContent = true;
      if( browser.getUserShowSource() ) {
        folder.addTab( feature.getSnippet().getSimpleName() + ".java",
                       ResourceUtil.getImage( "icons/jcu_obj.gif" ),
                       createBrowser( folder.getFolder(), feature.getSnippetHtmlUrl() ) );
      }
    }
    if( feature.getUrl() != null && feature.getSnippet() != null ) {
      hasContent = true;
      if( browser.getUserShowSource() ) {
        String url = feature.getUrl();
        folder.addTab( url.substring( url.lastIndexOf( '/' ) + 1 ),
                       ResourceUtil.getImage( "icons/internal_browser.gif" ),
                       createBrowser( folder.getFolder(), url ) );
      }
    }
  }

}
