package com.projects.vkotov.todotestapp.view;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.projects.vkotov.todotestapp.Prefs;
import com.projects.vkotov.todotestapp.R;
import com.projects.vkotov.todotestapp.model.Model;
import com.projects.vkotov.todotestapp.model.dto.ApiResponse;
import com.projects.vkotov.todotestapp.other.App;
import com.projects.vkotov.todotestapp.presenter.LoginPresenter;
import com.projects.vkotov.todotestapp.view.fragments.IView;
import com.projects.vkotov.todotestapp.view.fragments.LoginFragment;
import com.projects.vkotov.todotestapp.view.fragments.TodoListFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActivityCallback {

    @Inject
    Model model;

    private static String TAG = "TAG";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab)     FloatingActionButton fab;

    private FragmentManager fragmentManager;
    private Disposable dispose = Disposables.empty();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        App.getComponent().inject(this);
        fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (Prefs.getToken(this) == null)
            replaceFragment(new LoginFragment(), false);
        else
            startTodosFragment();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Adding todo is under developmment", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        fab.setVisibility(View.INVISIBLE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        log = findViewById(R.id.login);
//        pas = findViewById(R.id.password);
//        btn = findViewById(R.id.button);
//
////        presenter = new LoginPresenter(this);
//
//        btn.setOnClickListener(view -> presenter.onLoginClick());
    }

    private void replaceFragment(Fragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment, TAG);
        if (addBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

//        final MenuItem searchItem = menu.findItem(R.id.search);
//        searchItem.setVisible(false);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

/*        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else */if (id == R.id.logout) {
            RelativeLayout layout = findViewById(R.id.dlg);
            ProgressBar progressBar = new ProgressBar(MainActivity.this,null,android.R.attr.progressBarStyleLarge);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            layout.addView(progressBar,params);
            progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            model.signOut(Prefs.getToken(this))
                    .subscribe(new Observer<ApiResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            dispose = d;
                        }

                        @Override
                        public void onNext(ApiResponse response) {
                            Prefs.saveToken(getApplicationContext(), null);
                            replaceFragment(new LoginFragment(), false);
                            fab.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.GONE);     // To Hide ProgressBar
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            progressBar.setVisibility(View.GONE);     // To Hide ProgressBar
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Snackbar.make(fab, e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } /*else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        if (!dispose.isDisposed()) {
            dispose.dispose();
        }
        super.onStop();
    }
    //    @Override
//    public String getLogin() {
//        return log.getText().toString();
//    }
//
//    @Override
//    public String getPassword() {
//        return pas.getText().toString();
//    }

    @Override
    public void startTodosFragment() {
        replaceFragment(new TodoListFragment(), false);
        fab.setVisibility(View.VISIBLE);
    }
}
