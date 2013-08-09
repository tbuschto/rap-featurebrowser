/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others.
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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public class InfoBox {

  private final Composite contentComp;
  private Composite spacer;

  public InfoBox( Composite parent ) {
    spacer = new Composite( parent, SWT.NONE );
    applyGridLayout( spacer ).margins( 10, 20, 0, 20 );
    contentComp = createInfoboxContentComposite();
  }

  private Composite createInfoboxContentComposite() {
    Composite contentComp = new Composite( spacer, SWT.NONE );
    contentComp.setBackgroundMode( SWT.INHERIT_FORCE );
    style( contentComp ).as( "infobox" );
    applyGridLayout( contentComp ).margin( 35 ).spacing( 20 ).cols( 2 );
    applyGridData( contentComp ).fill();
    Control icon = createImage( contentComp,
                                "icons/1375930009_information-balloon_basic_blue_edit1.png",
                                getClass() );
    applyGridData( icon ).vAlign( SWT.TOP ).hAlign( SWT.LEFT );
    return contentComp;
  }

  public void addText( String text ) {
    Control label = createMarkupLabel( contentComp, text );
    applyGridData( label ).fill();
    style( label ).as( "infobox" );
  }

  public Control getControl() {
    return spacer;
  }

}
