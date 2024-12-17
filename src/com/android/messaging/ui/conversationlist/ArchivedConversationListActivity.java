/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.messaging.ui.conversationlist;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import com.android.messaging.R;

public class ArchivedConversationListActivity extends AbstractConversationListActivity
        implements FragmentOnAttachListener {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ConversationListFragment fragment =
                ConversationListFragment.createArchivedConversationListFragment();
        getSupportFragmentManager().addFragmentOnAttachListener(this);
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        invalidateActionBar();
    }

    protected void updateActionBar(ActionBar actionBar) {
        actionBar.setTitle(getString(R.string.archived_activity_title));
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(
                getResources().getColor(
                        R.color.archived_conversation_action_bar_background_color_dark)));
        actionBar.show();
        super.updateActionBar(actionBar);
    }

    @Override
    public void onBackPressed() {
        if (isInConversationListSelectMode()) {
            exitMultiSelectState();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onActionBarHome();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onActionBarHome() {
        onBackPressed();
    }

    @Override
    public boolean isSwipeAnimatable() {
        return false;
    }

    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager,
                                 @NonNull Fragment fragment) {
        if (fragment instanceof ConversationListFragment) {
            mConversationListFragment = (ConversationListFragment) fragment;
            mConversationListFragment.setHost(this);
        }
    }
}
