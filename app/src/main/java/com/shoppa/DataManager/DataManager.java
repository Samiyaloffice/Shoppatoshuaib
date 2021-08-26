package com.shoppa.DataManager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.shoppa.Model.BuyerModel;
import com.shoppa.Model.HomeCategoryModel;
import com.shoppa.Model.UserDataModel;
import com.shoppa.NetworkCallManager.ApiManager;
import com.shoppa.NetworkCallManager.NetworkCallManager;
import com.shoppa.R;
import com.shoppa.ui.Login.LoginFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataManager {

    public static MaterialButton dashboardBuyerCount;
    public static String listedSellerPName = "", listedSellerPImg = "", listedSellerPId = "", listedSellerId = "", listedSellerIsFrom = "";
    public static ArrayList<String> listedSellerArrayList;
    public static String catProListId = "", catProListName = "", catProListIsFrom = "";
    public static String subCatId = "", subCatIsFrom = "";
    public static ArrayList<HomeCategoryModel> mAllCatArrayList;
    public static String userStatus = "";
    public static int userProgress = 0;
    public static String userType = "";
    public static String noDataImg = "https://cdn.dribbble.com/users/888330/screenshots/2653750/empty_data_set.png";
    public static String userId = "";
    public static String userAddress = "";
    public static String userCountryName = "";
    public static String userCountryId = "";
    public static String userStateName = "";
    public static String userStateId = "";
    public static String userCityName = "";
    public static String userCityId = "";
    public static RecyclerView mChatSenRecyclerView, mChatInboxRecyclerView;
    public static FrameLayout mChatSentFrameLayout, mChatInboxFrameLayout;
    public static String isFrom = "";
    public static CookieManager mCookieManager = null;
    public static Context mContext;
    public static MaterialTextView mClassNameTxt, mCourseNameTxt;
    public static ImageView mStoreIconTopBar;
    public static String notiCount = "";
    public static MaterialButton mNotification;
    public static Dialog dialog;
    public static LoginFragment loginFragment;
    public static String supplierInfoId = "", supplierInfoIsFrom = "";
    public static ArrayList<BuyerModel> homeBuyerArrayList;
    public static String buyerUnit = "";
    private static DataManager mInstance = null;

    private DataManager() {
        init();
    }

    public static void storeIcon(String Opr) {
        if (Opr.matches("show")) {
            mStoreIconTopBar.setVisibility(View.VISIBLE);
        } else if (Opr.matches("hide")) {
            mStoreIconTopBar.setVisibility(View.GONE);
        }
    }

    public static float generateRandNumber() {

        float min = 3.5f, max = 5.0f;
        Random r = new SecureRandom();
        float random = (float) (min + r.nextDouble() * (max - min));


        return random;
    }

    public static String calculatePercent(String amount, String percent) {
        double res = (Double.parseDouble(amount) * Double.parseDouble(percent)) / 100.0f;
        res = Double.parseDouble(amount) - res;
        return String.valueOf(res);
    }

    public static void checkUserStatus() {
        userProgress = 0;
        boolean isAllDataAvailable = true;
        if (UserDataModel.getmInstance().getSl_number().matches("")) {
            isAllDataAvailable = false;
            userProgress = userProgress - 5;
        } else {
            userProgress = userProgress + 5;
        }
        if (UserDataModel.getmInstance().getSeller_website().matches("")) {
            isAllDataAvailable = false;
            userProgress = userProgress - 5;
        } else {
            userProgress = userProgress + 5;
        }
        if (UserDataModel.getmInstance().getSeller_address().matches("")) {
            isAllDataAvailable = false;
            userProgress = userProgress - 5;
        } else {
            userProgress = userProgress + 5;
        }
        if (UserDataModel.getmInstance().getSeller_company().matches("")) {
            isAllDataAvailable = false;
            userProgress = userProgress - 5;
        } else {
            userProgress = userProgress + 5;
        }
        if (UserDataModel.getmInstance().getSeller_type().matches("")) {
            isAllDataAvailable = false;
            userProgress = userProgress - 5;
        } else {
            userProgress = userProgress + 5;
        }
        if (UserDataModel.getmInstance().getSeller_whatsapp().matches("")) {
            isAllDataAvailable = false;
            userProgress = userProgress - 5;
        } else {
            userProgress = userProgress + 5;
        }
        if (UserDataModel.getmInstance().getSeller_skype().matches("")) {
            isAllDataAvailable = false;
            userProgress = userProgress - 5;
        } else {
            userProgress = userProgress + 5;
        }
        if (UserDataModel.getmInstance().getSeller_image().matches("")) {
            isAllDataAvailable = false;
            userProgress = userProgress - 5;
        } else {
            userProgress = userProgress + 5;
        }
        if (UserDataModel.getmInstance().getSeller_email().matches("")) {
            isAllDataAvailable = false;
            userProgress = userProgress - 5;
        } else {
            userProgress = userProgress + 5;
        }
        if (UserDataModel.getmInstance().getSeller_name().matches("")) {
            isAllDataAvailable = false;
            userProgress = userProgress - 5;
        } else {
            userProgress = userProgress + 5;
        }
        if (UserDataModel.getmInstance().getSeller_plan().matches("")) {
            userType = "GUEST";
        } else {
            userType = "MEMBER";
            userProgress = 50;
        }

        Log.i("UserType", "checkUserStatus: Usertype - " + UserDataModel.getmInstance().getSeller_plan());

        if (isAllDataAvailable) {
            userStatus = "ACTIVE";
        } else {
            userStatus = "INACTIVE";
        }
        if (userProgress < 0) {
            userProgress = 0;
        }

        if (userType.matches("MEMBER") && userStatus.matches("ACTIVE")) {
            userProgress = 100;
        }

    }


    public static void refreshUser(Context context) {
        JSONObject params = new JSONObject();
        try {
            params.put("seller_email", UserDataModel.getmInstance().getSeller_email());
            params.put("seller_mpassword", UserDataModel.getmInstance().getSeller_password());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("emailLogin", "refreshUser: Userparams - " + params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.getEmailPassUrl(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("emailLogin", "onResponse: main response - " + response);

                        Toast.makeText(context, "User Updated", Toast.LENGTH_LONG).show();

                        UserDataModel.getmInstance().setId(response.optString("id"));
                        UserDataModel.getmInstance().setSl_number(response.optString("sl_number"));
                        UserDataModel.getmInstance().setSeller_password(response.optString("seller_mpassword"));
                        UserDataModel.getmInstance().setSeller_name(response.optString("seller_name"));
                        UserDataModel.getmInstance().setSeller_image(response.optString("seller_image"));
                        UserDataModel.getmInstance().setSeller_email(response.optString("seller_email"));
                        UserDataModel.getmInstance().setSeller_usertype(response.optString("seller_usertype"));
                        UserDataModel.getmInstance().setSeller_userstatus(response.optString("seller_userstatus"));
                        UserDataModel.getmInstance().setSeller_company(response.optString("seller_company"));
                        UserDataModel.getmInstance().setSeller_address(response.optString("seller_address"));
                        UserDataModel.getmInstance().setSeller_website(response.optString("seller_website"));
                        UserDataModel.getmInstance().setSeller_post_id(response.optString("seller_post_id"));
                        UserDataModel.getmInstance().setSeller_whatsapp(response.optString("seller_whatsapp"));
                        UserDataModel.getmInstance().setSeller_skype(response.optString("seller_skype"));
                        UserDataModel.getmInstance().setSeller_type(response.optString("seller_type"));
                        UserDataModel.getmInstance().setSeller_plan(response.optString("seller_plan"));

                        /*sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("auth", "present");
                        editor.putString("email", response.optString("seller_email"));
                        editor.putString("password", response.optString("seller_mpassword"));
                        editor.apply();*/

                        /*isError = false;
                        isUserAvailable = true;*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
//                isError = true;

                DataManager.checkVolleyError(error, context);

            }
        });
        NetworkCallManager.getInstance(context).getRequestQueue().getCache().clear();
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }

    public static void htmlConverter(MaterialTextView textView, String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(source));
        }
    }

    public static String setCommas(String amount) {
        Log.i("amount", "setCommas: ammount = " + amount);
        StringBuilder stringBuilder = new StringBuilder();
        int current = 0;
        int gap = 3;
        for (int i = amount.length() - 1; i >= 0; i--) {
            if (current == gap) {
                stringBuilder.append("");
                gap = 2;
                current = 0;
            }
            stringBuilder.append(amount.charAt(i));
            current++;
        }
        Log.i("setComma", "setCommas: value" + stringBuilder);

        StringBuilder newStringBuilder = new StringBuilder();

        for (int i = stringBuilder.length() - 1; i >= 0; i--) {
            newStringBuilder.append(stringBuilder.charAt(i));
        }

        return String.valueOf(newStringBuilder);
    }

    public static void checkVolleyError(VolleyError error, Context context) {
        String message = null;
        if (error instanceof NetworkError) {

            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }

        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
    }

    public static void updateClassCourse(String className, String courseName) {
        mClassNameTxt.setText(className);
        mCourseNameTxt.setText(courseName);
    }

    public static DataManager getInstance(Context context) {
        if (mInstance == null) {
            mContext = context;
            mInstance = new DataManager();
        }
        return mInstance;
    }

    public static void setDescriptionLinks(Context context, String descriptionText, MaterialTextView mDescriptionTxt) {
        mDescriptionTxt.setText(descriptionText);
        Linkify.addLinks(mDescriptionTxt, Linkify.WEB_URLS);
        mDescriptionTxt.setLinkTextColor(ContextCompat.getColor(context,
                R.color.linkColor));
    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static boolean isValidGSTNo(String str) {
        String regex = "^[0-9]{2}[A-Z]{5}[0-9]{4}"
                + "[A-Z]{1}[1-9A-Z]{1}"
                + "Z[0-9A-Z]{1}$";

        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String ConvertToTime(String timeStr) {

        int timeInt = Integer.parseInt(timeStr);
        int p1 = timeInt % 60;
        int p2 = timeInt / 60;
        int p3 = p2 % 60;
        p2 = p2 / 60;

        /*String inputPattern = "H-m-s";
        String outputPattern = "HH-mm-ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;
        String source = String.valueOf(p2 + ":" + p3 + ":" + p1);

        try {
            date = inputFormat.parse(source);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */
        if (!timeStr.matches("null")) {
            int intStr = Integer.parseInt(timeStr);

            String hms = String.format("%02d:%02d:%02d", p2, p3, p1);
            return hms;
        } else {
            return timeStr;
        }
    }

    public static ArrayList<String> separateIds(String numbers) {

        ArrayList<String> mIdList = new ArrayList<>();

        String[] elements = numbers.split(","); // step two : convert String array to list of String
        List<String> fixedLenghtList = Arrays.asList(elements); // step three : copy fixed list to an ArrayList
        ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);


        Log.i("idList", "separateIds: idList - " + listOfString);
        return listOfString;

    }

    public static ArrayList<String> separateNumbers(String numbers) {
        ArrayList<String> mNumbersList = new ArrayList<>();

        String editableNumber = numbers;
        editableNumber = editableNumber.replaceAll("\\s", "");

        for (int i = 0; i < editableNumber.length(); i++) {
            if (String.valueOf(numbers.charAt(i)).matches(",")) {
                mNumbersList.add(editableNumber.substring(0, i));
                editableNumber = editableNumber.substring(i + 1);
                i = 0;
            }
        }

        mNumbersList.add(editableNumber);

        return mNumbersList;
    }

    public static String convertToTime(String timeStr) {

        int timeInt = Integer.parseInt(timeStr);
        int p1 = timeInt % 60;
        int p2 = timeInt / 60;
        int p3 = p2 % 60;
        p2 = p2 / 60;

        String inputPattern = "H-m-s";
        String outputPattern = "HH-mm-ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;
        String source = p2 + ":" + p3 + ":" + p1;

        /*try {
            date = inputFormat.parse(source);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        return source;
    }

    public static String newConvertToTime(long millis) {

        String hms = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return hms;
    }

    public static String convertYoutubeDateTime(String timeStr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 19; i++) {
            builder.append(timeStr.charAt(i));
        }
        String time = String.valueOf(builder);
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Log.i("timeStr", "convertYoutubeDateTime: "+timeStr);;
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
            LocalDate date = null;
            date = LocalDate.parse(time, inputFormatter);
            String formattedDate = outputFormatter.format(date);
            System.out.println(formattedDate);
            return formattedDate;
        }
        return "";*/

        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "hh:mm:ssa dd-MM-yyyy";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String DateFormatCheck(String dateStr) {
        String inputPattern = "yyyy-mm-dd";
        String outputPattern = "dd-mm-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateStr);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void setSearchFilter(final SearchView searchView, final MaterialTextView materialTextView, Activity activity, Context context) {
        SearchManager searchManager =
                (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        searchView.setFocusable(true);// searchEmployee is null
        searchView.setFocusableInTouchMode(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialTextView.setVisibility(View.GONE);
                searchView.setMaxWidth(Integer.MAX_VALUE);
            }
        });
        //

    }

    public static void loadPDF(String url) {

    }

    public static boolean checkExpire(String expire) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

        try {
            Date strDate = sdf.parse(expire);
            return new Date().after(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void showDialog(Context context, Activity activity, Dialog dialog, String action) {

        if (action.matches("open")) {
            dialog.setContentView(R.layout.dialog_loading_data);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.setCancelable(false);

            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int height = (int) (metrics.heightPixels * 1);
            int width = (int) (metrics.widthPixels * 1);

            dialog.getWindow().setLayout(width, height);

            dialog.show();

        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 100);
        }
    }

/*
    public static void refreshUser(Context context) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApiManager.refreshUser() + UserDataModel.getmInstance().getId(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("refreshUser", "onResponse: " + response.toString());
                        if (response.optBoolean("success")) {
                            try {
                                JSONObject data = response.getJSONObject("data");
                                UserDataModel.getmInstance().setCourseId(data.optString("course_id"));
                                UserDataModel.getmInstance().setClassId(data.optString("class_id"));
                                UserDataModel.getmInstance().setClassName(data.optString("class_name"));
                                UserDataModel.getmInstance().setCourseName(data.optString("course_name"));
                                UserDataModel.getmInstance().setCoins(data.optString("totalPoints"));
                                DataManager.updateClassCourse(UserDataModel.getmInstance().getClassName(), UserDataModel.getmInstance().getCourseName());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-api-key", UserDataModel.getmInstance().getToken());
                return params;
            }
        };
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }
*/

/*
    public static void refreshNotification(Context context) {

        JSONObject params = new JSONObject();
        try {
            params.putOpt("user_id", UserDataModel.getmInstance().getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("user_id", UserDataModel.getmInstance().getId());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiManager.refreshNotification(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("refreshnoti", "onResponse: " + response.toString());
                        if (response.optBoolean("success")) {
                            Log.i("refreshnoti", "onResponse: " + response.toString());
                            try {
                                if (response.getJSONObject("data").optString("count").matches("") || response.getJSONObject("data").optString("count").matches("0") || response.getJSONObject("data").optString("count").matches("null")) {
                                    mNotification.setVisibility(View.GONE);
                                } else {
                                    mNotification.setText(response.getJSONObject("data").optString("count"));
                                    mNotification.setVisibility(View.VISIBLE);
                                }
                                Log.i("notification", "onResponse: count " + response.getJSONObject("data").optString("count"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-api-key", UserDataModel.getmInstance().getToken());
                return params;

            }
        };
        NetworkCallManager.getInstance(context).addToRequestQue(jsonObjectRequest);
    }
*/

    public static void showNoDataDialog(Context context, Activity activity, Dialog dialog, String action) {

        if (action.matches("open")) {
            dialog.setContentView(R.layout.dialog_no_data);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            dialog.setCancelable(true);

            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int height = (int) (metrics.heightPixels * 1);
            int width = (int) (metrics.widthPixels * 1);

            dialog.getWindow().setLayout(width, height);

            dialog.show();

        }

    }

    public static void setAlphaAnimation(View v, String action) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(v, "alpha", 1f, .0f);
        fadeOut.setDuration(200);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v, "alpha", .0f, 1f);
        fadeIn.setDuration(200);

        final AnimatorSet mAnimationSet = new AnimatorSet();

        if (action.matches("fadeIn")) {
            mAnimationSet.play(fadeIn);
        } else if (action.matches("fadeOut")) {
            mAnimationSet.play(fadeOut);
        }

        /*mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimationSet.start();
            }
        });*/
        mAnimationSet.start();
    }

    public static void clearCache(Context context) {
        try {

            File dir = context.getCacheDir();
            deleteDir(dir);
            Toast.makeText(context, "Cache Cleared", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public void init() {
        mCookieManager = new CookieManager();
        CookieHandler.setDefault(mCookieManager);
    }


/*
    public static void logout(Context context, Activity activity){
        SharedPreferences sharedPreferences = Objects.requireNonNull(context).getSharedPreferences("MyPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("auth",null);
        editor.apply();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        activity.finish();
    }
*/

}
