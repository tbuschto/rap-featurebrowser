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

import static org.eclipse.rap.featurebrowser.HtmlDocument.img;
import static org.eclipse.rap.featurebrowser.HtmlDocument.locationOf;
import static org.eclipse.rap.featurebrowser.HtmlDocument.p;

import org.eclipse.rap.featurebrowser.HtmlDocument.Element;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public class Gallery {


  private Browser browser;

  public Gallery( Composite parent, Feature root, FeatureTree tree ) {
    browser = new Browser( parent, SWT.NONE );
    browser.setText( generateHTML( root ) ); // TODO store in ResourceManager
  }

  public Control getControl() {
    return browser;
  }

  private static String generateHTML( Feature root ) {
    HtmlDocument doc = new HtmlDocument();
    final Element p = p();
    root.forEach( new FeatureVisitor() {
      public void visit( Feature feature ) {
        if( feature.getPreview() != null ) {
          p.addContent( img().src( locationOf( feature.getPreview() ) ) );
        }
      }
    } );
    doc.body.content( p );
    return doc.toString();
  }

}
