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
package org.eclipse.rap.featurebrowser.util;

import org.eclipse.rap.rwt.RWT;


public class HtmlDocument {

  public static Element div() {
    return new Element( "div" );
  }

  public static Element span() {
    return new Element( "span" );
  }

  public static Element p() {
    return new Element( "p" );
  }

  public static Element img() {
    return new Element( "img" );
  }

  public static Element script() {
    return new Element( "script" ).content( "" );
  }

  public static Element link() {
    return new Element( "link" );
  }

  public static Element pre() {
    return new Element( "pre" );
  }

  public static class Element {

    private StringBuilder attributes;
    private StringBuilder content;
    private String tagName;


    public Element( String tagName ) {
      this.tagName = tagName;
    }

    public Element style( String value ) {
      return attr( "style", value );
    }

    public Element type( String value ) {
      return attr( "type", value );
    }

    public Element href( String value ) {
      return attr( "href", value );
    }

    public Element rel( String value ) {
      return attr( "rel", value );
    }

    public Element src( String value ) {
      return attr( "src", value );
    }

    public Element cssClass( String value ) {
      return attr( "class", value );
    }

    public Element attr( String name, String value ) {
      if( attributes == null ) {
        attributes = new StringBuilder();
      }
      attributes.append( " " );
      attributes.append( name );
      attributes.append( "=\"" );
      attributes.append( value );
      attributes.append( "\"" );
      return this;
    }

    public Element content( Object... contentObjects ) {
      content = null;
      return addContent( contentObjects );
    }

    public Element addContent( Object... contentObjects ) {
      if( content == null ) {
        content = new StringBuilder();
      }
      for( Object contentObject : contentObjects ) {
        content.append( contentObject );
      }
      return this;
    }


    @Override
    public String toString() {
      StringBuilder result = new StringBuilder();
      result.append( "<" );
      result.append( tagName );
      if( attributes != null ) {
        result.append( attributes );
      }
      if( content != null ) {
        result.append( ">\n" );
        result.append( content );
        result.append( "</" );
        result.append( tagName );
        result.append( ">\n" );
      } else {
        result.append( " />\n" );
      }
      return result.toString();
    }

  }

  public Element head = new Element( "head" );
  public Element body = new Element( "body" );

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append( "<!DOCTYPE html>\n" );
    result.append( head );
    result.append( body );
    return result.toString();
  }

  public static String locationOf( String path ) {
    return "/" + RWT.getResourceManager().getLocation( path );
  }

}
