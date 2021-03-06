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

package org.eclipse.rap.demo.samples.util;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

public class GridLayoutUtil {

  private final GridLayout gridLayout;

  private GridLayoutUtil( GridLayout gridLayout ) {
    this.gridLayout = gridLayout;
  }

  public static GridLayoutUtil applyGridLayout( Composite composite ) {
    GridLayout gridLayout = new GridLayout();
    gridLayout.marginHeight = 0;
    gridLayout.marginWidth = 0;
    gridLayout.verticalSpacing = 0;
    gridLayout.horizontalSpacing = 0;
    composite.setLayout( gridLayout );
    return new GridLayoutUtil( gridLayout );
  }

  public static GridLayoutUtil onGridLayout( Composite composite ) {
    Layout layout = composite.getLayout();
    if( layout instanceof GridLayout ) {
      return new GridLayoutUtil( ( GridLayout )layout );
    }
    throw new IllegalStateException( "Composite has to have a GridLayout. Has " + layout );
  }

  public GridLayoutUtil cols( int numColumns ) {
    gridLayout.numColumns = numColumns;
    return this;
  }

  public GridLayoutUtil equalColumns() {
    gridLayout.makeColumnsEqualWidth = true;
    return this;
  }

  public GridLayoutUtil horizontalSpacing( int horizontalSpacing ) {
    gridLayout.horizontalSpacing = horizontalSpacing;
    return this;
  }

  public GridLayoutUtil verticalSpacing( int verticalSpacing ) {
    gridLayout.verticalSpacing = verticalSpacing;
    return this;
  }

  public GridLayoutUtil marginWidth( int marginWidth ) {
    gridLayout.marginWidth = marginWidth;
    return this;
  }

  public GridLayoutUtil margins( int marginTop, int marginRight, int marginBottom, int marginLeft ) {
    gridLayout.marginTop = marginTop;
    gridLayout.marginRight = marginRight;
    gridLayout.marginBottom = marginBottom;
    gridLayout.marginLeft = marginLeft;
    return this;
  }

  public GridLayoutUtil marginHeight( int marginHeight ) {
    gridLayout.marginHeight = marginHeight;
    return this;
  }

  public GridLayoutUtil marginTop( int marginTop ) {
    gridLayout.marginTop = marginTop;
    return this;
  }

  public GridLayoutUtil marginBottom( int marginBottom ) {
    gridLayout.marginBottom = marginBottom;
    return this;
  }

  public GridLayoutUtil marginLeft( int marginLeft ) {
    gridLayout.marginLeft = marginLeft;
    return this;
  }

  public GridLayoutUtil marginRight( int marginRight ) {
    gridLayout.marginRight = marginRight;
    return this;
  }

  public GridLayoutUtil margin( int margin ) {
    gridLayout.marginTop = margin;
    gridLayout.marginRight = margin;
    gridLayout.marginBottom = margin;
    gridLayout.marginLeft = margin;
    return this;
  }

  public GridLayoutUtil spacing( int spacing ) {
    gridLayout.verticalSpacing = spacing;
    gridLayout.horizontalSpacing = spacing;
    return this;
  }

}
