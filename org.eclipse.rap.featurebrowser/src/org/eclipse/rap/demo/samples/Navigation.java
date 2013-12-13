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

import org.eclipse.rap.demo.samples.ui.FeatureTree;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.SingletonUtil;
import org.eclipse.rap.rwt.client.WebClient;
import org.eclipse.rap.rwt.client.service.BrowserNavigation;
import org.eclipse.rap.rwt.client.service.BrowserNavigationEvent;
import org.eclipse.rap.rwt.client.service.BrowserNavigationListener;


public class Navigation {

  public static Navigation getInstance() {
    return SingletonUtil.getSessionInstance( Navigation.class );
  }

  private Map<String, Feature> map = new HashMap<String, Feature>();

  public void register( Feature feature ) {
    map.put( feature.toString(), feature );
  }

  public void init( final FeatureTree featureTree ) {
    if( RWT.getClient() instanceof WebClient ) {
      BrowserNavigation navigation = RWT.getClient().getService( BrowserNavigation.class );
      navigation.addBrowserNavigationListener( new BrowserNavigationListener() {
        public void navigated( BrowserNavigationEvent event ) {
          Feature feature = map.get( event.getState() );
          featureTree.select( feature );
        }
      } );
    }
  }

  public void push( Feature feature ) {
    if( RWT.getClient() instanceof WebClient ) {
      BrowserNavigation navigation = RWT.getClient().getService( BrowserNavigation.class );
      navigation.pushState( feature.toString(), null );
    }
  }

}
