package a295.sushell.com.imageuploadtofirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DisplayTypeActivity extends AppCompatActivity {
    RequestQueue queue;
    String url;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_images);
        textView= (TextView)findViewById(R.id.foodType);
        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        url= bundle.get("url").toString();
        queue=Volley.newRequestQueue(this);
        getRecognizedImage();
    }
    private JSONObject getRecognizedImage=null;
    private JSONObject getFoodInformation=null;
    int userId;
    int servings;
    String mealTypeId;
    String foodItemText;

    // Instantiate the cache
//    Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
//
//    // Set up the network to use HttpURLConnection as the HTTP client.
//    Network network = new BasicNetwork(new HurlStack());

    public static String serverlurl= "http://35.197.90.224:3001/api/v1/classify_image";




    public void getRecognizedImage(){

        JsonObjectRequest jsObjRequest;
        try{
           getRecognizedImage = new JSONObject();
           getRecognizedImage.put("url", url);

        }
        catch (JSONException e){
            Log.e("ProfileStaticFragment", e.getMessage());
        }


            jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, serverlurl, getRecognizedImage, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                textView.setText(response.getString("message"));
                                getFoodInformation();

                            }
                            catch (JSONException e){
                                Log.e("ProfileStaticFragment", e.getMessage());
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("error" + error);

                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    //super.getHeaders();
                    HashMap<String, String> headers = new HashMap<String, String>();
                    //headers.put("Content-Type", "application/json");
                    headers.put("Content-type", "application/json");
                    return headers;
                }

            };


            queue.add(jsObjRequest);


    }

    private void getFoodInformation() {
        JsonObjectRequest jsObjRequest;
        try{
            getFoodInformation = new JSONObject();
            getFoodInformation.put("userId", userId);
            getFoodInformation.put("servings", servings);
            getFoodInformation.put("mealTypeId", mealTypeId);
            getFoodInformation.put("foodItemText", foodItemText);

        }
        catch (JSONException e){
            Log.e("ProfileStaticFragment", e.getMessage());
        }




    }


}
