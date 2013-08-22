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
package org.eclipse.rap.featurebrowser.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;



public interface EnhancedTabFolder {

  public void addTab( String name, Image image, Control control );
  public Composite getFolder();

  public class CTabFolderWrapper implements EnhancedTabFolder {

    private CTabFolder folder;

    public CTabFolderWrapper( Composite parent ) {
      folder = new CTabFolder( parent, SWT.TOP );
      folder.addCTabFolder2Listener( new CTabFolder2Adapter() {
        @Override
        public void close( CTabFolderEvent event ) {
          event.doit = false; // the X is just for show to make it look more like Eclipse
        }
      } );
    }

    public void addTab( String text, Image image, Control control ) {
      final CTabItem item = new CTabItem( folder, SWT.CLOSE );
      item.setControl( control );
      item.setImage( image );
      item.setText( text );
      if( folder.getItemCount() == 1 ) {
        folder.setSelection( 0 );
      }
    }

    public Composite getFolder() {
      return folder;
    }

  }

  public static class TabFolderWrapper implements EnhancedTabFolder {

    private TabFolder folder;

    public TabFolderWrapper( Composite parent ) {
      folder = new TabFolder( parent, SWT.TOP );
    }

    public void addTab( String text, Image image, Control control ) {
      TabItem item = new TabItem( folder, SWT.NONE );
      item.setText( text );
      item.setImage( image );
      item.setControl( control );
    }

    public Composite getFolder() {
      return folder;
    }

  }



}
