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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.rap.featurebrowser.Feature;
import org.eclipse.rap.featurebrowser.FeatureBrowser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class DemoArea {

  private Composite main;

  public DemoArea( FeatureBrowser browser ) {
    Composite parent = browser.getMainComposite();
    main = new Composite( parent, SWT.NONE );
    style( main ).as( "floatingBox" );
    applyGridLayout( main );
  }

  public Control getControl() {
    return main;
  }

  public void setFeature( Feature feature ) {
    clearContents();
    if( feature.getSnippet() != null ) {
      createSnippetArea( feature );
    }
    if( feature.getDescription() != null ) {
      InfoBox desc = new InfoBox( main );
      desc.addText( feature.getDescription() );
      applyGridData( desc.getControl() ).horizontalFill();
    }
  }

  private void createSnippetArea( Feature feature ) {
    Composite snippetParent = createSnippetParent();
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

  private Composite createSnippetParent() {
    Composite snippetParent = new Composite( main, SWT.NONE );
    applyGridLayout( snippetParent ).margin( 10 ).verticalSpacing( 5 );
    applyGridData( snippetParent ).fill().vIndent( 29 );
    return snippetParent;
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
