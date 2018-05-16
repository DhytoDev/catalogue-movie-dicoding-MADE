package com.dhytodev.cataloguemovie.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.dhytodev.cataloguemovie.R;
import com.dhytodev.cataloguemovie.data.service.reminder.DailyAlarmReceiver;

import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by izadalab on 26/02/18.
 */

public class SettingsPreferenceFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    @BindString(R.string.key_setting_locale)
    String keySettingLocale;
    @BindString(R.string.key_reminder_daily)
    String keyDailyReminder;
    @BindString(R.string.key_reminder_upcoming)
    String keyUpcomingReminder;

    private SwitchPreference switchDailyReminder ;
    private SwitchPreference switchUpcomingReminder ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        ButterKnife.bind(this, getActivity());

        switchDailyReminder = (SwitchPreference) findPreference(keyDailyReminder);
        switchDailyReminder.setOnPreferenceChangeListener(this);
        switchUpcomingReminder = (SwitchPreference) findPreference(keyUpcomingReminder);
        switchUpcomingReminder.setOnPreferenceChangeListener(this);
        findPreference(keySettingLocale).setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();

        if (key.equals(keySettingLocale)) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
            return true;
        }

        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String key = preference.getKey();

        DailyAlarmReceiver dailyAlarmReceiver = new DailyAlarmReceiver();

        if (key.equals(keyDailyReminder)) {
            if (switchDailyReminder.isChecked()) {
                Toast.makeText(getActivity(), "Unchecked", Toast.LENGTH_SHORT).show();
                switchDailyReminder.setChecked(false);
                dailyAlarmReceiver.cancelAlarm(getActivity());
                return false ;
            } else {
                Toast.makeText(getActivity(), "Checked", Toast.LENGTH_SHORT).show();
                switchDailyReminder.setChecked(true);
                dailyAlarmReceiver.setRepeatingAlarm(getActivity());
                return true ;
            }
        } else {
            if (switchUpcomingReminder.isChecked()) {
                Toast.makeText(getActivity(), "Unchecked", Toast.LENGTH_SHORT).show();
                switchUpcomingReminder.setChecked(false);
                return false ;
            } else {
                Toast.makeText(getActivity(), "Checked", Toast.LENGTH_SHORT).show();
                switchUpcomingReminder.setChecked(true);
                return true ;
            }
        }
    }
}
