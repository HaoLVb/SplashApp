package com.example.levanhao.splashapp.manage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.levanhao.splashapp.StaticVarriable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by HaoLV on 11/9/2017.
 */

public class RequestManager {
    private RequestQueue requestQueue;

    public RequestManager(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void login(String phonenumber, String password, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/login";
        Map<String, String> params = new HashMap<>();
        params.put("password", password);
        params.put("phonenumber", phonenumber);
        volleyRequest(url, params, StaticVarriable.LOGIN, handler);
    }

    public void logout(String token, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/logout";
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        volleyRequest(url, params, StaticVarriable.LOGOUT, handler);
    }

    public void getListCategoryProduct(int categoryId, int index, int count, String token, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/get_list_products";
        Map<String, String> params = new HashMap<>();
        if (token != null) {
            params.put("token", token);
        }
        params.put("category_id", String.valueOf(categoryId));
        params.put("index", String.valueOf(index));
        params.put("count", String.valueOf(count));
        volleyRequest(url, params, StaticVarriable.GET_LIST_CATEGORY_PROODUCT, handler);
    }

    public void getProductInfo(int id, String token, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/get_products";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        if (token != null) {
            params.put("token", token);
        }
        volleyRequest(url, params, StaticVarriable.GET_PRODUCT_INFO, handler);
    }

    public void getComment(int productId, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/get_comment_products";
        Map<String, String> params = new HashMap<>();
        params.put("product_id", String.valueOf(productId));
        volleyRequest(url, params, StaticVarriable.GET_COMMENT, handler);
    }

    public void setComment(String token, int productId, String comment, int index, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/set_comment_products";
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("product_id", String.valueOf(productId));
        params.put("comment", comment);
        params.put("index", String.valueOf(index));
        volleyRequest(url, params, StaticVarriable.SET_COMMENT, handler);
    }

    public void getUserInfo(String token, int userId, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/get_user_info";
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        if (userId != 0) {
            params.put("user_id", String.valueOf(userId));
        }
        volleyRequest(url, params, StaticVarriable.GET_USER_INFO, handler);
    }

    public void getMyLike(String token, String index, String count, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/get_my_likes";
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("index", index);
        params.put("count", count);
        volleyRequest(url, params, StaticVarriable.GET_MY_LIKE, handler);
    }

    public void getFollowing(int userId, String token, String index, String count, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/get_list_following";
        Map<String, String> params = new HashMap<>();
        if (token != null) {
            params.put("token", token);
        }
        params.put("user_id", String.valueOf(userId));
        params.put("index", index);
        params.put("count", count);
        volleyRequest(url, params, StaticVarriable.GET_LIST_FOLLOWING, handler);
    }

    public void getFollowed(int userId, String token, String index, String count, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/get_list_followed";
        Map<String, String> params = new HashMap<>();
        if (token != null) {
            params.put("token", token);
        }
        params.put("user_id", String.valueOf(userId));
        params.put("index", index);
        params.put("count", count);
        volleyRequest(url, params, StaticVarriable.GET_LIST_FOLLOWED, handler);
    }

    public void getUserListing(int userId, String token, String index, String count, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/get_user_listings";
        Map<String, String> params = new HashMap<>();
        if (token != null) {
            params.put("token", token);
            params.put("user_id", String.valueOf(userId));
        } else {
            if (userId != 0) {
                params.put("user_id", String.valueOf(userId));
            }
        }
        params.put("index", index);
        params.put("count", count);
        volleyRequest(url, params, StaticVarriable.GET_USER_LISTING, handler);
    }

    public void likeProduct(String token, int productId, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/like_products";
        Map<String, String> params = new HashMap<>();
        if (token != null) {
            params.put("token", token);
        }

        params.put("product_id", String.valueOf(productId));

        volleyRequest(url, params, StaticVarriable.LIKE_PRODUCT, handler);
    }

