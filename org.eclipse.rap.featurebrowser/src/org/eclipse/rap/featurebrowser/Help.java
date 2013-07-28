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
package org.eclipse.rap.featurebrowser;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;


public class Help {

  public Help( Shell parentShell ) {
    ensureHtml();
    final Shell shell = new Shell( parentShell, SWT.CLOSE | SWT.TOP | SWT.APPLICATION_MODAL );
    Button close = new Button( shell, SWT.PUSH );
    shell.setFullScreen( true );
    shell.setLayout( new FormLayout() );
    shell.setData( RWT.CUSTOM_VARIANT, "helpShell" );
    shell.addListener( SWT.Hide, new Listener() {
      public void handleEvent( Event event ) {
        shell.dispose();
      }
    } );
    Composite area = new Composite( shell, SWT.BORDER );
    FormData formData = new FormData();
    formData.left = new FormAttachment( 0, 35 );
    formData.top = new FormAttachment( 0, 35 );
    formData.right = new FormAttachment( 100, -35 );
    formData.bottom = new FormAttachment( 100, -35 );
    area.setLayoutData( formData );
    area.setData( RWT.CUSTOM_VARIANT, "helpBox" );
    GridLayout layout = new GridLayout( 1, true );
    layout.marginWidth = 15;
    layout.marginTop = 8;
    layout.marginBottom = -5; // WTF?
    layout.verticalSpacing = 10;
    area.setLayout( layout );
    Label heading = new Label( area, SWT.NONE );
    heading.setText( "Getting Started..." );
    heading.setLayoutData( new GridData( SWT.CENTER, SWT.CENTER, true, false ) );
    heading.setFont( new Font( heading.getDisplay(), "Segoe UI, Arimo, Calibri, Verdana, Sans-serif", 36, SWT.ITALIC ) );
    heading.setData( RWT.CUSTOM_VARIANT, "helpHeading" );
    Browser browser = new Browser( area, SWT.NONE );
    browser.setLayoutData( LayoutUtil.createFillData() );
    browser.setUrl( RWT.getResourceManager().getLocation( "help.html" ) );
    InputStream stream = getClass().getClassLoader().getResourceAsStream( "x-mark-4-xl.png" );
    close.setImage( new Image( close.getDisplay(), stream ) );
    try {
      stream.close();
    } catch( IOException e1 ) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    close.setData( RWT.CUSTOM_VARIANT, "helpClose" );
    close.addListener( SWT.Selection, new Listener() {
      public void handleEvent( Event event ) {
        shell.dispose();
      }
    } );
    try {
      stream.close();
    } catch( IOException e ) {
      throw new RuntimeException( e );
    }
    formData = new FormData();
    formData.top = new FormAttachment( 0, 10 );
    formData.right = new FormAttachment( 100, -10 );
    close.setLayoutData( formData );
    shell.open();
  }

  private static void ensureHtml() {
    ResourceManager resourceManager = RWT.getResourceManager();
    if( !resourceManager.isRegistered( "help.html" ) ) {
      InputStream stream
        = Help.class.getClassLoader().getResourceAsStream( "help.html" );
      resourceManager.register( "help.html", stream );
      try {
        stream.close();
      } catch( IOException e ) {
        e.printStackTrace();
      }
    }
  }

}
