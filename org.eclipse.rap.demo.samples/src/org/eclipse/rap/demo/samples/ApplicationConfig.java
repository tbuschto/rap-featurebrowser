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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;


public class ApplicationConfig implements ApplicationConfiguration {

  public void configure( Application application ) {
    Map<String, String> properties = new HashMap<String, String>();
    properties.put( WebClient.PAGE_TITLE, "RAP Samples" );
    application.addStyleSheet( RWT.DEFAULT_THEME_ID, "/css/app.css" );
    application.addEntryPoint( "/samples", SamplesBrowser.class, properties );
    application.addResource( "prettify.js", FeaturesResourceLoader.getInstance() );
    application.addResource( "prettify.css", FeaturesResourceLoader.getInstance() );
    application.addResource( "gallery.css", FeaturesResourceLoader.getInstance() );
  }

}
