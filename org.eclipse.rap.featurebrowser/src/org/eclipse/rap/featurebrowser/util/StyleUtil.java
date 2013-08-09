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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Control;


public class StyleUtil {

  private Control control;

  public static StyleUtil style( Control control ) {
    return new StyleUtil( control );
  }

  public StyleUtil( Control control ) {
    this.control = control;
  }

  public StyleUtil font( String name, int size, int style ) {
    control.setFont( new Font( control.getDisplay(), name, size, style ) );
    return this;
  }

  public void as( String string ) {
    control.setData( RWT.CUSTOM_VARIANT, string );
  }



}
