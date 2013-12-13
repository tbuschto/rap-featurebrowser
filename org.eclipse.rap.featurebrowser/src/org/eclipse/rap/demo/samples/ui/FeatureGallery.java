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
package org.eclipse.rap.demo.samples.ui;

import static org.eclipse.rap.demo.samples.util.HtmlDocument.*;

import org.eclipse.rap.demo.samples.Feature;
import org.eclipse.rap.demo.samples.Navigation;
import org.eclipse.rap.demo.samples.util.FeatureVisitor;
import org.eclipse.rap.demo.samples.util.HtmlDocument;
import org.eclipse.rap.demo.samples.util.HtmlDocument.Element;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;


public class FeatureGallery {


  private Browser browser;

  public FeatureGallery( Composite parent, Feature root ) {
    browser = new Browser( parent, SWT.NONE );
    browser.setText( generateHTML( root ) );
    new BrowserFunction( browser, "goto" ) {
      @Override
      public Object function( Object[] arguments ) {
        final String target = ( String )arguments[ 0 ];
        // Disposing the browser from within a BrowserFunction doesn't work
        Display.getCurrent().asyncExec( new Runnable() {
          public void run() {
            Navigation.getInstance().navigateTo( target );
          }
        } );
        return null;
      }
    };
  }

  public Control getControl() {
    return browser;
  }

  private static String generateHTML( Feature root ) {
    HtmlDocument doc = new HtmlDocument();
    doc.head.addContent(
      link().type( "text/css" ).rel( "stylesheet" ).href( locationOf( "gallery.css" ) )
    );
    final Element p = p();
    root.forEach( new FeatureVisitor() {
      public void visit( Feature feature ) {
        if( feature.getPreview() != null ) {
          p.addContent(
            a().cssClass( "item" ).href( "javascript:goto('" + feature + "')" ).content(
              img().src( locationOf( feature.getPreview() ) ),
              span().content( feature.getName() )
            )
          );
        }
      }
    } );
    doc.body.content( p );
    return doc.toString();
  }

}
