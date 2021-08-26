package com.shoppa.NetworkCallManager;

import android.content.Context;
import android.util.Log;
import java.net.CookieHandler;
import java.net.CookieManager;

public class ApiManager {
    public static Context mContext = null;
    public static CookieManager mCookieManager = null;
  public static ApiManager mInstance = null;
    public static final String serverUrl = "https://www.shoppa.in/OWS/api";

   public ApiManager() {
        init();
    }

    public static ApiManager getInstance(Context context) {
        mContext = context;
        ApiManager apiManager = new ApiManager();
        mInstance = apiManager;
        return apiManager;
    }

    public static String getCategoryData() {
        return "https://www.shoppa.in/OWS/api/product/read_cat.php";
    }

    public static String getSubCatData(String id) {
        return "https://www.shoppa.in/OWS/api/subcat_from_cat/read_one_subcat.php?id=" + id;
    }

    public static String getCatProductData(String id) {
        Log.i("catProductID", "getCatProductData: id = " + id);
        return "https://www.shoppa.in/OWS/api/product_from_subcat/read_one_product.php?id=" + id;
    }

    public static String getProductDetailsData(String id) {
        return "https://www.shoppa.in/OWS/api/product_data/read_one_pro.php?id=" + id;
    }

    public static String getSellerDetails(String id) {
        return "https://www.shoppa.in/OWS/api/seller/read_one_pro.php?id=" + id;
    }

    public static String getSellerProduct(String id) {
        return "https://www.shoppa.in/OWS/api/seller_products/read_seller_pro.php?id=" + id;
    }

    public static String getOTPUrl() {
        return "https://www.shoppa.in/OWS/api/login/update.php";
    }

    public static String getUserDetail() {
        return "https://www.shoppa.in/OWS/api/login/get_data_from_otp.php";
    }

    public static String getEmailPassUrl() {
        return "https://www.shoppa.in/OWS/api/login/email_pass.php";
    }

    public static String getSignUpUrl() {
        return "https://www.shoppa.in/OWS/api/signup/create.php";
    }

    public static String getSellersDataUrl() {
        return "https://www.shoppa.in/OWS/api/premium_sellers/read_all_pro.php";
    }

    public static String getPackageUrl() {
        return "https://www.shoppa.in/OWS/api/packages/read_all_pro.php";
    }

    public static String getBuyLeadURL() {
        return "https://www.shoppa.in/OWS/api/latest_buy_leads/read_all_pro.php";
    }

    public static String getCountryUrl() {
        return "https://www.shoppa.in/OWS/api2/country/read_all_country.php";
    }

    public static String getStateUrl(String id) {
        return "https://www.shoppa.in/OWS/api2/country/read_one_state.php? id=" + id;
    }

    public static String getCityUrl(String id) {
        return "https://www.shoppa.in/OWS/api2/country/read_one_city.php?id=" + id;
    }

    public static String getBuyerEnquiryURL() {
        return "https://www.shoppa.in/OWS/api/buyer_enquiry/enquiry.php";
    }

    public static String getBuyerRequestURL() {
        return "https://www.shoppa.in/OWS/api/post_your_req/post.php";
    }

    public static String getPasswordChangeURL() {
        return "https://www.shoppa.in/OWS/api2/pass_update/update.php";
    }

    public static String getProfileImageURL() {
        return "https://www.shoppa.in/OWS/api2/seller_details_update/update_seller_img.php";
    }

    public static String getMessengerURL() {
        return "https://www.shoppa.in/OWS/api2/seller_details_update/update_seller_messengers.php";
    }

    public static String getEditDetailURL() {
        return "https://www.shoppa.in/OWS/api2/seller_details_update/update_seller_details.php";
    }

    public static String getBuyerRequest(String number) {
        return "https://www.shoppa.in/OWS/api2/get_buyers_from_phone/read_one_buyer.php?phone=" + number;
    }

    public static String getReportURL() {
        return "https://www.shoppa.in/OWS/api2/post_complaint/create.php";
    }

    public static String getProductIdUrl() {
        return "https://www.shoppa.in/OWS/api2/sellers_product_data/read_one_cat.php";
    }

    public static String getReports(String sl_number) {
        return "https://www.shoppa.in/OWS/api2/get_complaints/read_one_comp.php?phone=" + sl_number;
    }

    public static String getTradeAlerts() {
        return "https://www.shoppa.in/OWS/api2/get_discount_data/read_all_discount.php";
    }

    public static String getAllProducts() {
        return "https://www.shoppa.in/OWS/api/product_data/read_all_pro.php";
    }

    public static String getSellerId(String ProductName) {
        return "https://www.shoppa.in/OWS/api/product_from_subcat/read_seller_id.php?pro_name=" + ProductName;
    }

    public static String getSinglePackageUrl(String packageName) {
        return "https://www.shoppa.in/OWS/api/packages/read_one_pro.php?id=" + packageName;
    }

    public static String getSellerProductApi2(String id) {
        return "https://www.shoppa.in/OWS/api2/buyers_from_sellers_pro/read_seller_pro_id.php?id=" + id;
    }

    public static String getProductBuyer(String product) {
        return "https://www.shoppa.in/OWS/api2/buyers_from_sellers_pro/read_buyers.php?pro=" + product;
    }

    public void init() {
        CookieManager cookieManager = new CookieManager();
        mCookieManager = cookieManager;
        CookieHandler.setDefault(cookieManager);
    }
}