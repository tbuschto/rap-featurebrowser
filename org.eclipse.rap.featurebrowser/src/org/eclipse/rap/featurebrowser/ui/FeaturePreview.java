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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public class FeaturePreview {

  public FeaturePreview( Composite parent, Feature feature, final FeatureTree featureTree ) {
    Composite area = new Composite( parent, SWT.NONE );
    applyGridLayout( area ).marginTop( 20 );
    Control label = createLabel( area, feature.getName() );
    style( label ).font( "Arial", 30, SWT.NONE );
    applyGridData( label ).horizontalFill();
    String path = feature.getPreview();
    if( path != null ) {
      Control preview = createImage( area, path, getClass() );
      applyGridData( preview ).fill().center();
    }
    if( feature.getDescription() != null ) {
      InfoBox desc = new InfoBox( area );
      desc.addText( feature.getDescription() );
      applyGridData( desc.getControl() ).horizontalFill();
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
