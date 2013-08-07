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

import static org.eclipse.rap.featurebrowser.GridDataUtil.applyGridData;
import static org.eclipse.rap.featurebrowser.GridLayoutUtil.applyGridLayout;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;


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
    contentComp.setData( RWT.CUSTOM_VARIANT, "infobox" );
    applyGridLayout( contentComp ).margin( 35 ).spacing( 20 ).cols( 2 );
    applyGridData( contentComp ).fill();
    Label icon = new Label( contentComp, SWT.NONE );
    InputStream stream = getClass().getClassLoader()
        .getResourceAsStream( "icons/1375930009_information-balloon_basic_blue_edit1.png" );
    //    .getResourceAsStream( "icons/1375930019_Info_edit1.png" );
    icon.setImage( new Image( icon.getDisplay(), stream ) );
    try {
      stream.close();
    } catch( IOException ex ) {
      throw new RuntimeException( ex );
    }
    applyGridData( icon ).vAlign( SWT.TOP ).hAlign( SWT.LEFT );

    return contentComp;
  }

  public void addText( String text ) {
    Label label = new Label( contentComp, SWT.WRAP );
    label.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
    label.setText( text );
    applyGridData( label ).fill();
    label.setData( RWT.CUSTOM_VARIANT, "infobox" );
  }

  public Control getControl() {
    return spacer;
  }

}
