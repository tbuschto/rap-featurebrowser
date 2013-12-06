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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.WebClient;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;


public class StyleUtil {


  private interface Styler {
    public void style( Widget widget, StyleUtil style );
  }

  private Widget widget;
  private static Map<String,Styler> styleMap;

  public static StyleUtil style( Widget widget ) {
    return new StyleUtil( widget );
  }

  public StyleUtil( Widget widget ) {
    this.widget = widget;
  }

  public StyleUtil font( String name, int size, int style ) {
    if( widget instanceof Control ) {
      Control control = ( Control )widget;
      control.setFont( new Font( widget.getDisplay(), name, size, style ) );
    }
    return this;
  }

  public void background( String string ) {
    if( "transparent".equals( string ) ) {
      as( "transparent" );
    } else {
      if( widget instanceof Control ) {
        Control control = ( Control )widget;
        control.setBackground( new Color( widget.getDisplay(), stringToRGB( string ) ) );
      }
    }
  }

  public void background( int red, int green, int blue ) {
    if( widget instanceof Control ) {
      Control control = ( Control )widget;
      control.setBackground( new Color( widget.getDisplay(), red, green, blue ) );
    }
  }

  public void color( String string ) {
    if( widget instanceof Control ) {
      Control control = ( Control )widget;
      control.setForeground( new Color( widget.getDisplay(), stringToRGB( string ) ) );
    }
  }

  public void as( String string ) {
    if( RWT.getClient() instanceof WebClient ) {
      widget.setData( RWT.CUSTOM_VARIANT, string );
    } else {
      Styler styler = getStyleMap().get( string );
      if( styler != null ) {
        styler.style( widget, new StyleUtil( widget ) );
      }
    }
  }

  private static RGB stringToRGB( String string ) {
    if( string.charAt( 0 ) != '#' || string.length() != 7 ) {
      throw new IllegalArgumentException( "Ivalid color " + string );
    }
    int red = Integer.valueOf( string.substring( 1, 3 ), 16 ).intValue();
    int green = Integer.valueOf( string.substring( 3, 5 ), 16 ).intValue();
    int blue = Integer.valueOf( string.substring( 5, 7 ), 16 ).intValue();
    RGB rgb = new RGB( red, green, blue );
    return rgb;
  }

  private static Map<String,Styler> getStyleMap(){
    if( styleMap == null ) {
      initStyleMap();
    }
    return styleMap;
  }

  private static void initStyleMap() {
    styleMap = new HashMap<String,Styler>();
    styleMap.put( "header", new Styler() {
      public void style( Widget control, StyleUtil style ) {
        style.background( "#31619c" );
      }
    } );
    styleMap.put( "headerLabel", new Styler() {
      public void style( Widget control, StyleUtil style ) {
        style.color( "#eeeeee" );
        style.font( "Open Sans, sans-serif", 10, SWT.ITALIC );
      }
    } );
    styleMap.put( "subheader", new Styler() {
      public void style( Widget control, StyleUtil style ) {
        style.background( 52, 51, 47 );
      }
    } );
  }

}
