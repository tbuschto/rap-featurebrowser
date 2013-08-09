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

import static org.eclipse.rap.featurebrowser.util.FormDataUtil.*;
import static org.eclipse.rap.featurebrowser.util.GridDataUtil.*;
import static org.eclipse.rap.featurebrowser.util.GridLayoutUtil.*;
import static org.eclipse.rap.featurebrowser.util.StyleUtil.*;
import static org.eclipse.rap.featurebrowser.util.WidgetFactory.*;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class HelpOverlay {

  public HelpOverlay( Shell parentShell ) {
    ensureHtml();
    final Shell shell = createOverlayShell( parentShell );
    style( shell ).as( "helpShell" );
    Control close = createButton( shell, "icons/x-mark-4-xl.png", getClass() );
    style( close ).as( "helpClose" );
    close.addListener( SWT.Selection, new Listener() {
      public void handleEvent( Event event ) {
        shell.dispose();
      }
    } );
    shell.setLayout( new FormLayout() );
    shell.addListener( SWT.Hide, new Listener() {
      public void handleEvent( Event event ) {
        shell.dispose();
      }
    } );
    Composite area = new Composite( shell, SWT.BORDER );
    applyFormData( area ).left( 0, 35 ).top( 0, 35 ).right( 100, -35 ).bottom( 100, -35 );
    style( area ).as( "helpBox" );
    applyGridLayout( area ).equalColumns().margins( 8, 15, 10, 15 );
    Control heading = createLabel( area, "Getting Started..." );
    applyGridData( heading ).center().hGrab();
    style( heading ).font( "Segoe UI, Arimo, Calibri, Verdana, Sans-serif", 36, SWT.ITALIC );
    style( heading ).as( "helpHeading" );
    Browser browser = createBrowser( area, RWT.getResourceManager().getLocation( "help.html" ) );
    applyGridData( browser ).fill();
    applyFormData( close ).top( 0, 10 ).right( 100, -10 );
    shell.open();
  }

  private static void ensureHtml() {
    ResourceManager resourceManager = RWT.getResourceManager();
    if( !resourceManager.isRegistered( "help.html" ) ) {
      InputStream stream
        = HelpOverlay.class.getClassLoader().getResourceAsStream( "help.html" );
      resourceManager.register( "help.html", stream );
      try {
        stream.close();
      } catch( IOException e ) {
        e.printStackTrace();
      }
    }
  }

}
