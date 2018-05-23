package com.dhytodev.cataloguemovie.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;

import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;

import android.util.Log;
import android.widget.Toast;

import com.dhytodev.cataloguemovie.R;
import com.dhytodev.cataloguemovie.data.model.Movie;
import com.dhytodev.cataloguemovie.data.network.MovieService;
import com.dhytodev.cataloguemovie.data.service.reminder.DailyAlarmReceiver;
import com.dhytodev.cataloguemovie.data.service.reminder.UpcomingAlarmReceiver;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by izadalab on 26/02/18.
 */

public class SettingsPreferenceFragment extends PreferenceFragment
        implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener, SettingsView {

    @BindString(R.string.key_setting_locale)
    String keySettingLocale;
    @BindString(R.string.key_reminder_daily)
    String keyDailyReminder;
    @BindString(R.string.key_reminder_upcoming)
    String keyUpcomingReminder;

    private SwitchPreference switchDailyReminder;
    private SwitchPreference switchUpcomingReminder;


    private SettingsPresenter<SettingsView, SettingsInteractor> presenter;
    private UpcomingAlarmReceiver alarmReceiver ;
    private List<Movie> movies = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        ButterKnife.bind(this, getActivity());

        alarmReceiver = new UpcomingAlarmReceiver();

        switchDailyReminder = (SwitchPreference) findPreference(keyDailyReminder);
        switchDailyReminder.setOnPreferenceChangeListener(this);
        switchUpcomingReminder = (SwitchPreference) findPreference(keyUpcomingReminder);
        switchUpcomingReminder.setOnPreferenceChangeListener(this);
        findPreference(keySettingLocale).setOnPreferenceClickListener(this);


        SettingsInteractor interactor = new SettingsInteractorImpl(MovieService.ServiceGenerator.instance());
        presenter = new SettingsPresenter<>(interactor, new CompositeDisposable());
        presenter.onAttach(this);
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
        boolean isOn = (boolean) o;

        DailyAlarmReceiver dailyAlarmReceiver = new DailyAlarmReceiver();

        if (key.equals(keyDailyReminder)) {
            if (isOn) {
                dailyAlarmReceiver.setRepeatingAlarm(getActivity());
            } else {
                dailyAlarmReceiver.cancelAlarm(getActivity());
            }
        } else {
            if (isOn) {
                presenter.setRepeatingAlarm();

            } else {
                presenter.cancelAlarm();

            }
        }

        return true;

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAlarm(Movie movie) {
        this.movies.clear();
        this.movies.add(movie);
        alarmReceiver.setRepeatingAlarm(getActivity(), movies);
    }

    @Override
    public void cancelAlarm() {
        alarmReceiver.cancelAlarm(getActivity());
    }
}
