package edu.rosehulman.harrislb.droiddressed;

import android.content.Context;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

/**
 * Created by harrislb on 1/25/2016.
 */
public class Util {

    public static String randomImageUrl() {
        String[] urls = new String[]{

                "http://www.polyvore.com/cgi/img-thing?.out=jpg&size=l&tid=74799381",
                "http://bmodish.com/wp-content/uploads/2015/03/floral-zara-shirt-blouses-chic-office-style-bmodish.jpg",
                "https://s-media-cache-ak0.pinimg.com/236x/c8/dd/bd/c8ddbd17d7222703562dfb51fd0f5075.jpg",
                "http://www.stylishwife.com/wp-content/uploads/2014/08/Stylish-Fall-Outfits-For-Women-21.jpg",
                "http://www.stylishwife.com/wp-content/uploads/2014/08/Cool-Winter-Outfits-for-2014-36.jpg",
                "http://stylesweekly.com/wp-content/uploads/2014/11/Chic-Winter-Outfi-Idea-for-2015.jpg",
                "http://i0.wp.com/fmag.com/wp-content/uploads/2014/11/sweater-dress-12.jpg?fit=1024,1024",
                "https://s-media-cache-ak0.pinimg.com/236x/85/92/49/8592492f641c1735276e82317288c80e.jpg",
                "http://aelida.com/wp-content/uploads/2013/12/parka-coat-outfit.jpg",
                "http://glamradar.com/wp-content/uploads/2014/09/army-green-parka.jpg",

        };
        return urls[(int) (Math.random() * urls.length)];
    }

    public static String randomArticleUrl() {
        String[] urls = new String[]{

                "https://s-media-cache-ak0.pinimg.com/236x/8b/8c/d7/8b8cd736db55f33a98657a6cf7465b58.jpg",
                "http://s7d5.scene7.com/is/image/ColumbiaSportswear2/AL6009_508_f?$COL_grid$",
                "http://www.polyvore.com/cgi/img-thing?.out=jpg&size=l&tid=82441424",
                "http://g02.a.alicdn.com/kf/HTB1azndIFXXXXapXpXXq6xXFXXXU/East-Knitting-SW-013-long-sleeve-oversized-font-b-sweaters-b-font-for-font-b-women.jpg",
                "https://img1.etsystatic.com/041/0/5668685/il_340x270.591000313_ms5r.jpg",
                "http://maur.imageg.net/graphics/product_images/pMAUR1-18623551v275.jpg?01AD=3yhKEF_IGzmXNoNpmDceW9Oh1xA_mD48T0Tdjc4KoUpAjqMHnzHyK_g&01RI=7C6EDC235384F66&01NA=",
                "http://www.bananarepublic.com/products/res/mainimg/sloan-fit-charcoal-slim-ankle-pant-2.jpg",
                "http://www.polyvore.com/cgi/img-thing?.out=jpg&size=l&tid=88156818",
                "http://www.polyvore.com/cgi/img-thing?.out=jpg&size=l&tid=65330750",



        };
        return urls[(int) (Math.random() * urls.length)];
    }

    public static void removeCategory(Context context, Category category) {
        // MB: Moved to first to try to speed up UI. Test for race conditions.
        // Removes from list of courses
        Firebase courseRef = new Firebase(Constants.CATEGORY_PATH + "/" + category.getKey());
        courseRef.removeValue();

        // Remove this course from all its owners.
        Firebase ownersRef = new Firebase(Constants.OWNERS_PATH);
        for (String uid : category.getOwners().keySet()) {
            ownersRef.child(uid).child(Owner.CATEGORIES).child(category.getKey()).removeValue();
        }

        // CONSIDER: Remove all students associated with this course

        //TODO fixthis
        // Remove all assignments
//        final Firebase assignmentsRef = new Firebase(Constants.ARTICLES_PATH);
//        Query assignmentsForCourseRef = assignmentsRef.orderByChild(Article.CATEGORY_KEY).equalTo(category.getKey());
//        assignmentsForCourseRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    assignmentsRef.child(snapshot.getKey()).removeValue();
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Log.d(Constants.TAG, "Cancelled");
//            }
//        });

        // CONSIDER: Remove all grade entries associated with this course


        // Remove from SharedPrefs
        // MB: CONSIDER What if we aren't removing the current course?
        SharedPreferencesUtils.removeCurrentCourseKey(context);
    }

    public static void removeOutfitCategory(Context context, OutfitCategory category) {

    }

        public static void removeArticle(Context context, Article article) {
        //TODO hande delete
    }



}