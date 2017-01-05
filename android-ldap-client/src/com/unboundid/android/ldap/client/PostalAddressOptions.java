/*
 * Copyright 2009-2017 UnboundID Corp.
 * All Rights Reserved.
 */
/*
 * Copyright (C) 2009-2017 UnboundID Corp.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPLv2 only)
 * or the terms of the GNU Lesser General Public License (LGPLv2.1 only)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 */
package com.unboundid.android.ldap.client;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static com.unboundid.android.ldap.client.Logger.*;



/**
 * This class provides an Android activity that may be used to display a set
 * of options to perform on a postal address.  It includes the ability to view
 * a map of that address or copy the address to the clipboard.
 */
public final class PostalAddressOptions
       extends Activity
       implements OnItemClickListener
{
  /**
   * The name of the field used to provide the e-mail address.
   */
  public static final String BUNDLE_FIELD_POSTAL_ADDRESS = "POSTAL_ADDRESS";



  /**
   * The tag that will be used for log messages generated by this class.
   */
  private static final String LOG_TAG = "PostalAddressOptions";



  // The postal address that was selected.
  private volatile String address = null;



  /**
   * Performs all necessary processing when this activity is started or resumed.
   */
  @Override()
  protected void onResume()
  {
    logEnter(LOG_TAG, "onResume");

    super.onResume();

    setContentView(R.layout.layout_popup_menu);
    setTitle(R.string.activity_label);


    // Get the phone number for this
    final Intent intent = getIntent();
    final Bundle extras = intent.getExtras();

    address = (String) extras.getSerializable(BUNDLE_FIELD_POSTAL_ADDRESS);
    setTitle(getString(R.string.postal_address_options_title, address));


    // Populate the list of options.
    final String[] options =
    {
      getString(R.string.postal_address_options_option_map),
      getString(R.string.postal_address_options_option_navigate),
      getString(R.string.postal_address_options_option_copy)
    };

    final ListView optionList =
         (ListView) findViewById(R.id.layout_popup_menu_list_view);
    final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
         android.R.layout.simple_list_item_1, options);
    optionList.setAdapter(listAdapter);
    optionList.setOnItemClickListener(this);
  }



  /**
   * Takes any appropriate action after a list item was clicked.
   *
   * @param  parent    The list containing the item that was clicked.
   * @param  item      The item that was clicked.
   * @param  position  The position of the item that was clicked.
   * @param  id        The ID of the item that was clicked.
   */
  public void onItemClick(final AdapterView<?> parent, final View item,
                          final int position, final long id)
  {
    logEnter(LOG_TAG, "onItemClick", parent, item, position, id);

    // Figure out which item was clicked and take the appropriate action.
    switch (position)
    {
      case 0:
        doMap();
        break;
      case 1:
        doNavigate();
        break;
      case 2:
        doCopy();
        break;
      default:
        break;
    }
    finish();
  }



  /**
   * Invokes the maps application to display the associated address.
   */
  private void doMap()
  {
    logEnter(LOG_TAG, "doMap");

    final Intent i = new Intent(Intent.ACTION_VIEW,
         Uri.parse("geo:0,0?q=" + address));
    startActivity(i);
  }



  /**
   * Invokes the maps application to navigate to the associated address.
   */
  private void doNavigate()
  {
    logEnter(LOG_TAG, "doNavigate");

    final Intent i = new Intent(Intent.ACTION_VIEW,
         Uri.parse("google.navigation:q=" + address));
    startActivity(i);
  }



  /**
   * Copies the postal address to the clipboard.
   */
  private void doCopy()
  {
    logEnter(LOG_TAG, "doCopy");

    final ClipboardManager clipboard =
         (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    clipboard.setText(address);
  }
}
