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
package org.eclipse.rap.demo.samples.util;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.demo.samples.ui.EnhancedTabFolder;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.Client;
import org.eclipse.rap.rwt.client.WebClient;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;


public class WidgetFactory {

  public static Browser createBrowser( Composite wrapper, String url ) {
    Browser browser = new Browser( wrapper, SWT.NONE );
    browser.setUrl( url );
    return browser;
  }

  public static Control createLabel( Composite area, String text ) {
    Label label = new Label( area, SWT.LEFT );
    label.setText( text );
    return label;
  }

  public static Control createMarkupLabel( Composite parent, String text ) {
    Label label = new Label( parent, SWT.WRAP );
    if( RWT.getClient() instanceof WebClient ) {
      label.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
      label.setText( text );
    } else {
      label.setText( stripHtml( text ) );
    }
    return label;
  }

  public static EnhancedTabFolder createEnhancedTabFolder( Composite parent ) {
    Client client = RWT.getClient();
    if( client == null || client instanceof WebClient ) {
      return new EnhancedTabFolder.CTabFolderWrapper( parent );
    }
    return new EnhancedTabFolder.TabFolderWrapper( parent );
  }

  public static Control createImage( Composite parent, String path, Class<?> classLoaderClass ) {
    Label label = new Label( parent, SWT.LEFT );
    InputStream stream = classLoaderClass.getClassLoader().getResourceAsStream( path );
    label.setImage( new Image( label.getDisplay(), stream ) );
    try {
      stream.close();
    } catch( IOException ex ) {
      throw new RuntimeException( ex );
    }
    return label;
  }

  public static Control createButton( Composite parent, String path, Class<?> classLoaderClass ) {
    Button button = new Button( parent, SWT.NONE );
    InputStream stream = classLoaderClass.getClassLoader().getResourceAsStream( path );
    button.setImage( new Image( button.getDisplay(), stream ) );
    try {
      stream.close();
    } catch( IOException ex ) {
      throw new RuntimeException( ex );
    }
    return button;
  }

  public static Shell createOverlayShell( Shell parentShell ) {
    final Shell shell = new Shell( parentShell, SWT.TOP | SWT.APPLICATION_MODAL );
    shell.setFullScreen( true );
    return shell;
  }

  private static String stripHtml( String text ) {
    String[] split = text.replaceAll( "\n", " " ).split( "[<>]" );
    StringBuilder result = new StringBuilder();
    for( int i = 0; i < split.length; i += 2 ) {
      result.append( split[ i ] );
    }
    return result.toString();
  }
}
