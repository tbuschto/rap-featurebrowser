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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.rap.featurebrowser.Feature;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class SnippetInstanceArea {

  public SnippetInstanceArea( Composite parent, Feature feature ) {
    Composite main = new Composite( parent, SWT.NONE );
    Composite snippetParent = new Composite( main, SWT.NONE );
    applyGridLayout( main );
    applyGridLayout( snippetParent ).margin( 10 ).verticalSpacing( 5 );
    applyGridData( snippetParent ).verticalFill();
    try {
      createContents( feature.getSnippet(), snippetParent );
    } catch( InstantiationException e ) {
      showError( parent, e );
    } catch( IllegalAccessException e ) {
      showError( parent, e );
    } catch( InvocationTargetException e ) {
      showError( parent, e.getCause() != null ? e.getCause() : e );
    } catch( NoSuchMethodException e ) {
      showError( parent, e );
    } catch( ClassCastException e ) {
      showError( parent, e );
    }
    if( feature.getDescription() != null ) {
      InfoBox desc = new InfoBox( main );
      desc.addText( feature.getDescription() );
      applyGridData( desc.getControl() ).horizontalFill();
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

  static void createContents( Class<?> clazz, Composite parent )
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