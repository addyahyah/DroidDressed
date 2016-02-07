package edu.rosehulman.harrislb.droiddressed;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.OnLoginListener,
        ArticleListFragment.OnArticleSelectedListener,
        CategoryListFragment.OnCategorySelectedListener,
        ClosetFragment.ClosetCallback, OutfitpicListFragment.Callback,
        ArticleListFragment.ArticleCallback
         {

    private FloatingActionButton mFab;
    private Toolbar mToolbar;

    private Firebase mFirebaseRef;

    public FloatingActionButton getFab() {
        return mFab;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar);

        //mToolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(mToolbar);

        // Skipped during rotation, but Firebase settings should persist.
        if (savedInstanceState == null) {
            System.out.println("calling initialize firebase");
            initializeFirebase();
            System.out.println("successful initiailize");
        }

      //  mFab = (FloatingActionButton) findViewById(R.id.fab);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            Firebase.setAndroidContext(this);
        }
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
        if (mFirebaseRef.getAuth() == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, new LoginFragment());
            ft.commit();
        } else {
            onLoginComplete();
        }
    }

    private void initializeFirebase() {
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
        mFirebaseRef.keepSynced(true);
    }

    @Override
    public void onLoginComplete() {
        Log.d(Constants.TAG, "User is authenticated");
        String uid = mFirebaseRef.getAuth().getUid();
        SharedPreferencesUtils.setCurrentUser(this, uid);

        // Check if they have a current course
        String currentCatalogKey = SharedPreferencesUtils.getCurrentCourseKey(this);
        Fragment switchTo;
        if (currentCatalogKey == null || currentCatalogKey.isEmpty()) {
            switchTo = new ClosetFragment();
        } else {
            //switchTo = ArticleListFragment.newInstance(currentCourseKey);
            switchTo = ArticleListFragment.newInstance(currentCatalogKey);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, switchTo);
        ft.commit();
    }


//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        String currentCourseKey = SharedPreferencesUtils.getCurrentCourseKey(this);
//        int id = item.getItemId();
//        Fragment switchTo = null;
//
//
//// TODO: May be useful if I implement return to the chosen fragment after choosing a course.
//        if (id == R.id.nav_sign_out) {
//            Utils.signOut(this);
//            switchTo = new LoginFragment();
//        } else if (id == R.id.nav_courses || currentCourseKey == null) {
//            switchTo = new CourseListFragment();
//        } else if (id == R.id.nav_assignments) {
//            switchTo = AssignmentListFragment.newInstance(currentCourseKey);
//        } else if (id == R.id.nav_students) {
//            switchTo = new StudentListFragment();
//        } else if (id == R.id.nav_owners) {
//            switchTo = new OwnerListFragment();
//        }
//
//        if (switchTo != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.container, switchTo);
//            ft.commit();
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    @Override
    public void onArticleSelected(Article article) {
        // DONE: go to grade entry fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, ArticleListFragment.newInstance(article.getKey()));
        ft.addToBackStack("article_fragment");
        ft.commit();

    }

    @Override
    public void onCategorySelected(Category selectedCategory) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, ArticleListFragment.newInstance(selectedCategory.getKey()));
        ft.addToBackStack("category_fragment");
        ft.commit();
    }

             @Override
             public void onOutfitButtonSelected() {

             }

             @Override
             public void onArticlesButtonSelected() {
                 FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                 CategoryListFragment fragment = new CategoryListFragment();
                 ft.replace(R.id.fragment_container, fragment);
                 ft.addToBackStack("article");
                 ft.commit();
             }

             @Override
             public void onOutfitpicSelected(OutfitPic weatherPic) {

             }

//    @Override
//    public void onThisOwnerRemoved() {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_container, new ClosetFragment());
//        ft.commit();
//    }
}




//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.firebase.client.Firebase;
//
//public class MainActivity extends AppCompatActivity {
//
//    private Firebase mFirebaseRef;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button signInButton = (Button) findViewById(R.id.sign_in_button);
//        Button registerButton = (Button) findViewById(R.id.register_button);
//
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
//                startActivity(intent);
//
//            }
//
//        });
//
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//}


