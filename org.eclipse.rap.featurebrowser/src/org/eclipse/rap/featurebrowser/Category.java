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

import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.json.JsonObject;


public class Category {

  private Object[] children;
  private String name = null;
  private Object parent;

  public Category( JsonObject obj, Object parent ) {
    this( obj.get( "children" ).asArray() );
    this.parent = parent;
    name = obj.get( "category" ).asString();
  }

  public Category( JsonArray json ) {
    children = new Object[ json.size() ];
    for( int i = 0; i < json.size(); i++ ) {
      JsonObject obj = json.get( i ).asObject();
      if( obj.get( "category" ) != null ) {
        children[ i ] = new Category( obj, this );
      } else {
        children[ i ] = new Feature( obj, this );
      }
    }
  }

  @Override
  public String toString() {
    return name;
  }

  public Object[] getChildren() {
    return children;
  }

  public Object getParent() {
    return parent;
  }

}
