/*
 * Copyright (C) 2020 The Android Open Source Project
 * Copyright (C) 2022 Paranoid Android
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
package co.aospa.systemui.volume.dagger;

import android.content.Context;
import android.media.AudioManager;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.volume.VolumeComponent;
import com.android.systemui.volume.VolumeDialogImpl;
import com.android.systemui.volume.VolumePanelFactory;
import com.android.systemui.volume.dagger.VolumeModule;

import co.aospa.systemui.tristate.dagger.TriStateModule;
import co.aospa.systemui.volume.ParanoidVolumeDialogComponent;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger Module for code in the volume package.
 * Fork of {@link VolumeModule}
 */
@Module(includes = {TriStateModule.class})
public interface ParanoidVolumeModule {
    /** */
    @Binds
    VolumeComponent provideVolumeComponent(ParanoidVolumeDialogComponent volumeDialogComponent);

    /** */
    @Provides
    static VolumeDialog provideVolumeDialog(
            Context context,
            VolumeDialogController volumeDialogController,
            AccessibilityManagerWrapper accessibilityManagerWrapper,
            DeviceProvisionedController deviceProvisionedController,
            ConfigurationController configurationController,
            MediaOutputDialogFactory mediaOutputDialogFactory,
            VolumePanelFactory volumePanelFactory,
            ActivityStarter activityStarter,
            InteractionJankMonitor interactionJankMonitor,
            DumpManager dumpManager) {
        VolumeDialogImpl impl = new VolumeDialogImpl(
                context,
                volumeDialogController,
                accessibilityManagerWrapper,
                deviceProvisionedController,
                configurationController,
                mediaOutputDialogFactory,
                volumePanelFactory,
                activityStarter,
                interactionJankMonitor,
                dumpManager);
        impl.setStreamImportant(AudioManager.STREAM_SYSTEM, false);
        impl.setAutomute(true);
        impl.setSilentMode(false);
        return impl;
    }
}
