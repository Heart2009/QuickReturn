package com.etiennelawlor.quickreturn.fragments;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.etiennelawlor.quickreturn.R;
import com.etiennelawlor.quickreturn.utils.QuickReturnUtils;

/**
 * Created by etiennelawlor on 6/23/14.
 */
public class QuickReturnFooterListFragment extends ListFragment {

    // region Constants
    // endregion

    // region Member Variables
    private ListView mListView;
    private TextView mQuickReturnTextView;
    private String[] mValues;
    private int mMinFooterTranslation;
    private int mFooterHeight;
    private View mPlaceHolderView;
    private int mPrevScrollY = 0;
    private int mDiffTotal = 0;
    private TranslateAnimation mAnim;
    // endregion

    //region Listeners

    private AbsListView.OnScrollListener mListViewOnScrollListener = new AbsListView.OnScrollListener() {
        @SuppressLint("NewApi")
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

            int scrollY = QuickReturnUtils.getScrollY(mListView);
            int diff = mPrevScrollY - scrollY;

            if(diff <=0){ // scrolling down
                mDiffTotal = Math.max(mDiffTotal + diff, -mMinFooterTranslation);
            } else { // scrolling up
                mDiffTotal = Math.min(Math.max(mDiffTotal + diff, -mMinFooterTranslation), 0);
            }

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                mAnim = new TranslateAnimation(0, 0, -mDiffTotal,
                        -mDiffTotal);
                mAnim.setFillAfter(true);
                mAnim.setDuration(0);
                mQuickReturnTextView.startAnimation(mAnim);
            } else {
                mQuickReturnTextView.setTranslationY(-mDiffTotal);
            }

            mPrevScrollY = scrollY;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    };
    //endregion

    // region Constructors
    public static QuickReturnFooterListFragment newInstance() {
        QuickReturnFooterListFragment fragment = new QuickReturnFooterListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public QuickReturnFooterListFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFooterHeight = getResources().getDimensionPixelSize(R.dimen.footer_height);
        mMinFooterTranslation = mFooterHeight;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_quick_return_footer, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindUIElements(view);

        mValues = getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item, R.id.item_tv, mValues);

        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(mListViewOnScrollListener);

//        mListView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch(event.getAction())
//                {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.d("QuickReturnFooterListFragment", "onTouch() : ACTION_DOWN");
////                        v.setBackgroundColor(R.color.black);
////                        return true;
//                    case MotionEvent.ACTION_CANCEL:
//                        Log.d("QuickReturnFooterListFragment", "onTouch() : ACTION_CANCEL");
//
//                    case MotionEvent.ACTION_OUTSIDE:
//                        Log.d("QuickReturnFooterListFragment", "onTouch() : ACTION_OUTSIDE");
//
////                        v.setBackgroundDrawable(null);
////                        return true;
//                    case MotionEvent.ACTION_UP:
//                        Log.d("QuickReturnFooterListFragment", "onTouch() : ACTION_UP : mDiffTotal - "+mDiffTotal);
//                        Log.d("QuickReturnFooterListFragment", "onTouch() : mFooterHeight - "+mFooterHeight);
//
//
//                        if(-mDiffTotal <= mFooterHeight/2){
//                            Log.d("QuickReturnFooterListFragment", "onTouch() : slide up");
////                            mQuickReturnTextView.startAnimation(-mDiffTotal);
//
////                            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_footer_down);
////                            mQuickReturnTextView.startAnimation(animation);
//
//
////                            mQuickReturnTextView.setTranslationY(0);
//
////                            mAnim = new TranslateAnimation(0, 0, 0,
////                                    0);
////                            mAnim.setFillAfter(true);
////                            mAnim.setDuration(2000);
////                            mQuickReturnTextView.startAnimation(mAnim);
//                        } else {
////                            mQuickReturnTextView.setTranslationY(mFooterHeight);
//
//                            mAnim = new TranslateAnimation(0, 0, mFooterHeight,
//                                    mFooterHeight);
//                            mAnim.setFillAfter(true);
//                            mAnim.setDuration(2000);
//                            mQuickReturnTextView.startAnimation(mAnim);
//
//                            Log.d("QuickReturnFooterListFragment", "onTouch() : slide down");
//
//                        }
//
////                        v.setBackgroundDrawable(null);
////                        Intent myIntent = new Intent(v.getContext(), SearchActivity.class);
////                        startActivity(myIntent);
//
////                        return true;
//                }
//                return false;
//            }
//
//
//        });
    }

    // endregion

    // region Helper Methods
    private void bindUIElements(View view){
        mListView = (ListView) view.findViewById(android.R.id.list);
        mQuickReturnTextView = (TextView) view.findViewById(R.id.quick_return_tv);
    }
    // endregion
}
