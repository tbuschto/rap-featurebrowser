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

import org.eclipse.rap.featurebrowser.util.StyleUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;



public interface EnhancedTabFolder {

  public void addTab( String name, Image image, Control control );
  public Composite getFolder();
  public Control getControl();

  public class CTabFolderWrapper implements EnhancedTabFolder {

    private CTabFolder folder;

    public CTabFolderWrapper( Composite parent ) {
      folder = new CTabFolder( parent, SWT.TOP );
      folder.addSelectionListener( new SelectionAdapter() {
        @Override
        public void widgetSelected( SelectionEvent event ) {
          CTabItem[] items = folder.getItems();
          int selected = folder.getSelectionIndex();
          for( int i = 0; i < items.length; i++ ) {
            StyleUtil.style( items[ i ] ).as( i == selected ? "selected" : null );
          }
        }
      } );
    }

    public void addTab( String text, Image image, Control control ) {
      final CTabItem item = new CTabItem( folder, SWT.NONE );
      if( folder.getItemCount() == 1 ) {
        StyleUtil.style( item ).as( "selected" );
      }
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

    public Control getControl() {
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

    public Control getControl() {
      return folder;
    }

  }




}
