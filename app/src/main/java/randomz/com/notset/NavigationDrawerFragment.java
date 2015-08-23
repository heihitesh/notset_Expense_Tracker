package randomz.com.notset;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
///$$$$ always import import android.support.v4.app.Fragment; this fragment package
//$$ and for toolbar  import android.support.v7.widget.Toolbar;

public class NavigationDrawerFragment extends Fragment {



    private RecyclerView recyclerView;

    private static final String PREF_FILE_NAME = "test";
    private static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mdrawerToogle;
    private DrawerLayout mDrawerlayout;
    private boolean mUserLearnedDrawer;  //indicates weather the user is aware of the drawer or not
    private boolean mFromSavedInstanceState; //indicates wheater the fragment is started for the very first time or orientation is changed

    private HitAdapter adapter;

    //////for facebook

    private TextView name;

    ProfilePictureView profileImage;
    private CallbackManager callbackManager;
    private AccessTokenTracker tracker;
    ProfileTracker profileTracker;

    private  View  ContainerView;


    //this metod will say is our login good or bad
    private FacebookCallback<LoginResult> mcallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile(); // will get the user profile


            DisplayWelcomeMessage(profile);



            profileImage.setProfileId(profile.getId());


        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //reading the value form the preferences
        mUserLearnedDrawer= Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        //1)context ,2)Name of the Pref ,3)default value

        if(savedInstanceState != null){ // this means that the app is comming back from a roatation
            mFromSavedInstanceState = true;
        }

        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.

        callbackManager = CallbackManager.Factory.create();

        //this will handel all the changes made in the facebook profile on the website
        tracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                DisplayWelcomeMessage(newProfile);
            }
        };

        tracker.startTracking();
        profileTracker.startTracking();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        //adding the recycler view to the java code to manupulate it
        //recycler view needs a layoutmanager
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new HitAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  //very important this sets the layout of the list to be vertical Linear layout

        return layout;
        // return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

    }


    public List<Information> getData() {
        //load only static data inside a drawer
        List<Information> data = new ArrayList<>();
        int[] icons = {R.drawable.gmail, R.drawable.drive,R.drawable.phone};
        String[] titles = {"Gmail","Drive","Phone"};
        for (int i = 0; i < titles.length; i++) {
            Information information = new Information();
            information.title = titles[i];
            information.iconId = icons[i];
            data.add(information);
        }
        return data;
    }


    public void setUp(int FragmentID ,DrawerLayout drawerLayout, final Toolbar toolbar) {
        ContainerView = getActivity().findViewById(FragmentID);

        mDrawerlayout = drawerLayout;
        mdrawerToogle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){ // if the user has never seen the navigationdrawer
                    mUserLearnedDrawer = true;
                    savedToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");

                }
                getActivity().invalidateOptionsMenu(); // it will partially hide the actionbar

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu(); // it will partially hide the actionbar
            }
            // this will change the alpha of the action bar acc to the slide

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if(slideOffset<0.6){
                    toolbar.setAlpha(1-slideOffset);
                }
            }
        };
        //takes the parameter Activity , Layout, toolbar , open and close

        if(!mUserLearnedDrawer) // the user has never seen the drawer and if it isthe veryfirst time in that case display the drawer
        {
            mDrawerlayout.openDrawer(ContainerView);
        }

        //implemnt the listner veryImportant
        mDrawerlayout.setDrawerListener(mdrawerToogle);

        mDrawerlayout.post(new Runnable() { // this method is for the HAMBURGER SYMBOL

            @Override
            public void run() {
                mdrawerToogle.syncState();
            }
        });
    }

    public static void savedToPreferences(Context context, String preferencesName, String preferenceValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(preferencesName, preferenceValue);
        editor.apply(); // using apply() instead of commit(); ,because its much more fast
    }

    public static String readFromPreferences(Context context, String preferencesName, String preferenceValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        return sharedPref.getString(preferencesName, preferenceValue);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = (TextView) view.findViewById(R.id.tvDetails);

        profileImage = (ProfilePictureView) view.findViewById(R.id.profilePicture);
        LoginButton button = (LoginButton) view.findViewById(R.id.login_button);

        button.setReadPermissions("user_friends"); //get the permission
        button.setFragment(this);
        button.registerCallback(callbackManager, mcallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void DisplayWelcomeMessage(Profile profile) {
        if (profile != null) {
            name.setText("Welcome: " + profile.getName());
            profileImage.setProfileId(profile.getId());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        DisplayWelcomeMessage(profile);
    }

    @Override
    public void onStop() {
        super.onStop();
        tracker.stopTracking();
        profileTracker.stopTracking();
    }
}
