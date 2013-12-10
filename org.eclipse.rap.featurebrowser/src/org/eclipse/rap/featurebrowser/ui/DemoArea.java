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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.rap.featurebrowser.Feature;
import org.eclipse.rap.featurebrowser.FeatureBrowser;
import org.eclipse.rap.featurebrowser.util.GridDataUtil;
import org.eclipse.rap.featurebrowser.util.ResourceUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class DemoArea {

  private Composite main;
  private FeatureBrowser browser;

  public DemoArea( FeatureBrowser browser ) {
    this.browser = browser;
    Composite parent = browser.getMainComposite();
    main = new Composite( parent, SWT.NONE );
    style( main ).as( "floatingBox" );
    style( main ).background( "#ffffff" );
    applyGridLayout( main );
  }

  public Control getControl() {
    return main;
  }

  public void setFeature( Feature feature ) {
    clearContents();
    if( feature.getSnippet() != null ) {
      createSnippetArea( feature );
    } else if( feature.getPreview() != null ) {
      createPreview( feature );
    } else {
      createGallery( feature );
    }
    if( feature.getDescription() != null ) {
      createDescription( feature );
    }
  }

  private void createPreview( Feature feature ) {
    createHeader( feature.getName() );
    Composite previewArea = new Composite( main, SWT.NONE );
    applyGridData( previewArea ).fill();
    applyGridLayout( previewArea ).marginTop( 20 );
    String path = feature.getPreview();
    if( path != null ) {
      Control preview = createImage( previewArea, path, getClass() );
      applyGridData( preview ).fill().center();
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

  private void createGallery( Feature feature ) {
    createHeader( feature.getName() );
    FeatureGallery gallery = new FeatureGallery( main, feature );
    applyGridData( gallery.getControl() ).fill();
  }

  private void createHeader( String text ) {
    Composite header = new Composite( main, SWT.NONE );
    header.setBackgroundMode( SWT.INHERIT_FORCE );
    style( header ).as( "miniHeader" );
    applyGridData( header ).height( 29 ).horizontalFill();
    applyGridLayout( header ).cols( 3 );
    createMiniButton( header, "icons/shift_l_edit.gif", new Listener() {
      public void handleEvent( Event event ) {
        browser.previousFeature();
      }
    } );
    CLabel headerLabel = new CLabel( header, SWT.CENTER );
    applyGridData( headerLabel ).horizontalFill();
    headerLabel.setText( text.replaceAll( "&", "&&" ) );
    createMiniButton( header, "icons/shift_r_edit.gif", new Listener() {
      public void handleEvent( Event event ) {
        browser.nextFeature();
      }
    } );
  }

  private static Button createMiniButton( Composite toolBar, String imagePath, Listener listener ) {
    Button item = new Button( toolBar, SWT.PUSH );
    item.setImage( ResourceUtil.getImage( imagePath ) );
    style( item ).as( "mini" );
    item.addListener( SWT.Selection, listener );
    applyGridData( item );
    return item;
  }

  private void createDescription( Feature feature ) {
    InfoBox desc = new InfoBox( main );
    desc.addText( feature.getDescription() );
    applyGridData( desc.getControl() ).horizontalFill().vAlign( SWT.BOTTOM ).vGrab();
  }

  private void createSnippetArea( Feature feature ) {
    String text =   feature.getParent().getName()
                  + " "
                  + feature.getName();
    createHeader( text );
    Composite snippetParent = createSnippetParent( feature );
    try {
      runSnippet( feature.getSnippet(), snippetParent );
    } catch( InstantiationException e ) {
      showError( snippetParent, e );
    } catch( IllegalAccessException e ) {
      showError( snippetParent, e );
    } catch( InvocationTargetException e ) {
      showError( snippetParent, e.getCause() != null ? e.getCause() : e );
    } catch( NoSuchMethodException e ) {
      showError( snippetParent, e );
    } catch( ClassCastException e ) {
      showError( snippetParent, e );
    }
  }

  private Composite createSnippetParent( Feature feature ) {
    boolean boxed = feature.getWidth() > 0 && feature.getHeight() > 0;
    Composite result = new Composite( main, SWT.NONE );
    style( result ).as( boxed ? "snippetBox" : "snippet" );
    applyGridLayout( result ).margin( 10 ).verticalSpacing( 5 );
    GridDataUtil gridData = applyGridData( result ).center();
    if( feature.getWidth() > 0 ) {
      gridData.width( feature.getWidth() ).hGrab();
    } else {
      gridData.horizontalFill();
    }
    if( feature.getHeight() > 0 ) {
      gridData.height( feature.getHeight() ).vIndent( 40 );
    } else {
      gridData.verticalFill();
    }
    return result;
  }

  void clearContents() {
    Control[] children = main.getChildren();
    for( int i = 0; i < children.length; i++ ) {
      children[ i ].dispose();
    }
  }

  static void showError( Composite snippetParent, Throwable fail ) {
    Text msg = new Text( snippetParent, SWT.MULTI );
    msg.setEditable( false );
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream( baos );
    ps.append( fail.getMessage() + "\n" );
    fail.printStackTrace( ps );
    fail.printStackTrace();
    try {
      msg.setText( baos.toString( "UTF-8" ) );
    } catch( UnsupportedEncodingException e ) {
      e.printStackTrace();
    }
    applyGridData( msg ).fill();
  }

  static void runSnippet( Class<?> clazz, Composite parent )
    throws InstantiationException, IllegalAccessException, InvocationTargetException,
    NoSuchMethodException
  {
    Object instance = clazz.newInstance();
    Class<?> params[] = { Composite.class };
    Object paramsObj[] = { parent };
    Method declaredMethod = clazz.getDeclaredMethod( "createContents", params );
    declaredMethod.setAccessible( true );
    declaredMethod.invoke( instance, paramsObj );
  }

}
