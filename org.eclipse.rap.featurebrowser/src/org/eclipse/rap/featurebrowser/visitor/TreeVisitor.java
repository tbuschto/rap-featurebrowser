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
package org.eclipse.rap.featurebrowser.visitor;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;


public abstract class TreeVisitor {

  public static void forEach( Tree tree, TreeVisitor visitor ) {
    TreeItem[] items = tree.getItems();
    for( TreeItem item : items ) {
      visitor.visit( item );
      forEach( item, visitor );
    }
  }

  private static void forEach( TreeItem parent, TreeVisitor visitor ) {
    TreeItem[] items = parent.getItems();
    for( TreeItem item : items ) {
      visitor.visit( item );
      forEach( item, visitor );
    }
  }

  public abstract void visit( TreeItem item );

}
