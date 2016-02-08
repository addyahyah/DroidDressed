//package edu.rosehulman.harrislb.droiddressed;
//
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.Button;
//
//import com.firebase.client.Firebase;
//
//public class HomeActivity extends AppCompatActivity implements OutfitpicListFragment.Callback, ClosetFragment.ClosetCallback, ArticleListFragment.ArticleCallback,
//CategoryListFragment.OnCategorySelectedListener{
////
////    private Button outfitButton;
////    private Button articlesButton;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_home);
////
////        outfitButton = (Button) findViewById(R.id.outfit_button);
////        articlesButton = (Button) findViewById(R.id.articles_button);
////
////    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_container, new ClosetFragment());
//        int nEntries = getSupportFragmentManager().getBackStackEntryCount();
//        for(int i = 0; i<nEntries; i++){
//            getSupportFragmentManager().popBackStackImmediate();
//        }
//
//        ft.commit();
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onOutfitpicSelected(OutfitPic outfitPic){
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        OutfitpicDetailFragment fragment = OutfitpicDetailFragment.newInstance(outfitPic);
//        ft.replace(R.id.fragment_container, fragment);
//        ft.addToBackStack("detail");
//        ft.commit();
//    }
//
//    @Override
//    public void onOutfitButtonSelected(){
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        OutfitpicListFragment fragment = new OutfitpicListFragment();
//        ft.replace(R.id.fragment_container, fragment);
//        ft.addToBackStack("outfit");
//        ft.commit();
//    }
//
//    @Override
//    public void onArticlesButtonSelected() {
//        //old way
////        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////        ArticleListFragment fragment = new ArticleListFragment();
////        ft.replace(R.id.fragment_container, fragment);
////        ft.addToBackStack("article");
////        ft.commit();
//
//        //new way
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        CategoryListFragment fragment = new CategoryListFragment();
//        ft.replace(R.id.fragment_container, fragment);
//        ft.addToBackStack("article");
//        ft.commit();
//    }
//
//
//    @Override
//    public boolean onArticleSelected(Article article) {
//        return true;
//    }
//
//    @Override
//    public void onCategorySelected(Category selectedCategory) {
//
//    }
//}
