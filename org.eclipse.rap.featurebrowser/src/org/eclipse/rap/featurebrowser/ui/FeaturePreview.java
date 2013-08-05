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

import static org.eclipse.rap.featurebrowser.GridDataUtil.applyGridData;
import static org.eclipse.rap.featurebrowser.GridLayoutUtil.applyGridLayout;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.featurebrowser.Feature;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class FeaturePreview {

  public FeaturePreview( Composite parent, Feature feature, final FeatureTree featureTree ) {
    Composite area = new Composite( parent, SWT.NONE );
    applyGridLayout( area ).marginTop( 20 );
    Label label = new Label( area, SWT.CENTER );
    applyGridData( label ).horizontalFill();
    label.setText( feature.getName() );
    label.setFont( new Font( parent.getDisplay(), "Arial", 30, SWT.BORDER ) );
    if( feature.getPreview() != null ) {
      Label preview = new Label( area, SWT.BORDER );
      InputStream stream = getClass().getClassLoader().getResourceAsStream( feature.getPreview() );
      preview.setImage( new Image( label.getDisplay(), stream ) );
      applyGridData( preview ).fill().center();
      try {
        stream.close();
      } catch( IOException ex ) {
        throw new RuntimeException( ex );
      }
    }
//    Label separator = new Label( area, SWT.SEPARATOR | SWT.HORIZONTAL );
//    applyGridData( separator ).horizontalFill();
//    final Table table = new Table( area, SWT.NONE );
//    table.setFont( new Font( table.getDisplay(), "Courier New", 12, SWT.NORMAL ) );
//    table.setLinesVisible( true );
//    applyGridData( table ).horizontalFill().vAlign( SWT.FILL );
//    feature.forEach( new FeatureVisitor() {
//      public void visit( Feature feature ) {
//        if( feature.getSnippet() != null ) {
//          TableItem item = new TableItem( table, SWT.NONE );
//          item.setText( feature.getSnippet().getSimpleName() + ".java" );
//          item.setData( feature );
//        }
//      }
//    } );
//    table.addListener( SWT.Selection, new Listener() {
//      public void handleEvent( Event event ) {
//        featureTree.select( ( Feature )event.item.getData() );
//      }
//    } );
  }
}
