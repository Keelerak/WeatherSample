package com.example.andrey.weather;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    public static final String TAG = "SearchActivity";

    private final String APP_SETTINGS_CITY = "city";
    private final String APP_SETTINGS_COUNTRY_CODE = "country_code";
    private final String APP_SETTINGS_LATITUDE = "latitude";
    private final String APP_SETTINGS_LONGITUDE = "longitude";

    private List<CitySearch> mCites;
    private SearchCityAdapter mSearchCityAdapter;
    //private SharedPreferences mCityPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        setupSearchView();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        mCites = new ArrayList<>();
        mSearchCityAdapter = new SearchCityAdapter(mCites);
        recyclerView.setAdapter(mSearchCityAdapter);
    }

    private void setupSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchCityAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchCityAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private class SearchCityHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        //private CitySearch mCity;
        private TextView mCityName;
        private TextView mCountryName;
        private String temp;// = String.valueOf(1);

        SearchCityHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mCityName = (TextView) itemView.findViewById(R.id.city_name);
            mCountryName = (TextView) itemView.findViewById(R.id.country_code);
        }

        void bindCity(CitySearch city) {
            mCityName.setText(city.getCityName());
            mCountryName.setText(city.getCountryCode());
            temp = String.valueOf(Math.round(city.getTemperature()- 273.15));
        }

        @Override
        public void onClick(View v) {
            v.setBackgroundColor(Color.rgb(227, 227, 227));
            Intent intent33 = new Intent();
            intent33.putExtra(Constants.FIRST_NAME, mCityName.getText().toString());
            intent33.putExtra(Constants.COUNTRY_NAME, mCountryName.getText().toString());
            intent33.putExtra(Constants.TEMPERATURE, temp);
            setResult(RESULT_OK, intent33);
            finish();
        }
    }
    private class SearchCityAdapter extends RecyclerView.Adapter<SearchCityHolder>
            implements Filterable {

        private List<CitySearch> mCites;

        SearchCityAdapter(List<CitySearch> cites) {
            mCites = cites;
        }

        @Override
        public int getItemCount() {
            if (mCites != null)
                return mCites.size();

            return 0;
        }

        @Override
        public void onBindViewHolder(SearchCityHolder holder, int position) {
            CitySearch city = mCites.get(position);
            holder.bindCity(city);
        }

        @Override
        public SearchCityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(SearchActivity.this);
            View v = inflater.inflate(R.layout.city_item, parent, false);

            return new SearchCityHolder(v);
        }

        @Override
        public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults = new FilterResults();

                    List<CitySearch> citySearchList = CityParser.getCity(charSequence.toString());
                    filterResults.values = citySearchList;
                    filterResults.count = citySearchList != null ? citySearchList.size() : 0;

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence,
                                              FilterResults filterResults) {
                    mCites.clear();
                    if (filterResults.values != null) {
                        mCites.addAll((ArrayList<CitySearch>) filterResults.values);
                    }
                    notifyDataSetChanged();
                }
            };
        }
    }
}