    public void sendReport(String token, int productId, String subject, String details, Handler handler) {
        String url = StaticVarriable.DOMAIN + "/report_products";
        Map<String, String> params = new HashMap<>();
        if (token != null) {
            params.put("token", token);
        }
        params.put("subject", subject);
        params.put("details", details);
        params.put("product_id", String.valueOf(productId));

        volleyRequest(url, params, StaticVarriable.SEND_REPORT, handler);
    }

    public void getSize(Handler handler) {
        String url = StaticVarriable.DOMAIN + "/get_list_sizes";
        Map<String, String> params = new HashMap<>();
        volleyRequest(url, params, StaticVarriable.GET_SIZE, handler);
    }

    public void getBrand(Handler handler) {
        String url = StaticVarriable.DOMAIN + "/get_list_brands";
        Map<String, String> params = new HashMap<>();
        volleyRequest(url, params, StaticVarriable.GET_BRAND, handler);
    }


    void volleyRequest(String url, Map<String, String> params, final int message, final Handler handler) {
        Log.e("request", url + params.toString());
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    processMessage(jsonObject, message, handler);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = handler.obtainMessage();
                message.what = StaticVarriable.ERROR_INTERNET;
                handler.sendMessage(message);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        requestQueue.add(sr);
    }

    private void processMessage(JSONObject jsonObject, int message, Handler handler) {
        try {
            int code = jsonObject.getInt("code");
            JSONObject result;
            switch (message) {
            case StaticVarriable.LOGIN:
                Message loginMessage = handler.obtainMessage();
                loginMessage.what = code;
                if (code == 1000) {
                    result = jsonObject.getJSONObject("data");
                    loginMessage.obj = result;
                    loginMessage.what = StaticVarriable.LOGIN_SUCCESSFUL;
                    Log.e("333", "1");
                } else {
                    loginMessage.what = StaticVarriable.LOGIN_FAIL;
                }
                handler.sendMessage(loginMessage);
                break;
//            case StaticVarriable.GET_LIST_PRODUCT:
//                Message listProductMessage = handler.obtainMessage();
//                listProductMessage.what = code;
//                if (code == 1000) {
//                    result = jsonObject.getJSONObject("data");
//                    listProductMessage.obj = result;
//                    listProductMessage.what = StaticVarriable.GET_LIST_PRODUCT;
//                }
//                handler.sendMessage(listProductMessage);
//                break;

            case StaticVarriable.GET_LIST_CATEGORY_PROODUCT:
                Message categotyMessage = handler.obtainMessage();
                categotyMessage.what = code;
                if (code == 1000) {
                    result = jsonObject.getJSONObject("data");
                    categotyMessage.obj = result;
                    categotyMessage.what = StaticVarriable.GET_LIST_CATEGORY_PROODUCT;
                }
                handler.sendMessage(categotyMessage);
                break;
            case StaticVarriable.GET_COMMENT:
                Message commentMessage = handler.obtainMessage();
                commentMessage.what = code;
                if (code == 1000) {
                    JSONArray jsonArray;
                    jsonArray = jsonObject.getJSONArray("data");
                    commentMessage.obj = jsonArray;
                    commentMessage.what = StaticVarriable.GET_COMMENT;
                }
                handler.sendMessage(commentMessage);
                break;
            case StaticVarriable.GET_PRODUCT_INFO:
                Message inforProductMessage = handler.obtainMessage();
                inforProductMessage.what = code;
                if (code == 1000) {
                    result = jsonObject.getJSONObject("data");
                    inforProductMessage.obj = result;
                    inforProductMessage.what = StaticVarriable.GET_PRODUCT_INFO;
                }
                handler.sendMessage(inforProductMessage);
                break;
            case StaticVarriable.GET_USER_INFO:
                Message userInfoMessage = handler.obtainMessage();
                userInfoMessage.what = code;
                if (code == 1000) {
                    result = jsonObject.getJSONObject("data");
                    userInfoMessage.obj = result;
                    userInfoMessage.what = StaticVarriable.GET_USER_INFO;
                }
                handler.sendMessage(userInfoMessage);
                break;
            case StaticVarriable.LOGOUT:
                Message logoutMessage = handler.obtainMessage();
                logoutMessage.what = code;
                if (code == 1000) {
                    logoutMessage.what = StaticVarriable.LOGOUT;
                }
                handler.sendMessage(logoutMessage);
                break;
            case StaticVarriable.GET_MY_LIKE:
                Message likeMessage = handler.obtainMessage();
                likeMessage.what = code;
                if (code == 1000) {
                    JSONArray jsonArray;
                    jsonArray = jsonObject.getJSONArray("data");
                    likeMessage.obj = jsonArray;
                    likeMessage.what = StaticVarriable.GET_MY_LIKE;
                    Log.e("comment1", jsonObject.getJSONArray("data").toString());

                }
                handler.sendMessage(likeMessage);
                break;
            case StaticVarriable.GET_LIST_FOLLOWING:
                Message follwingMessage = handler.obtainMessage();
                follwingMessage.what = code;
                if (code == 1000) {
                    JSONArray jsonArray;
                    jsonArray = jsonObject.getJSONArray("data");
                    follwingMessage.obj = jsonArray;
                    follwingMessage.what = StaticVarriable.GET_LIST_FOLLOWING;

                }
                handler.sendMessage(follwingMessage);
                break;
            case StaticVarriable.GET_LIST_FOLLOWED:
                Message follwedMessage = handler.obtainMessage();
                follwedMessage.what = code;
                if (code == 1000) {
                    JSONArray jsonArray;
                    jsonArray = jsonObject.getJSONArray("data");
                    follwedMessage.obj = jsonArray;
                    follwedMessage.what = StaticVarriable.GET_LIST_FOLLOWED;
                }
                handler.sendMessage(follwedMessage);
                break;
            case StaticVarriable.GET_USER_LISTING:
                Message listMessage = handler.obtainMessage();
                listMessage.what = code;
                if (code == 1000) {
                    JSONArray jsonArray;
                    jsonArray = jsonObject.getJSONArray("data");
                    listMessage.obj = jsonArray;
                    listMessage.what = StaticVarriable.GET_USER_LISTING;
                }
                handler.sendMessage(listMessage);
                break;

            case StaticVarriable.SET_COMMENT:
                Message setCommentMessage = handler.obtainMessage();
                setCommentMessage.what = code;
                if (code == 1000) {
                    JSONArray jsonArray;
                    jsonArray = jsonObject.getJSONArray("data");
                    setCommentMessage.obj = jsonArray;
                    setCommentMessage.what = StaticVarriable.SET_COMMENT;
                }
                handler.sendMessage(setCommentMessage);
                break;
            case StaticVarriable.LIKE_PRODUCT:
                Message likeProductMessage = handler.obtainMessage();
                likeProductMessage.what = code;
                if (code == 1000) {
                    result = jsonObject.getJSONObject("data");
                    likeProductMessage.obj = result;
                    likeProductMessage.what = StaticVarriable.LIKE_PRODUCT;
                }
                handler.sendMessage(likeProductMessage);
                break;
            case StaticVarriable.SEND_REPORT:
                Message reportMessage = handler.obtainMessage();
                reportMessage.what = code;
                if (code == 1000) {
                    reportMessage.what = StaticVarriable.SEND_REPORT;
                }
                handler.sendMessage(reportMessage);
                break;
            case StaticVarriable.GET_SIZE:
                Message sizeMessage = handler.obtainMessage();
                sizeMessage.what = code;
                if (code == 1000) {
                    JSONArray jsonArray;
                    jsonArray = jsonObject.getJSONArray("data");
                    sizeMessage.obj = jsonArray;
                    sizeMessage.what = StaticVarriable.GET_SIZE;
                }
                handler.sendMessage(sizeMessage);
                break;

            case StaticVarriable.GET_BRAND:
                Message brandMessage = handler.obtainMessage();
                brandMessage.what = code;
                if (code == 1000) {
                    JSONArray jsonArray;
                    jsonArray = jsonObject.getJSONArray("data");
                    brandMessage.obj = jsonArray;
                    brandMessage.what = StaticVarriable.GET_BRAND;
                }
                handler.sendMessage(brandMessage);
                break;

            default:
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
