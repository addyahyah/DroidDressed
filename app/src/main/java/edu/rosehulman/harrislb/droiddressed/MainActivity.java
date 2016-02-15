package edu.rosehulman.harrislb.droiddressed;


import android.app.FragmentManager;
import android.content.Intent;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        LoginFragment.OnLoginListener,
        CategoryListFragment.OnCategorySelectedListener,
        ClosetFragment.ClosetCallback, OutfitpicListFragment.Callback,
        ArticleListFragment.OnArticleSelectedListener
         {

    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private ArrayList<Article> previewArticles;
    private Firebase mFirebaseRef;
private Fragment fragment;

    public FloatingActionButton getFab() {
        return mFab;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public ArrayList<Article> getPreviewArticles(){
        return this.previewArticles;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String s1 = intent.getStringExtra("Check");


        if(s1!= null && s1.equals("1")){
            System.out.println("Got check");
            s1="";
            Bundle bundle = new Bundle();
            bundle.putString("UPLOAD_URL", intent.getStringExtra("UPLOAD_URL"));
            bundle.putString("CURRENT_CATEGORY", intent.getStringExtra("CURRENT_CATEGORY"));
            String currentCategoryKey = SharedPreferencesUtils.getCurrentCategoryKey(this);
            Fragment fragment = ArticleListFragment.newInstance(currentCategoryKey);
            fragment.setArguments(bundle);
            this.fragment = fragment;

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.fragment_container, fragment);
//            ft.commit();
        }
        else{
            //should only be called the very first time activity launches
            initializeFirebaseOnce();
        }



        System.out.println("Main's on create called");
        setContentView(R.layout.app_bar);

        //mToolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(mToolbar);

        // Skipped during rotation, but Firebase settings should persist.
        if (savedInstanceState == null) {
            System.out.println("calling initialize firebase");
            reInitializeFirebase();
            System.out.println("successful initiailize");
        }

        previewArticles = new ArrayList<Article>();

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

             //top two can only be called once per app
    private void initializeFirebaseOnce() {
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
        mFirebaseRef.keepSynced(true);
    }

             private void reInitializeFirebase() {
                 mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
                 mFirebaseRef.keepSynced(true);
             }


    @Override
    public void onLoginComplete() {
        Log.d(Constants.TAG, "User is authenticated");
        String uid = mFirebaseRef.getAuth().getUid();
        SharedPreferencesUtils.setCurrentUser(this, uid);

        // Check if they have a current course
        String currentCategoryKey = SharedPreferencesUtils.getCurrentCategoryKey(this);
        Fragment switchTo;
        if (currentCategoryKey == null || currentCategoryKey.isEmpty()) {
            switchTo = new ClosetFragment();
        } else if(fragment!=null) {
            switchTo = fragment;
            fragment = null;
            }else{
            switchTo = ArticleListFragment.newInstance(currentCategoryKey);
           // switchTo = new ClosetFragment();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, switchTo);
        ft.commit();
    }

             public void onBackPressed() {
                 if (getFragmentManager().getBackStackEntryCount() == 0) {
                     FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                     ft.replace(R.id.fragment_container, new ClosetFragment());
                     ft.commit();
                 } else {
                     super.onBackPressed();
                 }
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
    public boolean onArticleSelected(Article article) {
        // DONE: go to grade entry fragment
        //TODO if you want a new Fragment to appear
//        System.out.println("onArticleSelected called");
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_container, ArticleDetailFragment.newInstance(article.getKey()));
//        ft.addToBackStack("article_fragment");
//        ft.commit();

        if(previewArticles.contains(article)){
            previewArticles.remove(article);
            if(previewArticles.size()==0){
                return false;
            }
        }
        else{
            previewArticles.add(article);

        }
        return true;

    }

             @Override
             public boolean isPreviewButtonVisible() {
                 if(previewArticles.size()==0){
                     return false;
                 }
                 else{
                     return true;
                 }
             }

             @Override
    public void onCategorySelected(Category selectedCategory) {
        System.out.println("Category selected. key is: " + selectedCategory.getKey());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, ArticleListFragment.newInstance(selectedCategory.getKey()));
        ft.addToBackStack("category_fragment");
        ft.commit();



    }

             @Override
             public boolean isPreviewButtonVisibleCategoryFragment() {
                 if(previewArticles.size()==0) {
                     return false;
                 }

                 else{
                     return true;
                 }
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

             public void resetPreviewArticles(){
                 this.previewArticles = new ArrayList<Article>();
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


