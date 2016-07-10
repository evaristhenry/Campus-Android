package de.tum.in.tumcampusapp.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import de.tum.in.tumcampus.R;
import de.tum.in.tumcampusapp.models.managers.CafeteriaManager;
import de.tum.in.tumcampusapp.services.MensaWidgetService;


/**
 * Implementation of Mensa Widget functionality.
 * The Update intervals is set to 10 hours in mensa_widget_info.xml
 */
public class MensaWidget extends AppWidgetProvider {

    private RemoteViews rv;
    AppWidgetManager appWidgetManager;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        this.appWidgetManager = appWidgetManager;

        for (int i = 0; i < N; i++) {
            Intent intent = new Intent(context, MensaWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            rv = new RemoteViews(context.getPackageName(), R.layout.mensa_widget);

            // set the header for the Widget layout
            CafeteriaManager mensaManager = new CafeteriaManager(context);
            String mensaName = mensaManager.getBestMatchMensaName(context);
            rv.setTextViewText(R.id.mensa_widget_header, mensaName);

            // set the adapter for the list view in the mensaWidget
            rv.setRemoteAdapter(R.id.food_item, intent); //appWidgetIds[i],
            rv.setEmptyView(R.id.empty_view, R.id.empty_view);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}

