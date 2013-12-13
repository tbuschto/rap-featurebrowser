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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public class DescriptionArea {

  private Composite main;
  private Control label;

  public DescriptionArea( FeatureBrowser browser ) {
    main = new Composite( browser.getMainComposite(), SWT.NONE );
    main.setBackgroundMode( SWT.INHERIT_FORCE );
    style( main ).as( "descBox" );
    applyGridLayout( main ).margin( 20 ).spacing( 20 ).cols( 2 );
    Control icon = createImage( main, "icons/balloon.png", getClass() );
    applyGridData( icon ).vAlign( SWT.TOP ).hAlign( SWT.LEFT );
  }

  public Control getControl() {
    return main;
  }

  public void setFeature( Feature feature ) {
    clearContent();
    if( feature.getDescription() != null ) {
      label = createMarkupLabel( main, feature.getDescription() );
      style( label ).as( "descLabel" );
      applyGridData( label ).fill();
    }
  }

  private void clearContent() {
    if( label != null ) {
      label.dispose();
      label = null;
    }
  }


}
