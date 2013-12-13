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
package org.eclipse.rap.demo.samples;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ResourceManager;


public class SWTStart {

  public static void main( String[] args ) {
    ResourceManager resourceManager = RWT.getResourceManager();
    resourceManager.register( "prettify.css", SWTStart.class.getClassLoader().getResourceAsStream( "prettify.css" ) );
    resourceManager.register( "prettify.js", SWTStart.class.getClassLoader().getResourceAsStream( "prettify.js" ) );
    new FeatureBrowser().createUI();
  }
}
